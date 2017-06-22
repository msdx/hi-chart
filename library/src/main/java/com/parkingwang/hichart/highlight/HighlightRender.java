/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.highlight;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

import com.parkingwang.hichart.data.PointValue;
import com.parkingwang.hichart.render.BaseRender;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class HighlightRender extends BaseRender {
    private static final int DEFAULT_LINE_COLOR = Color.WHITE;
    private static final int DEFAULT_LINE_WIDTH = 2;

    private Paint mHighlightCirclePaint;
    private Paint mHighlightLinePaint;

    private boolean mEnabled = false;

    private float mHighlightRadius;
    private boolean mDrawVerticalLine = true;
    private boolean mDrawHorizontalLine = true;

    private PointValue mPointValue;

    public HighlightRender() {
        mHighlightCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mHighlightCirclePaint.setStyle(Paint.Style.FILL);

        mHighlightLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mHighlightLinePaint.setColor(DEFAULT_LINE_COLOR);
        mHighlightLinePaint.setStrokeWidth(DEFAULT_LINE_WIDTH);
        mHighlightLinePaint.setStyle(Paint.Style.FILL);
    }

    public void setDrawVerticalLine(boolean drawVerticalLine) {
        mDrawVerticalLine = drawVerticalLine;
    }

    public void setDrawHorizontalLine(boolean drawHorizontalLine) {
        mDrawHorizontalLine = drawHorizontalLine;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
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

    public void setPointValue(PointValue pointValue) {
        mPointValue = pointValue;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mPointValue == null || !mEnabled) {
            return;
        }
        RectF rect = getDrawRect();
        canvas.drawCircle(mPointValue.x, mPointValue.y, mHighlightRadius, mHighlightCirclePaint);
        if (mDrawHorizontalLine) {
            canvas.drawLine(rect.left, mPointValue.y, rect.right, mPointValue.y, mHighlightLinePaint);
        }
        if (mDrawVerticalLine) {
            canvas.drawLine(mPointValue.x, rect.top, mPointValue.x, rect.bottom, mHighlightLinePaint);
        }
    }
}
