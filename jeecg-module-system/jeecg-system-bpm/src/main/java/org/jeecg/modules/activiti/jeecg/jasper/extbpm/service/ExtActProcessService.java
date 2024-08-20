package org.jeecg.modules.activiti.jeecg.jasper.extbpm.service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.jeecg.commons.lang.MyStreamUtils;
import org.jeecg.modules.extbpm.process.common.ProcDealStyleEnum;
import org.jeecg.modules.extbpm.process.entity.*;
import org.jeecg.modules.extbpm.process.exception.BpmException;
import org.jeecg.modules.extbpm.process.mapper.*;
import org.jeecg.modules.extbpm.process.pojo.DesignFormDataDTO;
import org.jeecg.modules.extbpm.process.pojo.RoleInfo;
import org.jeecg.modules.extbpm.process.pojo.UserInfo;
import org.jeecg.modules.extbpm.process.service.IExtActProcessService;
import org.jeecgframework.designer.util.DesUtils;
import org.jeecgframework.designer.vo.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Blob;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//ImplK 的
@Service("extActProcessService")
public class ExtActProcessService extends ServiceImpl<ExtActProcessMapper, ExtActProcess> implements IExtActProcessService {
    private static final Logger a = LoggerFactory.getLogger(ExtActProcessService.class);
    @Autowired
    private ExtActProcessMapper extActProcessMapper;
    @Autowired
    private ExtActProcessFormMapper extActProcessFormMapper;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    private ExtActProcessNodeMapper extActProcessNodeMapper;
    @Autowired
    private ExtActProcessNodeDeploymentMapper extActProcessNodeDeploymentMapper;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ExtActFlowDataMapper extActFlowDataMapper;
    @Autowired
    private ExtActDesignFlowDataMapper extActDesignFlowDataMapper;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    public ExtActProcessService() {
    }

    public List<UserInfo> getBpmUsers() {
        return this.extActProcessMapper.getBpmUsers();
    }

    public Page<UserInfo> getPageBpmUsers(String account, String name, Page<UserInfo> page) {
        return page.setRecords(this.extActProcessMapper.getPageBpmUsers(page, account, name));
    }

    public List<RoleInfo> getBpmRoles() {
        return this.extActProcessMapper.getBpmRoles();
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void deployProcess(ExtActProcess extActProcess) throws Exception {
        Deployment var2 = this.repositoryService.createDeployment().addInputStream(extActProcess.getProcessKey() + ".bpmn", MyStreamUtils.byteTOInputStream(extActProcess.getProcessXml())).name(extActProcess.getProcessKey()).deploy();
        extActProcess.setProcessStatus(org.jeecg.modules.extbpm.process.common.a.b);
        LambdaQueryWrapper<ExtActProcessNode> var3 = new LambdaQueryWrapper<>();
        var3.eq(ExtActProcessNode::getProcessId, extActProcess.getId());
        List var4 = this.extActProcessNodeMapper.selectList(var3);
        if (var4 != null && var4.size() > 0) {
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
                ExtActProcessNode var6 = (ExtActProcessNode)var5.next();
                ExtActProcessNodeDeployment var7 = new ExtActProcessNodeDeployment();
                var7.setDeploymentId(var2.getId());
                var7.setFormId(var6.getFormId());
                var7.setModelAndView(var6.getModelAndView());
                var7.setModelAndViewMobile(var6.getModelAndViewMobile());
                var7.setNodeTimeout(var6.getNodeTimeout());
                var7.setProcessId(var6.getProcessId());
                var7.setProcessNodeCode(var6.getProcessNodeCode());
                var7.setProcessNodeName(var6.getProcessNodeName());
                this.extActProcessNodeDeploymentMapper.insert(var7);
            }
        }

        this.extActProcessMapper.updateById(extActProcess);
    }

    public void cleanBpmUser() {
        this.extActProcessMapper.cleanBpmActIdMembership();
        this.extActProcessMapper.cleanBpmActIdUser();
        this.extActProcessMapper.cleanBpmActIdGroup();
    }

    public List<UserInfo> getSysUsers() {
        return this.extActProcessMapper.getSysUsers();
    }

    public List<RoleInfo> getSysRolesByUserId(String userId) {
        return this.extActProcessMapper.getSysRolesByUserId(userId);
    }

    public void saveActIdMembership(UserInfo user, List<RoleInfo> roleList) {
        UserQuery var3 = this.identityService.createUserQuery();
        List var4 = var3.userId(user.getId()).list();
        User var5 = null;
        if (var4.size() == 1) {
            var5 = (User)var4.get(0);
        } else {
            if (var4.size() > 1) {
                String var6 = "发现重复用户：id=" + user.getId();
                throw new RuntimeException(var6);
            }

            var5 = this.identityService.newUser(user.getId());
        }

        this.a(user, var5);
        this.a(roleList);
        this.a(user, roleList);
    }

    private void a(UserInfo var1, User var2) {
        var2.setFirstName(var1.getFirstName());
        var2.setLastName(var1.getFirstName());
        var2.setEmail(var1.getEmail());
        this.identityService.saveUser(var2);
    }

    private void a(List<RoleInfo> var1) {
        GroupQuery var2 = this.identityService.createGroupQuery();
        Iterator var3 = var1.iterator();

        while(var3.hasNext()) {
            RoleInfo var4 = (RoleInfo)var3.next();
            List var5 = var2.groupId(var4.getId()).list();
            if (var5.size() <= 0) {
                Group var6 = this.identityService.newGroup(var4.getId());
                var6.setId(var4.getId());
                var6.setName(var4.getName());
                this.identityService.saveGroup(var6);
            }
        }

    }

    private void a(UserInfo var1, List<RoleInfo> var2) {
        List var3 = this.identityService.createGroupQuery().groupMember(var1.getId()).list();
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            Group var5 = (Group)var4.next();
            a.debug("delete group from activit: {}", var5);
            this.identityService.deleteMembership(var1.getId(), var5.getId());
        }

        var4 = var2.iterator();

        while(var4.hasNext()) {
            RoleInfo var6 = (RoleInfo)var4.next();
            this.identityService.createMembership(var1.getId(), var6.getId());
        }

    }

    public Map<String, Object> getDataById(String tableName, String id) {
        HashMap var3 = new HashMap();
        Map var4 = this.extActProcessMapper.getDataById(tableName, id);
        Iterator var5 = var4.entrySet().iterator();

        while(true) {
            Entry var6;
            do {
                if (!var5.hasNext()) {
                    return var3;
                }

                var6 = (Entry)var5.next();
            } while(var6.getValue() != null && (var6.getValue() instanceof Clob || var6.getValue() instanceof Blob));

            var3.put(((String)var6.getKey()).toLowerCase(), var6.getValue());
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public ProcessInstance startMutilProcess(String userid, String dataId, Map<String, Object> dataForm, ExtActProcessForm extActProcessForm) throws BpmException {
        ExtActFlowData var5 = new ExtActFlowData();
        var5.setBpmStatusField(extActProcessForm.getFlowStatusCol());
        var5.setFormDataId(dataId);
        var5.setFormTableName(extActProcessForm.getFormTableName());
        var5.setProcessFormId(extActProcessForm.getId());
        var5.setRelationCode(extActProcessForm.getRelationCode());
        var5.setUserId(userid);
        dataForm.put(org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.d);
        ProcessInstance var6 = this.a(userid, dataId, dataForm, extActProcessForm);
        var5.setProcessKey(var6.getProcessDefinitionKey());
        var5.setProcessInstId(var6.getProcessInstanceId());
        var5.setBpmStatus(org.jeecg.modules.extbpm.process.common.a.d);
        var5.setProcessName(var6.getProcessDefinitionName());
        var5.setProcessExpTitle(extActProcessForm.getTitleExp());
        var5.setFormType(extActProcessForm.getFormType());
        LambdaQueryWrapper<ExtActFlowData> var7 = new LambdaQueryWrapper<>();
        var7.eq(ExtActFlowData::getBpmStatusField, extActProcessForm.getFlowStatusCol());
        var7.eq(ExtActFlowData::getFormDataId, dataId);
        var7.eq(ExtActFlowData::getFormTableName, extActProcessForm.getFormTableName());
        var7.eq(ExtActFlowData::getProcessFormId, extActProcessForm.getId());
        var7.eq(ExtActFlowData::getRelationCode, extActProcessForm.getRelationCode());
        var7.eq(ExtActFlowData::getUserId, userid);
        var7.eq(ExtActFlowData::getProcessKey, var6.getProcessDefinitionKey());
        var7.isNull(ExtActFlowData::getProcessInstId);
        ExtActFlowData var8 = (ExtActFlowData)this.extActFlowDataMapper.selectOne(var7);
        if (var8 != null) {
            var5.setId(var8.getId());
            this.extActFlowDataMapper.updateById(var5);
        } else {
            this.extActFlowDataMapper.insert(var5);
        }

        String var9 = extActProcessForm.getFormTableName().toUpperCase();
        String var10 = extActProcessForm.getFlowStatusCol().toUpperCase();
        if (oConvertUtils.isEmpty(var10)) {
            var10 = "BPM_STATUS";
        }

        this.extActProcessMapper.updateBpmStatusById(var9, dataId, var10, org.jeecg.modules.extbpm.process.common.a.d);
        return var6;
    }

    public ProcessInstance startMutilProcessApi(String username, String flowCode, String id, String formUrl, String formUrlMobile) throws BpmException {
        if (oConvertUtils.isEmpty(flowCode)) {
            throw new BpmException("flowCode参数不能为空");
        } else if (oConvertUtils.isEmpty(id)) {
            throw new BpmException("id参数不能为空");
        } else if (oConvertUtils.isEmpty(formUrl)) {
            throw new BpmException("formUrl参数不能为空");
        } else if (oConvertUtils.isEmpty(username)) {
            throw new BpmException("流程发起人，参数不能为空");
        } else {
            ExtActProcessForm var6 = null;
            new HashMap();

            Map var7;
            try {
                LambdaQueryWrapper<ExtActProcessForm> var8 = new LambdaQueryWrapper<>();
                var8.eq(ExtActProcessForm::getRelationCode, flowCode);
                var6 = (ExtActProcessForm)this.extActProcessFormMapper.selectOne(var8);
                String var9 = var6.getFormTableName();
                var7 = this.getDataById(var9, id);
                var7.put(org.jeecg.modules.extbpm.process.common.a.l, id);
                var7.put(org.jeecg.modules.extbpm.process.common.a.o, formUrl);
                if (oConvertUtils.isNotEmpty(formUrlMobile)) {
                    var7.put(org.jeecg.modules.extbpm.process.common.a.p, formUrlMobile);
                } else {
                    var7.put(org.jeecg.modules.extbpm.process.common.a.p, formUrl);
                }

                var7.put(org.jeecg.modules.extbpm.process.common.a.h, var9);
            } catch (Exception var10) {
                var10.printStackTrace();
                throw new BpmException("获取流程信息异常");
            }

            ProcessInstance var11 = this.startMutilProcess(username, id, var7, var6);
            a.info("启动成功流程实例 ProcessInstance: ", var11);
            return var11;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public ExtActFlowData saveMutilProcessDraft(String userid, String dataId, ExtActProcessForm extActProcessForm) throws BpmException {
        ExtActFlowData var4 = new ExtActFlowData();
        var4.setBpmStatusField(extActProcessForm.getFlowStatusCol());
        var4.setFormDataId(dataId);
        if (extActProcessForm.getRelationCode().indexOf("desform_") != -1) {
            var4.setFormTableName("EXT_ACT_DESIGN_FLOW_DATA");
        } else {
            var4.setFormTableName(extActProcessForm.getFormTableName());
        }

        var4.setProcessFormId(extActProcessForm.getId());
        var4.setRelationCode(extActProcessForm.getRelationCode());
        var4.setBpmStatus(org.jeecg.modules.extbpm.process.common.a.c);
        ExtActProcess var5 = (ExtActProcess)this.extActProcessMapper.selectById(extActProcessForm.getProcessId());
        var4.setProcessKey(var5.getProcessKey());
        var4.setUserId(userid);
        var4.setProcessName(var5.getProcessName());
        var4.setProcessExpTitle(extActProcessForm.getTitleExp());
        var4.setFormType(extActProcessForm.getFormType());
        this.extActFlowDataMapper.insert(var4);
        return var4;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void startDesFormMutilProcess(String userid, String dataId, Map<String, Object> dataForm, ExtActProcessForm extActProcessForm) throws BpmException {
        String var5 = (String)dataForm.get(org.jeecg.modules.extbpm.process.common.a.h);
        ExtActFlowData var6 = new ExtActFlowData();
        var6.setBpmStatusField(extActProcessForm.getFlowStatusCol());
        var6.setFormDataId(dataId);
        var6.setFormTableName(var5);
        var6.setProcessFormId(extActProcessForm.getId());
        var6.setRelationCode(extActProcessForm.getRelationCode());
        var6.setUserId(userid);
        a.debug("-- startDesFormMutilProcess --- dataId： " + dataId);
        LambdaQueryWrapper<ExtActDesignFlowData> var7 = new LambdaQueryWrapper<>();
        var7.eq(ExtActDesignFlowData::getId, dataId);
        ExtActDesignFlowData var8 = (ExtActDesignFlowData)this.extActDesignFlowDataMapper.selectOne(var7);
        a.debug("-- startDesFormMutilProcess --- extActDesignFlowData： " + var8);
        dataForm.put(org.jeecg.modules.extbpm.process.common.a.n, var8.getDesformDataId());
        dataForm.put(org.jeecg.modules.extbpm.process.common.a.m, var8.getDesformCode());
        dataForm.put(org.jeecg.modules.extbpm.process.common.a.q, org.jeecg.modules.extbpm.process.common.a.d);
        ProcessInstance var9 = this.a(userid, dataId, dataForm, extActProcessForm);
        var6.setProcessKey(var9.getProcessDefinitionKey());
        var6.setProcessInstId(var9.getProcessInstanceId());
        var6.setBpmStatus(org.jeecg.modules.extbpm.process.common.a.d);
        var6.setProcessName(var9.getProcessDefinitionName());
        var6.setProcessExpTitle(extActProcessForm.getTitleExp());
        var6.setFormType(extActProcessForm.getFormType());
        LambdaQueryWrapper<ExtActFlowData> var10 = new LambdaQueryWrapper<>();
        var10.eq(ExtActFlowData::getBpmStatusField, extActProcessForm.getFlowStatusCol());
        var10.eq(ExtActFlowData::getFormDataId, dataId);
        var10.eq(ExtActFlowData::getFormTableName, var5);
        var10.eq(ExtActFlowData::getProcessFormId, extActProcessForm.getId());
        var10.eq(ExtActFlowData::getRelationCode, extActProcessForm.getRelationCode());
        var10.eq(ExtActFlowData::getUserId, userid);
        var10.eq(ExtActFlowData::getProcessKey, var9.getProcessDefinitionKey());
        var10.isNull(ExtActFlowData::getProcessInstId);
        ExtActFlowData var11 = (ExtActFlowData)this.extActFlowDataMapper.selectOne(var10);
        if (var11 != null) {
            var6.setId(var11.getId());
            this.extActFlowDataMapper.updateById(var6);
        } else {
            this.extActFlowDataMapper.insert(var6);
        }

        String var12 = oConvertUtils.getString(dataForm.get(org.jeecg.modules.extbpm.process.common.a.m));
        String var13 = oConvertUtils.getString(dataForm.get(org.jeecg.modules.extbpm.process.common.a.n));
        a.debug("------------------【表单设计器】回写物理表的流程状态------------------");
        String var14 = this.extActDesignFlowDataMapper.getDesignFormRelationTableName(var12);
        a.debug("--------【表单设计器】回写物理表的流程状态-------- desFormCode： " + var12);
        a.debug("--------【表单设计器】回写物理表的流程状态-------- bpmDesDataId： " + var13);
        a.debug("--------【表单设计器】回写物理表的流程状态-------- dbTableName： " + var14);
        DesignFormDataDTO var15 = this.extActDesignFlowDataMapper.getDesignFormDataById(var13);
        a.debug("--------【表单设计器】回写物理表的流程状态-------- designFormDataDto： " + var15);
        if (oConvertUtils.isNotEmpty(var14) && var15 != null && oConvertUtils.isNotEmpty(var15.getOnlineFormDataId())) {
            String var16 = extActProcessForm.getFlowStatusCol().toUpperCase();
            if (oConvertUtils.isEmpty(var16)) {
                var16 = "BPM_STATUS";
            }

            a.debug("--------【表单设计器】回写物理表的流程状态-------- update column： " + var16);
            a.debug("--------【表单设计器】回写物理表的流程状态-------- getOnlineFormDataId： " + var15.getOnlineFormDataId());
            this.extActProcessMapper.updateBpmStatusById(var14, var15.getOnlineFormDataId(), var16, org.jeecg.modules.extbpm.process.common.a.d);
        }

        var8.setBpmStatus(org.jeecg.modules.extbpm.process.common.a.d);
        var8.setFlowCode(extActProcessForm.getRelationCode());
        this.extActDesignFlowDataMapper.updateById(var8);
    }

    private ProcessInstance a(String var1, String var2, Map<String, Object> var3, ExtActProcessForm var4) throws BpmException {
        this.identityService.setAuthenticatedUserId(var1);
        var3.put(org.jeecg.modules.extbpm.process.common.a.x, var2);
        var3.put(org.jeecg.modules.extbpm.process.common.a.t, var4.getFormType());
        var3.put(org.jeecg.modules.extbpm.process.common.a.r, org.jeecg.modules.bpm.util.a.a(var3, var4.getTitleExp()));
        var3.put(org.jeecg.modules.extbpm.process.common.a.s, ProcDealStyleEnum.toEnum(var4.getFormDealStyle()).getCode());
        ExtActProcess var5 = (ExtActProcess)this.extActProcessMapper.selectById(var4.getProcessId());
        ProcessDefinition var6 = (ProcessDefinition)this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(var5.getProcessKey()).latestVersion().singleResult();
        if (var6 == null) {
            throw new BpmException("流程未发布，请先发布流程！");
        } else {
            String var7 = (String)var3.get(org.jeecg.modules.extbpm.process.common.a.o);
            String var8 = (String)var3.get(org.jeecg.modules.extbpm.process.common.a.p);
            var7 = org.jeecg.modules.bpm.util.a.a(var3, var7, var2);
            var8 = org.jeecg.modules.bpm.util.a.a(var3, var8, var2);
            var3.put(org.jeecg.modules.extbpm.process.common.a.o, var7);
            var3.put(org.jeecg.modules.extbpm.process.common.a.p, var8);
            ProcessInstance var9 = this.runtimeService.startProcessInstanceByKey(var5.getProcessKey(), var2, var3);
            if (var9 != null && !oConvertUtils.isEmpty(var9.getProcessInstanceId())) {
                this.runtimeService.setVariable(var9.getProcessInstanceId(), org.jeecg.modules.extbpm.process.common.a.j, var9.getProcessInstanceId());
                return var9;
            } else {
                return null;
            }
        }
    }

    public void deleteActIdMembership(String userName) {
        this.identityService.deleteUser(userName);
    }

    public List<String> getProcessKeysByProcessName(String processName) {
        return this.extActProcessMapper.getProcessKeysByProcessName(processName);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public AjaxJson saveProcess(HttpServletRequest request) throws Exception {
        AjaxJson var2 = new AjaxJson();
        String var3 = oConvertUtils.getString(request.getParameter("processDefinitionId"));
        String var4 = oConvertUtils.getString(request.getParameter("processDescriptor"));
        String var5 = oConvertUtils.getString(request.getParameter("processName"));
        String var6 = oConvertUtils.getString(request.getParameter("processkey"));
        String var7 = oConvertUtils.getString(request.getParameter("params"));
        String var8 = oConvertUtils.getString(request.getParameter("nodes"));
        String var9 = oConvertUtils.getString(request.getParameter("typeid"));
        String var10 = oConvertUtils.getString(request.getParameter("token"));
        a.info(" saveProcess 登录令牌token： " + var10);
        TokenUtils.verifyToken(request, this.sysBaseAPI, this.redisUtil);
        DesUtils.checkNodeDuplicate(var8);
        a.info(" processDefinitionId ：" + var3);
        a.info(" processDescriptor ：" + var4);
        ExtActProcess var11 = (ExtActProcess)this.extActProcessMapper.selectById(var3);
        LambdaQueryWrapper<ExtActProcess> var12;
        if ("0".equals(var3) && var11 == null) {
            var12 = new LambdaQueryWrapper<>();
            var12.eq(ExtActProcess::getProcessKey, var6);
            var11 = (ExtActProcess)this.extActProcessMapper.selectOne(var12);
        }

        if (var11 != null) {
            var11.setProcessName(var5);
            var11.setProcessKey(var6);
            var12 = new LambdaQueryWrapper();
            var12.eq(ExtActProcess::getProcessKey, var6);
            ExtActProcess var13 = (ExtActProcess)this.extActProcessMapper.selectOne(var12);
            if (var13 != null && !"0".equals(var3) && oConvertUtils.isNotEmpty(var3) && !var3.equals(var13.getId())) {
                var2.setMsg("保存流程失败，流程ID重复！");
                var2.setSuccess(false);
                return var2;
            }

            if (StringUtils.isNotEmpty(var9)) {
                var11.setProcessType(var9);
            }

            var11.setProcessXml(MyStreamUtils.StringTObyte(var4));
            this.extActProcessMapper.updateById(var11);
            var2.setMsg("流程修改成功");
        } else {
            var11 = new ExtActProcess();
            var11.setProcessName(var5);
            var11.setProcessKey(var6);
            if (StringUtils.isNotEmpty(var9)) {
                var11.setProcessType(var9);
            }

            var11.setProcessXml(MyStreamUtils.StringTObyte(var4));
            var11.setProcessStatus(org.jeecg.modules.extbpm.process.common.a.a);
            this.extActProcessMapper.insert(var11);
            var2.setMsg("流程创建成功");
            var2.setObj(var11.getId());
        }

        if (var8 != null && var8.length() > 3) {
            String[] var18 = var8.split("@@@");

            for(int var19 = 0; var19 < var18.length; ++var19) {
                ExtActProcessNode var14 = null;
                String[] var15 = var18[var19].split("###");
                String var16 = var15[0].substring(3);
                String var17 = var15[1].substring(9);
                var14 = this.extActProcessNodeMapper.queryByNodeCodeAndProcessKey(var6, var16);
                if (var14 == null) {
                    var14 = new ExtActProcessNode();
                    var14.setProcessNodeCode(var16);
                    var14.setProcessNodeName(var17);
                    var14.setProcessId(var11.getId());
                    this.extActProcessNodeMapper.insert(var14);
                } else {
                    var14.setProcessNodeCode(var16);
                    var14.setProcessNodeName(var17);
                    var14.setProcessId(var11.getId());
                    this.extActProcessNodeMapper.updateById(var14);
                }
            }
        }

        return var2;
    }
}
