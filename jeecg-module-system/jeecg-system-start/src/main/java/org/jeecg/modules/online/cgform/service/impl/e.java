package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.TemplateException;
import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.enums.CgformEnum;
import org.jeecg.common.util.*;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.cgform.entity.*;
import org.jeecg.modules.online.cgform.mapper.*;
import org.jeecg.modules.online.cgform.model.OnlGenerateModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOne;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOneToMany;
import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
import org.jeecgframework.codegenerate.generate.pojo.TableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.MainTableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.SubTableVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/* compiled from: OnlCgformHeadServiceImpl.java */
@Service("onlCgformHeadServiceImpl")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/service/impl/e.class */
public class e extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {
    private static final Logger a = LoggerFactory.getLogger(e.class);
    @Autowired
    private IOnlCgformFieldService fieldService;
    @Autowired
    private IOnlCgformIndexService indexService;
    @Autowired
    private OnlCgformEnhanceJsMapper onlCgformEnhanceJsMapper;
    @Autowired
    private OnlCgformButtonMapper onlCgformButtonMapper;
    @Autowired
    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;
    @Autowired
    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;
    @Autowired
    private org.jeecg.modules.online.config.a.b dataBaseConfig;
    @Autowired
    private IOnlAuthPageService onlAuthPageService;


    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public Result<?> addAll(org.jeecg.modules.online.cgform.model.a model) {
        String replace = UUID.randomUUID().toString().replace("-", "");
        OnlCgformHead head = model.getHead();
        List<OnlCgformField> fields = model.getFields();
        List<OnlCgformIndex> indexs = model.getIndexs();
        head.setId(replace);
        for (int i = 0; i < fields.size(); i++) {
            OnlCgformField onlCgformField = fields.get(i);
            onlCgformField.setId(null);
            onlCgformField.setCgformHeadId(replace);
            if (onlCgformField.getOrderNum() == null) {
                onlCgformField.setOrderNum(Integer.valueOf(i));
            }
            a(onlCgformField);
        }
        for (OnlCgformIndex onlCgformIndex : indexs) {
            onlCgformIndex.setId(null);
            onlCgformIndex.setCgformHeadId(replace);
        }
        head.setIsDbSynch("N");
        head.setQueryMode(org.jeecg.modules.online.cgform.d.b.single);
        head.setTableVersion(1);
        head.setCopyType(0);
        if (head.getTableType().intValue() == 3 && head.getTabOrderNum() == null) {
            head.setTabOrderNum(1);
        }
        super.save(head);
        this.fieldService.saveBatch(fields);
        this.indexService.saveBatch(indexs);
        a(head, fields);
        return Result.ok("添加成功");
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public Result<?> editAll(org.jeecg.modules.online.cgform.model.a model) {
        OnlCgformHead head = model.getHead();
        OnlCgformHead onlCgformHead = (OnlCgformHead) super.getById(head.getId());
        if (onlCgformHead == null) {
            return Result.error("未找到对应实体");
        }
        String isDbSynch = onlCgformHead.getIsDbSynch();
        if (org.jeecg.modules.online.cgform.d.b.a(onlCgformHead, head)) {
            isDbSynch = "N";
        }
        Integer tableVersion = onlCgformHead.getTableVersion();
        if (tableVersion == null) {
            tableVersion = 1;
        }
        head.setTableVersion(Integer.valueOf(tableVersion.intValue() + 1));
        List<OnlCgformField> fields = model.getFields();
        List<OnlCgformIndex> indexs = model.getIndexs();
        ArrayList<OnlCgformField> arrayList = new ArrayList();
        ArrayList<OnlCgformField> arrayList2 = new ArrayList();
        for (OnlCgformField onlCgformField : fields) {
            String valueOf = String.valueOf(onlCgformField.getId());
            a(onlCgformField);
            if (valueOf.length() >0&&valueOf!="null") {
                arrayList2.add(onlCgformField);
            } else if (!"_pk".equals(valueOf)) {
                onlCgformField.setId(null);
                onlCgformField.setCgformHeadId(head.getId());
                arrayList.add(onlCgformField);
            }
        }
        if (arrayList.size() > 0) {
            isDbSynch = "N";
        }
        int i = 0;
        for (OnlCgformField onlCgformField2 : arrayList2) {
            OnlCgformField onlCgformField3 = (OnlCgformField) this.fieldService.getById(onlCgformField2.getId());
            if (org.jeecg.modules.online.cgform.d.b.a(onlCgformField3, onlCgformField2)) {
                isDbSynch = "N";
            }
            if ((onlCgformField3.getOrderNum() == null ? 0 : onlCgformField3.getOrderNum().intValue()) > i) {
                i = onlCgformField3.getOrderNum().intValue();
            }
            this.fieldService.updateById(onlCgformField2);
        }
        for (OnlCgformField onlCgformField4 : arrayList) {
            if (onlCgformField4.getOrderNum() == null) {
                i++;
                onlCgformField4.setOrderNum(Integer.valueOf(i));
            }
            this.fieldService.save(onlCgformField4);
        }
        List<OnlCgformIndex> cgformIndexsByCgformId = this.indexService.getCgformIndexsByCgformId(head.getId());
        ArrayList arrayList3 = new ArrayList();
        ArrayList<OnlCgformIndex> arrayList4 = new ArrayList();
        for (OnlCgformIndex onlCgformIndex : indexs) {
            if (String.valueOf(onlCgformIndex.getId()).length() >0&&onlCgformIndex.getId()!=null) {
                arrayList4.add(onlCgformIndex);
            } else {
                onlCgformIndex.setId(null);
                onlCgformIndex.setIsDbSynch("N");
                onlCgformIndex.setDelFlag(CommonConstant.DEL_FLAG_0);
                onlCgformIndex.setCgformHeadId(head.getId());
                arrayList3.add(onlCgformIndex);
            }
        }
        for (OnlCgformIndex onlCgformIndex2 : cgformIndexsByCgformId) {
            if (!indexs.stream().anyMatch(onlCgformIndex3 -> {
                return onlCgformIndex2.getId().equals(onlCgformIndex3.getId());
            })) {
                onlCgformIndex2.setDelFlag(CommonConstant.DEL_FLAG_1);
                arrayList4.add(onlCgformIndex2);
                isDbSynch = "N";
            }
        }
        if (arrayList3.size() > 0) {
            isDbSynch = "N";
            this.indexService.saveBatch(arrayList3);
        }
        for (OnlCgformIndex onlCgformIndex4 : arrayList4) {
            if (org.jeecg.modules.online.cgform.d.b.a((OnlCgformIndex) this.indexService.getById(onlCgformIndex4.getId()), onlCgformIndex4)) {
                isDbSynch = "N";
                onlCgformIndex4.setIsDbSynch("N");
            }
            this.indexService.updateById(onlCgformIndex4);
        }
        if (model.getDeleteFieldIds().size() > 0) {
            isDbSynch = "N";
            this.fieldService.removeByIds(model.getDeleteFieldIds());
        }
        head.setIsDbSynch(isDbSynch);
        super.updateById(head);
        a(head, fields);
        b(head, fields);
        return Result.ok("全部修改成功");
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void doDbSynch(String code, String synMethod) throws HibernateException, IOException, TemplateException, SQLException, DBException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(code);
        if (onlCgformHead == null) {
            throw new DBException("实体配置不存在");
        }
        String tableName = onlCgformHead.getTableName();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = this.fieldService.list(lambdaQueryWrapper);
        org.jeecg.modules.online.config.a.a aVar = new org.jeecg.modules.online.config.a.a();
        aVar.setTableName(tableName);
        aVar.setJformPkType(onlCgformHead.getIdType());
        aVar.setJformPkSequence(onlCgformHead.getIdSequence());
        aVar.setContent(onlCgformHead.getTableTxt());
        aVar.setColumns(list);
        aVar.setDbConfig(this.dataBaseConfig);
        if (org.jeecg.modules.online.cgform.d.b.sH.equals(synMethod)) {
            long currentTimeMillis = System.currentTimeMillis();
            boolean booleanValue = org.jeecg.modules.online.config.b.d.a(tableName).booleanValue();
            a.info("==判断表是否存在消耗时间" + (System.currentTimeMillis() - currentTimeMillis) + "毫秒");
            // 如果表存在
            if (booleanValue) {
                org.jeecg.modules.online.config.b.c cVar = new org.jeecg.modules.online.config.b.c();
                for (String str : cVar.generateUpdateSql(aVar)) {
                    if (!oConvertUtils.isEmpty(str) && !oConvertUtils.isEmpty(str.trim())) {
                        ((OnlCgformHeadMapper) this.baseMapper).executeDDL(str);
                    }
                }
                for (OnlCgformIndex onlCgformIndex : this.indexService.list(new LambdaQueryWrapper<OnlCgformIndex>()
                        .eq(OnlCgformIndex::getCgformHeadId, code))) {
                    if ("N".equals(onlCgformIndex.getIsDbSynch()) || CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag())) {
                        if (this.indexService.isExistIndex(cVar.b(onlCgformIndex.getIndexName(), tableName))) {
                            String a2 = cVar.a(onlCgformIndex.getIndexName(), tableName);
                            try {
                                a.info("删除索引 executeDDL:" + a2);
                                ((OnlCgformHeadMapper) this.baseMapper).executeDDL(a2);
                                if (CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag())) {
                                    this.indexService.removeById(onlCgformIndex.getId());
                                }
                            } catch (Exception e) {
                                a.error("删除表【" + tableName + "】索引(" + onlCgformIndex.getIndexName() + ")失败!", e);
                            }
                        } else if (CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag())) {
                            this.indexService.removeById(onlCgformIndex.getId());
                        }
                    }
                }
            } else {
                // 不存在时直接新增
                org.jeecg.modules.online.config.b.c.a(aVar);
            }
        } else if (org.jeecg.modules.online.cgform.d.b.sG.equals(synMethod)) {
            ((OnlCgformHeadMapper) this.baseMapper).executeDDL(org.jeecg.modules.online.config.b.d.getTableHandle().dropTableSQL(tableName));
            org.jeecg.modules.online.config.b.c.a(aVar);
        }
        this.indexService.createIndex(code, org.jeecg.modules.online.config.b.d.getDatabaseType(), tableName);
        onlCgformHead.setIsDbSynch("Y");
        if (onlCgformHead.getTableVersion().intValue() == 1) {
            onlCgformHead.setTableVersion(2);
        }
        updateById(onlCgformHead);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void deleteRecordAndTable(String id) throws DBException, SQLException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(id);
        if (onlCgformHead == null) {
            throw new DBException("实体配置不存在");
        }
        long currentTimeMillis = System.currentTimeMillis();
        boolean booleanValue = org.jeecg.modules.online.config.b.d.a(onlCgformHead.getTableName()).booleanValue();
        a.info("==判断表是否存在消耗时间 " + (System.currentTimeMillis() - currentTimeMillis) + "毫秒");
        if (booleanValue) {
            String dropTableSQL = org.jeecg.modules.online.config.b.d.getTableHandle().dropTableSQL(onlCgformHead.getTableName());
            a.info(" 删除表  executeDDL： " + dropTableSQL);
            ((OnlCgformHeadMapper) this.baseMapper).executeDDL(dropTableSQL);
        }
        ((OnlCgformHeadMapper) this.baseMapper).deleteById(id);
        a(onlCgformHead);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void deleteRecord(String id) throws DBException, SQLException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(id);
        if (onlCgformHead == null) {
            throw new DBException("实体配置不存在");
        }
        ((OnlCgformHeadMapper) this.baseMapper).deleteById(id);
        a(onlCgformHead);
    }

    private void a(OnlCgformHead onlCgformHead) {
        OnlCgformHead onlCgformHead2;
        if (onlCgformHead.getTableType().intValue() == 3) {
            String str = null;
            for (OnlCgformField onlCgformField : this.fieldService.list( new LambdaQueryWrapper<OnlCgformField>()
                    .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()))) {
                str = onlCgformField.getMainTable();
                if (oConvertUtils.isNotEmpty(str)) {
                    break;
                }
            }
            if (oConvertUtils.isNotEmpty(str) && (onlCgformHead2 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne( new LambdaQueryWrapper<OnlCgformHead>()
                    .eq(OnlCgformHead::getTableName, str))) != null) {
                String subTableStr = onlCgformHead2.getSubTableStr();
                if (oConvertUtils.isNotEmpty(subTableStr)) {
                    List list = (List) Arrays.asList(subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)).stream().collect(Collectors.toList());
                    list.remove(onlCgformHead.getTableName());
                    onlCgformHead2.setSubTableStr(String.join(org.jeecg.modules.online.cgform.d.b.DOT_STRING, list));
                    ((OnlCgformHeadMapper) this.baseMapper).updateById(onlCgformHead2);
                }
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<Map<String, Object>> queryListData(String sql) {
        return ((OnlCgformHeadMapper) this.baseMapper).queryList(sql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.insert(onlCgformEnhanceJs);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceJs queryEnhance(String code, String type) {
        return (OnlCgformEnhanceJs) this.onlCgformEnhanceJsMapper.selectOne(new LambdaQueryWrapper<OnlCgformEnhanceJs>()
                .eq(OnlCgformEnhanceJs::getCgJsType, type)
                .eq(OnlCgformEnhanceJs::getCgformHeadId, code));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.updateById(onlCgformEnhanceJs);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceSql queryEnhanceSql(String formId, String buttonCode) {
        return (OnlCgformEnhanceSql) this.onlCgformEnhanceSqlMapper.selectOne( (new LambdaQueryWrapper<OnlCgformEnhanceSql>()
                .eq(OnlCgformEnhanceSql::getCgformHeadId, formId))
                .eq(OnlCgformEnhanceSql::getButtonCode, buttonCode));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgJavaType, onlCgformEnhanceJava.getCgJavaType());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
        return (OnlCgformEnhanceJava) this.onlCgformEnhanceJavaMapper.selectOne(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<OnlCgformButton> queryButtonList(String code, boolean isListButton) {
        LambdaQueryWrapper<OnlCgformButton> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, code);
        if (isListButton) {
            lambdaQueryWrapper.in(OnlCgformButton::getButtonStyle, new Object[]{"link", "button"});
        } else {
            lambdaQueryWrapper.eq(OnlCgformButton::getButtonStyle, org.jeecg.modules.online.cgform.d.b.form);
        }
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<OnlCgformButton> queryButtonList(String code) {
        LambdaQueryWrapper<OnlCgformButton> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, code);
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<String> queryOnlinetables() {
        return ((OnlCgformHeadMapper) this.baseMapper).queryOnlinetables();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void saveDbTable2Online(String tbname) {
        OnlCgformHead onlCgformHead = new OnlCgformHead();
        onlCgformHead.setTableType(1);
        onlCgformHead.setIsCheckbox("Y");
        onlCgformHead.setIsDbSynch("Y");
        onlCgformHead.setIsTree("N");
        onlCgformHead.setIsPage("Y");
        onlCgformHead.setQueryMode("group");
        onlCgformHead.setTableName(tbname.toLowerCase());
        onlCgformHead.setTableTxt(tbname);
        onlCgformHead.setTableVersion(1);
        onlCgformHead.setFormTemplate("1");
        onlCgformHead.setCopyType(0);
        onlCgformHead.setIsDesForm("N");
        onlCgformHead.setScroll(1);
        onlCgformHead.setThemeTemplate(org.jeecg.modules.online.cgform.d.b.sH);
        String generate = UUIDGenerator.generate();
        onlCgformHead.setId(generate);
        ArrayList arrayList = new ArrayList();
        try {
            List b = org.jeecg.codegenerate.fix.postgres.DbReadTableUtil.b(tbname);
            for (int i = 0; i < b.size(); i++) {
                ColumnVo columnVo = (ColumnVo) b.get(i);
                a.info("  columnt : " + columnVo.toString());
                String fieldDbName = columnVo.getFieldDbName();
                OnlCgformField onlCgformField = new OnlCgformField();
                onlCgformField.setCgformHeadId(generate);
                onlCgformField.setDbFieldNameOld(columnVo.getFieldDbName().toLowerCase());
                onlCgformField.setDbFieldName(columnVo.getFieldDbName().toLowerCase());
                if (oConvertUtils.isNotEmpty(columnVo.getFiledComment())) {
                    onlCgformField.setDbFieldTxt(columnVo.getFiledComment());
                } else {
                    onlCgformField.setDbFieldTxt(columnVo.getFieldName());
                }
                onlCgformField.setDbIsKey(0);
                onlCgformField.setIsShowForm(1);
                onlCgformField.setIsQuery(0);
                onlCgformField.setFieldMustInput("0");
                onlCgformField.setIsShowList(1);
                onlCgformField.setOrderNum(Integer.valueOf(i + 1));
                onlCgformField.setQueryMode(org.jeecg.modules.online.cgform.d.b.single);
                onlCgformField.setDbLength(Integer.valueOf(oConvertUtils.getInt(columnVo.getPrecision())));
                onlCgformField.setFieldLength(120);
                onlCgformField.setDbPointLength(Integer.valueOf(oConvertUtils.getInt(columnVo.getScale())));
                onlCgformField.setFieldShowType("text");
                onlCgformField.setDbIsNull(Integer.valueOf("Y".equals(columnVo.getNullable()) ? 1 : 0));
                onlCgformField.setIsReadOnly(0);
                if ("id".equalsIgnoreCase(fieldDbName)) {
                    if (Arrays.asList("java.lang.Integer", "java.lang.Long").contains(columnVo.getFieldType())) {
                        onlCgformHead.setIdType("NATIVE");
                    } else {
                        onlCgformHead.setIdType("UUID");
                    }
                    onlCgformField.setDbIsKey(1);
                    onlCgformField.setIsShowForm(0);
                    onlCgformField.setIsShowList(0);
                    onlCgformField.setIsReadOnly(1);
                }
                if ("create_by".equalsIgnoreCase(fieldDbName) || "create_time".equalsIgnoreCase(fieldDbName) || "update_by".equalsIgnoreCase(fieldDbName) || "update_time".equalsIgnoreCase(fieldDbName) || "sys_org_code".equalsIgnoreCase(fieldDbName)) {
                    onlCgformField.setIsShowForm(0);
                    onlCgformField.setIsShowList(0);
                }
                if ("java.lang.Integer".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("int");
                } else if ("java.lang.Long".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("int");
                } else if ("java.util.Date".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("Date");
                    onlCgformField.setFieldShowType(org.jeecg.modules.online.cgform.d.i.DATE);
                } else if ("java.lang.Double".equalsIgnoreCase(columnVo.getFieldType()) || "java.lang.Float".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("double");
                } else if ("java.math.BigDecimal".equalsIgnoreCase(columnVo.getFieldType()) || "BigDecimal".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("BigDecimal");
                } else if ("byte[]".equalsIgnoreCase(columnVo.getFieldType()) || columnVo.getFieldType().contains("blob")) {
                    onlCgformField.setDbType("Blob");
                    columnVo.setCharmaxLength((String) null);
                } else if ("java.lang.Object".equals(columnVo.getFieldType()) && ("text".equalsIgnoreCase(columnVo.getFieldDbType()) || "ntext".equalsIgnoreCase(columnVo.getFieldDbType()))) {
                    onlCgformField.setDbType(org.jeecg.modules.online.config.b.b.j);
                    onlCgformField.setFieldShowType(org.jeecg.modules.online.config.b.b.k);
                } else if ("java.lang.Object".equals(columnVo.getFieldType()) && "image".equalsIgnoreCase(columnVo.getFieldDbType())) {
                    onlCgformField.setDbType("Blob");
                } else {
                    onlCgformField.setDbType(org.jeecg.modules.online.config.b.b.i);
                }
                if (oConvertUtils.isEmpty(columnVo.getPrecision()) && oConvertUtils.isNotEmpty(columnVo.getCharmaxLength())) {
                    if (Long.valueOf(columnVo.getCharmaxLength()).longValue() >= 3000) {
                        onlCgformField.setDbType(org.jeecg.modules.online.config.b.b.j);
                        onlCgformField.setFieldShowType(org.jeecg.modules.online.config.b.b.k);
                        try {
                            onlCgformField.setDbLength(Integer.valueOf(columnVo.getCharmaxLength()));
                        } catch (Exception e) {
                            a.error(e.getMessage(), e);
                        }
                    } else {
                        onlCgformField.setDbLength(Integer.valueOf(columnVo.getCharmaxLength()));
                    }
                } else {
                    if (oConvertUtils.isNotEmpty(columnVo.getPrecision())) {
                        onlCgformField.setDbLength(Integer.valueOf(columnVo.getPrecision()));
                    } else if (onlCgformField.getDbType().equals("int")) {
                        onlCgformField.setDbLength(10);
                    }
                    if (oConvertUtils.isNotEmpty(columnVo.getScale())) {
                        onlCgformField.setDbPointLength(Integer.valueOf(columnVo.getScale()));
                    }
                }
                if (oConvertUtils.getInt(columnVo.getPrecision()) == -1 && oConvertUtils.getInt(columnVo.getScale()) == 0) {
                    onlCgformField.setDbType(org.jeecg.modules.online.config.b.b.j);
                }
                if ("Blob".equals(onlCgformField.getDbType()) || org.jeecg.modules.online.config.b.b.j.equals(onlCgformField.getDbType()) || "Date".equals(onlCgformField.getDbType())) {
                    onlCgformField.setDbLength(0);
                    onlCgformField.setDbPointLength(0);
                }
                arrayList.add(onlCgformField);
            }
        } catch (Exception e2) {
            a.error(e2.getMessage(), e2);
        }
        if (oConvertUtils.isEmpty(onlCgformHead.getFormCategory())) {
            onlCgformHead.setFormCategory("bdfl_include");
        }
        save(onlCgformHead);
        this.fieldService.saveBatch(arrayList);
    }

    private void a(OnlCgformHead onlCgformHead, List<OnlCgformField> list) {
        OnlCgformHead onlCgformHead2;
        if (onlCgformHead.getTableType().intValue() == 3) {
            OnlCgformHead onlCgformHead3 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectById(onlCgformHead.getId());
            for (int i = 0; i < list.size(); i++) {
                String mainTable = list.get(i).getMainTable();
                if (!oConvertUtils.isEmpty(mainTable) && (onlCgformHead2 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
                        .selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                                .eq(OnlCgformHead::getTableName, mainTable))) != null) {
                    String subTableStr = onlCgformHead2.getSubTableStr();
                    if (oConvertUtils.isEmpty(subTableStr)) {
                        subTableStr = onlCgformHead3.getTableName();
                    } else if (subTableStr.indexOf(onlCgformHead3.getTableName()) < 0) {
                        ArrayList arrayList = new ArrayList(Arrays.asList(subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)));
                        int i2 = 0;
                        while (true) {
                            if (i2 >= arrayList.size()) {
                                break;
                            }
                            OnlCgformHead onlCgformHead4 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
                                    .selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                                            .eq(OnlCgformHead::getTableName, (String) arrayList.get(i2)));
                            if (onlCgformHead4 == null || onlCgformHead3.getTabOrderNum().intValue() >= oConvertUtils.getInt(onlCgformHead4.getTabOrderNum(), 0)) {
                                i2++;
                            } else {
                                arrayList.add(i2, onlCgformHead3.getTableName());
                                break;
                            }
                        }
                        if (arrayList.indexOf(onlCgformHead3.getTableName()) < 0) {
                            arrayList.add(onlCgformHead3.getTableName());
                        }
                        subTableStr = String.join(org.jeecg.modules.online.cgform.d.b.DOT_STRING, arrayList);
                    }
                    onlCgformHead2.setSubTableStr(subTableStr);
                    ((OnlCgformHeadMapper) this.baseMapper).updateById(onlCgformHead2);
                    return;
                }
            }
            return;
        }
        List<OnlCgformHead> selectList = ((OnlCgformHeadMapper) this.baseMapper)
                .selectList((Wrapper) new LambdaQueryWrapper<OnlCgformHead>().like(OnlCgformHead::getSubTableStr, onlCgformHead.getTableName()));
        if (selectList != null && selectList.size() > 0) {
            for (OnlCgformHead onlCgformHead5 : selectList) {
                String subTableStr2 = onlCgformHead5.getSubTableStr();
                if (onlCgformHead5.getSubTableStr().equals(onlCgformHead.getTableName())) {
                    subTableStr2 = "";
                } else if (onlCgformHead5.getSubTableStr().startsWith(onlCgformHead.getTableName() + org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    subTableStr2 = subTableStr2.replace(onlCgformHead.getTableName() + org.jeecg.modules.online.cgform.d.b.DOT_STRING, "");
                } else if (onlCgformHead5.getSubTableStr().endsWith(org.jeecg.modules.online.cgform.d.b.DOT_STRING + onlCgformHead.getTableName())) {
                    subTableStr2 = subTableStr2.replace(org.jeecg.modules.online.cgform.d.b.DOT_STRING + onlCgformHead.getTableName(), "");
                } else if (onlCgformHead5.getSubTableStr().indexOf(org.jeecg.modules.online.cgform.d.b.DOT_STRING + onlCgformHead.getTableName() + org.jeecg.modules.online.cgform.d.b.DOT_STRING) != -1) {
                    subTableStr2 = subTableStr2.replace(org.jeecg.modules.online.cgform.d.b.DOT_STRING + onlCgformHead.getTableName() + org.jeecg.modules.online.cgform.d.b.DOT_STRING, org.jeecg.modules.online.cgform.d.b.DOT_STRING);
                }
                onlCgformHead5.setSubTableStr(subTableStr2);
                ((OnlCgformHeadMapper) this.baseMapper).updateById(onlCgformHead5);
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public String saveManyFormData(String code, JSONObject json, String xAccessToken) throws DBException, BusinessException {
        JSONObject jSONObject=new JSONObject();
        String[] split;
        OnlCgformHead onlCgformHead;
        OnlCgformHead onlCgformHead2 = (OnlCgformHead) getById(code);
        if (onlCgformHead2 == null) {
            throw new DBException("数据库主表ID[" + code + "]不存在");
        }
        executeEnhanceJava("add", org.jeecg.modules.online.cgform.d.b.al, onlCgformHead2, json);
        String f = org.jeecg.modules.online.cgform.d.b.f(onlCgformHead2.getTableName());
        if (onlCgformHead2.getTableType().intValue() == 2) {
            String subTableStr = onlCgformHead2.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    JSONArray jSONArray = json.getJSONArray(str);
                    if (jSONArray != null && jSONArray.size() != 0 && (onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
                            .selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                                    .eq(OnlCgformHead::getTableName, str))) != null) {
                        List<OnlCgformField> list = this.fieldService.list((Wrapper) new LambdaQueryWrapper<OnlCgformField>()
                                .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()));
                        String str2 = "";
                        String str3 = null;
                        for (OnlCgformField onlCgformField : list) {
                            if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                                str2 = onlCgformField.getDbFieldName();
                                String mainField = onlCgformField.getMainField();
                                if (json.get(mainField.toLowerCase()) != null) {
                                    str3 = json.getString(mainField.toLowerCase());
                                }
                                if (json.get(mainField.toUpperCase()) != null) {
                                    str3 = json.getString(mainField.toUpperCase());
                                }
                            }
                        }
                        for (int i = 0; i < jSONArray.size(); i++) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            if (str3 != null) {
                                jSONObject2.put(str2, str3);
                            }
                            this.fieldService.saveFormData(list, str, jSONObject2);
                        }
                    }
                }
            }
        }
        if ("Y".equals(onlCgformHead2.getIsTree())) {
            this.fieldService.saveTreeFormData(code, f, json, onlCgformHead2.getTreeIdField(), onlCgformHead2.getTreeParentIdField());
        } else {
            this.fieldService.saveFormData(code, f, json, false);
        }
        executeEnhanceSql("add", code, json);
        executeEnhanceJava("add", "end", onlCgformHead2, json);
        if (oConvertUtils.isNotEmpty(json.get(org.jeecg.modules.online.cgform.d.b.sE)) || oConvertUtils.isNotEmpty(json.get(org.jeecg.modules.online.cgform.d.b.sE.toUpperCase()))) {
            try {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
                httpHeaders.set("Accept", "application/json;charset=UTF-8");
                httpHeaders.set("X-Access-Token", xAccessToken);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("flowCode", "onl_" + onlCgformHead2.getTableName());
                jSONObject3.put("id", json.get("id"));
                jSONObject3.put("formUrl", "modules/bpm/task/form/OnlineFormDetail");
                jSONObject3.put("formUrlMobile", "online/OnlineDetailForm");
                if (((JSONObject) RestUtil.request(RestUtil.getBaseUrl() + "/act/process/extActProcess/saveMutilProcessDraft", HttpMethod.POST, httpHeaders, (JSONObject) null, jSONObject3, JSONObject.class).getBody()) != null) {
                    a.info("保存流程草稿 dataId : " + jSONObject.getString("result"));
                }
            } catch (Exception e) {
                a.error("保存流程草稿异常, " + e.getMessage(), e);
            }
        }
        return onlCgformHead2.getTableName();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public Map<String, Object> querySubFormData(String table, String mainId) throws DBException {
        new HashMap();
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                .eq(OnlCgformHead::getTableName, table));
        if (onlCgformHead == null) {
            throw new DBException("数据库子表[" + table + "]不存在");
        }
        List<OnlCgformField> queryFormFields = this.fieldService.queryFormFields(onlCgformHead.getId(), false);
        String str = null;
        Iterator<OnlCgformField> it = queryFormFields.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OnlCgformField next = it.next();
            if (oConvertUtils.isNotEmpty(next.getMainField())) {
                str = next.getDbFieldName();
                break;
            }
        }
        List<Map<String, Object>> querySubFormData = this.fieldService.querySubFormData(queryFormFields, table, str, mainId);
        if (querySubFormData != null && querySubFormData.size() == 0) {
            throw new DBException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
        }
        if (querySubFormData.size() > 1) {
            throw new DBException("数据库子表[" + table + "]存在多条记录,主表ID为" + mainId);
        }
        return querySubFormData.get(0);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<Map<String, Object>> queryManySubFormData(String table, String mainId) throws DBException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne( new LambdaQueryWrapper<OnlCgformHead>()
                .eq(OnlCgformHead::getTableName, table));
        if (onlCgformHead == null) {
            throw new DBException("数据库子表[" + table + "]不存在");
        }
        List<OnlCgformField> queryFormFields = this.fieldService.queryFormFields(onlCgformHead.getId(), false);
        String str = null;
        String str2 = null;
        String str3 = null;
        Iterator<OnlCgformField> it = queryFormFields.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OnlCgformField next = it.next();
            if (oConvertUtils.isNotEmpty(next.getMainField())) {
                str = next.getDbFieldName();
                str2 = next.getMainTable();
                str3 = next.getMainField();
                break;
            }
        }
        ArrayList arrayList = new ArrayList();
        OnlCgformField onlCgformField = new OnlCgformField();
        onlCgformField.setDbFieldName(str3);
        arrayList.add(onlCgformField);
        Map<String, Object> queryFormData = this.fieldService.queryFormData(arrayList, str2, mainId);
        List<Map<String, Object>> querySubFormData = this.fieldService.querySubFormData(queryFormFields, table, str, oConvertUtils.getString(oConvertUtils.getString(queryFormData.get(str3)), oConvertUtils.getString(queryFormData.get(str3.toUpperCase()))));
        if (querySubFormData != null && querySubFormData.size() == 0) {
            throw new DBException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
        }
        ArrayList arrayList2 = new ArrayList(querySubFormData.size());
        for (Map<String, Object> map : querySubFormData) {
            arrayList2.add(org.jeecg.modules.online.cgform.d.b.b(map));
        }
        return arrayList2;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public Map<String, Object> queryManyFormData(String code, String id) throws DBException {
        String[] split;
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(code);
        if (onlCgformHead == null) {
            throw new DBException("数据库主表ID[" + code + "]不存在");
        }
        Map<String, Object> queryFormData = this.fieldService.queryFormData(this.fieldService.queryFormFields(code, false), onlCgformHead.getTableName(), id);
        if (onlCgformHead.getTableType().intValue() == 2) {
            String subTableStr = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    OnlCgformHead onlCgformHead2 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                            .eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead2 != null) {
                        List<OnlCgformField> queryFormFields = this.fieldService.queryFormFields(onlCgformHead2.getId(), false);
                        String str2 = "";
                        String str3 = null;
                        for (OnlCgformField onlCgformField : queryFormFields) {
                            if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                                str2 = onlCgformField.getDbFieldName();
                                String mainField = onlCgformField.getMainField();
                                if (null == queryFormData.get(mainField)) {
                                    str3 = queryFormData.get(mainField.toUpperCase()).toString();
                                } else {
                                    str3 = queryFormData.get(mainField).toString();
                                }
                            }
                        }
                        List<Map<String, Object>> querySubFormData = this.fieldService.querySubFormData(queryFormFields, str, str2, str3);
                        if (querySubFormData == null || querySubFormData.size() == 0) {
                            queryFormData.put(str, new String[0]);
                        } else {
                            queryFormData.put(str, org.jeecg.modules.online.cgform.d.b.d(querySubFormData));
                        }
                    }
                }
            }
        }
        return queryFormData;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public String editManyFormData(String code, JSONObject json) throws DBException, BusinessException {
        String[] split;
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(code);
        if (onlCgformHead == null) {
            // 通过表单名称查询
            onlCgformHead = (OnlCgformHead) getOne(new LambdaQueryWrapper<OnlCgformHead>()
                    .eq(OnlCgformHead::getTableName,code));
            code=onlCgformHead.getId();

            if (onlCgformHead == null) {
                throw new DBException("数据库主表ID[" + code + "]不存在");
            }
        }
        executeEnhanceJava("edit", org.jeecg.modules.online.cgform.d.b.al, onlCgformHead, json);
        String tableName = onlCgformHead.getTableName();
        if ("Y".equals(onlCgformHead.getIsTree())) {
            this.fieldService.editTreeFormData(code, tableName, json, onlCgformHead.getTreeIdField(), onlCgformHead.getTreeParentIdField());
        } else {
            this.fieldService.editFormData(code, tableName, json, false);
        }
        if (onlCgformHead.getTableType().intValue() == 2) {
            String subTableStr = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    OnlCgformHead onlCgformHead2 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                            .eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead2 != null) {
                        List<OnlCgformField> list = this.fieldService.list((Wrapper) new LambdaQueryWrapper<OnlCgformField>()
                                .eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId()));
                        String str2 = "";
                        String str3 = null;
                        for (OnlCgformField onlCgformField : list) {
                            if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                                str2 = onlCgformField.getDbFieldName();
                                String mainField = onlCgformField.getMainField();
                                if (json.get(mainField.toLowerCase()) != null) {
                                    str3 = json.getString(mainField.toLowerCase());
                                }
                                if (json.get(mainField.toUpperCase()) != null) {
                                    str3 = json.getString(mainField.toUpperCase());
                                }
                            }
                        }
                        if (!oConvertUtils.isEmpty(str3)) {
                            this.fieldService.deleteAutoList(str, str2, str3);
                            JSONArray jSONArray = json.getJSONArray(str);
                            if (jSONArray != null && jSONArray.size() != 0) {
                                for (int i = 0; i < jSONArray.size(); i++) {
                                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                                    if (str3 != null) {
                                        jSONObject.put(str2, str3);
                                    }
                                    this.fieldService.saveFormData(list, str, jSONObject);
                                }
                            }
                        }
                    }
                }
            }
        }
        executeEnhanceJava("edit", "end", onlCgformHead, json);
        executeEnhanceSql("edit", code, json);
        return tableName;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public int executeEnhanceJava(String buttonCode, String eventType, OnlCgformHead head, JSONObject json) throws BusinessException {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, buttonCode);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, head.getId());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, eventType);
        Object a2 = a((OnlCgformEnhanceJava) this.onlCgformEnhanceJavaMapper.selectOne(lambdaQueryWrapper));
        if (a2 != null && (a2 instanceof CgformEnhanceJavaInter)) {
            return ((CgformEnhanceJavaInter) a2).execute(head.getTableName(), json);
        }
        return 1;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeEnhanceExport(OnlCgformHead head, List<Map<String, Object>> dataList) throws BusinessException {
        executeEnhanceList(head, org.jeecg.modules.online.cgform.d.b.ag, dataList);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeEnhanceList(OnlCgformHead head, String buttonCode, List<Map<String, Object>> dataList) throws BusinessException {
        Object a2;
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, buttonCode);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, head.getId());
        List selectList = this.onlCgformEnhanceJavaMapper.selectList(lambdaQueryWrapper);
        if (selectList != null && selectList.size() > 0 && (a2 = a((OnlCgformEnhanceJava) selectList.get(0))) != null && (a2 instanceof CgformEnhanceJavaListInter)) {
            ((CgformEnhanceJavaListInter) a2).execute(head.getTableName(), dataList);
        }
    }

    private Object a(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        if (onlCgformEnhanceJava != null) {
            String cgJavaType = onlCgformEnhanceJava.getCgJavaType();
            String cgJavaValue = onlCgformEnhanceJava.getCgJavaValue();
            if (oConvertUtils.isNotEmpty(cgJavaValue)) {
                Object obj = null;
                if ("class".equals(cgJavaType)) {
                    try {
                        obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
                    } catch (IllegalAccessException e) {
                        a.error(e.getMessage(), e);
                    } catch (InstantiationException e2) {
                        a.error(e2.getMessage(), e2);
                    }
                } else if ("spring".equals(cgJavaType)) {
                    obj = SpringContextUtils.getBean(cgJavaValue);
                }
                return obj;
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeEnhanceSql(String buttonCode, String formId, JSONObject json) {
        String[] split;
        LambdaQueryWrapper<OnlCgformEnhanceSql> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getButtonCode, buttonCode);
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getCgformHeadId, formId);
        OnlCgformEnhanceSql onlCgformEnhanceSql = (OnlCgformEnhanceSql) this.onlCgformEnhanceSqlMapper.selectOne(lambdaQueryWrapper);
        if (onlCgformEnhanceSql != null && oConvertUtils.isNotEmpty(onlCgformEnhanceSql.getCgbSql())) {
            for (String str : org.jeecg.modules.online.cgform.d.b.a(onlCgformEnhanceSql.getCgbSql(), json).split(";")) {
                if (str != null && !str.toLowerCase().trim().equals("")) {
                    a.info(" online sql 增强： " + str);
                    ((OnlCgformHeadMapper) this.baseMapper).executeDDL(str);
                }
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeCustomerButton(String buttonCode, String formId, String dataId) throws BusinessException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(formId);
        if (onlCgformHead == null) {
            throw new BusinessException("未找到表配置信息");
        }
        JSONObject parseObject = JSONObject.parseObject(JSON.toJSONString(((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(org.jeecg.modules.online.cgform.d.b.f(onlCgformHead.getTableName()), dataId)));
        executeEnhanceJava(buttonCode, org.jeecg.modules.online.cgform.d.b.al, onlCgformHead, parseObject);
        executeEnhanceSql(buttonCode, formId, parseObject);
        executeEnhanceJava(buttonCode, "end", onlCgformHead, parseObject);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<OnlCgformButton> queryValidButtonList(String headId) {
        LambdaQueryWrapper<OnlCgformButton> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, headId);
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceJs queryEnhanceJs(String formId, String cgJsType) {
        LambdaQueryWrapper<OnlCgformEnhanceJs> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJs::getCgformHeadId, formId);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJs::getCgJsType, cgJsType);
        return (OnlCgformEnhanceJs) this.onlCgformEnhanceJsMapper.selectOne(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void deleteOneTableInfo(String formId, String dataId) throws BusinessException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(formId);
        if (onlCgformHead == null) {
            throw new BusinessException("未找到表配置信息");
        }
        String f = org.jeecg.modules.online.cgform.d.b.f(onlCgformHead.getTableName());
        Map<String, Object> queryOneByTableNameAndId = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(f, dataId);
        if (queryOneByTableNameAndId == null) {
            throw new BusinessException("未找到数据信息");
        }
        JSONObject parseObject = JSONObject.parseObject(JSON.toJSONString(org.jeecg.modules.online.cgform.d.b.b(queryOneByTableNameAndId)));
        executeEnhanceJava("delete", org.jeecg.modules.online.cgform.d.b.al, onlCgformHead, parseObject);
        updateParentNode(onlCgformHead, dataId);
        if (onlCgformHead.getTableType().intValue() == 2) {
            this.fieldService.deleteAutoListMainAndSub(onlCgformHead, dataId);
        } else {
            ((OnlCgformHeadMapper) this.baseMapper).deleteOne("delete from " + f + " where id = '" + dataId + org.jeecg.modules.online.cgform.d.b.sz);
        }
        executeEnhanceSql("delete", formId, parseObject);
        executeEnhanceJava("delete", "end", onlCgformHead, parseObject);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Deprecated
    public JSONObject queryFormItem(OnlCgformHead head, String username) {
        List<String> queryFormDisabledCode;
        List<OnlCgformField> queryAvailableFields = this.fieldService.queryAvailableFields(head.getId(), head.getTableName(), head.getTaskId(), false);
        ArrayList arrayList = new ArrayList();
        if (oConvertUtils.isEmpty(head.getTaskId())) {
            List<String> queryFormDisabledCode2 = this.onlAuthPageService.queryFormDisabledCode(head.getId());
            if (queryFormDisabledCode2 != null && queryFormDisabledCode2.size() > 0 && queryFormDisabledCode2.get(0) != null) {
                arrayList.addAll(queryFormDisabledCode2);
            }
        } else {
            List<String> queryDisabledFields = this.fieldService.queryDisabledFields(head.getTableName(), head.getTaskId());
            if (queryDisabledFields != null && queryDisabledFields.size() > 0 && queryDisabledFields.get(0) != null) {
                arrayList.addAll(queryDisabledFields);
            }
        }
        JSONObject a2 = org.jeecg.modules.online.cgform.d.b.a(queryAvailableFields, arrayList, (org.jeecg.modules.online.cgform.model.d) null);
        if (head.getTableType().intValue() == 2) {
            String subTableStr = head.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
                            .selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                                    .eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null) {
                        List<OnlCgformField> queryAvailableFields2 = this.fieldService.queryAvailableFields(onlCgformHead.getId(), onlCgformHead.getTableName(), head.getTaskId(), false);
                        new ArrayList();
                        if (oConvertUtils.isNotEmpty(head.getTaskId())) {
                            queryFormDisabledCode = this.fieldService.queryDisabledFields(onlCgformHead.getTableName(), head.getTaskId());
                        } else {
                            queryFormDisabledCode = this.onlAuthPageService.queryFormDisabledCode(onlCgformHead.getId());
                        }
                        JSONObject jSONObject = new JSONObject();
                        if (1 == onlCgformHead.getRelationType().intValue()) {
                            jSONObject = org.jeecg.modules.online.cgform.d.b.a(queryAvailableFields2, queryFormDisabledCode, (org.jeecg.modules.online.cgform.model.d) null);
                        } else {
                            jSONObject.put("columns", org.jeecg.modules.online.cgform.d.b.a(queryAvailableFields2, queryFormDisabledCode));
                        }
                        jSONObject.put("relationType", onlCgformHead.getRelationType());
                        jSONObject.put("view", "tab");
                        jSONObject.put("order", onlCgformHead.getTabOrderNum());
                        jSONObject.put("formTemplate", onlCgformHead.getFormTemplate());
                        jSONObject.put("describe", onlCgformHead.getTableTxt());
                        jSONObject.put("key", onlCgformHead.getTableName());
                        a2.getJSONObject("properties").put(onlCgformHead.getTableName(), jSONObject);
                    }
                }
            }
        }
        return a2;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<String> generateCode(OnlGenerateModel model) throws Exception {
        TableVo tableVo = new TableVo();
        tableVo.setEntityName(model.getEntityName());
        tableVo.setEntityPackage(model.getEntityPackage());
        tableVo.setFtlDescription(model.getFtlDescription());
        tableVo.setTableName(model.getTableName());
        tableVo.setSearchFieldNum(-1);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        a(model.getCode(), arrayList, arrayList2);
        OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
                .selectOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                        .eq(OnlCgformHead::getId, model.getCode()));
        HashMap hashMap = new HashMap();
        hashMap.put("scroll", onlCgformHead.getScroll() == null ? "0" : onlCgformHead.getScroll().toString());
        // 放入首字母大写的包名
        hashMap.put("entityPackageUpperCase", model.getEntityPackage().substring(0, 1).toUpperCase() + model.getEntityPackage().substring(1));
        String formTemplate = onlCgformHead.getFormTemplate();
        if (oConvertUtils.isEmpty(formTemplate)) {
            tableVo.setFieldRowNum(1);
        } else {
            tableVo.setFieldRowNum(Integer.valueOf(Integer.parseInt(formTemplate)));
        }
        if ("Y".equals(onlCgformHead.getIsTree())) {
            hashMap.put("pidField", onlCgformHead.getTreeParentIdField());
            hashMap.put("hasChildren", onlCgformHead.getTreeIdField());
            hashMap.put("textField", onlCgformHead.getTreeFieldname());
        }
        tableVo.setExtendParams(hashMap);
        CgformEnum cgformEnumByConfig = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        ArrayList generateCodeFile = (ArrayList) new CodeGenerateOne(tableVo, arrayList, arrayList2).generateCodeFile(model.getProjectPath(), cgformEnumByConfig.getTemplatePath(), cgformEnumByConfig.getStylePath());
        if (generateCodeFile == null || generateCodeFile.size() == 0) {
            generateCodeFile = new ArrayList();
            generateCodeFile.add(" :::::: 生成失败ERROR提醒 :::::: ");
            generateCodeFile.add("1.未找到代码生成器模板，请确认路径是否含有中文或特殊字符！");
            generateCodeFile.add("2.如果是JAR包运行，请参考此文档 http://doc.jeecg.com/2043922");
        }
        return generateCodeFile;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<String> generateOneToMany(OnlGenerateModel model) throws Exception {
        MainTableVo mainTableVo = new MainTableVo();
        mainTableVo.setEntityName(model.getEntityName());
        mainTableVo.setEntityPackage(model.getEntityPackage());
        mainTableVo.setFtlDescription(model.getFtlDescription());
        mainTableVo.setTableName(model.getTableName());
        String formTemplate = ((OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
                .selectOne( new LambdaQueryWrapper<OnlCgformHead>()
                        .eq(OnlCgformHead::getId, model.getCode()))).getFormTemplate();
        if (oConvertUtils.isEmpty(formTemplate)) {
            mainTableVo.setFieldRowNum(1);
        } else {
            mainTableVo.setFieldRowNum(Integer.valueOf(Integer.parseInt(formTemplate)));
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        a(model.getCode(), arrayList, arrayList2);
        List<OnlGenerateModel> subList = model.getSubList();
        ArrayList arrayList3 = new ArrayList();
        for (OnlGenerateModel onlGenerateModel : subList) {
            OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
                    .selectOne( new LambdaQueryWrapper<OnlCgformHead>()
                            .eq(OnlCgformHead::getTableName, onlGenerateModel.getTableName()));
            if (onlCgformHead != null) {
                SubTableVo subTableVo = new SubTableVo();
                subTableVo.setEntityName(onlGenerateModel.getEntityName());
                subTableVo.setEntityPackage(model.getEntityPackage());
                subTableVo.setTableName(onlGenerateModel.getTableName());
                subTableVo.setFtlDescription(onlGenerateModel.getFtlDescription());
                subTableVo.setForeignRelationType(onlCgformHead.getRelationType().intValue() == 1 ? "1" : "0");
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                OnlCgformField a2 = a(onlCgformHead.getId(), arrayList4, arrayList5);
                if (a2 != null) {
                    subTableVo.setOriginalForeignKeys(new String[]{a2.getDbFieldName()});
                    subTableVo.setForeignKeys(new String[]{a2.getDbFieldName()});
                    subTableVo.setColums(arrayList4);
                    subTableVo.setOriginalColumns(arrayList5);
                    arrayList3.add(subTableVo);
                }
            }
        }
        CgformEnum cgformEnumByConfig = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        return new CodeGenerateOneToMany(mainTableVo, arrayList, arrayList2, arrayList3).generateCodeFile(model.getProjectPath(), cgformEnumByConfig.getTemplatePath(), cgformEnumByConfig.getStylePath());
    }

    private OnlCgformField a(String str, List<ColumnVo> list, List<ColumnVo> list2) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        OnlCgformField onlCgformField = null;
        for (OnlCgformField onlCgformField2 : this.fieldService.list(lambdaQueryWrapper)) {
            if (oConvertUtils.isNotEmpty(onlCgformField2.getMainTable())) {
                onlCgformField = onlCgformField2;
            }
            ColumnVo columnVo = new ColumnVo();
            /*
             * FIXME 如果控件长度是默认的120，去判断有无设置数据库长度，如果设置了数据库长度，就用数据库长度
             *  更改这个逻辑后，可能影响online页面的列表搜索展示的长度，这时候需要去调整字段长度（非120）即可
             */
            if (onlCgformField2.getFieldLength() == 120 && onlCgformField2.getDbLength() != 0) {
                columnVo.setFieldLength(onlCgformField2.getDbLength());
            } else {
                columnVo.setFieldLength(onlCgformField2.getFieldLength());
            }
            columnVo.setFieldHref(onlCgformField2.getFieldHref());
            columnVo.setFieldValidType(onlCgformField2.getFieldValidType());
            columnVo.setFieldDefault(onlCgformField2.getDbDefaultVal());
            columnVo.setFieldShowType(onlCgformField2.getFieldShowType());
            columnVo.setFieldOrderNum(onlCgformField2.getOrderNum());
            columnVo.setIsKey(onlCgformField2.getDbIsKey().intValue() == 1 ? "Y" : "N");
            columnVo.setIsShow(onlCgformField2.getIsShowForm().intValue() == 1 ? "Y" : "N");
            columnVo.setIsShowList(onlCgformField2.getIsShowList().intValue() == 1 ? "Y" : "N");
            columnVo.setIsQuery(onlCgformField2.getIsQuery().intValue() == 1 ? "Y" : "N");
            columnVo.setQueryMode(onlCgformField2.getQueryMode());
            columnVo.setDictField(onlCgformField2.getDictField());
            if (oConvertUtils.isNotEmpty(onlCgformField2.getDictTable()) && onlCgformField2.getDictTable().indexOf("where") > 0) {
                columnVo.setDictTable(onlCgformField2.getDictTable().split("where")[0].trim());
            } else {
                columnVo.setDictTable(onlCgformField2.getDictTable());
            }
            columnVo.setDictText(onlCgformField2.getDictText());
            columnVo.setFieldDbName(onlCgformField2.getDbFieldName());
            columnVo.setFieldName(oConvertUtils.camelName(onlCgformField2.getDbFieldName()));
            columnVo.setFiledComment(onlCgformField2.getDbFieldTxt());
            columnVo.setFieldDbType(onlCgformField2.getDbType());
            columnVo.setFieldType(a(onlCgformField2.getDbType()));
            columnVo.setClassType(onlCgformField2.getFieldShowType());
            columnVo.setClassType_row(onlCgformField2.getFieldShowType());
            if (onlCgformField2.getDbIsNull().intValue() == 0 || "*".equals(onlCgformField2.getFieldValidType()) || "1".equals(onlCgformField2.getFieldMustInput())) {
                columnVo.setNullable("N");
            } else {
                columnVo.setNullable("Y");
            }
            if (org.jeecg.modules.online.cgform.d.b.sI.equals(onlCgformField2.getFieldShowType())) {
                columnVo.setDictField(onlCgformField2.getFieldExtendJson());
            }
            columnVo.setSort("1".equals(onlCgformField2.getSortFlag()) ? "Y" : "N");
            Integer num = 1;
            columnVo.setReadonly(num.equals(onlCgformField2.getIsReadOnly()) ? "Y" : "N");
            if (oConvertUtils.isNotEmpty(onlCgformField2.getFieldDefaultValue()) && !onlCgformField2.getFieldDefaultValue().trim().startsWith("${") && !onlCgformField2.getFieldDefaultValue().trim().startsWith("#{") && !onlCgformField2.getFieldDefaultValue().trim().startsWith("{{")) {
                columnVo.setDefaultVal(onlCgformField2.getFieldDefaultValue());
            }
            if (("file".equals(onlCgformField2.getFieldShowType()) || "image".equals(onlCgformField2.getFieldShowType())) && oConvertUtils.isNotEmpty(onlCgformField2.getFieldExtendJson())) {
                JSONObject parseObject = JSONObject.parseObject(onlCgformField2.getFieldExtendJson());
                if (oConvertUtils.isNotEmpty(parseObject.getString("uploadnum"))) {
                    columnVo.setUploadnum(parseObject.getString("uploadnum"));
                }
            }
            list2.add(columnVo);
            if (onlCgformField2.getIsShowForm().intValue() == 1 || onlCgformField2.getIsShowList().intValue() == 1) {
                list.add(columnVo);
            }
        }
        return onlCgformField;
    }

    private String a(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.indexOf("int") >= 0) {
            return "java.lang.Integer";
        }
        if (lowerCase.indexOf("double") >= 0) {
            return "java.lang.Double";
        }
        if (lowerCase.indexOf(org.jeecg.modules.online.config.b.b.e) >= 0) {
            return "java.math.BigDecimal";
        }
        if (lowerCase.indexOf(org.jeecg.modules.online.cgform.d.i.DATE) >= 0) {
            return "java.util.Date";
        }
        return "java.lang.String";
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void addCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
        String subTableStr;
        String[] split;
        JSONArray parseArray;
        OnlCgformHead onlCgformHead;
        OnlCgformHead onlCgformHead2 = getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tbname));
        if (onlCgformHead2 == null) {
            throw new DBException("数据库主表[" + tbname + "]不存在");
        }
        if (onlCgformHead2.getTableType().intValue() == 2 && (subTableStr = onlCgformHead2.getSubTableStr()) != null) {
            for (String str : subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                String string = json.getString(org.jeecg.modules.online.cgform.d.b.ae + str);
                if (!oConvertUtils.isEmpty(string) && (parseArray = JSONArray.parseArray(URLDecoder.decode(string, "UTF-8"))) != null && parseArray.size() != 0 && (onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne(new LambdaQueryWrapper<OnlCgformHead>()
                        .eq(OnlCgformHead::getTableName, str))) != null) {
                    List<OnlCgformField> list = this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>()
                            .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()));
                    String str2 = "";
                    String str3 = null;
                    for (OnlCgformField onlCgformField : list) {
                        if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                            str2 = onlCgformField.getDbFieldName();
                            str3 = json.getString(onlCgformField.getMainField());
                        }
                    }
                    for (int i = 0; i < parseArray.size(); i++) {
                        JSONObject jSONObject = parseArray.getJSONObject(i);
                        if (str3 != null) {
                            jSONObject.put(str2, str3);
                        }
                        this.fieldService.executeInsertSQL(org.jeecg.modules.online.cgform.d.b.c(str, list, jSONObject));
                    }
                }
            }
        }
        this.fieldService.saveFormData(onlCgformHead2.getId(), tbname, json, true);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void editCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
        String[] split;
        JSONArray parseArray;
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne(new LambdaQueryWrapper<OnlCgformHead>()
                .eq(OnlCgformHead::getTableName, tbname));
        if (onlCgformHead == null) {
            throw new DBException("数据库主表[" + tbname + "]不存在");
        }
        if (onlCgformHead.getTableType().intValue() == 2) {
            for (String str : onlCgformHead.getSubTableStr().split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                OnlCgformHead onlCgformHead2 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne(new LambdaQueryWrapper<OnlCgformHead>()
                        .eq(OnlCgformHead::getTableName, str));
                if (onlCgformHead2 != null) {
                    List<OnlCgformField> list = this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>()
                            .eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId()));
                    String str2 = "";
                    String str3 = null;
                    for (OnlCgformField onlCgformField : list) {
                        if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                            str2 = onlCgformField.getDbFieldName();
                            str3 = json.getString(onlCgformField.getMainField());
                        }
                    }
                    if (!oConvertUtils.isEmpty(str3)) {
                        this.fieldService.deleteAutoList(str, str2, str3);
                        String string = json.getString(org.jeecg.modules.online.cgform.d.b.ae + str);
                        if (!oConvertUtils.isEmpty(string) && (parseArray = JSONArray.parseArray(URLDecoder.decode(string, "UTF-8"))) != null && parseArray.size() != 0) {
                            for (int i = 0; i < parseArray.size(); i++) {
                                JSONObject jSONObject = parseArray.getJSONObject(i);
                                if (str3 != null) {
                                    jSONObject.put(str2, str3);
                                }
                                this.fieldService.executeInsertSQL(org.jeecg.modules.online.cgform.d.b.c(str, list, jSONObject));
                            }
                        }
                    }
                }
            }
        }
        this.fieldService.editFormData(onlCgformHead.getId(), tbname, json, true);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public Integer getMaxCopyVersion(String physicId) {
        Integer maxCopyVersion = ((OnlCgformHeadMapper) this.baseMapper).getMaxCopyVersion(physicId);
        return Integer.valueOf(maxCopyVersion == null ? 0 : maxCopyVersion.intValue());
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void copyOnlineTableConfig(OnlCgformHead physicTable) throws Exception {
        String id = physicTable.getId();
        OnlCgformHead onlCgformHead = new OnlCgformHead();
        String generate = UUIDGenerator.generate();
        onlCgformHead.setId(generate);
        onlCgformHead.setPhysicId(id);
        onlCgformHead.setCopyType(1);
        onlCgformHead.setCopyVersion(physicTable.getTableVersion());
        onlCgformHead.setTableVersion(1);
        onlCgformHead.setTableName(a(id, physicTable.getTableName()));
        onlCgformHead.setTableTxt(physicTable.getTableTxt());
        onlCgformHead.setFormCategory(physicTable.getFormCategory());
        onlCgformHead.setFormTemplate(physicTable.getFormTemplate());
        onlCgformHead.setFormTemplateMobile(physicTable.getFormTemplateMobile());
        onlCgformHead.setIdSequence(physicTable.getIdSequence());
        onlCgformHead.setIdType(physicTable.getIdType());
        onlCgformHead.setIsCheckbox(physicTable.getIsCheckbox());
        onlCgformHead.setIsPage(physicTable.getIsPage());
        onlCgformHead.setIsTree(physicTable.getIsTree());
        onlCgformHead.setQueryMode(physicTable.getQueryMode());
        onlCgformHead.setTableType(1);
        onlCgformHead.setIsDbSynch("N");
        onlCgformHead.setIsDesForm(physicTable.getIsDesForm());
        onlCgformHead.setDesFormCode(physicTable.getDesFormCode());
        onlCgformHead.setTreeParentIdField(physicTable.getTreeParentIdField());
        onlCgformHead.setTreeFieldname(physicTable.getTreeFieldname());
        onlCgformHead.setTreeIdField(physicTable.getTreeIdField());
        onlCgformHead.setRelationType(null);
        onlCgformHead.setTabOrderNum(null);
        onlCgformHead.setSubTableStr(null);
        onlCgformHead.setThemeTemplate(physicTable.getThemeTemplate());
        onlCgformHead.setScroll(physicTable.getScroll());
        for (OnlCgformField onlCgformField : this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>()
                .eq(OnlCgformField::getCgformHeadId, id))) {
            OnlCgformField onlCgformField2 = new OnlCgformField();
            onlCgformField2.setCgformHeadId(generate);
            a(onlCgformField, onlCgformField2);
            this.fieldService.save(onlCgformField2);
        }
        ((OnlCgformHeadMapper) this.baseMapper).insert(onlCgformHead);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void initCopyState(List<OnlCgformHead> headList) {
        List<String> queryCopyPhysicId = ((OnlCgformHeadMapper) this.baseMapper).queryCopyPhysicId();
        for (OnlCgformHead onlCgformHead : headList) {
            if (queryCopyPhysicId.contains(onlCgformHead.getId())) {
                onlCgformHead.setHascopy(1);
            } else {
                onlCgformHead.setHascopy(0);
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void deleteBatch(String ids, String flag) {
        String[] split = ids.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
        if ("1".equals(flag)) {
            for (String str : split) {
                try {
                    deleteRecordAndTable(str);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (DBException e2) {
                    e2.printStackTrace();
                }
            }
            return;
        }
        removeByIds(Arrays.asList(split));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void updateParentNode(OnlCgformHead head, String dataId) {
        if ("Y".equals(head.getIsTree())) {
            String f = org.jeecg.modules.online.cgform.d.b.f(head.getTableName());
            String treeParentIdField = head.getTreeParentIdField();
            Map<String, Object> queryOneByTableNameAndId = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(f, dataId);
            String str = null;
            if (queryOneByTableNameAndId.get(treeParentIdField) != null && !"0".equals(queryOneByTableNameAndId.get(treeParentIdField))) {
                str = queryOneByTableNameAndId.get(treeParentIdField).toString();
            } else if (queryOneByTableNameAndId.get(treeParentIdField.toUpperCase()) != null && !"0".equals(queryOneByTableNameAndId.get(treeParentIdField.toUpperCase()))) {
                str = queryOneByTableNameAndId.get(treeParentIdField.toUpperCase()).toString();
            }
            if (str != null && ((OnlCgformHeadMapper) this.baseMapper).queryChildNode(f, treeParentIdField, str).intValue() == 1) {
                this.fieldService.updateTreeNodeNoChild(f, head.getTreeIdField(), str);
            }
        }
    }

    private void b(OnlCgformHead onlCgformHead, List<OnlCgformField> list) {
        List<OnlCgformHead> list2 = list((Wrapper) new LambdaQueryWrapper<OnlCgformHead>()
                .eq(OnlCgformHead::getPhysicId, onlCgformHead.getId()));
        if (list2 != null && list2.size() > 0) {
            for (OnlCgformHead onlCgformHead2 : list2) {
                List<OnlCgformField> list3 = this.fieldService.list((Wrapper) new LambdaQueryWrapper<OnlCgformField>()
                        .eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId()));
                if (list3 == null || list3.size() == 0) {
                    for (OnlCgformField onlCgformField : list) {
                        OnlCgformField onlCgformField2 = new OnlCgformField();
                        onlCgformField2.setCgformHeadId(onlCgformHead2.getId());
                        a(onlCgformField, onlCgformField2);
                        this.fieldService.save(onlCgformField2);
                    }
                } else {
                    HashMap<String,Object> hashMap = new HashMap();
                    for (OnlCgformField onlCgformField3 : list3) {
                        hashMap.put(onlCgformField3.getDbFieldName(), 1);
                    }
                    HashMap<String,Object> hashMap2 = new HashMap();
                    for (OnlCgformField onlCgformField4 : list) {
                        hashMap2.put(onlCgformField4.getDbFieldName(), 1);
                    }
                    ArrayList<String> arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    for (String str : hashMap2.keySet()) {
                        if (hashMap.get(str) == null) {
                            arrayList2.add(str);
                        } else {
                            arrayList.add(str);
                        }
                    }
                    ArrayList arrayList3 = new ArrayList();
                    for (String str2 : hashMap.keySet()) {
                        if (hashMap2.get(str2) == null) {
                            arrayList3.add(str2);
                        }
                    }
                    if (arrayList3.size() > 0) {
                        for (OnlCgformField onlCgformField5 : list3) {
                            if (arrayList3.contains(onlCgformField5.getDbFieldName())) {
                                this.fieldService.removeById(onlCgformField5.getId());
                            }
                        }
                    }
                    if (arrayList2.size() > 0) {
                        for (OnlCgformField onlCgformField6 : list) {
                            if (arrayList2.contains(onlCgformField6.getDbFieldName())) {
                                OnlCgformField onlCgformField7 = new OnlCgformField();
                                onlCgformField7.setCgformHeadId(onlCgformHead2.getId());
                                a(onlCgformField6, onlCgformField7);
                                this.fieldService.save(onlCgformField7);
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        for (String str3 : arrayList) {
                            b(str3, list, list3);
                        }
                    }
                }
            }
        }
    }

    private void b(String str, List<OnlCgformField> list, List<OnlCgformField> list2) {
        OnlCgformField onlCgformField = null;
        for (OnlCgformField onlCgformField2 : list) {
            if (str.equals(onlCgformField2.getDbFieldName())) {
                onlCgformField = onlCgformField2;
            }
        }
        OnlCgformField onlCgformField3 = null;
        for (OnlCgformField onlCgformField4 : list2) {
            if (str.equals(onlCgformField4.getDbFieldName())) {
                onlCgformField3 = onlCgformField4;
            }
        }
        if (onlCgformField != null && onlCgformField3 != null) {
            boolean z = false;
            if (!onlCgformField.getDbType().equals(onlCgformField3.getDbType())) {
                onlCgformField3.setDbType(onlCgformField.getDbType());
                z = true;
            }
            if (!onlCgformField.getDbDefaultVal().equals(onlCgformField3.getDbDefaultVal())) {
                onlCgformField3.setDbDefaultVal(onlCgformField.getDbDefaultVal());
                z = true;
            }
            if (onlCgformField.getDbLength() != onlCgformField3.getDbLength()) {
                onlCgformField3.setDbLength(onlCgformField.getDbLength());
                z = true;
            }
            if (onlCgformField.getDbIsNull() != onlCgformField3.getDbIsNull()) {
                onlCgformField3.setDbIsNull(onlCgformField.getDbIsNull());
                z = true;
            }
            if (z) {
                this.fieldService.updateById(onlCgformField3);
            }
        }
    }

    private void a(OnlCgformField onlCgformField, OnlCgformField onlCgformField2) {
        onlCgformField2.setDbDefaultVal(onlCgformField.getDbDefaultVal());
        onlCgformField2.setDbFieldName(onlCgformField.getDbFieldName());
        onlCgformField2.setDbFieldNameOld(onlCgformField.getDbFieldNameOld());
        onlCgformField2.setDbFieldTxt(onlCgformField.getDbFieldTxt());
        onlCgformField2.setDbIsKey(onlCgformField.getDbIsKey());
        onlCgformField2.setDbIsNull(onlCgformField.getDbIsNull());
        onlCgformField2.setDbLength(onlCgformField.getDbLength());
        onlCgformField2.setDbPointLength(onlCgformField.getDbPointLength());
        onlCgformField2.setDbType(onlCgformField.getDbType());
        onlCgformField2.setDictField(onlCgformField.getDictField());
        onlCgformField2.setDictTable(onlCgformField.getDictTable());
        onlCgformField2.setDictText(onlCgformField.getDictText());
        onlCgformField2.setFieldExtendJson(onlCgformField.getFieldExtendJson());
        onlCgformField2.setFieldHref(onlCgformField.getFieldHref());
        onlCgformField2.setFieldLength(onlCgformField.getFieldLength());
        onlCgformField2.setFieldMustInput(onlCgformField.getFieldMustInput());
        onlCgformField2.setFieldShowType(onlCgformField.getFieldShowType());
        onlCgformField2.setFieldValidType(onlCgformField.getFieldValidType());
        onlCgformField2.setFieldDefaultValue(onlCgformField.getFieldDefaultValue());
        onlCgformField2.setIsQuery(onlCgformField.getIsQuery());
        onlCgformField2.setIsShowForm(onlCgformField.getIsShowForm());
        onlCgformField2.setIsShowList(onlCgformField.getIsShowList());
        onlCgformField2.setMainField(null);
        onlCgformField2.setMainTable(null);
        onlCgformField2.setOrderNum(onlCgformField.getOrderNum());
        onlCgformField2.setQueryMode(onlCgformField.getQueryMode());
        onlCgformField2.setIsReadOnly(onlCgformField.getIsReadOnly());
        onlCgformField2.setSortFlag(onlCgformField.getSortFlag());
        onlCgformField2.setQueryDefVal(onlCgformField.getQueryDefVal());
        onlCgformField2.setQueryConfigFlag(onlCgformField.getQueryConfigFlag());
        onlCgformField2.setQueryDictField(onlCgformField.getQueryDictField());
        onlCgformField2.setQueryDictTable(onlCgformField.getQueryDictTable());
        onlCgformField2.setQueryDictText(onlCgformField.getQueryDictText());
        onlCgformField2.setQueryMustInput(onlCgformField.getQueryMustInput());
        onlCgformField2.setQueryShowType(onlCgformField.getQueryShowType());
        onlCgformField2.setQueryValidType(onlCgformField.getQueryValidType());
        onlCgformField2.setConverter(onlCgformField.getConverter());
    }

    private void a(OnlCgformField onlCgformField) {
        if (org.jeecg.modules.online.config.b.b.j.equals(onlCgformField.getDbType()) || "Blob".equals(onlCgformField.getDbType())) {
            onlCgformField.setDbLength(0);
            onlCgformField.setDbPointLength(0);
        }
    }

    private String a(String str, String str2) {
        List<String> queryAllCopyTableName = ((OnlCgformHeadMapper) this.baseMapper).queryAllCopyTableName(str);
        int i = 0;
        if (queryAllCopyTableName != null || queryAllCopyTableName.size() > 0) {
            for (int i2 = 0; i2 < queryAllCopyTableName.size(); i2++) {
                int parseInt = Integer.parseInt(queryAllCopyTableName.get(i2).split("\\$")[1]);
                if (parseInt > i) {
                    i = parseInt;
                }
            }
        }
        return str2 + org.jeecg.modules.online.cgform.d.b.ss + (i + 1);
    }
}
