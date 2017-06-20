/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis.extend;

import com.parkingwang.hichart.axis.YAxis;
import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.data.Line;

import java.util.List;

/**
 * 数值显示友好的Y轴。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-16 0.1
 */
public class PrettyYAxis extends YAxis {

    @Override
    public void calcMinMax() {
        mMinValue = 0;
        int max = Integer.MIN_VALUE;

        List<Line> lines = getData();
        for (Line line : lines) {
            for (Entry entry : line) {
                if (entry.y > max) {
                    max = (int) entry.y;
                }
            }
        }
        boolean divide = false;
        if (max > 100) {
            max /= 100;
            divide = true;
        }
        max += max * getRangeRatio();

        int piece = getDrawCount() - 1;
        max += (piece - max % piece);
        if (divide) {
            max *= 100;
        }

        int minRange = piece * 10;
        if (max < mMinValue + minRange) {
            max = (int) (mMinValue + minRange);
        }

        mMaxValue = max;
    }
}
