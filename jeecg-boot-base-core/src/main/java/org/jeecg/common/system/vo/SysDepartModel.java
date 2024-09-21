package org.jeecg.common.system.vo;

import lombok.Data;
import org.jeecg.common.constant.CommonConstant;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * 部门机构model
 */
@Data
public class SysDepartModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;
    /**
     * 父机构ID
     */
    private String parentId;
    /**
     * 机构/部门名称
     */
    private String departName;
    /**
     * 英文名
     */
    private String departNameEn;
    /**
     * 缩写
     */
    private String departNameAbbr;
    /**
     * 排序
     */
    private Integer departOrder;
    /**
     * 描述
     */
    private String description;
    /**
     * 机构类别 1组织机构，2岗位
     */
    private String orgCategory;
    /**
     * 机构类型
     */
    private String orgType;
    /**
     * 机构编码
     */
    private String orgCode;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 传真
     */
    private String fax;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态（1启用，0不启用）
     */
    private String status;
    /**
     * 删除状态（0，正常，1已删除）
     */
    private String delFlag;

    List<SysDepartModel> children = new ArrayList<SysDepartModel>();

    /**
     * 迭代根树
     *
     * @param rootTreeList 根树列表
     * @param comparator   排序
     * @param consumer     消费
     * @author Yoko
     * @since 2024/8/7 上午11:53
     */
    public static void iteratorRootTree(List<SysDepartModel> rootTreeList, Consumer<SysDepartModel> consumer, Comparator<SysDepartModel> comparator) {
        if (rootTreeList == null || rootTreeList.isEmpty()) {
            return;
        }
        if (comparator != null) {
            rootTreeList.sort(comparator);
        }
        for (SysDepartModel item : rootTreeList) {
            consumer.accept(item);
            if (item.getChildren() != null && !item.getChildren().isEmpty()) {
                iteratorRootTree(item.getChildren(), consumer, comparator);
            }
        }
    }

    /**
     * 迭代根树
     *
     * @param rootTreeList 根树列表
     * @param consumer     消费
     * @author Yoko
     * @since 2024/8/7 上午11:53
     */
    public static void iteratorRootTree(List<SysDepartModel> rootTreeList, Consumer<SysDepartModel> consumer) {
        iteratorRootTree(rootTreeList, consumer, SysDepartModel::compareDepartAsc);
    }

    /**
     * 获取根树
     *
     * @param list       完整列表
     * @param comparator 排序
     * @return java.util.List<org.jeecg.common.system.vo.SysDepartModel>
     * @author Yoko
     * @since 2024/8/7 下午2:56
     */
    public static List<SysDepartModel> toRootTree(List<SysDepartModel> list, Comparator<SysDepartModel> comparator) {
        return toTree(list, comparator, CommonConstant.SYS_DEPART_PARENT_ID_ROOT_FLAG);
    }

    /**
     * 获取根树
     *
     * @param list 完整列表
     * @return java.util.List<org.jeecg.common.system.vo.SysDepartModel>
     * @author Yoko
     * @since 2024/8/7 下午2:56
     */
    public static List<SysDepartModel> toRootTree(List<SysDepartModel> list) {
        return toTree(list, SysDepartModel::compareDepartAsc, CommonConstant.SYS_DEPART_PARENT_ID_ROOT_FLAG);
    }

    /**
     * 获取树
     *
     * @param list       完整列表
     * @param comparator 排序
     * @param rootPid 根节点父ID
     * @return java.util.List<org.jeecg.common.system.vo.SysDepartModel>
     */
    public static List<SysDepartModel> toTree(List<SysDepartModel> list, Comparator<SysDepartModel> comparator, String rootPid) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        // 存储 id 到 SysDepartModel 的映射
        Map<String, SysDepartModel> map = new HashMap<>();
        for (SysDepartModel item : list) {
            map.put(item.getId(), item);
            item.setChildren(new ArrayList<>()); // 初始化子节点列表
        }

        // 构建树
        List<SysDepartModel> rootNodes = new ArrayList<>();
        for (SysDepartModel item : list) {
            if (Objects.equals(item.getParentId(), rootPid) || !map.containsKey(item.getParentId())) {
                rootNodes.add(item); // 根节点
            } else {
                SysDepartModel parent = map.get(item.getParentId());
                parent.getChildren().add(item); // 添加到父节点的子节点列表
            }
        }

        // 对树进行排序
        if (comparator != null) {
            sortTree(rootNodes, comparator);
        }

        return rootNodes;
    }

    /**
     * 对树进行排序
     *
     * @param tree       树的根节点列表
     * @param comparator 排序器
     */
    private static void sortTree(List<SysDepartModel> tree, Comparator<SysDepartModel> comparator) {
        tree.sort(comparator);
        for (SysDepartModel node : tree) {
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                sortTree(node.getChildren(), comparator);
            }
        }
    }

    /**
     * 字符串数组ASC排序
     *
     * @param a
     * @param b
     * @return int
     * @author Yoko
     * @since 2024/8/7 上午10:57
     */
    private static int compareStrAsc(String a, String b) {
        if (!StringUtils.hasText(a)) {
            return -1;
        }
        try {
            Integer aa = Integer.parseInt(a);
            Integer bb = Integer.parseInt(b);
            return aa.compareTo(bb);
        } catch (NumberFormatException e) {
            return a.compareTo(b);
        }
    }

    /**
     * 部门实体对象ASC排序
     * 先根据parentId、再根据具体排序字段
     *
     * @param a
     * @param b
     * @return int
     * @author Yoko
     * @since 2024/8/7 上午10:57
     */
    private static int compareDepartAsc(SysDepartModel a, SysDepartModel b) {
        // 先比较父项，再比较排序
        if (!a.getParentId().equals(b.getParentId())) {
            return compareStrAsc(a.getParentId(), b.getParentId());
        }
        try {
            Integer aa = a.getDepartOrder();
            Integer bb = b.getDepartOrder();
            return aa.compareTo(bb);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
