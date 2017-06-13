/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.demo;

import android.graphics.Color;

/**
 * 线图表配置类
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-03-25
 */
public class LineChartConfig {

    public DataSetConfig dataSetConfig;
    public AxisRightConfig axisRightConfig;
    public XAxisConfig xAxisConfig;
    public HighlightConfig highlightConfig;

    public LineChartConfig() {
        this.dataSetConfig = new DataSetConfig();
        this.axisRightConfig = new AxisRightConfig();
        this.xAxisConfig = new XAxisConfig();
        this.highlightConfig = new HighlightConfig();
    }

    public static class DataSetConfig {
        public int lineWidth;
        public int lineColor;
        public int circleColor;
        public int circleHoleColor;
        public int fillColor;
        public float circleRadius;
        public float circleHoleRadius;
        public boolean drawFilled;
        public boolean drawValues;
        public boolean drawHorizontalHighlight;

        private DataSetConfig() {
            this.lineWidth = 2;
            this.lineColor = Color.WHITE;
            this.circleRadius = 3.5f;
            this.circleColor = Color.WHITE;
            this.circleHoleRadius = 1.5f;
            this.circleHoleColor = Color.parseColor("#1D8FEE");
            this.fillColor = Color.parseColor("#1BFFFFFF");
            this.drawFilled = true;
            this.drawValues = false;
            this.drawHorizontalHighlight = false;
        }
    }

    public static class AxisRightConfig {
        public boolean drawLabels;
        public boolean enableGridDashLine;
        public int dashLineLength;
        public int dashLineSpace;
        public int dashLineWidth;
        public int gridColor;
        public int textColor;
        public int textSize;
        public int labelCount;

        private AxisRightConfig() {
            this.drawLabels = true;
            this.enableGridDashLine = true;
            this.dashLineLength = 3;
            this.dashLineSpace = 4;
            this.dashLineWidth = 1;
            this.gridColor = Color.parseColor("#33FFFFFF");
            this.textColor = Color.parseColor("#80FFFFFF");
            this.textSize = 9;
            this.labelCount = 4;
        }
    }

    public static class XAxisConfig {
        public int labelCount = 7;
        public int labelColor = Color.parseColor("#B6CCDE");
        public int labelTextSize = 10;

        private XAxisConfig() {
        }
    }

    public static class HighlightConfig {
        public float circleRadius = 3.5f;
        public int circleColor = Color.WHITE;
        public int lineColor = Color.parseColor("#4DFFFFFF");
        public float lineWidth = 1;
    }
}
