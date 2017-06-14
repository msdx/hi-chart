/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.divider;

import android.support.annotation.ColorInt;

/**
 * 分割线
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class Divider {
    private float mPadding;
    @ColorInt
    private int mColor;
    private int mWidth;

    public float getPadding() {
        return mPadding;
    }

    public void setPadding(float padding) {
        mPadding = padding;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }
}
