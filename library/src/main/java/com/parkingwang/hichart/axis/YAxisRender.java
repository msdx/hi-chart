/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.parkingwang.hichart.util.DimenUtil;

import java.text.DecimalFormat;

/**
 * The render to draw Y-axis.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @version 0.3
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
        public String format(float value, float max) {
            return DEFAULT_DECIMAL_FORMAT.format(value);
        }
    };

    private boolean mEnabled = DEFAULT_ENABLED;
    private boolean mDrawLabels = DEFAULT_DRAW_LABELS;
    private boolean mEnabledGridLine = DEFAULT_ENABLED_GRID_LINE;
    private float mLabelPadding = DEFAULT_LABEL_PADDING;
    private float mInsetBottom = 0;

    private Paint mGridLinePaint;

    private YAxisGravity mAxisGravity = YAxisGravity.RIGHT;

    private int mWidth;

    public YAxisRender() {
        mGridLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mGridLinePaint.setColor(DEFAULT_GRID_COLOR);
        mGridLinePaint.setStrokeWidth(DEFAULT_GRID_LINE_WIDTH);
        mGridLinePaint.setStyle(Paint.Style.FILL);

        setTextColor(DEFAULT_TEXT_COLOR);
        setTextSize(DEFAULT_TEXT_SIZE);
    }

    /**
     * @since 0.3
     */
    public YAxisGravity getAxisGravity() {
        return mAxisGravity;
    }

    /**
     * @since 0.3
     */
    public void setAxisGravity(@NonNull YAxisGravity axisGravity) {
        mAxisGravity = axisGravity;
    }

    /**
     * Set the enabled state to this render.
     *
     * @param enabled True to draw Y-axis, false otherwise.
     */
    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    /**
     * Set whether draw the Y-axis labels.
     *
     * @param drawLabels True to draw labels, false otherwise.
     */
    public void setDrawLabels(boolean drawLabels) {
        mDrawLabels = drawLabels;
    }

    /**
     * Set the grid dash line length and space.
     *
     * @param dashLineLength The length to set.
     * @param dashLineSpace  The space to set.
     */
    public void setGridDashedLine(float dashLineLength, float dashLineSpace) {
        mGridLinePaint.setPathEffect(new DashPathEffect(new float[]{dashLineLength, dashLineSpace}, 0));
        mEnabledGridLine = true;
    }

    /**
     * Set whether enable draw the grid line
     *
     * @param enabled True to draw the grid line, false otherwise.
     */
    public void enabledGridDashedLine(boolean enabled) {
        mEnabledGridLine = enabled;
    }

    /**
     * Set the grid line width.
     *
     * @param gridLineWidth The width to set.
     */
    public void setGridLineWidth(float gridLineWidth) {
        mGridLinePaint.setStrokeWidth(gridLineWidth);
    }

    /**
     * Set the grid line color.
     *
     * @param gridColor The color to set.
     */
    public void setGridLineColor(int gridColor) {
        mGridLinePaint.setColor(gridColor);
    }

    /**
     * @return The bottom inset of the Y-axis.
     */
    public float getInsetBottom() {
        return mInsetBottom;
    }

    /**
     * Set the bottom inset of the Y-axis
     *
     * @param insetBottom The inset to set.
     */
    public void setInsetBottom(float insetBottom) {
        mInsetBottom = insetBottom;
    }

    /**
     * Set the label formatter for Y-axis
     *
     * @param labelFormatter The label formatter to set.
     */
    public void setLabelFormatter(AxisLabelFormatter labelFormatter) {
        if (labelFormatter != null) {
            mLabelFormatter = labelFormatter;
        }
    }

    /**
     * @return The width of the Y-axis.
     */
    public float getWidth() {
        if (mDrawLabels) {
            if (mWidth != 0) {
                return mWidth;
            }

            float maxWidth = 0;
            YAxis yAxis = getYAxis();
            int count = yAxis.getDrawCount();
            final float avg = yAxis.getRange() / (count - 1);
            final float max = yAxis.getMaxValue();
            float axisValue = yAxis.getMinValue();
            final Paint textPaint = getTextPaint();
            for (int i = 0; i < count; i++) {
                String label = mLabelFormatter.format(axisValue, max);
                maxWidth = Math.max(maxWidth, textPaint.measureText(label));
                axisValue += avg;
            }
            return maxWidth + mLabelPadding * 2;
        } else {
            return 0;
        }
    }

    /**
     * Set the width to the Y-axis
     *
     * @param width The width of the Y-axis
     */
    public void setWidth(int width) {
        mWidth = width;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mEnabled || getHost().getLineData().isEmpty()) {
            return;
        }

        float right = mDrawRect.right;
        float top = mDrawRect.top;
        float bottom = mDrawRect.bottom - mInsetBottom;

        float dataRight = getDataRender().getDrawRect().right;
        float dataLeft = getDataRender().getDrawRect().left;

        final Paint textPaint = getTextPaint();
        final YAxis yAxis = getYAxis();
        final int drawCount = yAxis.getDrawCount();
        final float distance = (bottom - top) / (drawCount - 1);
        float lineY = bottom;
        final float avg = yAxis.getRange() / (drawCount - 1);
        final float max = yAxis.getMaxValue();
        float axisValue = yAxis.getMinValue();
        final float textCenterOffsetY = (textPaint.ascent() + textPaint.descent()) / 2;
        // dataRight + (right - dataRight) / 2
        final float textCenterX = (mAxisGravity == YAxisGravity.LEFT)
                ? dataLeft / 2 : (right + dataRight) / 2;
        for (int i = 0; i < drawCount; i++) {
            if (mEnabledGridLine) {
                canvas.drawLine(dataLeft, lineY, dataRight, lineY, mGridLinePaint);
            }

            if (mDrawLabels) {
                String label = mLabelFormatter.format(axisValue, max);
                canvas.drawText(label, textCenterX, lineY - textCenterOffsetY, textPaint);
                axisValue += avg;
            }

            lineY -= distance;
        }
    }

    /**
     * @return The Y-axis of the line chart.
     */
    protected YAxis getYAxis() {
        return getHost().getYAxis(mAxisGravity);
    }
}
