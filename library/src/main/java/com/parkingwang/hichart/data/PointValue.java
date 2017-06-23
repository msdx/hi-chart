/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

/**
 * PointValue is the actual position of the {@link Entry} in canvas.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-20 0.1
 */
public class PointValue {
    public float x;
    public float y;

    public PointValue(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "PointValue{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
