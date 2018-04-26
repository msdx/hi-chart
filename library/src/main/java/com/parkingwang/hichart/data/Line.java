/*
 * Copyright (c) 2017-2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.data;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;

import com.parkingwang.hichart.axis.YAxisGravity;
import com.parkingwang.hichart.view.LineChartView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Line is an entries collection.
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @version 0.3
 * @since 2017-06-20 0.1
 */
public class Line implements Iterable<Entry> {

    private final List<Entry> mEntryList = new ArrayList<>();
    private List<PointValue> mPoints = new ArrayList<>();

    private LineStyle mStyle = new LineStyle();

    private String mTitle;
    private Object mTag;

    private YAxisGravity mDependentYAxis = YAxisGravity.RIGHT;

    /**
     * Append an entry to line.
     *
     * @param entry The entry to be appended
     */
    public void add(Entry entry) {
        mEntryList.add(entry);
    }

    /**
     * Appends all of the entries in the specified collection to the end of this list.
     *
     * @param entries The entries to be appended
     */
    public void addAll(List<Entry> entries) {
        mEntryList.addAll(entries);
    }

    /**
     * Remove all the entries of this line.
     */
    public void clear() {
        mEntryList.clear();
    }

    /**
     * Returns the entry at the specified position in this line.
     *
     * @param index Index of the entry to returns
     * @return The entry at the specified position in this line.
     */
    public Entry get(int index) {
        return mEntryList.get(index);
    }

    /**
     * @return The number of the entries in this list.
     */
    public int size() {
        return mEntryList.size();
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    public boolean isEmpty() {
        return mEntryList.isEmpty();
    }

    /**
     * Set a line style for this line.
     *
     * @param style The style to be set
     */
    public void setStyle(LineStyle style) {
        mStyle = style;
    }

    /**
     * @return The style about how to draw the line
     */
    public LineStyle getStyle() {
        return mStyle;
    }

    /**
     * Update all the points of the line. This method is call by {@link LineChartView#notifyDataSetChanged()}
     *
     * @param minX   The min value of the X-axis
     * @param minY   The min value of the Y-axis
     * @param left   The left coordinate value of the X-axis
     * @param bottom The bottom coordinate value of the Y-axis
     * @param ratioX (right - left) / (maxX - minX)
     * @param ratioY (bottom - top) / (maxY - minY)
     * @see PointValue
     */
    public void updatePointValues(float minX, float minY, float left, float bottom, float ratioX, float ratioY) {
        List<PointValue> points = new ArrayList<>(this.size());
        for (Entry entry : mEntryList) {
            points.add(new PointValue(left + (entry.x - minX) * ratioX,
                    bottom - (entry.y - minY) * ratioY,
                    entry.imaginary));
        }
        this.mPoints = points;
    }

    /**
     * @return The points of the line.
     */
    public List<PointValue> getPointValues() {
        return mPoints;
    }

    /**
     * @return The line title.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Set the title to the line.
     *
     * @param title The title to set
     */
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

    /**
     * @return The tag of the line.
     */
    public Object getTag() {
        return mTag;
    }

    /**
     * @param tag The tag to set.
     */
    public void setTag(Object tag) {
        mTag = tag;
    }

    /**
     * Return the YAxisGravity of the yAxis that this line depends on
     *
     * @return The YAxisGravity of the yAxis which this line depends on
     * @since 0.3
     */
    public YAxisGravity getDependentYAxis() {
        return mDependentYAxis;
    }

    /**
     * Set the YAxisGravity of the dependent yAxis
     *
     * @param dependentYAxis The YAxisGravity of the dependent yAxis
     * @since 0.3
     */
    public void setDependentYAxis(YAxisGravity dependentYAxis) {
        mDependentYAxis = dependentYAxis;
    }
}
