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

    private String mTitle;
    private final List<Entry> mEntryList = new ArrayList<>();
    private List<PointValue> mPoints = new ArrayList<>();

    private Object mTag;

    public void add(Entry entry) {
        mEntryList.add(entry);
    }

    public void clear() {
        mEntryList.clear();
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

    public void updatePointValues(float minX, float minY, float left, float bottom, float ratioX, float ratioY) {
        List<PointValue> points = new ArrayList<>(this.size());
        for (Entry entry : mEntryList) {
            points.add(new PointValue(left + (entry.x - minX) * ratioX,
                    bottom - (entry.y - minY) * ratioY));
        }
        this.mPoints = points;
    }

    public List<PointValue> getPointValues() {
        return mPoints;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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
