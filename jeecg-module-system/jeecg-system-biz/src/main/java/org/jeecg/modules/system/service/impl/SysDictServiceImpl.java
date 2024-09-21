package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.config.TenantContext;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.DataBaseConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.ResourceUtil;
import org.jeecg.common.system.vo.*;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.mybatis.MybatisPlusSaasConfig;
import org.jeecg.modules.system.entity.SysDict;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.entity.SysDictTableEnum;
import org.jeecg.modules.system.mapper.SysDictItemMapper;
import org.jeecg.modules.system.mapper.SysDictMapper;
import org.jeecg.modules.system.model.DuplicateCheckVo;
import org.jeecg.modules.system.model.TreeSelectModel;
import org.jeecg.modules.system.security.DictQueryBlackListHandler;
import org.jeecg.modules.system.service.ISysDictService;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@Service
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

	@Autowired
	private SysDictMapper sysDictMapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private DictQueryBlackListHandler dictQueryBlackListHandler;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 严格模式，严格模式下只允许SysDictTableEnum.java中定义的表名作为动态字典数据源
	 *
	 * @author Yoko
	 * @since 2024/8/12 上午8:50
	 */
	@Value(value = "${jeecg.dict.strict:false}")
	private Boolean strict;

	@Override
	public boolean duplicateCheckData(DuplicateCheckVo duplicateCheckVo) {
		Long count = null;

		// 1.针对采用 ${}写法的表名和字段进行转义和check
		String table = SqlInjectionUtil.getSqlInjectTableName(duplicateCheckVo.getTableName());
		String fieldName = SqlInjectionUtil.getSqlInjectField(duplicateCheckVo.getFieldName());
		duplicateCheckVo.setTableName(table);
		duplicateCheckVo.setFieldName(fieldName);

		// 2.SQL注入check（只限制非法串改数据库）
		//关联表字典（举例：sys_user,realname,id）
		SqlInjectionUtil.filterContent(table);
		SqlInjectionUtil.filterContent(fieldName);

		// 3.表字典黑名单check
		String checkSql = table + SymbolConstant.COMMA + fieldName + SymbolConstant.COMMA;
		dictQueryBlackListHandler.isPass(checkSql);

		// 4.执行SQL 查询是否存在值
		try{
			if (StringUtils.isNotBlank(duplicateCheckVo.getDataId())) {
				// [1].编辑页面校验
				count = sysDictMapper.duplicateCheckCountSql(duplicateCheckVo);
			} else {
				// [2].添加页面校验
				count = sysDictMapper.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
			}
		}catch(MyBatisSystemException e){
			log.error(e.getMessage(), e);
			String errorCause = "查询异常,请检查唯一校验的配置！";
			throw new JeecgBootException(errorCause);
		}

		// 4.返回结果
		if (count == null || count == 0) {
			// 该值可用
			return true;
		} else {
			// 该值不可用
			log.info("该值不可用，系统中已存在！");
			return false;
		}
	}


	/**
	 * 通过查询指定code 获取字典
	 * @param code
	 * @return
	 */
	@Override
	// @Cacheable(value = CacheConstant.SYS_DICT_CACHE,key = "#code", unless = "#result == null ")
	public List<DictModel> queryDictItemsByCode(String code) {
		// 需要转换系统变量的不走缓存、带查询条件的也不走缓存（table_name,text,id,condition）
		if (code.contains("#{") || code.split(SymbolConstant.COMMA).length == 4) {
			log.debug("无缓存dictCache的时候调用这里！");
			return this.getDictItems(code);
		}
		// 查询字段缓存
		Object r = redisUtil.get(CacheConstant.SYS_DICT_CACHE + ":" + code);
		if (null != r) {
			return (List<DictModel>) r;
		}
		log.debug("无缓存dictCache的时候调用这里！");
		List<DictModel> dictItems = this.getDictItems(code);
		if (null == dictItems) {
			return null;
		}
		// 缓存
		redisUtil.set(CacheConstant.SYS_DICT_CACHE + ":"  + code, dictItems);
		return dictItems;
	}

	@Override
	// @Cacheable(value = CacheConstant.SYS_ENABLE_DICT_CACHE,key = "#code", unless = "#result == null ")
	public List<DictModel> queryEnableDictItemsByCode(String code) {
		// 需要转换系统变量的不走缓存、带查询条件的也不走缓存（table_name,text,id,condition）
		if (code.contains("#{") || code.split(SymbolConstant.COMMA).length == 4) {
			throw new RuntimeException("字典查询失败，不支持表字典传入！");
		}
		// 查询字段缓存
		Object r = redisUtil.get(CacheConstant.SYS_ENABLE_DICT_CACHE + ":" + code);
		if (null != r) {
			return (List<DictModel>) r;
		}
		log.debug("无缓存dictCache的时候调用这里！");
		List<DictModel> dictItems = sysDictMapper.queryEnableDictItemsByCode(code);
		if (null == dictItems) {
			return null;
		}
		// 缓存
		redisUtil.set(CacheConstant.SYS_ENABLE_DICT_CACHE + ":"  + code, dictItems);
		return dictItems;
	}

	@Override
	public Map<String, List<DictModel>> queryDictItemsByCodeList(List<String> dictCodeList) {
		List<DictModelMany> list = sysDictMapper.queryDictItemsByCodeList(dictCodeList);
		Map<String, List<DictModel>> dictMap = new HashMap(5);
		for (DictModelMany dict : list) {
			List<DictModel> dictItemList = dictMap.computeIfAbsent(dict.getDictCode(), i -> new ArrayList<>());
			dict.setDictCode(null);
			dictItemList.add(new DictModel(dict.getValue(), dict.getText()));
		}
		return dictMap;
	}

	@Override
	public Map<String, List<DictModel>> queryAllDictItems() {
		Map<String, List<DictModel>> res = new HashMap(5);
		LambdaQueryWrapper<SysDict> sysDictQueryWrapper = new LambdaQueryWrapper<SysDict>();
		//------------------------------------------------------------------------------------------------
		//是否开启系统管理模块的多租户数据隔离【SAAS多租户模式】
		if(MybatisPlusSaasConfig.OPEN_SYSTEM_TENANT_CONTROL){
			sysDictQueryWrapper.eq(SysDict::getTenantId, oConvertUtils.getInt(TenantContext.getTenant(), 0))
					.or().eq(SysDict::getTenantId,0);
		}
		//------------------------------------------------------------------------------------------------

		List<SysDict> ls = sysDictMapper.selectList(sysDictQueryWrapper);
		LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<SysDictItem>();
		queryWrapper.eq(SysDictItem::getStatus, 1);
		queryWrapper.orderByAsc(SysDictItem::getSortOrder);
		List<SysDictItem> sysDictItemList = sysDictItemMapper.selectList(queryWrapper);

		for (SysDict d : ls) {
			List<DictModel> dictModelList = sysDictItemList.stream().filter(s -> d.getId().equals(s.getDictId())).map(item -> {
				DictModel dictModel = new DictModel();
				dictModel.setText(item.getItemText());
				dictModel.setValue(item.getItemValue());
				return dictModel;
			}).collect(Collectors.toList());
			res.put(d.getDictCode(), dictModelList);
		}
		//update-begin-author:taoyan date:2022-7-8 for: 系统字典数据应该包括自定义的java类-枚举
		Map<String, List<DictModel>> enumRes = ResourceUtil.getEnumDictData();
		res.putAll(enumRes);
		//update-end-author:taoyan date:2022-7-8 for: 系统字典数据应该包括自定义的java类-枚举
		log.debug("-------登录加载系统字典-----" + res.toString());
		return res;
	}

	/**
	 * 通过查询指定code 获取字典值text
	 * @param code
	 * @param key
	 * @return
	 */

	@Override
	// @Cacheable(value = CacheConstant.SYS_DICT_CACHE,key = "#code+':'+#key", unless = "#result == null ")
	public String queryDictTextByKey(String code, String key) {
		// 需要转换系统变量的不走缓存、带查询条件的也不走缓存（table_name,text,id,condition）
		if (code.contains("#{") || code.split(SymbolConstant.COMMA).length == 4) {
			throw new RuntimeException("字典翻译失败，不支持表字典传入！");
		}
		// 查询字段缓存
		Object r = redisUtil.get(CacheConstant.SYS_DICT_CACHE + ":" + code + ":" + key);
		if (null != r) {
			return (String) r;
		}
		log.debug("无缓存dictCache的时候调用这里！");
		String text = sysDictMapper.queryDictTextByKey(code, key);
		if (null == text) {
			return null;
		}
		// 缓存
		redisUtil.set(CacheConstant.SYS_DICT_CACHE + ":" + code + ":" + key, text);
		return text;
	}

	@Override
	public String queryDictKeyByText(String code, String key) {
		return sysDictMapper.queryDictKeyByText(code, key);
	}

	@Override
	public Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys) {
		List<DictModelMany> list = sysDictMapper.queryManyDictByKeys(dictCodeList, keys);
		Map<String, List<DictModel>> dictMap = new HashMap(5);
		for (DictModelMany dict : list) {
			List<DictModel> dictItemList = dictMap.computeIfAbsent(dict.getDictCode(), i -> new ArrayList<>());
			dictItemList.add(new DictModel(dict.getValue(), dict.getText()));
		}
		//update-begin-author:taoyan date:2022-7-8 for: 系统字典数据应该包括自定义的java类-枚举
		Map<String, List<DictModel>> enumRes = ResourceUtil.queryManyDictByKeys(dictCodeList, keys);
		dictMap.putAll(enumRes);
		//update-end-author:taoyan date:2022-7-8 for: 系统字典数据应该包括自定义的java类-枚举
		return dictMap;
	}

	/**
	 * 通过查询指定table的 text code 获取字典
	 * dictTableCache采用redis缓存有效期10分钟
	 * @param tableFilterSql
	 * @param text
	 * @param code
	 * @return
	 */
	@Override
	@Deprecated
	public List<DictModel> queryTableDictItemsByCode(String tableFilterSql, String text, String code) {
		log.debug("无缓存dictTableList的时候调用这里！");

		// 1.表字典黑名单check
		String str = tableFilterSql+","+text+","+code;
		if(!dictQueryBlackListHandler.isPass(str)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}

		// 2.分割SQL获取表名和条件
		String table = null;
		String filterSql = null;
		if(tableFilterSql.toLowerCase().indexOf(DataBaseConstant.SQL_WHERE)>0){
			String[] arr = tableFilterSql.split(" (?i)where ");
			table = arr[0];
			filterSql = oConvertUtils.getString(arr[1], null);
		}else{
			table = tableFilterSql;
		}

		// 3.SQL注入check
		SqlInjectionUtil.filterContent(table, text, code);
		SqlInjectionUtil.specialFilterContentForDictSql(filterSql);

		// 4.针对采用 ${}写法的表名和字段进行转义和check
		table = SqlInjectionUtil.getSqlInjectTableName(table);
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);

		// 严格模式下，启用表字典查询限制
		if (strict) {
			// 限制字典表枚举
			Optional<SysDictTableEnum> opt = SysDictTableEnum.of(table);
			if (opt.isPresent()) {
				return opt.get().getDictModels(text, code, null, filterSql);
			}
			throw new JeecgBootException("不支持的字典table策略：" + table);
		}

		//return sysDictMapper.queryTableDictItemsByCode(tableFilterSql,text,code);
		return sysDictMapper.queryTableDictWithFilter(table,text,code,filterSql);
	}

	@Override
	public List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql) {
		log.debug("无缓存dictTableList的时候调用这里！");

		// 1.SQL注入校验（只限制非法串改数据库）
		SqlInjectionUtil.specialFilterContentForDictSql(table);
		SqlInjectionUtil.filterContent(text);
		SqlInjectionUtil.filterContent(code);
		SqlInjectionUtil.specialFilterContentForDictSql(filterSql);

		// 2.表字典黑名单 Check
		String str = table+","+text+","+code;
		if(!dictQueryBlackListHandler.isPass(str)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}

		// 3.针对采用 ${}写法的表名和字段进行转义和check
		table = SqlInjectionUtil.getSqlInjectTableName(table);
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);

		// 严格模式下，启用表字典查询限制
		if (strict) {
			// 限制字典表枚举
			Optional<SysDictTableEnum> opt = SysDictTableEnum.of(table);
			if (opt.isPresent()) {
				return opt.get().getDictModels(text, code, null, filterSql);
			}
			throw new JeecgBootException("不支持的字典table策略：" + table);
		}

		return sysDictMapper.queryTableDictWithFilter(table,text,code,filterSql);
	}

	/**
	 * 通过查询指定table的 text code 获取字典值text
	 * dictTableCache采用redis缓存有效期10分钟
	 * @param table
	 * @param text
	 * @param code
	 * @param key
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstant.SYS_DICT_TABLE_CACHE, unless = "#result == null ")
	public String queryTableDictTextByKey(String table,String text,String code, String key) {
		log.debug("无缓存dictTable的时候调用这里！");

		// 1.表字典黑名单check
		String str = table+","+text+","+code;
		if(!dictQueryBlackListHandler.isPass(str)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}
		// 2.sql注入check
		SqlInjectionUtil.filterContent(table, text, code, key);

		// 3.针对采用 ${}写法的表名和字段进行转义和check
		table = SqlInjectionUtil.getSqlInjectTableName(table);
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);

		List<String> keys = Arrays.asList(key.split(","));
		// 严格模式下，启用表字典查询限制
		if (strict) {
			// 限制字典表枚举
			Optional<SysDictTableEnum> opt = SysDictTableEnum.of(table);
			if (opt.isPresent()) {
				return opt.get().getDictModels(text, code, keys, null).get(0).getText();
			}
			throw new JeecgBootException("不支持的字典table策略：" + table);
		}

		List<DictModel> dictModeList = sysDictMapper.queryTableDictByKeysAndFilterSql(table, text, code, null, keys);
		if(CollectionUtils.isEmpty(dictModeList)){
			return null;
		}else{
			return dictModeList.get(0).getText();
		}

		//此方法删除（20230902）
		//return sysDictMapper.queryTableDictTextByKey(table,text,code,key);
	}

	@Override
	public List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> codeValues) {
		// 1.表字典黑名单check
		String str = table+","+text+","+code;
		if(!dictQueryBlackListHandler.isPass(str)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}

		// 2.分割SQL获取表名和条件
		String filterSql = null;
		if(table.toLowerCase().indexOf(DataBaseConstant.SQL_WHERE)>0){
			String[] arr = table.split(" (?i)where ");
			table = arr[0];
			filterSql = arr[1];
		}

		// 3.SQL注入check
		SqlInjectionUtil.filterContent(table, text, code);
		SqlInjectionUtil.specialFilterContentForDictSql(filterSql);

		// 4.针对采用 ${}写法的表名和字段进行转义和check
		table = SqlInjectionUtil.getSqlInjectTableName(table);
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);

		// 严格模式下，启用表字典查询限制
		if (strict) {
			// 限制字典表枚举
			Optional<SysDictTableEnum> opt = SysDictTableEnum.of(table);
			if (opt.isPresent()) {
				return opt.get().getDictModels(text, code, codeValues, filterSql);
			}
			throw new JeecgBootException("不支持的字典table策略：" + table);
		}

		return sysDictMapper.queryTableDictByKeysAndFilterSql(table, text, code, filterSql, codeValues);
		//update-end-author:taoyan date:20220113 for: @dict注解支持 dicttable 设置where条件
	}

	@Override
	public List<String> queryTableDictByKeys(String table, String text, String code, String keys) {
		String str = table+","+text+","+code;
		if(!dictQueryBlackListHandler.isPass(str)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}

		return this.queryTableDictByKeys(table, text, code, keys, true);
	}

	/**
	 * 通过查询指定table的 text code 获取字典，包含text和value
	 * dictTableCache采用redis缓存有效期10分钟
	 * @param table
	 * @param text
	 * @param code
	 * @param codeValuesStr (逗号分隔)
	 * @param delNotExist 是否移除不存在的项，默认为true，设为false如果某个key不存在数据库中，则直接返回key本身
	 * @return
	 */
	@Override
	public List<String> queryTableDictByKeys(String table, String text, String code, String codeValuesStr, boolean delNotExist) {
		if(oConvertUtils.isEmpty(codeValuesStr)){
			return null;
		}

		//1.分割sql获取表名 和 条件sql
		String filterSql = null;
		if(table.toLowerCase().contains("where")){
            try {
                String[] arr = table.split(" (?i)where ");
                table = arr[0];
                filterSql = arr[1];
            } catch (Exception e) {
                throw new RuntimeException("字典查询::queryTableDictByKeys::table参数格式错误: " + table, e.getCause());
            }
        }

		// 2.SQL注入check
		SqlInjectionUtil.filterContent(table, text, code);
		SqlInjectionUtil.specialFilterContentForDictSql(filterSql);

		// 3.表字典黑名单check
		String str = table+","+text+","+code;
		if(!dictQueryBlackListHandler.isPass(str)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}

		// 4.针对采用 ${}写法的表名和字段进行转义和check
		table = SqlInjectionUtil.getSqlInjectTableName(table);
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);

		//字典条件值
		String[] codeValues = codeValuesStr.split(",");

		// 5.查询字典数据
		List<DictModel> dicts;
		// 严格模式下，启用表字典查询限制
		if (strict) {
			// 限制字典表枚举
			Optional<SysDictTableEnum> opt = SysDictTableEnum.of(table);
			if (opt.isPresent()) {
				dicts = opt.get().getDictModels(text, code, Arrays.asList(codeValues), filterSql);
			}
			throw new JeecgBootException("不支持的字典table策略：" + table);
		} else {
			dicts = sysDictMapper.queryTableDictByKeysAndFilterSql(SqlInjectionUtil.getSqlInjectTableName(table),
					SqlInjectionUtil.getSqlInjectField(text), SqlInjectionUtil.getSqlInjectField(code), filterSql, Arrays.asList(codeValues));
		}

		List<String> texts = new ArrayList<>(dicts.size());
		// 6.查询出来的顺序可能是乱的，需要排个序
		for (String conditionalVal : codeValues) {
			List<DictModel> res = dicts.stream().filter(i -> conditionalVal.equals(i.getValue())).collect(Collectors.toList());
			if (res.size() > 0) {
				texts.add(res.get(0).getText());
			} else if (!delNotExist) {
				texts.add(conditionalVal);
			}
		}
		return texts;
	}

	/**
	 * 根据字典类型id删除关联表中其对应的数据
	 */
	@Override
	public boolean deleteByDictId(SysDict sysDict) {
		sysDict.setDelFlag(CommonConstant.DEL_FLAG_1);
		return  this.updateById(sysDict);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer saveMain(SysDict sysDict, List<SysDictItem> sysDictItemList) {
		int insert=0;
		try{
			insert = sysDictMapper.insert(sysDict);
			if (sysDictItemList != null) {
				for (SysDictItem entity : sysDictItemList) {
					//update-begin---author:wangshuai ---date:20220211  for：[JTC-1168]如果字典项值为空，则字典项忽略导入------------
					if(oConvertUtils.isEmpty(entity.getItemValue())){
						return -1;
					}
					//update-end---author:wangshuai ---date:20220211  for：[JTC-1168]如果字典项值为空，则字典项忽略导入------------
					entity.setDictId(sysDict.getId());
					entity.setStatus(1);
					sysDictItemMapper.insert(entity);
				}
			}
		}catch(Exception e){
			return insert;
		}
		return insert;
	}

	@Override
	public List<DictModel> queryAllDepartBackDictModel() {
		return baseMapper.queryAllDepartBackDictModel();
	}

	@Override
	public List<DictModel> queryAllUserBackDictModel() {
		return baseMapper.queryAllUserBackDictModel();
	}

	//	@Override
	//	public List<DictModel> queryTableDictItems(String table, String text, String code, String keyword) {
	//		return baseMapper.queryTableDictItems(table, text, code, "%"+keyword+"%");
	//	}

	@Override
	public List<DictModel> queryLittleTableDictItems(String tableSql, String text, String code, String condition, String keyword, int pageSize) {
		Page<DictModel> page = new Page<DictModel>(1, pageSize);
		page.setSearchCount(false);

		//为了防止sql（jeecg提供了防注入的方法，可以在拼接 SQL 语句时自动对参数进行转义，避免SQL注入攻击）
		// 1. 针对采用 ${}写法的表名和字段进行转义和check
		String table = SqlInjectionUtil.getSqlInjectTableName(CommonUtils.getTableNameByTableSql(tableSql));
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);

		// 2. 查询条件SQL (获取条件sql方法含sql注入校验)
		String filterSql = getFilterSql(tableSql, text, code, condition, keyword);

		// 3. 返回表字典数据
		IPage<DictModel> pageList;
		// 严格模式下，启用表字典查询限制
		if (strict) {
			// 限制字典表枚举
			Optional<SysDictTableEnum> opt = SysDictTableEnum.of(table);
			if (opt.isPresent()) {
				pageList = opt.get().getDictModelsPage(text, code, null, filterSql, page);
			}
			throw new JeecgBootException("不支持的字典table策略：" + table);
		} else {
			pageList = baseMapper.queryPageTableDictWithFilter(page, table, text, code, filterSql);
		}
		return pageList.getRecords();
	}

	/**
	 * 获取条件语句 (下拉搜索组件 支持传入排序信息 查询排序)
	 *
	 * @param text
	 * @param code
	 * @param condition
	 * @param keyword
	 * @return
	 */
	private String getFilterSql(String tableSql, String text, String code, String condition, String keyword){
		String filterSql = "";
		String keywordSql = null;
		String sqlWhere = "where ";

		//【JTC-631】判断如果 table 携带了 where 条件，那么就使用 and 查询，防止报错
		if (tableSql.toLowerCase().contains(sqlWhere)) {
			sqlWhere = CommonUtils.getFilterSqlByTableSql(tableSql) + " and ";
		}

		// 下拉搜索组件 支持传入排序信息 查询排序
		String orderField = "", orderType = "";
		if (oConvertUtils.isNotEmpty(keyword)) {
			// 关键字里面如果写入了 排序信息 xxxxx[orderby:create_time,desc]
			String orderKey = "[orderby";
			if (keyword.indexOf(orderKey) >= 0 && keyword.endsWith("]")) {
				String orderInfo = keyword.substring(keyword.indexOf(orderKey) + orderKey.length() + 1, keyword.length() - 1);
				keyword = keyword.substring(0, keyword.indexOf(orderKey));
				String[] orderInfoArray = orderInfo.split(SymbolConstant.COMMA);
				orderField = orderInfoArray[0];
				orderType = orderInfoArray[1];
			}

			if (oConvertUtils.isNotEmpty(keyword)) {
				// 判断是否是多选
				if (keyword.contains(SymbolConstant.COMMA)) {
					//update-begin--author:scott--date:20220105--for：JTC-529【表单设计器】 编辑页面报错，in参数采用双引号导致 ----
					String inKeywords = "'" + String.join("','", keyword.split(",")) + "'";
					//update-end--author:scott--date:20220105--for：JTC-529【表单设计器】 编辑页面报错，in参数采用双引号导致----
					keywordSql = "(" + text + " in (" + inKeywords + ") or " + code + " in (" + inKeywords + "))";
				} else {
					keywordSql = "("+text + " like '%"+keyword+"%' or "+ code + " like '%"+keyword+"%')";
				}
			}
		}

		//下拉搜索组件 支持传入排序信息 查询排序
		if(oConvertUtils.isNotEmpty(condition) && oConvertUtils.isNotEmpty(keywordSql)){
			filterSql+= sqlWhere + condition + " and " + keywordSql;
		}else if(oConvertUtils.isNotEmpty(condition)){
			filterSql+= sqlWhere + condition;
		}else if(oConvertUtils.isNotEmpty(keywordSql)){
			filterSql+= sqlWhere + keywordSql;
		}

		// 增加排序逻辑
		if (oConvertUtils.isNotEmpty(orderField)) {
			filterSql += " order by " + orderField + " " + orderType;
		}

		// 处理返回条件
		// 1.1 返回条件SQL（去掉开头的 where ）
		final String wherePrefix = "(?i)where "; // (?i) 表示不区分大小写
		String filterSqlString = filterSql.trim().replaceAll(wherePrefix, "");
		// 1.2 条件SQL进行漏洞 check
		SqlInjectionUtil.specialFilterContentForDictSql(filterSqlString);
		// 1.3 判断如何返回条件是 order by开头则前面拼上 1=1
		if (oConvertUtils.isNotEmpty(filterSqlString) && filterSqlString.trim().toUpperCase().startsWith("ORDER")) {
			filterSqlString = " 1=1 " + filterSqlString;
		}
		return filterSqlString;
	}


	@Override
	public List<DictModel> queryAllTableDictItems(String table, String text, String code, String condition, String keyword) {
		// 1.获取条件sql
		String filterSql = getFilterSql(table, text, code, condition, keyword);

		// 为了防止sql（jeecg提供了防注入的方法，可以在拼接 SQL 语句时自动对参数进行转义，避免SQL注入攻击）
		// 2.针对采用 ${}写法的表名和字段进行转义和check
		table = SqlInjectionUtil.getSqlInjectTableName(table);
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);

		List<DictModel> ls;

		// 严格模式下，启用表字典查询限制
		if (strict) {
			// 限制字典表枚举
			Optional<SysDictTableEnum> opt = SysDictTableEnum.of(table);
			if (opt.isPresent()) {
				ls = opt.get().getDictModels(text, code, null, filterSql);
			}
			throw new JeecgBootException("不支持的字典table策略：" + table);
		} else {
			ls = baseMapper.queryTableDictWithFilter(table, text, code, filterSql);
		}

		return ls;
	}

	@Override
	public List<TreeSelectModel> queryTreeList(Map<String, String> query, String table, String text, String code, String pidField, String pid, String hasChildField, int converIsLeafVal) {
		//为了防止sql（jeecg提供了防注入的方法，可以在拼接 SQL 语句时自动对参数进行转义，避免SQL注入攻击）
		// 1.针对采用 ${}写法的表名和字段进行转义和check
		table = SqlInjectionUtil.getSqlInjectTableName(table);
		text = SqlInjectionUtil.getSqlInjectField(text);
		code = SqlInjectionUtil.getSqlInjectField(code);
		pidField = SqlInjectionUtil.getSqlInjectField(pidField);
		hasChildField = SqlInjectionUtil.getSqlInjectField(hasChildField);

		// 2.检测最终SQL是否存在SQL注入风险
		String dictCode = table + "," + text + "," + code;
		SqlInjectionUtil.filterContent(dictCode);

		// 3.表字典SQL表名黑名单 Check
		if(!dictQueryBlackListHandler.isPass(dictCode)){
			log.error("Sql异常：{}", dictQueryBlackListHandler.getError());
			return null;
		}
		// 4.检测查询条件是否存在SQL注入
		Map<String, String> queryParams = null;
		if (query != null) {
			queryParams = new HashMap<>(5);
			for (Map.Entry<String, String> searchItem : query.entrySet()) {
				String fieldName = searchItem.getKey();
				queryParams.put(SqlInjectionUtil.getSqlInjectField(fieldName), searchItem.getValue());
			}
		}

		return baseMapper.queryTreeList(queryParams, table, text, code, pidField, pid, hasChildField, converIsLeafVal);
	}

	@Override
	public void deleteOneDictPhysically(String id) {
		this.baseMapper.deleteOneById(id);
		this.sysDictItemMapper.delete(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getDictId,id));
	}

	@Override
	public void updateDictDelFlag(int delFlag, String id) {
		baseMapper.updateDictDelFlag(delFlag,id);
	}

	@Override
	public List<SysDict> queryDeleteList() {
		return baseMapper.queryDeleteList();
	}

	@Override
	public List<DictModel> queryDictTablePageList(DictQuery query, int pageSize, int pageNo) {
		Page page = new Page(pageNo,pageSize,false);

		//为了防止sql（jeecg提供了防注入的方法，可以在拼接 SQL 语句时自动对参数进行转义，避免SQL注入攻击）
		// 1. 针对采用 ${}写法的表名和字段进行转义和check
		String table = SqlInjectionUtil.getSqlInjectTableName(query.getTable());
		String text = SqlInjectionUtil.getSqlInjectTableName(query.getText());
		String code = SqlInjectionUtil.getSqlInjectTableName(query.getCode());
		query.setCode(table);
		query.setTable(text);
		query.setText(code);

		// 2.表字典黑名单check
		String dictCode = table+","+text+","+code;
		if(!dictQueryBlackListHandler.isPass(dictCode)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}

		// 3.SQL注入check
		SqlInjectionUtil.filterContent(dictCode);

		Page<DictModel> pageList = baseMapper.queryDictTablePageList(page, query);
		return pageList.getRecords();
	}

	@Override
	public List<DictModel> getDictItems(String dictCode) {
		List<DictModel> ls;
		if (dictCode.contains(SymbolConstant.COMMA)) {
			//关联表字典（举例：sys_user,realname,id）
			String[] params = dictCode.split(",");
			if (params.length < 3) {
				// 字典Code格式不正确
				return null;
			}

			if (params.length == 4) {
				// 转换系统变量
				if (params[3].contains("#{")) {
					params[3] = QueryGenerator.convertSystemVariables(params[3]);
				}
				ls = this.queryTableDictItemsByCodeAndFilter(params[0], params[1], params[2], params[3]);
			} else if (params.length == 3) {
				ls = this.queryTableDictItemsByCode(params[0], params[1], params[2]);
			} else {
				// 字典Code格式不正确
				return null;
			}
		} else {
			//字典表
			ls = this.sysDictMapper.queryDictItemsByCode(dictCode);
		}
		//update-begin-author:taoyan date:2022-8-30 for: 字典获取可以获取枚举类的数据
		if (ls == null || ls.size() == 0) {
			Map<String, List<DictModel>> map = ResourceUtil.getEnumDictData();
			if (map.containsKey(dictCode)) {
				return map.get(dictCode);
			}
		}
		//update-end-author:taoyan date:2022-8-30 for: 字典获取可以获取枚举类的数据
		return ls;
	}

	@Override
	public List<DictModel> loadDict(String dictCode, String keyword, Integer pageSize) {
		// 1.表字典黑名单check
		if(!dictQueryBlackListHandler.isPass(dictCode)){
			log.error(dictQueryBlackListHandler.getError());
			return null;
		}

		// 2.字典SQL注入风险check
		SqlInjectionUtil.specialFilterContentForDictSql(dictCode);

		if (dictCode.contains(SymbolConstant.COMMA)) {
			//update-begin-author:taoyan date:20210329 for: 下拉搜索不支持表名后加查询条件
			String[] params = dictCode.split(",");
			String condition = null;
			if (params.length != 3 && params.length != 4) {
				// 字典Code格式不正确
				return null;
			} else if (params.length == 4) {
				condition = params[3];
				// update-begin-author:taoyan date:20220314 for: online表单下拉搜索框表字典配置#{sys_org_code}报错 #3500
				if(condition.indexOf(SymbolConstant.SYS_VAR_PREFIX)>=0){
					condition =  QueryGenerator.getSqlRuleValue(condition);
				}
				// update-end-author:taoyan date:20220314 for: online表单下拉搜索框表字典配置#{sys_org_code}报错 #3500
			}

			// 字典Code格式不正确 [表名为空]
			if(oConvertUtils.isEmpty(params[0])){
				return null;
			}
			List<DictModel> ls;
			if (pageSize != null) {
				ls = this.queryLittleTableDictItems(params[0], params[1], params[2], condition, keyword, pageSize);
			} else {
				ls = this.queryAllTableDictItems(params[0], params[1], params[2], condition, keyword);
			}
			//update-end-author:taoyan date:20210329 for: 下拉搜索不支持表名后加查询条件
			return ls;
		} else {
			// 字典Code格式不正确
			return null;
		}
	}

	/**
	 * 查询字典表数据
	 *
	 * @author Yoko
	 * @since 2024/8/12 下午12:15
	 * @param queryForm 查询表单
	 * @return java.util.List<org.jeecg.common.system.vo.DictTreeModel>
	 */
	@Override
	public List<DictTreeModel> treeDictItems(DictTreeQuery queryForm) {
		Assert.hasText(queryForm.getDictCode(), "字典Code不能为空");
		LambdaQueryWrapper<SysDict> dw = Wrappers.lambdaQuery(SysDict.class).select(Arrays.asList(SysDict::getId, SysDict::getDictCode)).eq(SysDict::getDictCode, queryForm.getDictCode());
		SysDict sysDict = sysDictMapper.selectOne(dw);
		if (sysDict == null) {
			return Collections.emptyList();
		}
		LambdaQueryWrapper<SysDictItem> wrapper = Wrappers.lambdaQuery(new SysDictItem())
				.eq(SysDictItem::getDictId, sysDict.getId())
				.likeRight(StringUtils.isNotBlank(queryForm.getStructCode()), SysDictItem::getItemValue, queryForm.getStructCode())
				.orderByAsc(SysDictItem::getSortOrder);
		List<DictTreeModel> treeModels = sysDictItemMapper.selectList(wrapper).stream().map(item -> {
			DictTreeModel model = new DictTreeModel();
			model.setId(item.getId());
			model.setParentId(item.getParentId());
			model.setItemText(item.getItemText());
			model.setItemValue(item.getItemValue());
			model.setSortOrder(item.getSortOrder());
			return model;
		}).collect(Collectors.toList());
		return buildTree(treeModels, queryForm);
	}

	public List<DictTreeModel> buildTree(List<DictTreeModel> dictItems, DictTreeQuery queryForm) {
		List<DictTreeModel> roots = dictItems.stream()
				.filter(item -> null == item.getParentId() || Objects.equals(item.getParentId(), CommonConstant.SYS_DICT_ITEM_ROOT_FLAG) || Objects.equals(item.getItemValue(), queryForm.getStructCode()))
				.collect(Collectors.toList());
		roots.forEach(root -> buildTreeRecursively(root, dictItems, new HashSet<>()));
		return roots;
	}

	private void buildTreeRecursively(DictTreeModel parent, List<DictTreeModel> dictItems, Set<String> addedValues) {
		for (DictTreeModel dictItem : dictItems) {
			// 父子id关联
			boolean flag = !Objects.equals(dictItem.getParentId(), CommonConstant.SYS_DICT_ITEM_ROOT_FLAG) && Objects.equals(parent.getId(), dictItem.getParentId());
			// 实体码结构关联
			// flag = flag || dictItem.getItemValue().startsWith(parent.getItemValue() + "-");
			if (flag && addedValues.add(dictItem.getItemValue())) {
				if (parent.getChildren() == null) {
					parent.setChildren(new ArrayList<>());
				}
				if (!isNodeAlreadyAdded(parent, dictItem)) {
					parent.getChildren().add(dictItem);
					buildTreeRecursively(dictItem, dictItems, addedValues);
				}
			}
		}
	}

	private boolean isNodeAlreadyAdded(DictTreeModel parent, DictTreeModel node) {
		if (parent.getChildren() != null) {
			for (DictTreeModel child : parent.getChildren()) {
				if (child.getItemValue().equals(node.getItemValue())) {
					return true;
				}
			}
		}
		return false;
	}



}
