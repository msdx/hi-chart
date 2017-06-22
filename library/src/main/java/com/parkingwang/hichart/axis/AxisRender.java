/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.parkingwang.hichart.data.DataRender;
import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.view.LineChartView;
import com.parkingwang.hichart.render.BaseRender;

import java.util.List;

/**
 * 坐标绘制
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public abstract class AxisRender extends BaseRender {
    protected boolean mDrawBackground;

    protected final Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    protected final Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    private LineChartView mHost;

    public AxisRender() {
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setTextSize(float textSize) {
        mTextPaint.setTextSize(textSize);
    }

    public void setTextColor(@ColorInt int textColor) {
        mTextPaint.setColor(textColor);
    }

    public void setBackgroundColor(@ColorInt int color) {
        mBackgroundPaint.setColor(color);
    }

    public void setDrawBackground(boolean drawBackground) {
        this.mDrawBackground = drawBackground;
    }

    public void attachTo(LineChartView view) {
        mHost = view;
    }

    public DataRender getDataRender() {
        return mHost.getDataRender();
    }

    public List<Line> getLineData() {
        return mHost.getLineData();
    }

    LineChartView getHost() {
        return mHost;
    }
}
