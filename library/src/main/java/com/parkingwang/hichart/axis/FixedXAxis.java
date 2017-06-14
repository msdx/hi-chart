/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.axis;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 固定坐标。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class FixedXAxis extends XAxis {
    private List<String> mLabels = new ArrayList<>();

    @NonNull
    public List<String> getLabels() {
        return mLabels;
    }

    public void setLabels(List<String> labels) {
        mLabels = (labels == null) ? new ArrayList<String>() : labels;
    }
}
