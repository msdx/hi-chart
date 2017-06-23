/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.empty;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.text.TextUtils;

import com.parkingwang.hichart.render.BaseRender;

/**
 * Draw the text in the center of the line chart if there has no data.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class EmptyTextRender extends BaseRender implements EmptyRender {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private String mText;

    public EmptyTextRender() {
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        final float x = (mDrawRect.left + mDrawRect.right) / 2;
        final float y = (mDrawRect.top + mDrawRect.bottom) / 2 - (mPaint.ascent() + mPaint.descent()) / 2;
        canvas.drawText(mText, x, y, mPaint);
    }

    /**
     * @param text The text to draw.
     */
    public void setText(String text) {
        mText = text;
    }

    /**
     * @param color The color of the text.
     */
    public void setTextColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    /**
     * @param textSize The size of the text.
     */
    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
    }

    /**
     * @return The paint to draw the text.
     */
    public Paint getPaint() {
        return mPaint;
    }
}
