/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.listener;

import com.parkingwang.hichart.data.PointValue;
import com.parkingwang.hichart.view.LineChartView;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-13 0.1
 */
public interface OnChartValueSelectedListener {
    void onValueSelected(LineChartView chartView, int lineIndex, int pointIndex, PointValue point);

    void onValueUnselected();
}
