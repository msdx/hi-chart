/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 尺寸计算工具类
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-16 0.1
 */
public class DimenUtil {

    private static final DisplayMetrics DISPLAY_METRICS = Resources.getSystem().getDisplayMetrics();

    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, DISPLAY_METRICS);
    }
}
