/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.line.LineChartView;

import java.util.List;

/**
 * 坐标
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public abstract class Axis {

    private boolean mCustomLabel;
    private int mDrawCount;

    protected boolean mIsCustomMinMax;
    protected float mMinValue;
    protected float mMaxValue;

    private LineChartView mHost;

    public int getDrawCount() {
        return mDrawCount;
    }

    public void setDrawCount(int drawCount) {
        mDrawCount = drawCount;
    }

    public float getMinValue() {
        return mMinValue;
    }

    public void setMinValue(float min) {
        internalSetMinValue(min, true);
    }

    void internalSetMinValue(float min, boolean isCustom) {
        mMinValue = min;
        mIsCustomMinMax = isCustom;
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(float max) {
        internalSetMaxValue(max, true);
    }

    void internalSetMaxValue(float max, boolean isCustom) {
        mMaxValue = max;
        mIsCustomMinMax = isCustom;
    }

    public void calcMinMaxIfNotCustom() {
        if (mIsCustomMinMax) {
            return;
        }
        calcMinMax();
    }

    public abstract void calcMinMax();

    public float getRange() {
        return mMaxValue - mMinValue;
    }

    public void attachTo(LineChartView host) {
        mHost = host;
    }

    protected List<Line> getData() {
        return mHost.getLineData();
    }
}
