/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.data.Line;

import java.util.List;

/**
 * 纵坐标轴
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

    public void setRangeRatio(int ratio) {
        mRangeRatio = ratio;
    }

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
