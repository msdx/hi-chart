/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.empty;

import android.graphics.Canvas;

import com.parkingwang.hichart.render.Render;

/**
 * Draw the canvas when there has no data in line chart.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public interface EmptyRender extends Render {
    void draw(Canvas canvas);
}
