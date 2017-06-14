/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.demo;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.empty.EmptyTextRender;
import com.parkingwang.hichart.formatter.AxisValueFormatter;
import com.parkingwang.hichart.line.HighlightRender;
import com.parkingwang.hichart.line.LineChartLabel;
import com.parkingwang.hichart.line.LineChartView;
import com.parkingwang.hichart.line.LineRender;
import com.parkingwang.hichart.line.YAxis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final int ANIMATOR_TIME = 660;
    private static final int ANIMATOR_DELAY = 300;

    private static final int YUAN = 100;
    private static final int K_YUAN = 1000 * YUAN;
    private static final DecimalFormat K_YUAN_FORMAT = new DecimalFormat("#0.0");
    private static final DecimalFormat YUAN_FORMAT = new DecimalFormat("0.#");

    private LineChartView mLineChart;
    private LineChartLabel mChartLabel;

    private LabelFormatter mLabelFormatter;
    private int mColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChart = (LineChartView) findViewById(R.id.line_chart);
        mChartLabel = (LineChartLabel) findViewById(R.id.chart_labels);

        configLineChart(new LineChartConfig());

        configChartLabel();

        setLabelFormatter(new LabelFormatter() {
            @Override
            public String getFormattedLabel(int index, int column) {
                return String.valueOf(index + 1);
            }
        });
        CheckBox showEmpty = (CheckBox) findViewById(R.id.show_empty);
        showEmpty.setOnCheckedChangeListener(this);
    }

    private void configChartLabel() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        mChartLabel.setOffsetLeft((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, dm));
        mChartLabel.setOffsetRight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, dm));
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

    public void setLabelFormatter(LabelFormatter labelFormatter) {
        mLabelFormatter = labelFormatter;
    }

    public void setXAxisLabelCount(int count) {
        mChartLabel.setMaxCount(count);
    }

    public void setColumn(int column) {
        mColumn = column;
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.update:
                fillData();
                break;
            case R.id.clear:
                clearData();
                break;
        }
    }

    private void clearData() {
        mLineChart.setLineData(null);
        setColumn(7);
        notifyLabelsChanged();
    }

    public void notifyLabelsChanged() {
        final List<String> labels = new ArrayList<>();
        final LabelFormatter formatter = mLabelFormatter;
        if (formatter != null) {
            for (int i = 0; i < mColumn; i++) {
                labels.add(formatter.getFormattedLabel(i, mColumn));
            }
        }

        mChartLabel.setLabels(labels);
        mChartLabel.invalidate();
    }

    private void fillData() {
        Random random = new Random();
        int length = random.nextInt(18) + 7;
        List<Entry> entryList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            entryList.add(new Entry(i, random.nextInt(100000)));
        }

        final float offsetLeft = mChartLabel.getOffsetLeft();
        final float offsetRight = mChartLabel.getOffsetRight();
        final float offsetBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        mLineChart.setLineChartInsets(offsetLeft, mLineChart.getHeight() / 7, offsetRight, offsetBottom);

        mLineChart.setLineData(entryList);
        setColumn(entryList.size());
        notifyLabelsChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.show_empty) {
            showEmpty(isChecked);
        }
    }

    private void showEmpty(boolean show) {
        if (show) {
            EmptyTextRender textRender = new EmptyTextRender();
            textRender.setTextColor(Color.parseColor("#AAFFFFFF"));
            textRender.setTextSize(spToPx(12));
            textRender.setText(getString(R.string.no_data));
            mLineChart.setEmptyRender(textRender);
        } else {
            mLineChart.setEmptyRender(null);
        }
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float spToPx(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    public interface LabelFormatter {
        String getFormattedLabel(int index, int column);
    }
}
