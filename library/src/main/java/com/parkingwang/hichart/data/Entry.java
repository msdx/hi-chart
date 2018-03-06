/*
 * Copyright (c) 2017-2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

/**
 * Entry is the smallest data model for line chart.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @version 0.2
 * @since 2017-06-13 0.1
 */
public class Entry {
    public final float x;
    public final float y;
    public final Object data;
    public final boolean imaginary;

    public Entry(float x, float y) {
        this(x, y, null, false);
    }

    public Entry(float x, float y, Object data) {
        this(x, y, data, false);
    }

    public Entry(float x, float y, Object data, boolean imaginary) {
        this.x = x;
        this.y = y;
        this.data = data;
        this.imaginary = imaginary;
    }
}
