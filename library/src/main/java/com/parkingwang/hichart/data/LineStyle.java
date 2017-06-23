/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

import android.graphics.Color;
import android.graphics.Xfermode;
import android.support.annotation.ColorInt;

import com.parkingwang.hichart.util.DimenUtil;

/**
 * LineStyle is a configuration of the line about how to draw the it.
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
    private static final boolean DEFAULT_FILL = false;
    private static final int DEFAULT_FILL_COLOR = Color.parseColor("#1BFFFFFF");

    private float mLineWidth = DEFAULT_LINE_WIDTH;
    @ColorInt
    private int mLineColor = DEFAULT_LINE_COLOR;
    private float mCircleRadius = DEFAULT_CIRCLE_RADIUS;
    @ColorInt
    private int mCircleColor = DEFAULT_CIRCLE_COLOR;
    private float mCircleHoleRadius = DEFAULT_CIRCLE_HOLE_RADIUS;
    @ColorInt
    private int mCircleHoleColor = DEFAULT_CIRCLE_HOLE_COLOR;
    private boolean mIsFill = DEFAULT_FILL;
    @ColorInt
    private int mFillColor = DEFAULT_FILL_COLOR;
    private Xfermode mFillMode = null;

    /**
     * @return The width of the line.
     */
    public float getLineWidth() {
        return mLineWidth;
    }

    /**
     * Set the width to the line.
     *
     * @param lineWidth The width to set.
     */
    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
    }

    /**
     * @return The line color.
     */
    public int getLineColor() {
        return mLineColor;
    }

    /**
     * @param lineColor The color to set.
     */
    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
    }

    /**
     * @return The circle radius.
     */
    public float getCircleRadius() {
        return mCircleRadius;
    }

    /**
     * @param circleRadius The radius of the circle to set.
     */
    public void setCircleRadius(float circleRadius) {
        mCircleRadius = circleRadius;
    }

    /**
     * @return The circle color.
     */
    public int getCircleColor() {
        return mCircleColor;
    }

    /**
     * @param circleColor The color to set.
     */
    public void setCircleColor(int circleColor) {
        mCircleColor = circleColor;
    }

    /**
     * @return The radius of the filled circle
     */
    public float getCircleHoleRadius() {
        return mCircleHoleRadius;
    }

    /**
     * Set the radius of the filled circle.
     *
     * @param circleHoleRadius The radius to set.
     */
    public void setCircleHoleRadius(float circleHoleRadius) {
        mCircleHoleRadius = circleHoleRadius;
    }

    /**
     * @return The color of the filled color.
     */
    public int getCircleHoleColor() {
        return mCircleHoleColor;
    }

    /**
     * @param circleHoleColor The color to set.
     */
    public void setCircleHoleColor(int circleHoleColor) {
        mCircleHoleColor = circleHoleColor;
    }

    /**
     * Whether fill the area below the line.
     *
     * @return True to fill the area, false otherwise.
     */
    public boolean isFill() {
        return mIsFill;
    }

    /**
     * @param fill Set whether fill the area below the line.
     */
    public void setFill(boolean fill) {
        mIsFill = fill;
    }

    /**
     * @return The color of the filled area.
     */
    public int getFillColor() {
        return mFillColor;
    }

    /**
     * Set the color of the filled area.
     *
     * @param fillColor The color to set.
     */
    public void setFillColor(int fillColor) {
        mFillColor = fillColor;
    }

    /**
     * @return The fill mode.
     */
    public Xfermode getFillMode() {
        return mFillMode;
    }

    /**
     * Set the mode of the paint of how to draw the filled areas for all lines.
     *
     * @param fillMode The mode to set.
     * @see Xfermode
     */
    public void setFillMode(Xfermode fillMode) {
        mFillMode = fillMode;
    }
}