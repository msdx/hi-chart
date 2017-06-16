/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.parkingwang.hichart.data.Entry;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 绘制横坐标.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class XAxisRender extends AxisRender {
    private static final int MIN_SHOW_COUNT = 2;
    private static final DecimalFormat LABEL_FORMATTER = new DecimalFormat("###,###,###.##");

    protected AxisLabelFormatter mAxisLabelFormatter = new AxisLabelFormatter() {
        @Override
        public String format(float value) {
            return LABEL_FORMATTER.format(value);
        }
    };

    protected float mHeight;

    private float mPaddingLeft;
    private float mPaddingRight;

    public float getHeight() {
        return !getLineData().isEmpty() ? mHeight : 0;
    }

    public void setHeight(float height) {
        mHeight = height;
    }

    public float getPaddingLeft() {
        return mPaddingLeft;
    }

    public void setPaddingLeft(float paddingLeft) {
        mPaddingLeft = paddingLeft;
    }

    public float getPaddingRight() {
        return mPaddingRight;
    }

    public void setPaddingRight(float paddingRight) {
        mPaddingRight = paddingRight;
    }

    public void setAxisLabelFormatter(AxisLabelFormatter axisLabelFormatter) {
        mAxisLabelFormatter = axisLabelFormatter;
    }

    @Override
    public void draw(Canvas canvas) {
        List<Entry> data = getLineData();

        if (data.isEmpty()) {
            return;
        }

        if (drawBackground) {
            canvas.drawRect(mDrawRect.left, mDrawRect.top, mDrawRect.right, mDrawRect.bottom, mBackgroundPaint);
        }

        final int size = data.size();
        final int drawCount = Math.min(getXAxis().getDrawCount(), size);
        if (drawCount < MIN_SHOW_COUNT || size < MIN_SHOW_COUNT) {
            return;
        }

        final RectF dataRect = getDataRender().getDrawRect();

        final float start = Math.max(dataRect.left, mDrawRect.left + mPaddingLeft);
        final float end = Math.min(dataRect.right, mDrawRect.right - mPaddingRight);

        final float textY = mHeight / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2;
        final float interval = (size - 1.0f) / (drawCount - 1);
        final float drawWidth = end - start;

        for (float nearlyIndex = 0; nearlyIndex < size; nearlyIndex += interval) {
            final int labelPosition = (int) (nearlyIndex + 0.5f);
            final String label = mAxisLabelFormatter.format(data.get(labelPosition).x);
            final float halfTextWidth = mTextPaint.measureText(label) / 2;

            float textX = start + drawWidth * labelPosition / (size - 1);
            if (labelPosition == 0) {
                float allowTextX = mPaddingLeft + halfTextWidth;
                if (textX < allowTextX) {
                    textX = allowTextX;
                }
            } else if (labelPosition == size - 1) {
                float allowTextX = mDrawRect.right - mPaddingRight - halfTextWidth;
                if (textX > allowTextX) {
                    textX = allowTextX;
                }
            }

            canvas.drawText(label, textX, textY, mTextPaint);
        }
    }

    public XAxis getXAxis() {
        return getHost().getXAxis();
    }
}
