package org.jeecg.modules.activiti.jeecg.jexfm.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.es.JeecgElasticsearchTemplate;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.commons.lang.MyStringUtil;
import org.jeecg.modules.activiti.jeecg.jexfm.JexfmBA;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.desform.b.f;
import org.jeecg.modules.online.desform.constant.DesformConstant;
import org.jeecg.modules.online.desform.constant.WidgetTypes;
import org.jeecg.modules.online.desform.entity.DesignForm;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.mapper.DesignFormDataMapper;
import org.jeecg.modules.online.desform.mapper.DesignFormMapper;
import org.jeecg.modules.online.desform.service.IDesignFormDataService;
import org.jeecg.modules.online.desform.service.IDesignFormService;
import org.jeecg.modules.online.desform.vo.TranslateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service("designFormServiceImpl")
public class ImplE extends ServiceImpl<DesignFormMapper, DesignForm> implements IDesignFormService {
    private static final Logger a = LoggerFactory.getLogger(ImplE.class);
    @Autowired
    @Lazy
    private DesignFormMapper designFormMapper;
    @Autowired
    @Lazy
    private DesignFormDataMapper designFormDataMapper;
    @Autowired
    @Lazy
    private IDesignFormDataService dataService;
    @Autowired
    private ISysBaseAPI sysBaseApi;
    @Autowired
    private JeecgElasticsearchTemplate jes;
    @Autowired
    @Lazy
    private OnlCgformHeadMapper onlCgformHeadMapper;

    public ImplE() {
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void saveMain(DesignForm designForm) {
        if (oConvertUtils.isEmpty(designForm.getDesformType())) {
            designForm.setDesformType(DesformConstant.DESFORM_TYPE_MV);
        }

        org.jeecg.modules.online.desform.b.e.a(designForm.getDesformCode());
        this.designFormMapper.insert(designForm);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void updateMain(DesignForm designForm, List<DesignFormData> designFormDataList) {
        this.designFormMapper.updateById(designForm);
        this.designFormDataMapper.deleteByMainId(designForm.getId());
        Iterator var3 = designFormDataList.iterator();

        while(var3.hasNext()) {
            DesignFormData var4 = (DesignFormData)var3.next();
            var4.setDesformId(designForm.getId());
            this.designFormDataMapper.insert(var4);
        }

    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void delMain(String id) {
        DesignForm var2 = (DesignForm)((DesignFormMapper)this.baseMapper).selectById(id);
        if (var2 != null) {
            this.designFormDataMapper.deleteByMainId(var2.getId());
            this.designFormMapper.deleteById(var2.getId());
            org.jeecg.modules.online.desform.b.e.b(var2.getDesformCode());
        }

    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void delBatchMain(Collection<? extends Serializable> idList) {
        List var2 = ((DesignFormMapper)this.baseMapper).selectList((Wrapper)(new LambdaQueryWrapper<DesignForm>()).in(DesignForm::getId, idList));
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            DesignForm var4 = (DesignForm)var3.next();
            this.designFormDataMapper.deleteByMainId(var4.getId());
            this.designFormMapper.deleteById(var4.getId());
            org.jeecg.modules.online.desform.b.e.b(var4.getDesformCode());
        }

    }

    public DesignForm getByCode(String desformCode) {
        LambdaQueryWrapper<DesignForm> var2 = new LambdaQueryWrapper<>();
        var2.eq(DesignForm::getDesformCode, desformCode);
        DesignForm var3 = (DesignForm)((DesignFormMapper)this.baseMapper).selectOne(var2);
        // 添加表单id
        OnlCgformHead cgformHead= onlCgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>()
                .eq(OnlCgformHead::getTableName,desformCode));
        var3.setCgformId(cgformHead.getId());
        return var3;
    }

    public ModelAndView queryFormViewData(DesignForm designForm, String dataId, String onlineDataId, ModelAndView modelAndView) {
        if (designForm == null) {
            throw new JeecgBootException("表单设计不存在！");
        } else {
            modelAndView.addObject("designForm", this.queryAllDictItem(designForm));
            if (!"add".equals(dataId)) {
                DesignFormData var5 = null;
                if (MyStringUtil.isNotEmpty(onlineDataId)) {
                    String var6 = null;
                    String var7 = designForm.getDesformDesignJson();
                    if (JexfmBA.b(var7)) {
                        JSONObject var8 = JSONObject.parseObject(var7);
                        if (var8.get("config") != null) {
                            JSONObject var9 = (JSONObject)var8.get("config");
                            if (var9.get("onlineForm") != null) {
                                var6 = (String)var9.get("onlineForm");
                            }
                        }
                    }

                    if (MyStringUtil.isNotEmpty(var6)) {
                        Map var18 = this.designFormDataMapper.queryOneByTableNameAndId(var6, onlineDataId);
                        if (var18 == null) {
                            throw new JeecgBootException("表单数据不存在！");
                        }

                        String var19 = this.designFormDataMapper.getSubTablesByTableName(var6);
                        var5 = new DesignFormData();
                        if (MyStringUtil.isNotEmpty(var19)) {
                            String[] var10 = var19.split(",");
                            int var11 = var10.length;

                            for(int var12 = 0; var12 < var11; ++var12) {
                                String var13 = var10[var12];
                                String var14 = null;

                                try {
                                    var14 = URLEncoder.encode("[{}]", "UTF-8");
                                } catch (UnsupportedEncodingException var17) {
                                    var17.printStackTrace();
                                }

                                var18.put("sub-table-design_" + var13, var14);
                            }
                        }

                        try {
                            String var20 = CommonUtils.getDatabaseType();
                            if ("ORACLE".equalsIgnoreCase(var20)) {
                                var5.setDesformDataJson(JSONObject.toJSONString(f.a(var18)));
                            } else {
                                var5.setDesformDataJson(JSONObject.toJSONString(var18));
                            }
                        } catch (Exception var16) {
                            var16.printStackTrace();
                            var5.setDesformDataJson(JSONObject.toJSONString(var18));
                        }

                        var5.setDesformId(designForm.getId());
                        var5.setDesformCode(designForm.getDesformCode());
                        var5.setDesformName(designForm.getDesformName());
                        var5.setOnlineFormCode(var6);
                        var5.setOnlineFormDataId(onlineDataId);
                        LambdaQueryWrapper<DesignFormData> var21 = new LambdaQueryWrapper<>();
                        var21.eq(DesignFormData::getOnlineFormDataId, onlineDataId);
                        var21.eq(DesignFormData::getDesformId, designForm.getId());
                        DesignFormData var22 = (DesignFormData)this.designFormDataMapper.selectOne(var21);
                        if (var22 != null) {
                            var5.setId(var22.getId());
                        } else {
                            var5.setId("ONLINE-DATA-TEMP-ID");
                        }
                    }
                } else {
                    var5 = (DesignFormData)this.designFormDataMapper.selectById(dataId);
                }

                if (var5 == null) {
                    throw new JeecgBootException("表单数据不存在，dataId=" + dataId);
                }

                modelAndView.addObject("designFormData", var5);
                if (var5 != null) {
                    a.debug(" designFormData: " + var5.toString());
                }
            }

            return modelAndView;
        }
    }

    public ModelAndView queryFormViewById(String desformId, String dataId, String onlineDataId, ModelAndView modelAndView) {
        DesignForm var5 = (DesignForm)this.getById(desformId);
        return this.queryFormViewData(var5, dataId, onlineDataId, modelAndView);
    }

    public ModelAndView queryFormViewByCode(String desformCode, String dataId, String onlineDataId, ModelAndView modelAndView) {
        return this.queryFormViewByCode(desformCode, dataId, onlineDataId, false, modelAndView);
    }

    public ModelAndView queryFormAutoViewByCode(String desformCode, String dataId, String onlineDataId, ModelAndView modelAndView) {
        LambdaQueryWrapper<DesignForm> var5 = new LambdaQueryWrapper<>();
        var5.eq(DesignForm::getParentCode, desformCode);
        var5.eq(DesignForm::getIzMobileView, DesformConstant.IS_MOBILE_VIEW_Y);
        List var6 = super.list(var5);
        DesignForm var7 = null;
        if (var6.size() > 0) {
            var7 = (DesignForm)var6.get(0);
        } else {
            var7 = this.getByCode(desformCode);
        }

        if (var7 == null) {
            throw new JeecgBootException("表单设计不存在！");
        } else {
            return this.queryFormViewData(var7, dataId, onlineDataId, modelAndView);
        }
    }

    public ModelAndView queryFormViewByCode(String desformCode, String dataId, String onlineDataId, boolean isExternal, ModelAndView modelAndView) {
        DesignForm var6 = this.getByCode(desformCode);
        if (var6 != null && isExternal) {
            JSONObject var7 = JSON.parseObject(var6.getDesformDesignJson());
            JSONObject var8 = var7.getJSONObject("config");
            boolean var9 = var8.getBooleanValue("allowExternalLink");
            if (!var9) {
                throw new JeecgBootException("已禁止访问此链接");
            }
        }

        return this.queryFormViewData(var6, dataId, onlineDataId, modelAndView);
    }

    public DesignForm queryAllDictItem(DesignForm designForm) {
        String var2 = designForm.getDesformDesignJson();
        if (StringUtils.isEmpty(var2)) {
            return designForm;
        } else {
            JSONObject var3 = JSONObject.parseObject(var2);
            JSONArray var4 = var3.getJSONArray("list");

            for(int var5 = 0; var5 < var4.size(); ++var5) {
                JSONObject var6 = var4.getJSONObject(var5);
                String var7 = var6.getString("type");
                if (!"grid".equals(var7) && !"sub-table-design".equals(var7)) {
                    this.a(var6);
                } else {
                    JSONArray var8 = var6.getJSONArray("columns");

                    for(int var9 = 0; var9 < var8.size(); ++var9) {
                        JSONArray var10 = var8.getJSONObject(var9).getJSONArray("list");

                        for(int var11 = 0; var11 < var10.size(); ++var11) {
                            this.a(var10.getJSONObject(var11));
                        }
                    }
                }
            }

            designForm.setDesformDesignJson(var3.toJSONString());
            return designForm;
        }
    }

    private void a(JSONObject var1) {
        String var2 = var1.getString("type");
        if ("select".equals(var2)) {
            JSONArray var3 = this.b(var1);
            if (var3 != null) {
                JSONObject var4 = var1.getJSONObject("options");
                var4.put("remote", false);
                var4.put("showLabel", true);
                var4.put("options", var3);
            }
        }

    }

    private JSONArray b(JSONObject var1) {
        try {
            JSONObject var2 = var1.getJSONObject("options");
            String var3 = var2.getString("remote");
            if ("dict".equals(var3)) {
                String var4 = var2.getString("dictCode");
                List var5 = this.sysBaseApi.queryDictItemsByCode(var4);
                JSONArray var6 = new JSONArray();
                Iterator var7 = var5.iterator();

                while(var7.hasNext()) {
                    DictModel var8 = (DictModel)var7.next();
                    JSONObject var9 = new JSONObject();
                    var9.put("label", var8.getText());
                    var9.put("value", var8.getValue());
                    var6.add(var9);
                }

                return var6;
            }
        } catch (SerializationException var10) {
        }

        return null;
    }

    public Result redoAllIndex(String rowIds, Integer pageSize, boolean batchSave) {
        return pageSize == null ? this.a(rowIds, (Integer)null, (Integer)null, batchSave) : this.a(rowIds, 1, pageSize, batchSave);
    }

    private Result a(String var1, Integer var2, Integer var3, boolean var4) {
        try {
            long var5 = System.currentTimeMillis();
            a.info("-- 已开始快速重置ES索引数据");
            LambdaQueryWrapper<DesignFormData> var7 = new LambdaQueryWrapper<>();
            if (JexfmBA.b(var1)) {
                var7.in(DesignFormData::getDesformId, Arrays.asList(var1.split(",")));
            }

            Page var9 = null;
            List var8;
            if (var2 != null && var3 != null) {
                var4 = true;
                var9 = (Page)this.dataService.page(new Page((long)var2, (long)var3), var7);
                var8 = var9.getRecords();
                a.info("-- 快速分页重置：（第 " + var9.getCurrent() + " 页/共 " + var9.getPages() + " 页）");
            } else {
                var8 = this.dataService.list(var7);
            }

            int var10 = 0;
            int var11 = 0;
            String var14;
            if (!var4) {
                for(int var12 = 0; var12 < var8.size(); ++var12) {
                    DesignFormData var13 = (DesignFormData)var8.get(var12);
                    var14 = var13.getDesformCode();
                    a.info("---- 正在重置：" + var14 + "（第 " + (var12 + 1) + " 个/共 " + var8.size() + " 个）");
                    JSONObject var15 = JSON.parseObject(var13.getDesformDataJson());
                    if (this.jes.saveOrUpdate(var14, "design_form", var13.getId(), var15)) {
                        ++var10;
                    } else {
                        ++var11;
                    }
                }
            } else {
                HashMap var19 = new HashMap();
                Iterator var21 = var8.iterator();

                while(var21.hasNext()) {
                    DesignFormData var22 = (DesignFormData)var21.next();
                    String var23 = var22.getDesformCode();
                    JSONObject var16 = JSON.parseObject(var22.getDesformDataJson());
                    var16.put("id", var22.getId());
                    JSONArray var17 = (JSONArray)var19.computeIfAbsent(var23, (var0) -> {
                        return new JSONArray();
                    });
                    var17.add(var16);
                }

                var21 = var19.keySet().iterator();

                while(var21.hasNext()) {
                    var14 = (String)var21.next();
                    JSONArray var24 = (JSONArray)var19.get(var14);
                    a.info("---- 正在批量重置：" + var14 + "（共 " + var24.size() + " 条数据）");
                    this.jes.saveBatch(var14, "design_form", var24);
                }
            }

            long var20 = System.currentTimeMillis();
            if (var11 == 0) {
                var14 = "快速重置ES索引全部成功!";
            } else {
                var14 = String.format("快速重置ES索引：成功了%s条，失败了%s条", var10, var11);
            }

            a.info("-- " + var14);
            a.info("-- 总耗时：" + (var20 - var5) + " ms");
            return var9 != null && var9.hasNext() ? this.a(var1, var2 + 1, var3, var4) : Result.ok();
        } catch (Exception var18) {
            var18.printStackTrace();
            return Result.error(var18.getMessage());
        }
    }

    public Result redoAllIndexForce(String rowIds, Integer pageSize) {
        try {
            a.info("-- 已开始强制重置ES索引");
            LambdaQueryWrapper<DesignForm> var3 = new LambdaQueryWrapper<>();
            if (JexfmBA.b(rowIds)) {
                var3.in(DesignForm::getId, Arrays.asList(rowIds.split(",")));
            }

            List var4 = super.list(var3);
            long var5 = System.currentTimeMillis();

            for(int var7 = 0; var7 < var4.size(); ++var7) {
                DesignForm var8 = (DesignForm)var4.get(var7);
                String var9 = var8.getDesformCode();
                a.info("---- 正在重置：" + var9 + "（第 " + (var7 + 1) + " 个/共 " + var4.size() + " 个）");
                this.jes.removeIndex(var9);
                this.jes.createIndex(var9);
            }

            long var11 = System.currentTimeMillis();
            a.info("-- 强制重置ES索引完成，即将开始快速重置数据");
            a.info("-- 总耗时：" + (var11 - var5) + " ms");
            return this.redoAllIndex(rowIds, pageSize, true);
        } catch (Exception var10) {
            var10.printStackTrace();
            return Result.error(var10.getMessage());
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateDefMobileViewStatus(String parentCode, String desformCode) {
        DesignForm var3 = new DesignForm();
        QueryWrapper var4 = new QueryWrapper();
        var3.setIzMobileView(DesformConstant.IS_MOBILE_VIEW_N);
        ((QueryWrapper)((QueryWrapper)((QueryWrapper)var4.eq("iz_mobile_view", DesformConstant.IS_MOBILE_VIEW_Y)).eq("desform_code", parentCode)).or()).eq("parent_code", parentCode);
        super.update(var3, var4);
        var3.setIzMobileView(DesformConstant.IS_MOBILE_VIEW_Y);
        var4 = (QueryWrapper)(new QueryWrapper()).eq("desform_code", desformCode);
        super.update(var3, var4);
        return true;
    }

    public Map<String, List<DictModel>> translateColumns(List<TranslateData> translateDataList) {
        HashMap var2 = new HashMap();
        ArrayList var3 = new ArrayList();
        ArrayList var4 = new ArrayList();
        Iterator var5 = translateDataList.iterator();

        while(var5.hasNext()) {
            TranslateData var6 = (TranslateData)var5.next();
            WidgetTypes var7 = var6.getType();
            String var8 = var6.getCustomReturnField();
            if (var7 == WidgetTypes.SELECT_USER) {
                var3.add(var6);
            } else if (var7 == WidgetTypes.SELECT_DEPART) {
                var4.add(var6);
            }
        }

        String var9;
        String var10;
        List var11;
        if (var3.size() > 0) {
            var9 = ((TranslateData)var3.get(0)).getCustomReturnField();
            var10 = this.a((List)var3);
            var11 = null;
            if ("id".equals(var9)) {
                var11 = this.sysBaseApi.queryUsersByIds(var10);
            } else if ("username".equals(var9)) {
                var11 = this.sysBaseApi.queryUsersByIds(var10);
            }

            a.info("usernames: " + var10);
            this.a(var2, var3, var11, "realname");
        }

        if (var4.size() > 0) {
            var9 = ((TranslateData)var4.get(0)).getCustomReturnField();
            var10 = this.a((List)var4);
            a.info("orgIds: " + var10);
            var11 = null;
            if ("id".equals(var9)) {
                var11 = this.sysBaseApi.queryDepartsByIds(var10);
            } else if ("orgCode".equals(var9)) {
                var11 = this.sysBaseApi.queryDepartsByOrgcodes(var10);
            }

            this.a(var2, var4, var11, "departName");
        }

        return var2;
    }

    private String a(List<TranslateData> var1) {
        HashMap var2 = new HashMap();
        Iterator var3 = var1.iterator();

        String var5;
        while(var3.hasNext()) {
            TranslateData var4 = (TranslateData)var3.next();
            var5 = var4.getCustomReturnField();
            List var6 = (List)var2.computeIfAbsent(var5, (var0) -> {
                return new ArrayList();
            });
            var6.addAll(var4.getDataList());
        }

        LinkedHashSet var7 = new LinkedHashSet();
        Iterator var8 = var2.keySet().iterator();

        while(var8.hasNext()) {
            var5 = (String)var8.next();
            var7.addAll((Collection)var2.get(var5));
        }

        return StringUtils.join(var7.toArray(), ",");
    }

    private QueryWrapper b(List<TranslateData> var1) {
        QueryWrapper var2 = new QueryWrapper();
        HashMap var3 = new HashMap();
        Iterator var4 = var1.iterator();

        String var6;
        while(var4.hasNext()) {
            TranslateData var5 = (TranslateData)var4.next();
            var6 = var5.getCustomReturnField();
            List var7 = (List)var3.computeIfAbsent(var6, (var0) -> {
                return new ArrayList();
            });
            var7.addAll(var5.getDataList());
        }

        var4 = var3.keySet().iterator();

        while(var4.hasNext()) {
            String var8 = (String)var4.next();
            var6 = oConvertUtils.camelToUnderline(var8);
            ((QueryWrapper)var2.in(var6, (Collection)var3.get(var8))).or();
        }

        return var2;
    }

    private void a(Map<String, List<DictModel>> var1, List<TranslateData> var2, List<JSONObject> var3, String var4) {
        Iterator var5 = var2.iterator();

        label33:
        while(var5.hasNext()) {
            TranslateData var6 = (TranslateData)var5.next();
            Iterator var7 = var6.getDataList().iterator();

            while(true) {
                while(true) {
                    if (!var7.hasNext()) {
                        continue label33;
                    }

                    String var8 = (String)var7.next();
                    Iterator var9 = var3.iterator();

                    while(var9.hasNext()) {
                        JSONObject var10 = (JSONObject)var9.next();
                        String var11 = var10.getString(var6.getCustomReturnField());
                        if (var11 != null && var11.equals(var8)) {
                            String var12 = var10.getString(var4);
                            List var13 = (List)var1.computeIfAbsent(var6.getKey(), (var0) -> {
                                return new ArrayList();
                            });
                            var13.add(new DictModel(var11, var12));
                            break;
                        }
                    }
                }
            }
        }

    }
}
