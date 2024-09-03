[返回](../)

# Online列表JS增强

Online

1. 实现接口：`org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter`

示例代码：

```java
@Component
public class WarningTeamEnhanceJava implements CgformEnhanceJavaListInter, Serializable {

    private static final long serialVersionUID = -3746177283065879662L;

    @Resource
    private TbBisWarningMapper tbBisWarningMapper;

    /**
     * 执行sql增强
     *
     * @author Yoko
     * @since 2024/9/3 20:41
     * @param tableName 表名
     * @param paramList 已经筛选后的数据结果List
     */
    @Override
    public void execute(String tableName, List<Map<String, Object>> paramList) throws BusinessException {
        if (paramList.isEmpty()) {
            return;
        }
        // 拿到所有队伍id
        List<Object> id = paramList.stream().map(m -> (String)m.get("id")).filter(StringUtils::hasText).collect(Collectors.toList());
        if (id.isEmpty()) {
            return;
        }
        List<TbBisWarningTeamVO> teamVOS = tbBisWarningMapper.listTeamVO(new QueryWrapper<TbBisWarningTeamVO>().in("id", id));
        Map<String, TbBisWarningTeamVO> voMap = IterUtil.toMap(teamVOS, TbBisWarningTeamVO::getId);
        // 遍历list，组装经纬度
        for (Map<String, Object> record : paramList) {
            TbBisWarningTeamVO vo = voMap.get((String) record.get("id"));
            if (vo != null) {
                record.put("longitude", vo.getLongitude());
                record.put("latitude", vo.getLatitude());
                record.put("combineTeamName", vo.getCombineTeamName());
            }
        }
    }

}

```

2. Online列表去配置JavaBean，选中online表->Java增强->新增Java增强，类型选择`spring-key`，值填写：`warningTeamEnhanceJava`
