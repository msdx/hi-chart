/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.listener;

import com.parkingwang.hichart.data.PointValue;
import com.parkingwang.hichart.view.LineChartView;

/**
 * The chart value selected listener.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-13 0.1
 */
public interface OnChartValueSelectedListener {
    /**
     * @param chartView  The line chart view
     * @param lineIndex  The index of the line that contains the selected point.
     * @param pointIndex The index of the point in line.
     * @param point      The selected point.
     */
    void onValueSelected(LineChartView chartView, int lineIndex, int pointIndex, PointValue point);

    void onValueUnselected();
}
