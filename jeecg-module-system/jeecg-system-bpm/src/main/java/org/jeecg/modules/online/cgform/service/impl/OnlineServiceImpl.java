package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.d.EnhanceJsUtil;
import org.jeecg.modules.online.cgform.d.j;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.HrefSlots;
import org.jeecg.modules.online.cgform.model.OnlColumn;
import org.jeecg.modules.online.cgform.model.OnlComplexModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("onlineService")
public class OnlineServiceImpl implements IOnlineService {
    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;
    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IOnlAuthPageService onlAuthPageService;
    static String[] a = null;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v212, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v218, types: [java.util.List] */
    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public OnlComplexModel queryOnlineConfig(OnlCgformHead head, String username) {
        JSONObject parseObject;
        String id = head.getId();
        List<OnlCgformField> onlCgformFields = getCgformField(id);
        List<String> queryHideCode = this.onlAuthPageService.queryHideCode(id, true);
        List<OnlColumn> columns = new ArrayList<>();
        Map<String, List<DictModel>> dictOptions = new HashMap<>();
        List<HrefSlots> fieldHrefSlots = new ArrayList<>();
        List<org.jeecg.modules.online.cgform.model.b> foreignKeys = new ArrayList<>();
        List arrayList4 = new ArrayList();
        for (OnlCgformField onlCgformField : onlCgformFields) {
            String dbFieldName = onlCgformField.getDbFieldName();
            String mainTable = onlCgformField.getMainTable();
            String mainField = onlCgformField.getMainField();
            if (oConvertUtils.isNotEmpty(mainField) && oConvertUtils.isNotEmpty(mainTable)) {
                foreignKeys.add(new org.jeecg.modules.online.cgform.model.b(dbFieldName, mainField));
            }
            if (1 == onlCgformField.getIsShowList() && !"id".equals(dbFieldName) && !queryHideCode.contains(dbFieldName) && !arrayList4.contains(dbFieldName)) {
                OnlColumn onlColumn = new OnlColumn(onlCgformField.getDbFieldTxt(), dbFieldName);
                String dictField = onlCgformField.getDictField();
                String fieldShowType = onlCgformField.getFieldShowType();
                if (oConvertUtils.isNotEmpty(dictField) && !org.jeecg.modules.online.cgform.d.b.POPUP.equals(fieldShowType)) {
                    List<DictModel> dictModels = new ArrayList<>();
                    // TODO 这里字典转换后期量大效率太低，需要改进，可以固定字典的走这里，表字典跟前端走
                    if (oConvertUtils.isNotEmpty(onlCgformField.getDictTable())) {
                        dictModels = this.sysBaseAPI.queryTableDictItemsByCode(onlCgformField.getDictTable(), onlCgformField.getDictText(), dictField);
                    } else if (oConvertUtils.isNotEmpty(onlCgformField.getDictField())) {
                        // FIXME 如果表字典配置到这里，但是没有加#{}规则，会有缓存问题，请注意！暂时先让带条件的表字典配置（table_name,text,id,condition 即4长度）不走缓存
                        dictModels = this.sysBaseAPI.queryDictItemsByCode(dictField);
                    }
                    dictOptions.put(dbFieldName, dictModels);
                    onlColumn.setCustomRender(dbFieldName);
                }
                if (org.jeecg.modules.online.cgform.d.b.sI.equals(fieldShowType)) {
                    dictOptions.put(dbFieldName, org.jeecg.modules.online.cgform.d.b.a(onlCgformField));
                    onlColumn.setCustomRender(dbFieldName);
                }
                if (org.jeecg.modules.online.cgform.d.b.sP.equals(fieldShowType)) {
                    org.jeecg.modules.online.cgform.a.a aVar = JSONObject.parseObject(onlCgformField.getDictTable(), org.jeecg.modules.online.cgform.a.a.class);
                    dictOptions.put(dbFieldName, this.sysBaseAPI.queryTableDictItemsByCode(aVar.getTable(), aVar.getTxt(), aVar.getKey()));
                    onlColumn.setCustomRender(dbFieldName);
                    columns.add(onlColumn);
                    getCgformField(onlCgformFields, arrayList4, columns, dbFieldName, aVar.getLinkField());
                }
                if (org.jeecg.modules.online.cgform.d.b.sN.equals(fieldShowType)) {
                    String[] split = onlCgformField.getDictText().split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
                    dictOptions.put(dbFieldName, this.sysBaseAPI.queryTableDictItemsByCode(onlCgformField.getDictTable(), split[2], split[0]));
                    onlColumn.setCustomRender(dbFieldName);
                }
                if (org.jeecg.modules.online.cgform.d.b.CAT_TREE.equals(fieldShowType)) {
                    String dictText = onlCgformField.getDictText();
                    if (oConvertUtils.isEmpty(dictText)) {
                        dictOptions.put(dbFieldName, this.sysBaseAPI.queryFilterTableDictInfo(org.jeecg.modules.online.cgform.d.b.sW, org.jeecg.modules.online.cgform.d.b.sX, "ID", org.jeecg.modules.online.cgform.d.b.e(onlCgformField.getDictField())));
                        onlColumn.setCustomRender(dbFieldName);
                    } else {
                        onlColumn.setCustomRender("_replace_text_" + dictText);
                    }
                }
                if ("sel_depart".equals(fieldShowType)) {
                    dictOptions.put(dbFieldName, this.sysBaseAPI.queryAllDepartBackDictModel());
                    onlColumn.setCustomRender(dbFieldName);
                }
                if ("sel_user".equals(onlCgformField.getFieldShowType())) {
                    dictOptions.put(dbFieldName, this.sysBaseAPI.queryTableDictItemsByCode(org.jeecg.modules.online.cgform.d.b.sQ, org.jeecg.modules.online.cgform.d.b.sR, org.jeecg.modules.online.cgform.d.b.S));
                    onlColumn.setCustomRender(dbFieldName);
                }
                if (fieldShowType.indexOf("file") >= 0) {
                    onlColumn.setScopedSlots(new org.jeecg.modules.online.cgform.model.c(j.a));
                } else if (fieldShowType.indexOf("image") >= 0) {
                    onlColumn.setScopedSlots(new org.jeecg.modules.online.cgform.model.c(j.b));
                } else if (fieldShowType.indexOf(org.jeecg.modules.online.cgform.d.i.EDITOR) >= 0) {
                    onlColumn.setScopedSlots(new org.jeecg.modules.online.cgform.model.c(j.c));
                } else if (fieldShowType.equals(org.jeecg.modules.online.cgform.d.i.DATE)) {
                    onlColumn.setScopedSlots(new org.jeecg.modules.online.cgform.model.c(j.d));
                } else if (fieldShowType.equals(org.jeecg.modules.online.cgform.d.i.PCA)) {
                    onlColumn.setScopedSlots(new org.jeecg.modules.online.cgform.model.c(j.f));
                }
                String fieldExtendJson = onlCgformField.getFieldExtendJson();
                if ("1".equals(onlCgformField.getSortFlag())) {
                    onlColumn.setSorter(true);
                    if (org.jeecg.modules.online.cgform.d.c.b(fieldExtendJson) && fieldExtendJson.indexOf(org.jeecg.modules.online.cgform.d.b.orderRule) > 0 && (parseObject = JSON.parseObject(fieldExtendJson)) != null && parseObject.get(org.jeecg.modules.online.cgform.d.b.orderRule) != null) {
                        onlColumn.setSorterType(oConvertUtils.getString(parseObject.get(org.jeecg.modules.online.cgform.d.b.orderRule)).toString());
                    }
                }
                if (org.jeecg.modules.online.cgform.d.c.b(fieldExtendJson) && fieldExtendJson.indexOf(org.jeecg.modules.online.cgform.d.b.ax) > 0 && (parseObject = JSON.parseObject(fieldExtendJson)) != null && parseObject.get(org.jeecg.modules.online.cgform.d.b.ax) != null) {
                    onlColumn.setShowLength(oConvertUtils.getInt(parseObject.get(org.jeecg.modules.online.cgform.d.b.ax)).intValue());
                }
                if (!org.jeecg.modules.online.cgform.d.b.sP.equals(fieldShowType)) {
                    columns.add(onlColumn);
                }
            }
        }
        OnlComplexModel onlComplexModel = new OnlComplexModel();
        onlComplexModel.setCode(id);
        onlComplexModel.setTableType(head.getTableType());
        onlComplexModel.setFormTemplate(head.getFormTemplate());
        onlComplexModel.setDescription(head.getTableTxt());
        onlComplexModel.setCurrentTableName(head.getTableName());
        onlComplexModel.setPaginationFlag(head.getIsPage());
        onlComplexModel.setCheckboxFlag(head.getIsCheckbox());
        onlComplexModel.setScrollFlag(head.getScroll());
        onlComplexModel.setRelationType(head.getRelationType());
        onlComplexModel.setColumns(columns);
        onlComplexModel.setDictOptions(dictOptions);
        onlComplexModel.setFieldHrefSlots(fieldHrefSlots);
        onlComplexModel.setForeignKeys(foreignKeys);
        onlComplexModel.setHideColumns(queryHideCode);
        List<OnlCgformButton> queryButtonList = this.onlCgformHeadService.queryButtonList(id, true);
        List<OnlCgformButton> cgButtonList = new ArrayList<>();
        for (OnlCgformButton onlCgformButton : queryButtonList) {
            if (!queryHideCode.contains(onlCgformButton.getButtonCode())) {
                cgButtonList.add(onlCgformButton);
            }
        }
        onlComplexModel.setCgButtonList(cgButtonList);
        OnlCgformEnhanceJs queryEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(id, org.jeecg.modules.online.cgform.d.b.aj);
        if (queryEnhanceJs != null && oConvertUtils.isNotEmpty(queryEnhanceJs.getCgJs())) {
            onlComplexModel.setEnhanceJs(EnhanceJsUtil.b(queryEnhanceJs.getCgJs(), queryButtonList));
        }
        if ("Y".equals(head.getIsTree())) {
            onlComplexModel.setPidField(head.getTreeParentIdField());
            onlComplexModel.setHasChildrenField(head.getTreeIdField());
            onlComplexModel.setTextField(head.getTreeFieldname());
        }
        return onlComplexModel;
    }

    private void getCgformField(List<OnlCgformField> list, List<String> list2, List<OnlColumn> list3, String str, String str2) {
        String[] split;
        if (oConvertUtils.isNotEmpty(str2)) {
            for (String str3 : str2.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                Iterator<OnlCgformField> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        OnlCgformField next = it.next();
                        String dbFieldName = next.getDbFieldName();
                        if (1 == next.getIsShowList().intValue() && str3.equals(dbFieldName)) {
                            list2.add(str3);
                            OnlColumn onlColumn = new OnlColumn(next.getDbFieldTxt(), dbFieldName);
                            onlColumn.setCustomRender(str);
                            list3.add(onlColumn);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public JSONObject queryOnlineFormObj(OnlCgformHead head, OnlCgformEnhanceJs onlCgformEnhanceJs) {
        JSONObject jSONObject = new JSONObject();
        String id = head.getId();
        String taskId = head.getTaskId();
        List<OnlCgformField> queryAvailableFields = this.onlCgformFieldService.queryAvailableFields(id, head.getTableName(), taskId, false);
        ArrayList arrayList = new ArrayList();
        if (oConvertUtils.isEmpty(taskId)) {
            List<String> queryFormDisabledCode = this.onlAuthPageService.queryFormDisabledCode(head.getId());
            if (queryFormDisabledCode != null && queryFormDisabledCode.size() > 0 && queryFormDisabledCode.get(0) != null) {
                arrayList.addAll(queryFormDisabledCode);
            }
        } else {
            List<String> queryDisabledFields = this.onlCgformFieldService.queryDisabledFields(head.getTableName(), taskId);
            if (queryDisabledFields != null && queryDisabledFields.size() > 0 && queryDisabledFields.get(0) != null) {
                arrayList.addAll(queryDisabledFields);
            }
        }
        EnhanceJsUtil.a(onlCgformEnhanceJs, head.getTableName(), queryAvailableFields);
        org.jeecg.modules.online.cgform.model.d dVar = null;
        if ("Y".equals(head.getIsTree())) {
            dVar = new org.jeecg.modules.online.cgform.model.d();
            dVar.setCodeField("id");
            dVar.setFieldName(head.getTreeParentIdField());
            dVar.setPidField(head.getTreeParentIdField());
            dVar.setPidValue("0");
            dVar.setHsaChildField(head.getTreeIdField());
            dVar.setTableName(org.jeecg.modules.online.cgform.d.b.f(head.getTableName()));
            dVar.setTextField(head.getTreeFieldname());
        }
        JSONObject a2 = org.jeecg.modules.online.cgform.d.b.a(queryAvailableFields, arrayList, dVar);
        a2.put("table", head.getTableName());
        a2.put("describe", head.getTableTxt());
        jSONObject.put("schema", a2);
        jSONObject.put("head", head);
        List<OnlCgformButton> queryFormValidButton = queryFormValidButton(id);
        if (queryFormValidButton != null && queryFormValidButton.size() > 0) {
            jSONObject.put("cgButtonList", queryFormValidButton);
        }
        if (onlCgformEnhanceJs != null && oConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs())) {
            onlCgformEnhanceJs.setCgJs(EnhanceJsUtil.c(onlCgformEnhanceJs.getCgJs(), queryFormValidButton));
            jSONObject.put("enhanceJs", EnhanceJsUtil.a(onlCgformEnhanceJs.getCgJs()));
        }
        return jSONObject;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public JSONObject queryOnlineFormObj(OnlCgformHead head, String username) {
        return queryOnlineFormObj(head, this.onlCgformHeadService.queryEnhanceJs(head.getId(), org.jeecg.modules.online.cgform.d.b.form));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public List<OnlCgformButton> queryFormValidButton(String headId) {
        List<OnlCgformButton> queryButtonList = this.onlCgformHeadService.queryButtonList(headId, false);
        List<OnlCgformButton> list = null;
        if (queryButtonList != null && queryButtonList.size() > 0) {
            List<String> queryFormHideButton = this.onlAuthPageService.queryFormHideButton(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), headId);
            list = (List) queryButtonList.stream().filter(onlCgformButton -> {
                return queryFormHideButton == null || queryFormHideButton.indexOf(onlCgformButton.getButtonCode()) < 0;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public JSONObject queryOnlineFormItem(OnlCgformHead head, String username) {
        head.setTaskId(null);
        return getCgformField(head);
    }

    static {
//        a();
    }

//    public static void a() {
//        try {
//            if (a == null || a.length == 0) {
//                ResourceBundle a2 = org.jeecg.modules.online.cgform.d.c.a();
//                if (a2 == null) {
//                    a2 = ResourceBundle.getBundle(org.jeecg.modules.online.cgform.d.g.d());
//                }
//                if (StreamUtils.isr()) {
//                    a = new String[]{StringUtil.dl()};
//                } else {
//                    a = a2.getString(org.jeecg.modules.online.cgform.d.g.f()).split(org.jeecg.modules.online.cgform.d.b.DOT_STRING);
//                }
//            }
//            if (!org.jeecg.modules.online.cgform.d.c.b(a, org.jeecg.modules.online.cgform.d.h.b()) && !org.jeecg.modules.online.cgform.d.c.b(a, org.jeecg.modules.online.cgform.d.h.a())) {
//                System.err.println(org.jeecg.modules.online.cgform.d.g.h() + org.jeecg.modules.online.cgform.d.h.c());
//                System.err.println(org.jeecg.modules.online.cgform.d.f.c("kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=", "123456"));
//                System.exit(0);
//            }
//        } catch (Exception e) {
//            try {
//                System.err.println(org.jeecg.modules.online.cgform.d.g.h() + org.jeecg.modules.online.cgform.d.h.c());
//                System.err.println(org.jeecg.modules.online.cgform.d.f.c("kOzlPKX8Es8/FwQdXX60NZDs5Tyl/BLPPxcEHV1+tDWQ7OU8pfwSzz8XBB1dfrQ1iN5zqKZaPYep6qn8X+n2QLKRXOKp2vFQjVEFS7T75CGCnTDPggguLlLiSRDR+DN00a2paweobc4zkqYP/0gU+d4jODOhQvZ9DFAsE4kQZpsXOzNfRrTeD+fWUnMFtAcuP5wfzEsz9Gm/ENqSW/sf0JQaTCWAxHmqaMpauPUZXNWEiuzRhMaXSDRmD4nV1DOcTqvDj5BtUWWdnJQV+by4VjVceI6gYuC5E3R+m4p6QG4wiASRPe+mpGacCousLwjL6a6Zx+iA2MXhQiPM6WjQTWIWA3TKwu105/ojzopukCuQ7OU8pfwSzz8XBB1dfrQ1kOzlPKX8Es8/FwQdXX60Nb+YHwc5536J89tvlGzFHGI=", "123456"));
//                System.exit(0);
//            } catch (Exception e2) {
//            }
//        }
//    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public JSONObject queryFlowOnlineFormItem(OnlCgformHead head, String username, String taskId) {
        head.setTaskId(taskId);
        return getCgformField(head);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public String queryEnahcneJsString(String code, String type) {
        String str = "";
        OnlCgformEnhanceJs queryEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(code, type);
        if (queryEnhanceJs != null && oConvertUtils.isNotEmpty(queryEnhanceJs.getCgJs())) {
            str = EnhanceJsUtil.b(queryEnhanceJs.getCgJs(), (List<OnlCgformButton>) null);
        }
        return str;
    }

    private List<OnlCgformField> getCgformField(String str) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        return this.onlCgformFieldService.list(lambdaQueryWrapper);
    }

    private JSONObject getCgformField(OnlCgformHead onlCgformHead) {
        List<String> queryFormDisabledCode;
        OnlCgformEnhanceJs queryEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(onlCgformHead.getId(), org.jeecg.modules.online.cgform.d.b.form);
        JSONObject queryOnlineFormObj = queryOnlineFormObj(onlCgformHead, queryEnhanceJs);
        queryOnlineFormObj.put("formTemplate", onlCgformHead.getFormTemplate());
        if (onlCgformHead.getTableType().intValue() == 2) {
            JSONObject jSONObject = queryOnlineFormObj.getJSONObject("schema");
            String subTableStr = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                ArrayList<OnlCgformHead> arrayList = new ArrayList();
                for (String str : subTableStr.split(org.jeecg.modules.online.cgform.d.b.DOT_STRING)) {
                    OnlCgformHead onlCgformHead2 = (OnlCgformHead) this.onlCgformHeadService.getOne((Wrapper) new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead2 != null) {
                        arrayList.add(onlCgformHead2);
                    }
                }
                if (arrayList.size() > 0) {
                    Collections.sort(arrayList, new Comparator<OnlCgformHead>() { // from class: org.jeecg.modules.online.cgform.service.impl.i.1
                        @Override // java.util.Comparator
                        /* renamed from: a */
                        public int compare(OnlCgformHead onlCgformHead3, OnlCgformHead onlCgformHead4) {
                            Integer tabOrderNum = onlCgformHead3.getTabOrderNum();
                            if (tabOrderNum == null) {
                                tabOrderNum = 0;
                            }
                            Integer tabOrderNum2 = onlCgformHead4.getTabOrderNum();
                            if (tabOrderNum2 == null) {
                                tabOrderNum2 = 0;
                            }
                            return tabOrderNum.compareTo(tabOrderNum2);
                        }
                    });
                    for (OnlCgformHead onlCgformHead3 : arrayList) {
                        List<OnlCgformField> queryAvailableFields = this.onlCgformFieldService.queryAvailableFields(onlCgformHead3.getId(), onlCgformHead3.getTableName(), onlCgformHead.getTaskId(), false);
                        EnhanceJsUtil.b(queryEnhanceJs, onlCgformHead3.getTableName(), queryAvailableFields);
                        JSONObject jSONObject2 = new JSONObject();
                        new ArrayList();
                        if (oConvertUtils.isNotEmpty(onlCgformHead.getTaskId())) {
                            queryFormDisabledCode = this.onlCgformFieldService.queryDisabledFields(onlCgformHead3.getTableName(), onlCgformHead.getTaskId());
                        } else {
                            queryFormDisabledCode = this.onlAuthPageService.queryFormDisabledCode(onlCgformHead3.getId());
                        }
                        if (1 == onlCgformHead3.getRelationType().intValue()) {
                            jSONObject2 = org.jeecg.modules.online.cgform.d.b.a(queryAvailableFields, queryFormDisabledCode, (org.jeecg.modules.online.cgform.model.d) null);
                        } else {
                            jSONObject2.put("columns", org.jeecg.modules.online.cgform.d.b.a(queryAvailableFields, queryFormDisabledCode));
                            jSONObject2.put("hideButtons", this.onlAuthPageService.queryListHideButton(null, onlCgformHead3.getId()));
                        }
                        jSONObject2.put("foreignKey", this.onlCgformFieldService.queryForeignKey(onlCgformHead3.getId(), onlCgformHead.getTableName()));
                        jSONObject2.put("id", onlCgformHead3.getId());
                        jSONObject2.put("describe", onlCgformHead3.getTableTxt());
                        jSONObject2.put("key", onlCgformHead3.getTableName());
                        jSONObject2.put("view", "tab");
                        jSONObject2.put("order", onlCgformHead3.getTabOrderNum());
                        jSONObject2.put("relationType", onlCgformHead3.getRelationType());
                        jSONObject2.put("formTemplate", onlCgformHead3.getFormTemplate());
                        jSONObject.getJSONObject("properties").put(onlCgformHead3.getTableName(), jSONObject2);
                    }
                }
            }
            if (queryEnhanceJs != null && oConvertUtils.isNotEmpty(queryEnhanceJs.getCgJs())) {
                queryOnlineFormObj.put("enhanceJs", EnhanceJsUtil.a(queryEnhanceJs.getCgJs()));
            }
        }
        return queryOnlineFormObj;
    }
}
