/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.data.Line;

import java.util.List;

/**
 * 横坐标
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class XAxis extends Axis {

    @Override
    public void calcMinMax() {
        List<Line> lines = getData();
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (Line line : lines) {
            for (Entry entry : line) {
                int x = (int) entry.x;
                if (x > max) {
                    max = x;
                }
                if (x < min) {
                    min = x;
                }
            }

        }
        mMinValue = min;
        mMaxValue = max;
    }
}
