/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.parkingwang.hichart.util.DimenUtil;

import java.text.DecimalFormat;

/**
 * Y轴绘制。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-16 0.1
 */
public class YAxisRender extends AxisRender {
    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("###,###,###.##");

    private static final boolean DEFAULT_DRAW_LABELS = true;
    private static final boolean DEFAULT_ENABLED = true;
    private static final boolean DEFAULT_ENABLED_GRID_LINE = true;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_GRID_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 18;
    private static final int DEFAULT_GRID_LINE_WIDTH = 1;
    private static final float DEFAULT_LABEL_PADDING = DimenUtil.dpToPx(7);

    private AxisLabelFormatter mLabelFormatter = new AxisLabelFormatter() {
        @Override
        public String format(float value) {
            return DEFAULT_DECIMAL_FORMAT.format(value);
        }
    };

    private boolean mEnabled = DEFAULT_ENABLED;
    private boolean mDrawLabels = DEFAULT_DRAW_LABELS;
    private boolean mEnabledGridLine = DEFAULT_ENABLED_GRID_LINE;
    private float mLabelPadding = DEFAULT_LABEL_PADDING;
    private float mInsetBottom = 0;

    private Paint mGridLinePaint;
    private Paint mLabelPaint;

    private int mWidth;

    public YAxisRender() {
        mGridLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mGridLinePaint.setColor(DEFAULT_GRID_COLOR);
        mGridLinePaint.setStrokeWidth(DEFAULT_GRID_LINE_WIDTH);
        mGridLinePaint.setStyle(Paint.Style.FILL);

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLabelPaint.setTextAlign(Paint.Align.CENTER);
        mLabelPaint.setColor(DEFAULT_TEXT_COLOR);
        mLabelPaint.setTextSize(DEFAULT_TEXT_SIZE);
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
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

    public void setGridDashedLine(float dashLineLength, float dashLineSpace) {
        mGridLinePaint.setPathEffect(new DashPathEffect(new float[]{dashLineLength, dashLineSpace}, 0));
        mEnabledGridLine = true;
    }

    public void enabledGridDashedLine(boolean enabled) {
        mEnabledGridLine = enabled;
    }

    public void setGridLineWidth(float gridLineWidth) {
        mGridLinePaint.setStrokeWidth(gridLineWidth);
    }

    public void setGridColor(int gridColor) {
        mGridLinePaint.setColor(gridColor);
    }

    public float getInsetBottom() {
        return mInsetBottom;
    }

    public void setInsetBottom(float insetBottom) {
        mInsetBottom = insetBottom;
    }

    public void setLabelFormatter(AxisLabelFormatter labelFormatter) {
        if (labelFormatter != null) {
            mLabelFormatter = labelFormatter;
        }
    }

    public float getWidth() {
        if (mDrawLabels) {
            if (mWidth != 0) {
                return mWidth;
            }

            float maxWidth = 0;
            YAxis yAxis = getYAxis();
            int count = yAxis.getDrawCount();
            final float avg = yAxis.getRange() / (count - 1);
            float axisValue = yAxis.getMinValue();
            for (int i = 0; i < count; i++) {
                String label = mLabelFormatter.format(axisValue);
                maxWidth = Math.max(maxWidth, mLabelPaint.measureText(label));
                axisValue += avg;
            }
            return maxWidth + mLabelPadding * 2;
        } else {
            return 0;
        }
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mEnabled || getHost().getLineData().isEmpty()) {
            return;
        }

        float left = mDrawRect.left;
        float right = mDrawRect.right;
        float top = mDrawRect.top;
        float bottom = mDrawRect.bottom - mInsetBottom;

        float dataRight = getDataRender().getDrawRect().right;

        final YAxis yAxis = getYAxis();
        final int drawCount = yAxis.getDrawCount();
        final float distance = (bottom - top) / (drawCount - 1);
        float lineY = bottom;
        final float avg = yAxis.getRange() / (drawCount - 1);
        float axisValue = yAxis.getMinValue();
        final float textCenterOffsetY = (mLabelPaint.ascent() + mLabelPaint.descent()) / 2;
        final float textCenterX = (right + dataRight) / 2;// dataRight + (right - dataRight) / 2
        for (int i = 0; i < drawCount; i++) {
            if (mEnabledGridLine) {
                canvas.drawLine(left, lineY, dataRight, lineY, mGridLinePaint);
            }

            if (mDrawLabels) {
                String label = mLabelFormatter.format(axisValue);
                canvas.drawText(label, textCenterX, lineY - textCenterOffsetY, mLabelPaint);
                axisValue += avg;
            }

            lineY -= distance;
        }
    }

    protected YAxis getYAxis() {
        return getHost().getYAxis();
    }
}
