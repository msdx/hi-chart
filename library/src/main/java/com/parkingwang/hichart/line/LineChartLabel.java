/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Arrays;
import java.util.List;

/**
 * 折线图x坐标轴坐标
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-06 0.1
 */
public class LineChartLabel extends View {

    private static final int MIN_COUNT = 2;
    private static final int DEFAULT_MAX_COUNT = 7;

    private List<String> mLabels;
    private int mMaxCount = DEFAULT_MAX_COUNT;

    private Paint mPaint;

    private int mOffsetLeft;
    private int mOffsetRight;

    public LineChartLabel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        if (isInEditMode()) {
            setLabels(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int size = mLabels == null ? 0 : mLabels.size();
        if (mMaxCount < MIN_COUNT || size < MIN_COUNT) {
            return;
        }

        final float start = Math.max(mOffsetLeft, getPaddingLeft());
        final float end = getWidth() - Math.max(mOffsetRight, getPaddingRight());

        final float textY = getHeight() / 2 - (mPaint.descent() + mPaint.ascent()) / 2;
        final int drawCount = Math.min(mMaxCount, size);
        final float interval = (size - 1.0f) / (drawCount - 1);
        final float drawWidth = end - start;

        for (float nearlyIndex = 0; nearlyIndex < size; nearlyIndex += interval) {
            final int labelPosition = (int) (nearlyIndex + 0.5f);
            final String label = mLabels.get(labelPosition);
            final float halfTextWidth = mPaint.measureText(label) / 2;

            float textX = start + drawWidth * labelPosition / (size - 1);
            if (labelPosition == 0) {
                if (textX - halfTextWidth < getPaddingLeft()) {
                    textX = getPaddingLeft() + halfTextWidth;
                }
            } else if (labelPosition == size - 1) {
                if (textX + halfTextWidth > getWidth() - getPaddingRight()) {
                    textX = getWidth() - getPaddingRight() - halfTextWidth;
                }
            }

            canvas.drawText(label, textX, textY, mPaint);
        }
    }

    public final void setLabels(List<String> labels) {
        mLabels = labels;
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = Math.max(maxCount, MIN_COUNT);
        invalidate();
    }

    public void setTextSize(int textSize) {
        mPaint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, textSize, getResources().getDisplayMetrics()));
    }

    public void setTextColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    public int getOffsetLeft() {
        return mOffsetLeft;
    }

    public void setOffsetLeft(int offsetLeft) {
        mOffsetLeft = offsetLeft;
    }

    public int getOffsetRight() {
        return mOffsetRight;
    }

    public void setOffsetRight(int offsetRight) {
        mOffsetRight = offsetRight;
    }
}
