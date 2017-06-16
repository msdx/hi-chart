/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.line;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.formatter.AxisValueFormatter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 右坐标轴计算及绘制
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class YAxisBar {
    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("###,###,###.##");

    private static final int DEFAULT_MIN_VALUE = 0;

    private static final boolean DEFAULT_DRAW_LABELS = true;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_GRID_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 18;
    private static final int DEFAULT_LABEL_COUNT = 3;
    private static final int DEFAULT_GRID_LINE_WIDTH = 1;

    private boolean mDrawLabels = DEFAULT_DRAW_LABELS;
    private int mLabelCount = DEFAULT_LABEL_COUNT;

    private AxisValueFormatter mValueFormatter = new AxisValueFormatter() {
        @Override
        public String getFormattedValue(float value) {
            return DEFAULT_DECIMAL_FORMAT.format(value);
        }
    };

    private int mMinValue;
    private int mMaxValue;
    private boolean mIsCustomMinMax;

    private DashPathEffect mDashPathEffect;

    private Paint mGridLinePaint;
    private Paint mLabelPaint;

    private final RectF mOffsets = new RectF();
    private int mWidth;
    private int mHeight;

    public YAxisBar() {
        mGridLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mGridLinePaint.setColor(DEFAULT_GRID_COLOR);
        mGridLinePaint.setStrokeWidth(DEFAULT_GRID_LINE_WIDTH);
        mGridLinePaint.setStyle(Paint.Style.FILL);

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLabelPaint.setTextAlign(Paint.Align.CENTER);
        mLabelPaint.setColor(DEFAULT_TEXT_COLOR);
        mLabelPaint.setTextSize(DEFAULT_TEXT_SIZE);
    }

    public void setDrawLabels(boolean drawLabels) {
        mDrawLabels = drawLabels;
    }

    public void setTextColor(int textColor) {
        mLabelPaint.setColor(textColor);
    }

    public void setTextSize(float textSize) {
        mLabelPaint.setTextSize(textSize);
    }

    public void setLabelCount(int labelCount) {
        mLabelCount = labelCount;
    }

    public void enableGridDashedLine(float dashLineLength, float dashLineSpace) {
        mDashPathEffect = new DashPathEffect(new float[]{dashLineLength, dashLineSpace}, 0);
        mGridLinePaint.setPathEffect(mDashPathEffect);
    }

    public void setGridLineWidth(float gridLineWidth) {
        mGridLinePaint.setStrokeWidth(gridLineWidth);
    }

    public void setGridColor(int gridColor) {
        mGridLinePaint.setColor(gridColor);
    }

    public void setValueFormatter(AxisValueFormatter valueFormatter) {
        if (valueFormatter != null) {
            mValueFormatter = valueFormatter;
        }
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMinValue(int value) {
        mMinValue = value;
        mIsCustomMinMax = true;
    }

    public int getMinValue() {
        return mMinValue;
    }

    public void setMaxValue(int value) {
        mMaxValue = value;
        mIsCustomMinMax = true;
    }

    void setOffset(float left, float top, float right, float bottom) {
        mOffsets.set(left, top, right, bottom);
    }

    void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    int getCanvasWidth() {
        return mWidth;
    }

    int getCanvasHeight() {
        return mHeight;
    }

    public float getContentWidth() {
        return mWidth - mOffsets.left - mOffsets.right;
    }

    public float getContentHeight() {
        return mHeight - mOffsets.top - mOffsets.bottom;
    }

    public float getOffsetLeft() {
        return mOffsets.left;
    }

    public float getOffsetTop() {
        return mOffsets.top;
    }

    public float getOffsetRight() {
        return mOffsets.right;
    }

    public float getOffsetBottom() {
        return mOffsets.bottom;
    }

    public void calcMinMax(List<Entry> entryList) {
        if (mIsCustomMinMax) {
            return;
        }

        mMinValue = DEFAULT_MIN_VALUE;

        int max = Integer.MIN_VALUE;
        for (Entry entry : entryList) {
            int y = (int) entry.y;
            if (y > max) {
                max = y;
            }
        }
        boolean divide = false;
        if (max > 100) {
            max /= 100;
            divide = true;
        }
        max *= 1.2;
        int piece = mLabelCount - 1;
        max += (piece - max % piece);
        if (divide) {
            max *= 100;
        }

        int minRange = (mLabelCount - 1) * 10;
        if (max < mMinValue + minRange) {
            max = mMinValue + minRange;
        }

        mMaxValue = max;
    }

    void draw(Canvas canvas) {
        float left = mOffsets.left;
        float right = mWidth - mOffsets.right;
        float top = mOffsets.top;
        float bottom = mHeight - mOffsets.bottom;

        final float distance = (bottom - top) / (mLabelCount - 1);
        float lineY = bottom;
        final float avg = (mMaxValue - mMinValue) / (mLabelCount - 1);
        float axisValue = mMinValue;
        final float textCenterOffsetY = (mLabelPaint.ascent() + mLabelPaint.descent()) / 2;
        final float textCenterX = mWidth - mOffsets.right / 2;
        for (int i = 0; i < mLabelCount; i++) {
            canvas.drawLine(left, lineY, right, lineY, mGridLinePaint);

            if (mDrawLabels) {
                String label = mValueFormatter.getFormattedValue(axisValue);
                canvas.drawText(label, textCenterX, lineY - textCenterOffsetY, mLabelPaint);
                axisValue += avg;
            }

            lineY -= distance;
        }
    }
}
