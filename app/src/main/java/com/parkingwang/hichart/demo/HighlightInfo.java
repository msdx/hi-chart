/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.demo;

import android.support.annotation.ColorInt;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-23 0.1
 */
public class HighlightInfo {
    @ColorInt
    public int circleColor;
    public String text;

    public HighlightInfo(int circleColor, String text) {
        this.circleColor = circleColor;
        this.text = text;
    }
}
