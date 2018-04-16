/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.parkingwang.hichart.data.DataRender;
import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.render.BaseRender;
import com.parkingwang.hichart.view.LineChartView;

import java.util.List;

/**
 * The render to draw axis.
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

    protected Paint getTextPaint() {
        return mTextPaint;
    }

    /**
     * Set the text size of labels on axis.
     *
     * @param textSize The text size to set.
     */
    public final void setTextSize(float textSize) {
        mTextPaint.setTextSize(textSize);
    }

    /**
     * Set the text color of labels on axis
     *
     * @param textColor The text color to set.
     */
    public final void setTextColor(@ColorInt int textColor) {
        mTextPaint.setColor(textColor);
    }

    /**
     * Set the background color of axis.
     *
     * @param color The background color to set.
     */
    public void setBackgroundColor(@ColorInt int color) {
        mBackgroundPaint.setColor(color);
    }

    /**
     * Set whether draw the background.
     *
     * @param drawBackground True to draw the background
     */
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
