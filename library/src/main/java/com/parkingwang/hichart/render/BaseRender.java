/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.render;

import android.graphics.RectF;

/**
 * Render基类。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public abstract class BaseRender implements Render {
    protected RectF mDrawRect = new RectF();

    public RectF getDrawRect() {
        return mDrawRect;
    }

    @Override
    public void setDrawRect(float left, float top, float right, float bottom) {
        mDrawRect.set(left, top, right, bottom);
    }
}
