//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.online.desform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.es.JeecgElasticsearchTemplate;
import org.jeecg.common.es.QueryStringBuilder;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.online.desform.b.e;
import org.jeecg.modules.online.desform.constant.EsTypes;
import org.jeecg.modules.online.desform.entity.DesignForm;
import org.jeecg.modules.online.desform.entity.DesignFormData;
import org.jeecg.modules.online.desform.mapper.DesignFormDataMapper;
import org.jeecg.modules.online.desform.service.IDesignFormAuthService;
import org.jeecg.modules.online.desform.service.IDesignFormDataService;
import org.jeecg.modules.online.desform.service.IDesignFormService;
import org.jeecg.modules.online.desform.vo.es.EsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service("designFormDataServiceImpl")
public class b extends ServiceImpl<DesignFormDataMapper, DesignFormData> implements IDesignFormDataService {
    private static final Logger a = LoggerFactory.getLogger(b.class);
    @Autowired
    @Lazy
    private DesignFormDataMapper designFormDataMapper;
    @Autowired
    @Lazy
    private IDesignFormService designFormService;
    @Autowired
    private JeecgElasticsearchTemplate jes;
    @Autowired
    private IDesignFormAuthService designFormAuthService;

    public b() {
    }

    public boolean saveBatchByImport(DesignForm designForm, Collection<JSONObject> entityList, String token) {
        ArrayList var4 = new ArrayList();
        HttpHeaders var5 = new HttpHeaders();
        var5.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        var5.set("Accept", "application/json;charset=UTF-8");
        var5.set("X-Access-Token", token);

        DesignFormData var8;
        for(Iterator var6 = entityList.iterator(); var6.hasNext(); var4.add(var8)) {
            JSONObject var7 = (JSONObject)var6.next();
            var8 = new DesignFormData();
            var8.setDesformId(designForm.getId());
            var8.setDesformCode(designForm.getDesformCode());
            var8.setDesformName(designForm.getDesformName());
            var8.setOnlineFormCode(designForm.getCgformCode());
            var8.setDesformDataJson(var7.toJSONString());
            if (StringUtils.isNotBlank(designForm.getCgformCode())) {
                String var9 = RestUtil.getBaseUrl() + "/online/cgform/api/crazyForm/" + designForm.getCgformCode();
                JSONObject var10 = (JSONObject)RestUtil.request(var9, HttpMethod.POST, var5, (JSONObject)null, var7, JSONObject.class).getBody();
                if (var10 != null) {
                    String var11 = var10.getString("result");
                    var8.setOnlineFormDataId(var11);
                }
            }
        }

        boolean var12 = super.saveBatch(var4);
        if (var12) {
            Iterator var13 = var4.iterator();

            while(var13.hasNext()) {
                var8 = (DesignFormData)var13.next();
                e.a(designForm.getDesformCode(), var8);
            }
        }

        return var12;
    }

    public Result<IPage<DesignFormData>> pageList(String desformCode, int pageNo, int pageSize, QueryWrapper<DesignFormData> queryWrapper, JSONArray superQueryList, MatchTypeEnum matchType) {
        Result var7 = new Result();
        var7.setMessage("查询成功");
        Boolean var8 = this.a(desformCode, queryWrapper, superQueryList, matchType);
        if (var8 != null && !var8) {
            var7.setMessage("查询成功，但高级查询失败");
        }

        Page var9 = new Page((long)pageNo, (long)pageSize);
        var7.setResult(super.page(var9, queryWrapper));
        return var7;
    }

    public Result<IPage<DesignFormData>> queryPage(String desformCode, int pageNo, int pageSize, QueryWrapper<DesignFormData> queryWrapper, String superQuery, String username) throws UnsupportedEncodingException {
        Result var7 = new Result();
        Result var8 = this.a(desformCode, queryWrapper, superQuery, pageNo, pageSize, false, username);
        BeanUtils.copyProperties(var8, var7);
        return var7;
    }

    public Result<List<DesignFormData>> queryList(String desformCode, QueryWrapper<DesignFormData> queryWrapper, String superQuery, String username) throws UnsupportedEncodingException {
        Result var5 = new Result();
        Result var6 = this.a(desformCode, queryWrapper, superQuery, 0, 0, true, username);
        BeanUtils.copyProperties(var6, var5);
        return var5;
    }

    private Result a(String var1, QueryWrapper<DesignFormData> var2, String var3, int var4, int var5, boolean var6, String var7) throws UnsupportedEncodingException {
        JSONObject var8 = JSON.parseObject(var3);
        MatchTypeEnum var9 = MatchTypeEnum.getByValue(var8.getString("matchType"));
        JSONArray var10 = this.a(var8);
        var10 = this.designFormAuthService.queryDataAuth(var1, var7, var2, var10);
        Result var11 = new Result();
        var11.setMessage("查询成功");
        Boolean var12 = this.a(var1, var2, var10, var9);
        if (var12 != null && !var12) {
            var11.setMessage("查询成功，但高级查询失败");
        }

        if (var6) {
            var11.setResult(super.list(var2));
        } else {
            Page var13 = new Page((long)var4, (long)var5);
            var11.setResult(super.page(var13, var2));
        }

        return var11;
    }

    private JSONArray a(JSONObject var1) throws UnsupportedEncodingException {
        String var2 = var1.getString("superQueryParams");
        JSONArray var3 = new JSONArray();
        if (StringUtils.isNotBlank(var2)) {
            var2 = URLDecoder.decode(var2, "UTF-8");
            var3 = JSON.parseArray(var2);
        }

        return var3;
    }

    private Boolean a(String var1, QueryWrapper<DesignFormData> var2, JSONArray var3, MatchTypeEnum var4) {
        if (var3 != null && var3.size() != 0) {
            String var5 = "design_form";
            JSONArray var6 = new JSONArray();
            JSONArray var7 = new JSONArray();
            HashMap var8 = new HashMap();
            Map var9 = this.jes.getIndexMappingFormat(var1, var5, EsProperties.class);
            a.info("ES索引字段类型: " + JSON.toJSONString(var9));
            a.info("高级查询列表: " + JSON.toJSONString(var3));

            JSONObject var11;
            String var12;
            for(int var10 = 0; var10 < var3.size(); ++var10) {
                var11 = var3.getJSONObject(var10);
                var12 = var11.getString("val");
                String var13 = var11.getString("type");
                String var14 = var11.getString("field");
                boolean var15 = !"number".equals(var13) && !"date".equals(var13) && !"time".equals(var13);
                EsProperties var16 = (EsProperties)var9.get(var14);
                if (var16 != null && var16.getType() != null) {
                    EsTypes var17 = var16.getType();
                    if (var17 == EsTypes.DATE || var17 == EsTypes.INTEGER || var17 == EsTypes.LONG || var17 == EsTypes.SHORT || var17 == EsTypes.BYTE || var17 == EsTypes.DOUBLE || var17 == EsTypes.FLOAT || var17 == EsTypes.HALF_FLOAT || var17 == EsTypes.SCALED_FLOAT || var17 == EsTypes.BOOLEAN) {
                        var15 = false;
                    }
                }

                if (var15) {
                    var14 = var14 + ".keyword";
                }

                QueryRuleEnum var29 = QueryRuleEnum.getByValue(var11.getString("rule"));
                if (var29 != null) {
                    switch (var29) {
                        case GT:
                            this.a((Object)this.jes.buildRangeQuery(var14, var12, (Object)null, false, false), (MatchTypeEnum)var4, var6, (JSONArray)var7);
                            break;
                        case GE:
                            this.a((Object)this.jes.buildRangeQuery(var14, var12, (Object)null, true, false), (MatchTypeEnum)var4, var6, (JSONArray)var7);
                            break;
                        case LT:
                            this.a((Object)this.jes.buildRangeQuery(var14, (Object)null, var12, false, false), (MatchTypeEnum)var4, var6, (JSONArray)var7);
                            break;
                        case LE:
                            this.a((Object)this.jes.buildRangeQuery(var14, (Object)null, var12, false, true), (MatchTypeEnum)var4, var6, (JSONArray)var7);
                            break;
                        case EQ:
                            this.a(var8, var14, var12, var4, true);
                            break;
                        case NE:
                            this.b(var8, var14, var12);
                            break;
                        case IN:
                            String[] var18 = var12.split(",");
                            if (var18.length <= 0) {
                                break;
                            }

                            QueryStringBuilder var19 = new QueryStringBuilder(var14, var18[0], false);

                            for(int var20 = 1; var20 < var18.length; ++var20) {
                                var19.or(var18[var20]);
                            }

                            this.a((Object)this.jes.buildQueryString(var19.toString()), (MatchTypeEnum)var4, var6, (JSONArray)var7);
                            break;
                        case LIKE:
                            this.a(var8, var14, "*" + var12 + "*", var4, false);
                            break;
                        case LEFT_LIKE:
                            this.a(var8, var14, "*" + var12, var4, false);
                            break;
                        case RIGHT_LIKE:
                            this.a(var8, var14, var12 + "*", var4, false);
                    }
                }
            }

            Iterator var22 = var8.keySet().iterator();

            while(var22.hasNext()) {
                String var24 = (String)var22.next();
                var12 = ((QueryStringBuilder)var8.get(var24)).toString();
                this.a((Object)this.jes.buildQueryString(var12), (MatchTypeEnum)var4, var6, (JSONArray)var7);
            }

            try {
                List var23 = Collections.singletonList("_id");
                var11 = this.jes.buildQuery(var23, this.jes.buildBoolQuery(var6, (JSONArray)null, var7), 0, 10000);
                JSONObject var25 = this.jes.search(var1, var5, var11);
                JSONArray var26 = var25.getJSONObject("hits").getJSONArray("hits");
                if (var26.size() > 0) {
                    ArrayList var30 = new ArrayList();

                    for(int var27 = 0; var27 < var26.size(); ++var27) {
                        JSONObject var28 = var26.getJSONObject(var27);
                        var30.add(var28.getString("_id"));
                    }

                    var2.in("id", var30);
                } else {
                    var2.eq("1", "2");
                }

                return true;
            } catch (Exception var21) {
                a.error("ES索引数据查询失败：" + var21.getMessage(), var21);
                return false;
            }
        } else {
            return null;
        }
    }

    private void a(Object var1, MatchTypeEnum var2, JSONArray var3, JSONArray var4) {
        if (var2 == MatchTypeEnum.OR) {
            var4.add(var1);
        } else {
            var3.add(var1);
        }

    }

    private void a(Map<String, QueryStringBuilder> var1, String var2, String var3, MatchTypeEnum var4, boolean var5) {
        QueryStringBuilder var6 = this.a(var1, var2, var3, false, var5);
        if (var6 != null) {
            if (var4 == MatchTypeEnum.OR) {
                var6.or(var3, var5);
            } else {
                var6.and(var3, var5);
            }
        }

    }

    private void a(Map<String, QueryStringBuilder> var1, String var2, String var3) {
        QueryStringBuilder var4 = this.c(var1, var2, var3);
        if (var4 != null) {
            var4.and(var3);
        }

    }

    private void b(Map<String, QueryStringBuilder> var1, String var2, String var3) {
        QueryStringBuilder var4 = this.a(var1, var2, var3, true, true);
        if (var4 != null) {
            var4.not(var3);
        }

    }

    private QueryStringBuilder c(Map<String, QueryStringBuilder> var1, String var2, String var3) {
        return this.a(var1, var2, var3, false, true);
    }

    private QueryStringBuilder a(Map<String, QueryStringBuilder> var1, String var2, String var3, boolean var4, boolean var5) {
        QueryStringBuilder var6 = (QueryStringBuilder)var1.get(var2);
        if (var6 == null) {
            var6 = new QueryStringBuilder(var2, var3, var4, var5);
            var1.put(var2, var6);
            return null;
        } else {
            return var6;
        }
    }

    public List<DesignFormData> selectByMainId(String mainId) {
        return this.designFormDataMapper.selectByMainId(mainId);
    }

    public Result addOne(DesignFormData designFormData) {
        Result var2 = new Result();
        if (designFormData == null) {
            var2.error500("designFormData 不能为空！");
            return var2;
        } else {
            try {
                String var3 = designFormData.getDesformCode();
                if (StringUtils.isEmpty(var3)) {
                    return Result.error("必须传递 desformCode 参数");
                }

                DesignForm var4 = this.designFormService.getByCode(var3);
                if (var4 == null) {
                    return Result.error("不存在的 desformCode");
                }

                if (CommonConstant.DESIGN_FORM_TYPE_SUB.equals(var4.getDesformType())) {
                    designFormData.setDesformId(var4.getParentId());
                    designFormData.setDesformCode(var4.getParentCode());
                } else {
                    designFormData.setDesformId(var4.getId());
                }

                designFormData.setDesformName(var4.getDesformName());
                super.save(designFormData);
                e.a(var4.getDesformCode(), designFormData);
                var2.setResult(designFormData.getId());
                var2.success("添加成功！");
            } catch (Exception var5) {
                a.error(var5.getMessage(), var5);
                var2.error500("操作失败");
            }

            return var2;
        }
    }

    public Result editOne(DesignFormData designFormData) {
        Result var2 = new Result();
        DesignFormData var3 = (DesignFormData)super.getById(designFormData.getId());
        if (var3 == null) {
            var2.error500("未找到对应数据!");
        } else {
            designFormData.setDesformId((String)null);
            designFormData.setDesformCode((String)null);
            super.updateById(designFormData);
            e.a(var3.getDesformCode(), designFormData);
            var2.setResult(designFormData.getId());
            var2.success("修改成功!");
        }

        return var2;
    }

    public Result deleteOne(DesignFormData designFormData, String token) {
        designFormData = (DesignFormData)super.getById(designFormData.getId());
        if (designFormData == null) {
            return Result.error("未找到对应实体");
        } else {
            super.removeById(designFormData.getId());
            this.a(designFormData, token);
            e.b(designFormData.getDesformCode(), designFormData);
            return Result.ok("删除成功");
        }
    }

    public Result deleteOne(String id, String token) {
        DesignFormData var3 = new DesignFormData();
        var3.setId(id);
        return this.deleteOne(var3, token);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void deleteBatchMain(List<String> idList, String token) {
        List<DesignFormData> var3 = this.getBaseMapper().selectList(Wrappers.lambdaQuery(DesignFormData.class).in(DesignFormData::getId, idList));
        // List var3 = ((DesignFormDataMapper)this.baseMapper).selectList((Wrapper)(new LambdaQueryWrapper(DesignFormDataMapper.class)).in(DesignFormData::getId, idList));
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            DesignFormData var5 = (DesignFormData)var4.next();
            this.designFormDataMapper.deleteById(var5.getId());
            this.a(var5, token);
            e.b(var5.getDesformCode(), var5);
        }

    }

    private void a(DesignFormData var1, String var2) {
        String var3 = var1.getOnlineFormCode();
        String var4 = var1.getOnlineFormDataId();
        if (StringUtils.isNotBlank(var3) && StringUtils.isNotBlank(var4)) {
            HttpHeaders var5 = new HttpHeaders();
            var5.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
            var5.set("Accept", "application/json;charset=UTF-8");
            var5.set("X-Access-Token", var2);
            String var6 = RestUtil.getBaseUrl() + String.format("/online/cgform/api/formByCode/%s/%s", var3, var4);
            RestUtil.request(var6, HttpMethod.DELETE, var5, (JSONObject)null, (Object)null, JSONObject.class);
        }

    }
}
