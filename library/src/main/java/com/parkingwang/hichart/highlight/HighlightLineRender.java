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
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class HighlightLineRender extends HighlightRender {
    private static final int DEFAULT_LINE_COLOR = Color.WHITE;
    private static final int DEFAULT_LINE_WIDTH = 2;

    private Paint mHighlightCirclePaint;
    private Paint mHighlightLinePaint;

    private boolean mEnabled = false;

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

    public void setHighlightLineColor(@ColorInt int color) {
        mHighlightLinePaint.setColor(color);
    }

    public void setHighlightLineWidth(float width) {
        mHighlightLinePaint.setStrokeWidth(width);
    }

    @Override
    public void draw(Canvas canvas) {
        PointValue pointValue = getPointValue();
        if (pointValue == null || !mEnabled) {
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
