/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import com.parkingwang.hichart.data.Entry;

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
        float min = Float.MAX_VALUE;
        List<Entry> entryList = getData();

        float max = Float.MIN_VALUE;
        for (Entry entry : entryList) {
            int x = (int) entry.x;
            if (x > max) {
                max = x;
            }
            if (x < min) {
                min = x;
            }
        }

        mMinValue = min;
        mMaxValue = max;
    }
}
