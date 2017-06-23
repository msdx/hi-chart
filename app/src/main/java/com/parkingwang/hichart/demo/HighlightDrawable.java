/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;

import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.data.PointValue;
import com.parkingwang.hichart.highlight.HighlightDrawableRender;
import com.parkingwang.hichart.util.DimenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * IOP的高亮的一种实现示例。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-23 0.1
 */
public class HighlightDrawable extends Drawable implements HighlightDrawableRender.UpdateCallback {

    private final Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private final Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    private String mTitle;
    private float mCircleRadius;
    private float mCircleMargin;
    private float mTitleMargin;
    private float mLineSpace;
    private float mPadding;

    private final Drawable mBackground;
    private final Rect mBackgroundPadding;

    private int mWidth;
    private int mHeight;
    private final List<HighlightInfo> mHighlightInfoList = new ArrayList<>();

    public HighlightDrawable(Context context) {
        mBackground = ResourcesCompat.getDrawable(context.getResources(), R.drawable.chart_highlight_floating_bg, null);
        mBackgroundPadding = new Rect();
        mBackground.getPadding(mBackgroundPadding);

        mTextPaint.setColor(Color.parseColor("#333333"));
        mTextPaint.setTextSize(DimenUtil.dpToPx(12));
    }

    public void setTextColor(@ColorInt int color) {
        mTextPaint.setColor(color);
    }

    public void setTextSize(float size) {
        mTextPaint.setTextSize(size);
    }

    public void setCircleRadius(float circleRadius) {
        mCircleRadius = circleRadius;
    }

    public void setCircleMargin(float circleMargin) {
        mCircleMargin = circleMargin;
    }

    public void setTitleMargin(float titleMargin) {
        mTitleMargin = titleMargin;
    }

    public void setLineSpace(float lineSpace) {
        mLineSpace = lineSpace;
    }

    public void setPadding(float padding) {
        mPadding = padding;
    }

    @Override
    public Drawable onHighlightUpdate(int lineIndex, int pointIndex, PointValue point, List<Line> lines) {
        mTitle = "第" + (int) lines.get(lineIndex).get(pointIndex).x + "周";
        mHighlightInfoList.clear();
        for (Line line : lines) {
            mHighlightInfoList.add(new HighlightInfo(line.getStyle().getCircleColor(), line.getTitle() + ": " + line.get(pointIndex).y + ""));
        }
        measure(lines.size());
        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mBackground.setBounds(0, 0, mWidth, mHeight);
        mBackground.draw(canvas);

        float startX = mBackgroundPadding.left + mPadding;
        float startY = mBackgroundPadding.top + mPadding;
        canvas.drawText(mTitle, startX, startY - mTextPaint.ascent(), mTextPaint);
        startY += mTitleMargin;
        float textLineHeight = mTextPaint.descent() - mTextPaint.ascent();
        for (HighlightInfo info : mHighlightInfoList) {
            startY += textLineHeight;
            mCirclePaint.setColor(info.circleColor);
            canvas.drawCircle(startX + mCircleRadius, startY + textLineHeight / 2, mCircleRadius, mCirclePaint);
            canvas.drawText(info.text, startX + mCircleRadius * 2 + mCircleMargin, startY - mTextPaint.ascent(), mTextPaint);
            startY += mLineSpace;
        }
    }

    private void measure(int lineSize) {
        float maxTextWidth = 0;
        float textLineWidth;
        for (HighlightInfo info : mHighlightInfoList) {
            textLineWidth = mTextPaint.measureText(info.text);
            if (maxTextWidth < textLineWidth) {
                maxTextWidth = textLineWidth;
            }
        }
        float textLineHeight = mTextPaint.descent() - mTextPaint.ascent();
        mWidth = (int) (mBackgroundPadding.left + mPadding + mCircleRadius * 2 + mCircleMargin + maxTextWidth + mPadding + mBackgroundPadding.right);
        mHeight = (int) (mBackgroundPadding.top + mPadding + mTitleMargin + textLineHeight * (lineSize + 1) + mLineSpace * (lineSize - 1) + mPadding + mBackgroundPadding.bottom);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }
}
