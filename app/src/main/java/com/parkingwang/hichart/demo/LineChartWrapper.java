/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.demo;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.formatter.AxisValueFormatter;
import com.parkingwang.hichart.line.HighlightRender;
import com.parkingwang.hichart.line.LineChartLabel;
import com.parkingwang.hichart.line.LineChartView;
import com.parkingwang.hichart.line.LineRender;
import com.parkingwang.hichart.line.YAxis;
import com.parkingwang.hichart.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-03-25
 */
public class LineChartWrapper extends RelativeLayout {
    private static final int ANIMATOR_TIME = 660;
    private static final int ANIMATOR_DELAY = 300;

    private static final int YUAN = 100;
    private static final int K_YUAN = 1000 * YUAN;
    private static final DecimalFormat K_YUAN_FORMAT = new DecimalFormat("#0.0");
    private static final DecimalFormat YUAN_FORMAT = new DecimalFormat("0.#");

    private LineChartView mLineChart;
    private TextView mTip;
    private LineChartLabel mChartLabel;

    private int mColumn;
    private String mTime;

    private LabelFormatter mLabelFormatter;

    private DisplayMetrics mDisplayMetrics;

    public LineChartWrapper(@NonNull Context context) {
        super(context);
        setupLineChart(context);
    }

    public LineChartWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupLineChart(context);
    }

    private void setupLineChart(Context context) {
        mDisplayMetrics = getResources().getDisplayMetrics();
        View.inflate(context, R.layout.view_line_chart_wrapper, this);
        mLineChart = (LineChartView) findViewById(R.id.line_chart);
        mTip = (TextView) findViewById(R.id.tip);
        mChartLabel = (LineChartLabel) findViewById(R.id.chart_labels);

        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        mChartLabel.setOffsetLeft((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, dm));
        mChartLabel.setOffsetRight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, dm));
        mTip.setText("没有数据");
    }

    public void configLineChart(LineChartConfig config) {
        LineChartConfig.AxisRightConfig rightConfig = config.axisRightConfig;
        YAxis right = mLineChart.getYAxis();
        right.setDrawLabels(rightConfig.drawLabels);
        right.setTextColor(rightConfig.textColor);
        right.setLabelCount(rightConfig.labelCount);
        right.setTextSize(spToPx(rightConfig.textSize));
        right.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value >= K_YUAN) {
                    return K_YUAN_FORMAT.format(value / K_YUAN) + "k";
                } else if (value >= YUAN) {
                    return (int) value / YUAN + "";
                } else if (value >= 0) {
                    return YUAN_FORMAT.format(value / 100);
                } else {
                    return "";
                }
            }
        });
        if (rightConfig.enableGridDashLine) {
            right.enableGridDashedLine(dpToPx(rightConfig.dashLineLength), dpToPx(rightConfig.dashLineSpace));
            right.setGridLineWidth(dpToPx(rightConfig.dashLineWidth));
        }
        right.setGridColor(rightConfig.gridColor);

        LineChartConfig.DataSetConfig dataSetConfig = config.dataSetConfig;
        LineRender lineRender = mLineChart.getLineRender();
        lineRender.setLineWidth(dpToPx(dataSetConfig.lineWidth));
        lineRender.setLineColor(dataSetConfig.lineColor);
        lineRender.setCircleRadius(dpToPx(dataSetConfig.circleRadius));
        lineRender.setCircleColor(dataSetConfig.circleColor);
        lineRender.setCircleHoleRadius(dpToPx(dataSetConfig.circleHoleRadius));
        lineRender.setCircleHoleColor(dataSetConfig.circleHoleColor);

        mLineChart.getLineFillRender().setFillColor(dataSetConfig.fillColor);

        setXAxisLabelCount(config.xAxisConfig.labelCount);

        final LineChartConfig.HighlightConfig highlightConfig = config.highlightConfig;
        HighlightRender highlightRender = mLineChart.getHighlightRender();
        highlightRender.setHighlightCircleColor(highlightConfig.circleColor);
        highlightRender.setHighlightCircleRadius(dpToPx(highlightConfig.circleRadius));
        highlightRender.setHighlightLineColor(highlightConfig.lineColor);
        highlightRender.setHighlightLineWidth(dpToPx(highlightConfig.lineWidth));

        mChartLabel.setTextColor(config.xAxisConfig.labelColor);
        mChartLabel.setTextSize(config.xAxisConfig.labelTextSize);
        mChartLabel.setMaxCount(config.xAxisConfig.labelCount);

        mLineChart.setAnimatorTime(ANIMATOR_TIME);
        mLineChart.setAnimatorStartDelay(ANIMATOR_DELAY);
    }

    public void setAnimatorEnabled(boolean enabled) {
        mLineChart.setAnimated(enabled);
    }

    public void updateChart(String time, List<Entry> entryList) {
        if (mTime != null && !TextUtils.equals(mTime, time)) {
            return;
        }

        if (entryList.isEmpty()) {
            mTip.setVisibility(VISIBLE);
            mLineChart.setVisibility(INVISIBLE);
        } else {
            mTip.setVisibility(GONE);
            mLineChart.setVisibility(VISIBLE);
        }

        final float offsetLeft = mChartLabel.getOffsetLeft();
        final float offsetRight = mChartLabel.getOffsetRight();
        final float offsetBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        mLineChart.setLineChartOffset(offsetLeft, mLineChart.getHeight() / 7, offsetRight, offsetBottom);

        mLineChart.setLineData(entryList);
    }

    public void setAxisMinMax(int min, int max) {
        YAxis yAxis = mLineChart.getYAxis();
        yAxis.setMinValue(min);
        yAxis.setMaxValue(max);
    }

    public void setXAxisLabelCount(int count) {
        mChartLabel.setMaxCount(count);
    }

    public void setColumn(int column) {
        mColumn = column;
    }

    public void resetLineChart() {
        mLineChart.setLineData(null);
        mTip.setVisibility(INVISIBLE);
    }

    public void highlightLastValue() {
        List<Entry> entries = mLineChart.getLineData();
        if (!entries.isEmpty()) {
            mLineChart.highlighValue(entries.size() - 1);
        }
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setOnChartValueSelectedListener(OnChartValueSelectedListener listener) {
        mLineChart.setOnChartValueSelectedListener(listener);
    }

    public void setLabelFormatter(LabelFormatter labelFormatter) {
        mLabelFormatter = labelFormatter;
    }

    public void notifyLabelsChanged() {
        final List<String> labels = new ArrayList<>();
        final LabelFormatter formatter = mLabelFormatter;
        final String time = mTime;
        if (formatter != null) {
            for (int i = 0; i < mColumn; i++) {
                labels.add(formatter.getFormattedLabel(time, i, mColumn));
            }
        }

        mChartLabel.setLabels(labels);
        mChartLabel.invalidate();
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDisplayMetrics);
    }

    private float spToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, mDisplayMetrics);
    }

    public interface LabelFormatter {
        String getFormattedLabel(String time, int index, int column);
    }
}
