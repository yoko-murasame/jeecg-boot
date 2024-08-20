package org.jeecg.modules.online.cgform.model;

public class HrefSlots {
    private String slotName;
    private String href;

    public HrefSlots() {
    }

    public HrefSlots(String slotName, String href) {
        this.slotName = slotName;
        this.href = href;
    }

    public String getSlotName() {
        return this.slotName;
    }

    public String getHref() {
        return this.href;
    }


    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof HrefSlots)) return false;
        HrefSlots localHrefSlots = (HrefSlots) o;
        if (!localHrefSlots.canEqual(this)) return false;
        String str1 = getSlotName();
        String str2 = localHrefSlots.getSlotName();
        if (str1 == null ? str2 != null : !str1.equals(str2)) return false;
        String str3 = getHref();
        String str4 = localHrefSlots.getHref();
        return str3 == null ? str4 == null : str3.equals(str4);
    }

    protected boolean canEqual(Object other) {
        return other instanceof HrefSlots;
    }

    public int hashCode() {
        int i = 59;
        int j = 1;
        String str1 = getSlotName();
        j = j * 59 + (str1 == null ? 43 : str1.hashCode());
        String str2 = getHref();
        j = j * 59 + (str2 == null ? 43 : str2.hashCode());
        return j;
    }

    public String toString() {
        return "HrefSlots(slotName=" + getSlotName() + ", href=" + getHref() + ")";
    }

}
