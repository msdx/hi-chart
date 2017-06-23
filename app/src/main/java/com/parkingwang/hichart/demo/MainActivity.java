/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.demo;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.parkingwang.hichart.axis.AxisLabelFormatter;
import com.parkingwang.hichart.axis.XAxis;
import com.parkingwang.hichart.axis.XAxisRender;
import com.parkingwang.hichart.axis.YAxis;
import com.parkingwang.hichart.axis.YAxisRender;
import com.parkingwang.hichart.axis.extend.FixedXAxis;
import com.parkingwang.hichart.axis.extend.FixedXAxisRender;
import com.parkingwang.hichart.axis.extend.PrettyYAxis;
import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.data.LineStyle;
import com.parkingwang.hichart.divider.Divider;
import com.parkingwang.hichart.divider.DividersRender;
import com.parkingwang.hichart.empty.EmptyTextRender;
import com.parkingwang.hichart.highlight.HighlightDrawableRender;
import com.parkingwang.hichart.highlight.HighlightLineRender;
import com.parkingwang.hichart.highlight.HighlightRender;
import com.parkingwang.hichart.view.LineChartView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final int ANIMATOR_TIME = 660;
    private static final int ANIMATOR_DELAY = 300;

    private static final int YUAN = 100;
    private static final int K_YUAN = 1000 * YUAN;
    private static final DecimalFormat K_YUAN_FORMAT = new DecimalFormat("#0.0");
    private static final DecimalFormat YUAN_FORMAT = new DecimalFormat("0.#");

    private LineChartView mLineChart;

    private LabelFormatter mLabelFormatter;
    private int mColumn;

    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChart = (LineChartView) findViewById(R.id.line_chart);

        mColumn = mRandom.nextInt(18) + 7;

        configLineChart(new LineChartConfig());
        setLabelFormatter(new LabelFormatter() {
            @Override
            public String getFormattedLabel(int index, int column) {
                return String.valueOf(index + 1);
            }
        });

        setCheckBoxesListener(R.id.show_empty, R.id.top_divider, R.id.custom_x_axis,
                R.id.hide_y_axis, R.id.hide_y_axis_label, R.id.pretty_y_axis, R.id.y_axis_offset,
                R.id.y_axis_grid_line, R.id.enable_grid_line, R.id.enable_animator,
                R.id.animator_delay, R.id.fill_lines, R.id.random_fill_color, R.id.show_highlight,
                R.id.show_highlight_vertical, R.id.show_highlight_horizontal);
    }

    private void setCheckBoxesListener(@IdRes int... ids) {
        for (int id : ids) {
            ((CheckBox) findViewById(id)).setOnCheckedChangeListener(this);
        }
    }

    public void configLineChart(LineChartConfig config) {
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawCount(config.xAxisConfig.labelCount);
        XAxisRender xAxisRender = mLineChart.getXAxisRender();
        xAxisRender.setHeight(dpToPx(30));
        xAxisRender.setPaddingLeft(dpToPx(10));
        xAxisRender.setPaddingRight(dpToPx(10));
        xAxisRender.setTextColor(config.xAxisConfig.labelColor);
        xAxisRender.setTextSize(spToPx(config.xAxisConfig.labelTextSize));
        xAxisRender.setDrawBackground(true);

        LineChartConfig.AxisRightConfig rightConfig = config.axisRightConfig;
        YAxis yAxis = mLineChart.getYAxis();
        yAxis.setDrawCount(rightConfig.labelCount);
        yAxis.setMinValue(0);
        YAxisRender yAxisRender = mLineChart.getYAxisRender();
        yAxisRender.setDrawLabels(rightConfig.drawLabels);
        yAxisRender.setTextColor(rightConfig.textColor);
        yAxisRender.setTextSize(spToPx(rightConfig.textSize));
        yAxisRender.setLabelFormatter(new AxisLabelFormatter() {
            @Override
            public String format(float value) {
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
            yAxisRender.setGridDashedLine(dpToPx(rightConfig.dashLineLength), dpToPx(rightConfig.dashLineSpace));
            yAxisRender.setGridLineWidth(dpToPx(rightConfig.dashLineWidth));
        }
        yAxisRender.setGridColor(rightConfig.gridColor);

        final LineChartConfig.HighlightConfig highlightConfig = config.highlightConfig;
        HighlightRender highlightRender = mLineChart.getHighlightRender();
        if (highlightRender instanceof HighlightLineRender) {
            ((HighlightLineRender) highlightRender).setHighlightLineColor(highlightConfig.lineColor);
            ((HighlightLineRender) highlightRender).setHighlightLineWidth(dpToPx(highlightConfig.lineWidth));
        }

        final float offsetLeft = dpToPx(10);
        final float offsetRight = dpToPx(25);
        final float offsetTop = dpToPx(40);
        final float offsetBottom = dpToPx(10);
        mLineChart.setLineChartInsets(offsetLeft, offsetTop, offsetRight, offsetBottom);

        mLineChart.setAnimatorTime(ANIMATOR_TIME);
    }

    public void setLabelFormatter(LabelFormatter labelFormatter) {
        mLabelFormatter = labelFormatter;
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
            case R.id.add_line:
                addLine();
                break;
            case R.id.remove_line:
                removeLine();
                break;
            case R.id.random_fill_mode:
                randomFillMode();
                break;
            case R.id.iop_highlight:
                useIOPHighlight();
        }
    }

    private void clearData() {
        mLineChart.setLineData(null);
    }

    private void fillData() {
        mColumn = mRandom.nextInt(18) + 7;
        XAxis xAxis = mLineChart.getXAxis();
        if (xAxis instanceof FixedXAxis) {
            ((FixedXAxis) xAxis).setLabels(generateLabels());
        }

        Line line = new Line();
        for (int i = 0; i < mColumn; i++) {
            line.add(new Entry(i, mRandom.nextInt(10000)));
        }
        line.setTitle("第1条线");

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        mLineChart.setLineData(lines);
    }

    private void addLine() {
        Line line = new Line();
        for (int i = 0; i < mColumn; i++) {
            line.add(new Entry(i, mRandom.nextInt(10000)));
        }
        LineStyle style = line.getStyle();
        int color = Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
        style.setLineColor(color);
        style.setCircleColor(color);
        line.setTitle("第" + (mLineChart.getLineData().size() + 1) + "条线");
        mLineChart.addLine(line);
    }

    private void removeLine() {
        List<Line> lines = mLineChart.getLineData();
        if (!lines.isEmpty()) {
            lines.remove(lines.size() - 1);
            mLineChart.notifyDataSetChanged();
        }
    }

    private void randomFillMode() {
        List<Line> lines = mLineChart.getLineData();
        PorterDuff.Mode[] modes = PorterDuff.Mode.values();
        Xfermode mode = new PorterDuffXfermode(modes[mRandom.nextInt(modes.length)]);
        for (Line line : lines) {
            line.getStyle().setFillMode(mode);
        }
    }

    private void useIOPHighlight() {
        HighlightDrawable drawable = new HighlightDrawable(this);
        drawable.setCircleMargin(dpToPx(4));
        drawable.setTitleMargin(dpToPx(4));
        drawable.setLineSpace(dpToPx(2));
        drawable.setCircleRadius(dpToPx(3.5f));
        drawable.setPadding(dpToPx(8));
        HighlightDrawableRender render = new HighlightDrawableRender(drawable);
        render.setSelectAllPoint(true);
        render.setShowOnHighPoint(true);
        mLineChart.setHighlightRender(render);
    }

    @NonNull
    private List<String> generateLabels() {
        final List<String> labels = new ArrayList<>();
        final LabelFormatter formatter = mLabelFormatter;
        if (formatter != null) {
            for (int i = 0; i < mColumn; i++) {
                labels.add(formatter.getFormattedLabel(i, mColumn));
            }
        }
        return labels;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final int id = buttonView.getId();
        switch (id) {
            case R.id.show_empty:
                showEmpty(isChecked);
                break;
            case R.id.top_divider:
                showTopDivider(isChecked);
                break;
            case R.id.custom_x_axis:
                showFixedXAxis(isChecked);
                break;
            case R.id.hide_y_axis:
                hideYAxis(isChecked);
                break;
            case R.id.hide_y_axis_label:
                hideYAxisLabel(isChecked);
                break;
            case R.id.pretty_y_axis:
                showPrettyYAxis(isChecked);
                break;
            case R.id.y_axis_offset:
                offsetYAxis(isChecked);
                break;
            case R.id.y_axis_grid_line:
                drawYAxisGridLine(isChecked);
                break;
            case R.id.enable_grid_line:
                enableGridDashedLine(isChecked);
                break;
            case R.id.enable_animator:
                enabledAnimator(isChecked);
                break;
            case R.id.animator_delay:
                delayAnimator(isChecked);
                break;
            case R.id.fill_lines:
                fillCurrentLines(isChecked);
                break;
            case R.id.random_fill_color:
                randomFillColor(isChecked);
                break;
            case R.id.show_highlight:
                showHighlight(isChecked);
                break;
            case R.id.show_highlight_horizontal:
                showHighlightHorizontal(isChecked);
                break;
            case R.id.show_highlight_vertical:
                showHighlightVertical(isChecked);
                break;
            default:
                return;
        }
        mLineChart.notifyDataSetChanged();
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

    private void showTopDivider(boolean show) {
        DividersRender render = mLineChart.getDividersRender();
        if (show) {
            Divider divider = new Divider();
            divider.setPadding(dpToPx(12));
            divider.setWidth(2);
            divider.setColor(Color.parseColor("#4DFFFFFF"));
            render.setTopDivider(divider);
        } else {
            render.setTopDivider(null);
        }
    }

    private void showFixedXAxis(boolean show) {
        XAxisRender xAxisRender;
        if (show) {
            FixedXAxis axis = new FixedXAxis();
            axis.setDrawCount(7);
            mLineChart.setXAxis(axis);
            xAxisRender = new FixedXAxisRender();
            axis.setLabels(generateLabels());
            axis.calcMinMaxIfNotCustom();
        } else {
            XAxis axis = new XAxis();
            axis.setDrawCount(7);
            mLineChart.setXAxis(axis);
            axis.calcMinMaxIfNotCustom();
            xAxisRender = new XAxisRender();
        }
        xAxisRender.setHeight(dpToPx(30));
        xAxisRender.setPaddingLeft(dpToPx(10));
        xAxisRender.setPaddingRight(dpToPx(10));
        xAxisRender.setBackgroundColor(Color.WHITE);
        xAxisRender.setDrawBackground(true);
        xAxisRender.setTextColor(Color.parseColor("#B6CCDE"));
        xAxisRender.setTextSize(spToPx(10));
        mLineChart.setXAxisRender(xAxisRender);
    }

    private void hideYAxis(boolean hide) {
        mLineChart.getYAxisRender().setEnabled(!hide);
    }

    private void hideYAxisLabel(boolean hide) {
        YAxisRender render = mLineChart.getYAxisRender();
        render.setEnabled(true);
        render.setDrawLabels(!hide);
    }

    private void showPrettyYAxis(boolean pretty) {
        YAxis yAxis = pretty ? new PrettyYAxis() : new YAxis();
        yAxis.setDrawCount(4);
        mLineChart.setYAxis(yAxis);
        yAxis.calcMinMaxIfNotCustom();
        yAxis.setMinValue(0);
    }

    private void offsetYAxis(boolean offset) {
        mLineChart.getYAxisRender().setInsetBottom(offset ? dpToPx(10) : 0);
    }

    private void drawYAxisGridLine(boolean isDashedLine) {
        YAxisRender yAxisRender = mLineChart.getYAxisRender();
        if (isDashedLine) {
            yAxisRender.setGridDashedLine(dpToPx(3), dpToPx(4));
        } else {
            yAxisRender.setGridDashedLine(dpToPx(3), dpToPx(0));
        }
    }

    private void enableGridDashedLine(boolean enabled) {
        mLineChart.getYAxisRender().enabledGridDashedLine(enabled);
    }

    private void enabledAnimator(boolean enabled) {
        mLineChart.setAnimated(enabled);
    }

    private void delayAnimator(boolean delay) {
        if (delay) {
            mLineChart.setAnimatorStartDelay(ANIMATOR_DELAY);
        } else {
            mLineChart.setAnimatorStartDelay(0);
        }
    }

    private void fillCurrentLines(boolean fill) {
        List<Line> lines = mLineChart.getLineData();
        for (Line line : lines) {
            line.getStyle().setFill(fill);
        }
    }

    private void randomFillColor(boolean random) {
        List<Line> lines = mLineChart.getLineData();
        for (Line line : lines) {
            int color = random
                    ? Color.argb(mRandom.nextInt(100), mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255))
                    : Color.parseColor("#1BFFFFFF");
            line.getStyle().setFillColor(color);
        }
    }

    private void showHighlight(boolean show) {
        mLineChart.getHighlightRender().setEnabled(show);
    }

    private void showHighlightVertical(boolean show) {
        HighlightRender render = mLineChart.getHighlightRender();
        if (render instanceof HighlightLineRender) {
            ((HighlightLineRender) render).setDrawVerticalLine(show);
        }
    }

    private void showHighlightHorizontal(boolean show) {
        HighlightRender render = mLineChart.getHighlightRender();
        if (render instanceof HighlightLineRender) {
            ((HighlightLineRender) render).setDrawHorizontalLine(show);
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
