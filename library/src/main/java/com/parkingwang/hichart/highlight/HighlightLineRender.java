/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.highlight;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

import com.parkingwang.hichart.data.LineStyle;
import com.parkingwang.hichart.data.PointValue;

/**
 * The default render for highlight. It can draw a circle and vertical and horizontal line on the
 * highlight point.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class HighlightLineRender extends HighlightRender {
    private static final int DEFAULT_LINE_COLOR = Color.WHITE;
    private static final int DEFAULT_LINE_WIDTH = 2;

    private Paint mHighlightCirclePaint;
    private Paint mHighlightLinePaint;

    private boolean mDrawVerticalLine = true;
    private boolean mDrawHorizontalLine = true;

    public HighlightLineRender() {
        mHighlightCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mHighlightCirclePaint.setStyle(Paint.Style.FILL);

        mHighlightLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mHighlightLinePaint.setColor(DEFAULT_LINE_COLOR);
        mHighlightLinePaint.setStrokeWidth(DEFAULT_LINE_WIDTH);
        mHighlightLinePaint.setStyle(Paint.Style.FILL);
    }

    /**
     * Set whether to draw the vertical line.
     *
     * @param drawVerticalLine True to draw, false otherwise.
     */
    public void setDrawVerticalLine(boolean drawVerticalLine) {
        mDrawVerticalLine = drawVerticalLine;
    }

    /**
     * Set whether to draw the horizontal line
     *
     * @param drawHorizontalLine True to draw the horizontal direction line, false otherwise.
     */
    public void setDrawHorizontalLine(boolean drawHorizontalLine) {
        mDrawHorizontalLine = drawHorizontalLine;
    }

    /**
     * Set the color for the highlight line.
     *
     * @param color The color to set.
     */
    public void setHighlightLineColor(@ColorInt int color) {
        mHighlightLinePaint.setColor(color);
    }

    /**
     * Set the width to the highlight line.
     *
     * @param width The width to set.
     */
    public void setHighlightLineWidth(float width) {
        mHighlightLinePaint.setStrokeWidth(width);
    }

    @Override
    public void draw(Canvas canvas) {
        PointValue pointValue = getPointValue();
        if (pointValue == null || !isEnabled()) {
            return;
        }
        LineStyle style = getData().get(getLineIndex()).getStyle();
        mHighlightCirclePaint.setColor(style.getCircleColor());
        float radius = style.getCircleRadius();
        RectF rect = getDrawRect();
        canvas.drawCircle(pointValue.x, pointValue.y, radius, mHighlightCirclePaint);
        if (mDrawHorizontalLine) {
            canvas.drawLine(rect.left, pointValue.y, rect.right, pointValue.y, mHighlightLinePaint);
        }
        if (mDrawVerticalLine) {
            canvas.drawLine(pointValue.x, rect.top, pointValue.x, rect.bottom, mHighlightLinePaint);
        }
    }
}
