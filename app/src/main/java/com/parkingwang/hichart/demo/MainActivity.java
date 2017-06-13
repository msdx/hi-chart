/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parkingwang.hichart.data.Entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LineChartWrapper mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLineChart = (LineChartWrapper) findViewById(R.id.chart);
        mLineChart.configLineChart(new LineChartConfig());
        mLineChart.setLabelFormatter(new LineChartWrapper.LabelFormatter() {
            @Override
            public String getFormattedLabel(String time, int index, int column) {
                return String.valueOf(index + 1);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.update) {
            Random random = new Random();
            int length = random.nextInt(18) + 7;
            List<Entry> entryList = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                entryList.add(new Entry(i, random.nextInt(100000)));
            }
            mLineChart.updateChart(new Date().toString(), entryList);
            mLineChart.setColumn(entryList.size());
            mLineChart.notifyLabelsChanged();
        }
    }
}
