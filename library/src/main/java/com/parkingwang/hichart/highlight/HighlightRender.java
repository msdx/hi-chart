/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.highlight;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.data.PointValue;
import com.parkingwang.hichart.render.BaseRender;
import com.parkingwang.hichart.view.LineChartView;

import java.util.List;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public abstract class HighlightRender extends BaseRender {

    private PointValue mPointValue;
    private int mLineIndex;
    private int mPointIndex;

    private boolean mEnabled;

    private LineChartView mHost;

    /**
     * @return The enabled state of this render.
     */
    public boolean isEnabled() {
        return mEnabled;
    }

    /**
     * @param enabled True to enable this render, false otherwise.
     */
    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public PointValue getPointValue() {
        return mPointValue;
    }

    public int getLineIndex() {
        return mLineIndex;
    }

    public int getPointIndex() {
        return mPointIndex;
    }

    public void updateHighlightInfo(int lineIndex, int pointIndex, PointValue pointValue) {
        this.mLineIndex = lineIndex;
        this.mPointIndex = pointIndex;
        this.mPointValue = pointValue;
    }

    public void attachTo(LineChartView chartView) {
        mHost = chartView;
    }

    public List<Line> getData() {
        return mHost.getLineData();
    }

    public RectF getDataRectf() {
        return mHost.getDataRender().getDrawRect();
    }

    public LineChartView getHost() {
        return mHost;
    }

    @Override
    public abstract void draw(Canvas canvas);
}
