/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.overlay;

import android.graphics.Paint;
import android.graphics.RectF;

import com.parkingwang.hichart.axis.YAxisRender;
import com.parkingwang.hichart.render.BaseRender;
import com.parkingwang.hichart.view.LineChartView;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-04-16 2.0
 */
public abstract class OverlayRender extends BaseRender {

    private LineChartView mHost;

    public void setHost(LineChartView host) {
        mHost = host;
    }

    protected float getYAxisTop() {
        final YAxisRender yAxisRender = mHost.getYAxisRender();
        final Paint textPaint = yAxisRender.getTextPaint();
        return yAxisRender.getDrawRect().top - (textPaint.ascent() + textPaint.descent()) / 2;
    }

    protected float getYAxisBottom() {
        final YAxisRender yAxisRender = mHost.getYAxisRender();
        final Paint textPaint = yAxisRender.getTextPaint();
        return yAxisRender.getDrawRect().bottom + (textPaint.ascent() + textPaint.descent()) / 2;
    }

    protected RectF getDataRect() {
        return mHost.getDataRender().getDrawRect();
    }
}
