/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.parkingwang.hichart.axis.XAxis;
import com.parkingwang.hichart.axis.YAxis;
import com.parkingwang.hichart.axis.YAxisRender;
import com.parkingwang.hichart.line.LineChartView;
import com.parkingwang.hichart.render.BaseRender;

import java.util.ArrayList;
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

        prepareLinePoints(lines);

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
                    canvas.drawLine(previous.x, previous.y, point.x, point.y, mLinePaint);
                    drawPoint(canvas, circleRadius, circleHoleRadius, previous);
                }
                previous = point;
            }
            if (previous != null) {
                drawPoint(canvas, circleRadius, circleHoleRadius, previous);
            }
        }
    }

    private void drawPoint(Canvas canvas, float circleRadius, float circleHoleRadius, PointValue previous) {
        canvas.drawCircle(previous.x, previous.y, circleRadius, mCirclePaint);
        canvas.drawCircle(previous.x, previous.y, circleHoleRadius, mCircleHolePaint);
    }

    private void prepareLinePoints(List<Line> lines) {
        YAxisRender yAxisRender = mHost.getYAxisRender();
        RectF yAxisDrawRect = yAxisRender.getDrawRect();
        float left = mDrawRect.left;
        float right = mDrawRect.right;
        float top = yAxisDrawRect.top;
        float bottom = yAxisDrawRect.bottom - yAxisRender.getInsetBottom();

        XAxis xAxis = mHost.getXAxis();
        float minX = xAxis.getMinValue();
        float maxX = xAxis.getMaxValue();
        YAxis yAxis = mHost.getYAxis();
        float minY = yAxis.getMinValue();
        float maxY = yAxis.getMaxValue();

        float ratioX = (right - left) / (maxX - minX);
        float ratioY = (bottom - top) / (maxY - minY);

        for (Line line : lines) {
            if (line.isNeedUpdateValues()) {
                List<PointValue> points = new ArrayList<>(line.size());
                for (Entry entry : line) {
                    points.add(new PointValue(left + (entry.x - minX) * ratioX,
                            bottom - (entry.y - minY) * ratioY));
                }
                line.updatePointValues(points);
            }
        }
    }

    public void attachTo(LineChartView view) {
        mHost = view;
    }

    protected List<Line> getData() {
        return mHost.getLineData();
    }
}
