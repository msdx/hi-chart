/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.line;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * 折线图填充绘制
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class LineFillRender {
    private static final int DEFAULT_FILL_COLOR = Color.parseColor("#1BFFFFFF");

    private final RectF mFillContent;

    private final Path mPath;
    private final Paint mFillPaint;

    public LineFillRender() {
        mFillContent = new RectF();
        mPath = new Path();
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mFillPaint.setColor(DEFAULT_FILL_COLOR);
        mFillPaint.setStyle(Paint.Style.FILL);
    }

    public void setFillColor(int color) {
        mFillPaint.setColor(color);
    }

    void setFillContent(float left, float top, float right, float bottom) {
        mFillContent.set(left, top, right, bottom);
    }

    public void updateFillArea(Path path) {
        mPath.set(path);
        mPath.lineTo(mFillContent.right, mFillContent.bottom);
        mPath.lineTo(mFillContent.left, mFillContent.bottom);
    }

    void draw(Canvas canvas) {
        canvas.drawPath(mPath, mFillPaint);
    }
}
