/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.data.Line;

import java.util.List;

/**
 * Y-axis
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-16 0.1
 */
public class YAxis extends Axis {
    private static final float DEFAULT_RANGE_RATIO = 0.2f;
    private static final int DEFAULT_SHOW_COUNT = 3;

    private float mRangeRatio = DEFAULT_RANGE_RATIO;

    public YAxis() {
        setDrawCount(DEFAULT_SHOW_COUNT);
    }

    /**
     * Set the range ratio.
     * Normally, the Y-axis will reserve some space for the maximum and minimum values to make show better.
     * So this field was introduced to assist in calculating the maximum and minimum values.
     * The actual maximum value of the Y-axis is: The maximum value in lines + range * ratio
     * The actual minimum value of the Y-axis is: The minimum value in lines - range * ratio.
     *
     * @param ratio The range ratio to set.
     */
    public void setRangeRatio(int ratio) {
        mRangeRatio = ratio;
    }

    /**
     * @return The current range ratio.
     * @see #setRangeRatio(int)
     */
    public float getRangeRatio() {
        return mRangeRatio;
    }

    @Override
    public void calcMinMax() {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        List<Line> lines = getData();
        for (Line line : lines) {
            for (Entry entry : line) {
                int y = (int) entry.y;
                if (y > max) {
                    max = y;
                }
                if (y < min) {
                    min = y;
                }
            }
        }

        float rangeSpace = (max - min) * mRangeRatio;
        if (!mIsCustomMin) {
            mMinValue = min - rangeSpace;
        }
        if (!mIsCustomMax) {
            mMaxValue = max + rangeSpace;
        }
    }
}
