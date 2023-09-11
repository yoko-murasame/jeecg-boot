package org.jeecg.modules.online.config.service;

import org.jeecg.modules.online.config.b.a;
import org.jeecg.modules.online.config.exception.DBException;

/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/service/DbTableHandleI.class */
public interface DbTableHandleI {
    String getAddColumnSql(a aVar);

    String getReNameFieldName(a aVar);

    String getUpdateColumnSql(a aVar, a aVar2) throws DBException;

    String getMatchClassTypeByDataType(String str, int i);

    String dropTableSQL(String str);

    String getDropColumnSql(String str);

    String getCommentSql(a aVar);

    String getSpecialHandle(a aVar, a aVar2);

    String dropIndexs(String str, String str2);

    String countIndex(String str, String str2);
}