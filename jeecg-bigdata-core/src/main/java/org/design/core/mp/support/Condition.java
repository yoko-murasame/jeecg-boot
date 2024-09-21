//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.design.core.mp.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.design.core.tool.support.Kv;
import org.design.core.tool.utils.BeanUtil;
import org.design.core.tool.utils.Func;
import org.design.core.tool.utils.StringUtil;

import java.util.Map;

public class Condition {
    public Condition() {
    }

    public static <T> IPage<T> getPage(Query query) {
        Page<T> page = new Page((long)Func.toInt(query.getCurrent(), 1), (long)Func.toInt(query.getSize(), 10));
        String[] ascArr = Func.toStrArray(query.getAscs());
        String[] descArr = ascArr;
        int var4 = ascArr.length;

        int var5;
        for(var5 = 0; var5 < var4; ++var5) {
            String asc = descArr[var5];
            page.addOrder(new OrderItem[]{OrderItem.asc(StringUtil.cleanIdentifier(asc))});
        }

        descArr = Func.toStrArray(query.getDescs());
        String[] var8 = descArr;
        var5 = descArr.length;

        for(int var9 = 0; var9 < var5; ++var9) {
            String desc = var8[var9];
            page.addOrder(new OrderItem[]{OrderItem.desc(StringUtil.cleanIdentifier(desc))});
        }

        return page;
    }

    public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
        return new QueryWrapper(entity);
    }

    public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
        Kv exclude = Kv.init().set("column", "column").set("order", "order").set("current", "current").set("size", "size").set("ascs", "ascs").set("descs", "descs");
        return getQueryWrapper(query, exclude, clazz);
    }

    public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Map<String, Object> exclude, Class<T> clazz) {
        exclude.forEach((k, v) -> {
            query.remove(k);
        });
        QueryWrapper<T> qw = new QueryWrapper();
        qw.setEntity(BeanUtil.newInstance(clazz));
        SqlKeyword.buildCondition(query, qw);
        return qw;
    }
}
