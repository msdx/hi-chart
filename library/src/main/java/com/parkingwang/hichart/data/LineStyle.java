/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.parkingwang.hichart.util.DimenUtil;

/**
 * 线绘制效果配置。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-20 0.1
 */
public class LineStyle {
    private static final float DEFAULT_LINE_WIDTH = DimenUtil.dpToPx(2);
    private static final int DEFAULT_LINE_COLOR = Color.WHITE;
    private static final float DEFAULT_CIRCLE_RADIUS = DimenUtil.dpToPx(3.5f);
    private static final int DEFAULT_CIRCLE_COLOR = Color.WHITE;
    private static final float DEFAULT_CIRCLE_HOLE_RADIUS = DimenUtil.dpToPx(1.5f);
    private static final int DEFAULT_CIRCLE_HOLE_COLOR = Color.parseColor("#1D8FEE");

    private float mLineWidth = DEFAULT_LINE_WIDTH;
    @ColorInt
    private int mLineColor = DEFAULT_LINE_COLOR;
    private float mCircleRadius = DEFAULT_CIRCLE_RADIUS;
    @ColorInt
    private int mCircleColor = DEFAULT_CIRCLE_COLOR;
    private float mCircleHoleRadius = DEFAULT_CIRCLE_HOLE_RADIUS;
    @ColorInt
    private int mCircleHoleColor = DEFAULT_CIRCLE_HOLE_COLOR;

    public float getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
    }

    public float getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        mCircleRadius = circleRadius;
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int circleColor) {
        mCircleColor = circleColor;
    }

    public float getCircleHoleRadius() {
        return mCircleHoleRadius;
    }

    public void setCircleHoleRadius(float circleHoleRadius) {
        mCircleHoleRadius = circleHoleRadius;
    }

    public int getCircleHoleColor() {
        return mCircleHoleColor;
    }

    public void setCircleHoleColor(int circleHoleColor) {
        mCircleHoleColor = circleHoleColor;
    }
}