/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.parkingwang.hichart.line.LineChartView;
import com.parkingwang.hichart.render.BaseRender;

import java.util.List;

/**
 * 数据区
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class DataRender extends BaseRender {
    private LineChartView mHost;

    private final Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private final Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private final Paint mCircleHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    public DataRender() {
        mLinePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCircleHolePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        List<Line> lines = getData();
        if (lines.isEmpty()) {
            return;
        }

        float percent = mHost.getAnimatorProgress();
        float endDraw = mDrawRect.left + mDrawRect.width() * percent;
        boolean isClip = percent < LineChartView.PROGRESS_COMPLETE;

        if (isClip) {
            canvas.save();
            canvas.clipRect(0, mDrawRect.top, endDraw, mDrawRect.bottom);
        }

        for (Line line : lines) {
            final LineStyle lineStyle = line.getStyle();
            mLinePaint.setStrokeWidth(lineStyle.getLineWidth());
            mLinePaint.setColor(lineStyle.getLineColor());
            mCirclePaint.setColor(lineStyle.getCircleColor());
            mCircleHolePaint.setColor(lineStyle.getCircleHoleColor());
            final float circleRadius = lineStyle.getCircleRadius();
            final float circleHoleRadius = lineStyle.getCircleHoleRadius();

            List<PointValue> pointValues = line.getPointValues();
            PointValue previous = null;
            for (PointValue point : pointValues) {
                if (previous != null) {
                    if (previous.x > endDraw) {
                        break;
                    }
                    canvas.drawLine(previous.x, previous.y, point.x, point.y, mLinePaint);
                    drawPoint(canvas, circleRadius, circleHoleRadius, previous);
                }
                previous = point;
            }
            if (previous != null) {
                if (previous.x > endDraw) {
                    continue;
                }
                drawPoint(canvas, circleRadius, circleHoleRadius, previous);
            }
        }

        if (isClip) {
            canvas.restore();
        }
    }

    private void drawPoint(Canvas canvas, float circleRadius, float circleHoleRadius, PointValue previous) {
        canvas.drawCircle(previous.x, previous.y, circleRadius, mCirclePaint);
        canvas.drawCircle(previous.x, previous.y, circleHoleRadius, mCircleHolePaint);
    }

    public void attachTo(LineChartView view) {
        mHost = view;
    }

    protected List<Line> getData() {
        return mHost.getLineData();
    }
}
