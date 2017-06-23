/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.render;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Render is an interface that defines the draw area of the render and the drawing behavior.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public interface Render {
    RectF getDrawRect();

    void setDrawRect(float left, float top, float right, float bottom);

    void draw(Canvas canvas);
}
