/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

/**
 * Entry is the smallest data model for line chart.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-13 0.1
 */
public class Entry {
    public float x;
    public float y;
    public Object data;

    public Entry(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Entry(float x, float y, Object data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }
}
