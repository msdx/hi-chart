/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis.extend;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.parkingwang.hichart.axis.XAxisRender;

import java.util.List;

/**
 * 绘制固定的横坐标.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class FixedXAxisRender extends XAxisRender {
    private static final int MIN_SHOW_COUNT = 2;

    public float getHeight() {
        return mHeight;
    }

    @Override
    public void draw(Canvas canvas) {
        if (drawBackground) {
            canvas.drawRect(mDrawRect.left, mDrawRect.top, mDrawRect.right, mDrawRect.bottom, mBackgroundPaint);
        }

        List<String> labels = ((FixedXAxis) getXAxis()).getLabels();

        final int size = labels.size();
        final int drawCount = Math.min(getXAxis().getDrawCount(), size);
        if (drawCount < MIN_SHOW_COUNT || size < MIN_SHOW_COUNT) {
            return;
        }

        final RectF dataRect = getDataRender().getDrawRect();

        final float start = Math.max(dataRect.left, mDrawRect.left + getPaddingLeft());
        final float end = Math.min(dataRect.right, mDrawRect.right - getPaddingRight());

        final float textY = getHeight() / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2;
        final float interval = (size - 1.0f) / (drawCount - 1);
        final float drawWidth = end - start;

        for (float nearlyIndex = 0; nearlyIndex < size; nearlyIndex += interval) {
            final int labelPosition = (int) (nearlyIndex + 0.5f);
            final String label = labels.get(labelPosition);
            final float halfTextWidth = mTextPaint.measureText(label) / 2;

            float textX = start + drawWidth * labelPosition / (size - 1);
            if (labelPosition == 0) {
                float allowTextX = getPaddingLeft() + halfTextWidth;
                if (textX < allowTextX) {
                    textX = allowTextX;
                }
            } else if (labelPosition == size - 1) {
                float allowTextX = mDrawRect.right - getPaddingRight() - halfTextWidth;
                if (textX > allowTextX) {
                    textX = allowTextX;
                }
            }

            canvas.drawText(label, textX, textY, mTextPaint);
        }
    }
}
