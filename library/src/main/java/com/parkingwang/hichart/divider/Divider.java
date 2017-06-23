/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.divider;

import android.support.annotation.ColorInt;

/**
 * The divider of the line chart view.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class Divider {
    private float mPadding;
    @ColorInt
    private int mColor;
    private int mWidth;

    /**
     * @return The padding of the divider.
     */
    public float getPadding() {
        return mPadding;
    }

    /**
     * Set the padding to the divider.
     * If the divider is vertical direction, then it represents the top and the bottom padding of the divider.
     * Otherwise it represents the left and bottom padding of the divider.
     *
     * @param padding The padding to set.
     */
    public void setPadding(float padding) {
        mPadding = padding;
    }

    /**
     * @return The color of the divider
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Set the color into the divider.
     *
     * @param color The color to set.
     */
    public void setColor(int color) {
        mColor = color;
    }

    /**
     * @return The width of the vertical divider or the height of the horizontal divider.
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * @param width The width to set into the divider.
     */
    public void setWidth(int width) {
        mWidth = width;
    }
}
