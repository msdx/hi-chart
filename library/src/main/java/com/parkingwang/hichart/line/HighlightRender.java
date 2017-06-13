/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.line;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.ColorInt;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class HighlightRender {
    private static final int DEFAULT_LINE_COLOR = Color.WHITE;
    private static final int DEFAULT_LINE_WIDTH = 2;
    private static final int DEFAULT_CIRCLE_COLOR = Color.WHITE;
    private static final int DEFAULT_CIRCLE_RADIUS = 7;

    private Paint mHighlightCirclePaint;
    private Paint mHighlightLinePaint;

    private float mHighlightRadius;

    public HighlightRender() {
        mHighlightCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mHighlightCirclePaint.setStyle(Paint.Style.FILL);
        mHighlightCirclePaint.setColor(DEFAULT_CIRCLE_COLOR);

        mHighlightLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mHighlightLinePaint.setColor(DEFAULT_LINE_COLOR);
        mHighlightLinePaint.setStrokeWidth(DEFAULT_LINE_WIDTH);
        mHighlightLinePaint.setStyle(Paint.Style.FILL);

        mHighlightRadius = DEFAULT_CIRCLE_RADIUS;
    }

    public void setHighlightCircleColor(@ColorInt int color) {
        mHighlightCirclePaint.setColor(color);
    }

    public void setHighlightLineColor(@ColorInt int color) {
        mHighlightLinePaint.setColor(color);
    }

    public void setHighlightLineWidth(float width) {
        mHighlightLinePaint.setStrokeWidth(width);
    }

    public void setHighlightCircleRadius(float radius) {
        mHighlightRadius = radius;
    }

    void draw(Canvas canvas, PointF point, float bottom) {
        if (point == null) {
            return;
        }

        canvas.drawCircle(point.x, point.y, mHighlightRadius, mHighlightCirclePaint);
        canvas.drawLine(point.x, 0, point.x, bottom, mHighlightLinePaint);
    }
}
