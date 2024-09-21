package org.jeecg.modules.online.cgform.model;

import lombok.Data;

@Data
public class OnlColumn {
    private String title;
    private String dataIndex;
    private String align;
    private String customRender;
    private c scopedSlots;
    private String scopedSlotsRenderCode;
    private String hrefSlotName;
    private int showLength;
    private boolean sorter = false;

    private String sorterType;

    public OnlColumn() {
    }

    public OnlColumn(String title, String dataIndex) {
        this.align = "center";
        this.title = title;
        this.dataIndex = dataIndex;
    }



}
