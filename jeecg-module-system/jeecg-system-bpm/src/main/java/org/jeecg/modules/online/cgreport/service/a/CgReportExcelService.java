package org.jeecg.modules.online.cgreport.service.a;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.jeecg.modules.online.cgreport.service.CgReportExcelServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: CgReportExcelServiceImpl.java */
@Service("cgReportExcelService")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgreport/service/a/a.class */
public class CgReportExcelService implements CgReportExcelServiceI {
    private static final Logger a = LoggerFactory.getLogger(CgReportExcelService.class);

    @SneakyThrows
    @Override // org.jeecg.modules.online.cgreport.service.CgReportExcelServiceI
    public HSSFWorkbook exportExcel(String title, Collection<?> titleSet, Collection<?> dataSet) {
        HSSFWorkbook hSSFWorkbook = null;
        if (titleSet != null) {
            try {
            } catch (Exception e) {
                a.error(e.getMessage(), e);
            }
            if (titleSet.size() != 0) {
                if (title == null) {
                    title = "";
                }
                hSSFWorkbook = new HSSFWorkbook();
                HSSFSheet createSheet = hSSFWorkbook.createSheet(title);
                int i = 0;
                int i2 = 0;
                Row createRow = createSheet.createRow(0);
                createRow.setHeight((short) 450);
                HSSFCellStyle a2 = a(hSSFWorkbook);
                List<Map> list = (List) titleSet;
                Iterator<?> it = dataSet.iterator();
                for (Map map : list) {
                    String str = (String) map.get("field_txt");
                    Cell createCell = createRow.createCell(i2);
                    createCell.setCellValue(new HSSFRichTextString(str));
                    createCell.setCellStyle(a2);
                    i2++;
                }
                HSSFCellStyle c = c(hSSFWorkbook);
                while (it.hasNext()) {
                    int i3 = 0;
                    i++;
                    Row createRow2 = createSheet.createRow(i);
                    Map map2 = (Map) it.next();
                    for (Map map3 : list) {
                        String str2 = (String) map3.get(org.jeecg.modules.online.cgreport.b.a.o);
                        String obj = map2.get(str2) == null ? "" : map2.get(str2).toString();
                        Cell createCell2 = createRow2.createCell(i3);
                        HSSFRichTextString hSSFRichTextString = new HSSFRichTextString(obj);
                        createCell2.setCellStyle(c);
                        createCell2.setCellValue(hSSFRichTextString);
                        i3++;
                    }
                }
                for (int i4 = 0; i4 < list.size(); i4++) {
                    createSheet.autoSizeColumn(i4);
                }
                return hSSFWorkbook;
            }
        }
        throw new Exception("读取表头失败！");
    }

    private HSSFCellStyle a(HSSFWorkbook hSSFWorkbook) {
        HSSFCellStyle createCellStyle = hSSFWorkbook.createCellStyle();
        createCellStyle.setBorderLeft(BorderStyle.THIN);
        createCellStyle.setBorderRight(BorderStyle.THIN);
        createCellStyle.setBorderBottom(BorderStyle.THIN);
        createCellStyle.setBorderTop(BorderStyle.THIN);
        createCellStyle.setAlignment(HorizontalAlignment.CENTER);
        createCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        createCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return createCellStyle;
    }

    private void a(int i, int i2, HSSFWorkbook hSSFWorkbook) {
        HSSFSheet sheetAt = hSSFWorkbook.getSheetAt(0);
        HSSFCellStyle c = c(hSSFWorkbook);
        for (int i3 = 1; i3 <= i; i3++) {
            Row createRow = sheetAt.createRow(i3);
            for (int i4 = 0; i4 < i2; i4++) {
                createRow.createCell(i4).setCellStyle(c);
            }
        }
    }

    private HSSFCellStyle b(HSSFWorkbook hSSFWorkbook) {
        HSSFCellStyle createCellStyle = hSSFWorkbook.createCellStyle();
        createCellStyle.setBorderLeft(BorderStyle.THIN);
        createCellStyle.setBorderRight(BorderStyle.THIN);
        createCellStyle.setBorderBottom(BorderStyle.THIN);
        createCellStyle.setBorderTop(BorderStyle.THIN);
        createCellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
        createCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return createCellStyle;
    }

    private HSSFCellStyle c(HSSFWorkbook hSSFWorkbook) {
        HSSFCellStyle createCellStyle = hSSFWorkbook.createCellStyle();
        createCellStyle.setBorderLeft(BorderStyle.THIN);
        createCellStyle.setBorderRight(BorderStyle.THIN);
        createCellStyle.setBorderBottom(BorderStyle.THIN);
        createCellStyle.setBorderTop(BorderStyle.THIN);
        return createCellStyle;
    }
}
