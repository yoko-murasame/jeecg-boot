package org.jeecg.modules.online.cgreport.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Collection;

/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/service/CgReportExcelServiceI.class */
public interface CgReportExcelServiceI {
    HSSFWorkbook exportExcel(String str, Collection<?> collection, Collection<?> collection2);
}
