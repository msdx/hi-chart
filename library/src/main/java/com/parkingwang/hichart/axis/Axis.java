/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.view.LineChartView;

import java.util.List;

/**
 * Axis.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public abstract class Axis {

    private int mDrawCount;

    protected boolean mIsCustomMin;
    protected boolean mIsCustomMax;
    protected float mMinValue;
    protected float mMaxValue;

    private LineChartView mHost;

    /**
     * @return return the count that would be drawn
     */
    public int getDrawCount() {
        return mDrawCount;
    }

    /**
     * Set the count that would be drawn
     *
     * @param drawCount The count to set
     */
    public void setDrawCount(int drawCount) {
        mDrawCount = drawCount;
    }

    /**
     * Get the min value in axis
     *
     * @return The min value in axis
     */
    public float getMinValue() {
        return mMinValue;
    }

    /**
     * Customize the min value
     *
     * @param min The min value to set
     */
    public void setMinValue(float min) {
        internalSetMinValue(min, true);
    }

    void internalSetMinValue(float min, boolean isCustom) {
        mMinValue = min;
        mIsCustomMin = isCustom;
    }

    /**
     * Get the max value in axis
     *
     * @return The max value in axis
     */
    public float getMaxValue() {
        return mMaxValue;
    }

    /**
     * Customize the max value
     *
     * @param max The max value to set
     */
    public void setMaxValue(float max) {
        internalSetMaxValue(max, true);
    }

    void internalSetMaxValue(float max, boolean isCustom) {
        mMaxValue = max;
        mIsCustomMax = isCustom;
    }

    /**
     * Calculate the min and max values if them was not be customized
     */
    public void calcMinMaxIfNotCustom() {
        if (mIsCustomMin && mIsCustomMax) {
            return;
        }
        calcMinMax();
    }

    /**
     * Force calculate the min and max values
     */
    public void recalcMinMax() {
        mIsCustomMax = false;
        mIsCustomMin = false;
        calcMinMax();
    }

    /**
     * Calculate the min and max values by the axis itself.
     */
    protected abstract void calcMinMax();

    /**
     * Get the range of the axis value
     *
     * @return max - min
     */
    public float getRange() {
        return mMaxValue - mMinValue;
    }

    /**
     * The method will be call by LineChartView when you set to it.
     *
     * @param host The LineChartView it attached
     */
    public void attachTo(LineChartView host) {
        mHost = host;
    }

    /**
     * @return The lines in LineChartView
     */
    protected List<Line> getData() {
        return mHost.getLineData();
    }
}
