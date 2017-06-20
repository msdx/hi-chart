/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 线数据。
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-20 0.1
 */
public class Line implements Iterable<Entry> {

    private final LineStyle mStyle = new LineStyle();

    private final List<Entry> mEntryList = new ArrayList<>();
    private List<PointValue> mPoints = new ArrayList<>();

    private boolean mNeedUpdateValues;

    private Object mTag;

    public void add(Entry entry) {
        mEntryList.add(entry);
        mNeedUpdateValues = true;
    }

    public void clear() {
        mEntryList.clear();
        mNeedUpdateValues = true;
    }

    public Entry get(int index) {
        return mEntryList.get(index);
    }

    public int size() {
        return mEntryList.size();
    }

    public LineStyle getStyle() {
        return mStyle;
    }

    public boolean isNeedUpdateValues() {
        return mNeedUpdateValues;
    }

    void updatePointValues(List<PointValue> values) {
        if (values != null) {
            mPoints = values;
        }
    }

    List<PointValue> getPointValues() {
        return mPoints;
    }

    @NonNull
    @Override
    public Iterator<Entry> iterator() {
        return mEntryList.iterator();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void forEach(Consumer<? super Entry> action) {
        mEntryList.forEach(action);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Spliterator<Entry> spliterator() {
        return mEntryList.spliterator();
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        mTag = tag;
    }
}
