/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

/**
 * This interface was use to format the value into text.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public interface AxisLabelFormatter {
    /**
     * Format the value into text.
     *
     * @param value The value to format
     * @return The formatted text
     */
    String format(float value);
}
