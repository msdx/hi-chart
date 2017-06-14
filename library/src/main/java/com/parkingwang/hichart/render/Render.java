/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.render;

import android.graphics.Canvas;

/**
 * Render接口，设置每个Render应该绘制的区域。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public interface Render {

    void setRenderArea(float left, float top, float right, float bottom);

    void draw(Canvas canvas);
}
