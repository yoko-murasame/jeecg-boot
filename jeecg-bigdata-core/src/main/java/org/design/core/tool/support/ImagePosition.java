package org.design.core.tool.support;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/ImagePosition.class */
public class ImagePosition {
    public static final int TOP = 32;
    public static final int MIDDLE = 16;
    public static final int BOTTOM = 8;
    public static final int LEFT = 4;
    public static final int CENTER = 2;
    public static final int RIGHT = 1;
    private static final int PADDING_HORI = 6;
    private static final int PADDING_VERT = 6;
    private int boxPosX;
    private int boxPosY;

    public ImagePosition(int width, int height, int boxWidth, int boxHeight, int style) {
        switch (style & 7) {
            case 1:
                this.boxPosX = (width - boxWidth) - 6;
                break;
            case CENTER /* 2 */:
            case 3:
            default:
                this.boxPosX = (width - boxWidth) / 2;
                break;
            case LEFT /* 4 */:
                this.boxPosX = 6;
                break;
        }
        switch ((style >> 3) << 3) {
            case BOTTOM /* 8 */:
            default:
                this.boxPosY = (height - boxHeight) - 6;
                return;
            case MIDDLE /* 16 */:
                this.boxPosY = (height - boxHeight) / 2;
                return;
            case TOP /* 32 */:
                this.boxPosY = 6;
                return;
        }
    }

    public int getX() {
        return getX(0);
    }

    public int getX(int x) {
        return this.boxPosX + x;
    }

    public int getY() {
        return getY(0);
    }

    public int getY(int y) {
        return this.boxPosY + y;
    }
}
