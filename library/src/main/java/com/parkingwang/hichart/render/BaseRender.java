/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.render;

import android.graphics.RectF;

/**
 * Render基类。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 4.1
 */
public abstract class BaseRender implements Render {
    protected RectF mArea = new RectF();

    @Override
    public void setRenderArea(float left, float top, float right, float bottom) {
        mArea.set(left, top, right, bottom);
    }
}
