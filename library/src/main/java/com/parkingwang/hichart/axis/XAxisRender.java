/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.parkingwang.hichart.data.Line;

import java.text.DecimalFormat;
import java.util.List;

/**
 * The render to draw X-axis.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class XAxisRender extends AxisRender {
    private static final int MIN_SHOW_COUNT = 2;
    private static final DecimalFormat LABEL_FORMATTER = new DecimalFormat("###,###,##0.##");

    protected AxisLabelFormatter mAxisLabelFormatter = new AxisLabelFormatter() {
        @Override
        public String format(float value, float max) {
            return LABEL_FORMATTER.format(value);
        }
    };

    protected float mHeight;

    private float mPaddingLeft;
    private float mPaddingRight;

    /**
     * @return The height of the X-axis
     */
    public float getHeight() {
        return !getLineData().isEmpty() ? mHeight : 0;
    }

    /**
     * Set the height to the X-axis.
     *
     * @param height The height to set.
     */
    public void setHeight(float height) {
        mHeight = height;
    }

    /**
     * @return The left padding of the X-axis
     */
    public float getPaddingLeft() {
        return mPaddingLeft;
    }

    /**
     * Set the left padding of the X-axis
     *
     * @param paddingLeft The padding to set.
     */
    public void setPaddingLeft(float paddingLeft) {
        mPaddingLeft = paddingLeft;
    }

    /**
     * @return The right padding of the X-axis
     */
    public float getPaddingRight() {
        return mPaddingRight;
    }

    /**
     * Set the right padding of the X-axis
     *
     * @param paddingRight The padding to set
     */
    public void setPaddingRight(float paddingRight) {
        mPaddingRight = paddingRight;
    }

    /**
     * Set the axis label formatter
     *
     * @param axisLabelFormatter the formatter to set.
     */
    public void setAxisLabelFormatter(AxisLabelFormatter axisLabelFormatter) {
        mAxisLabelFormatter = axisLabelFormatter;
    }

    @Override
    public void draw(Canvas canvas) {
        List<Line> data = getLineData();
        if (data.isEmpty()) {
            return;
        }

        canvas.save();
        canvas.translate(0, getDrawRect().bottom - getHeight());
        drawXAxis(canvas);
        canvas.restore();
    }

    private void drawXAxis(Canvas canvas) {
        if (mDrawBackground) {
            canvas.drawRect(mDrawRect.left, 0, mDrawRect.right, getHeight(), mBackgroundPaint);
        }

        final int drawCount = getXAxis().getDrawCount();
        if (drawCount < MIN_SHOW_COUNT) {
            return;
        }

        final RectF dataRect = getDataRender().getDrawRect();

        final float start = Math.max(dataRect.left, mDrawRect.left + mPaddingLeft);
        final float end = Math.min(dataRect.right, mDrawRect.right - mPaddingRight);

        final float textY = mHeight / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2;
        final float drawWidth = end - start;
        final float distance = drawWidth / (drawCount - 1);

        XAxis xAxis = getXAxis();
        final float avg = xAxis.getRange() / (drawCount - 1);
        final float min = xAxis.getMinValue();
        final float max = xAxis.getMaxValue();
        float value = min;

        for (int i = 0; i < drawCount; i++) {
            final String label = mAxisLabelFormatter.format(value, max);
            final float halfTextWidth = mTextPaint.measureText(label) / 2;

            float textX = start + distance * i;
            if (i == 0) {
                float allowTextX = mPaddingLeft + halfTextWidth;
                if (textX < allowTextX) {
                    textX = allowTextX;
                }
            } else if (i == drawCount - 1) {
                float allowTextX = mDrawRect.right - mPaddingRight - halfTextWidth;
                if (textX > allowTextX) {
                    textX = allowTextX;
                }
            }

            canvas.drawText(label, textX, textY, mTextPaint);

            value += avg;
        }
    }

    /**
     * @return The X-axis it draws
     */
    public XAxis getXAxis() {
        return getHost().getXAxis();
    }
}
