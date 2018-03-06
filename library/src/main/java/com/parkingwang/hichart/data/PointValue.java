/*
 * Copyright (c) 2017-2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

/**
 * PointValue is the actual position of the {@link Entry} in canvas.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @version 0.2
 * @since 2017-06-20 0.1
 */
public class PointValue {
    public final float x;
    public final float y;
    public final boolean imaginary;

    public PointValue(float x, float y) {
        this.x = x;
        this.y = y;
        this.imaginary = false;
    }

    public PointValue(float x, float y, boolean imaginary) {
        this.x = x;
        this.y = y;
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        return "PointValue{" +
                "x=" + x +
                ", y=" + y +
                ", imaginary=" + imaginary +
                '}';
    }
}
