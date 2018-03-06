/*
 * Copyright (c) 2017-2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.parkingwang.hichart.axis.XAxis;
import com.parkingwang.hichart.axis.XAxisRender;
import com.parkingwang.hichart.axis.YAxis;
import com.parkingwang.hichart.axis.YAxisRender;
import com.parkingwang.hichart.data.DataRender;
import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.data.PointValue;
import com.parkingwang.hichart.divider.DividersRender;
import com.parkingwang.hichart.empty.EmptyRender;
import com.parkingwang.hichart.highlight.HighlightLineRender;
import com.parkingwang.hichart.highlight.HighlightRender;
import com.parkingwang.hichart.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @version 0.2
 * @since 2017-04-18 0.1
 */
public class LineChartView extends FrameLayout {

    public static final float PROGRESS_COMPLETE = 1f;
    private static final int DEFAULT_ANIMATOR_TIME = 1000;

    private List<Line> mLines = new ArrayList<>();
    private DataRender mDataRender;
    private DividersRender mDividersRender;
    private EmptyRender mEmptyRender;
    private HighlightRender mHighlightRender;
    private XAxis mXAxis;
    private XAxisRender mXAxisRender;
    private YAxis mYAxis;
    private YAxisRender mYAxisRender;

    private float mInsetLeft;
    private float mInsetTop;
    private float mInsetRight;
    private float mInsetBottom;

    private PointValue mHighlightPointValue;
    private OnChartValueSelectedListener mOnChartValueSelectedListener;

    private float mAnimatorProgress = PROGRESS_COMPLETE;
    private final ValueAnimator mAnimator = ValueAnimator.ofFloat(0, PROGRESS_COMPLETE);
    private boolean mAnimated = false;

    private boolean mDisallowParentIntercept = false;

    public LineChartView(Context context) {
        super(context);
        init();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setFilterTouchesWhenObscured(false);

        mDataRender = new DataRender();
        mDataRender.attachTo(this);
        mDividersRender = new DividersRender();
        setHighlightRender(new HighlightLineRender());
        setXAxis(new XAxis());
        setXAxisRender(new XAxisRender());
        setYAxis(new YAxis());
        setYAxisRender(new YAxisRender());

        mAnimator.setDuration(DEFAULT_ANIMATOR_TIME);
        initAnimatorListener();
    }


    private void initAnimatorListener() {
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorProgress = animation.getAnimatedFraction();
                invalidate();
            }
        });
    }

    public float getAnimatorProgress() {
        return mAnimatorProgress;
    }

    /**
     * Whether to enable the animator.
     *
     * @param animated True to enable the animator, false otherwise.
     */
    public void setAnimated(boolean animated) {
        mAnimated = animated;
    }

    /**
     * @param duration The animator time to set.
     */
    public void setAnimatorTime(int duration) {
        mAnimator.setDuration(duration);
    }

    /**
     * @param delay The start delay time of the animator to set.
     */
    public void setAnimatorStartDelay(int delay) {
        mAnimator.setStartDelay(delay);
    }

    /**
     * Set the interpolator into the animator.
     *
     * @param interpolator The interpolator to set.
     */
    public void setInterpolator(TimeInterpolator interpolator) {
        mAnimator.setInterpolator(interpolator);
    }

    public DataRender getDataRender() {
        return mDataRender;
    }

    public void setDataRender(DataRender dataRender) {
        mDataRender = dataRender;
    }

    public DividersRender getDividersRender() {
        return mDividersRender;
    }

    public void setDividersRender(DividersRender dividersRender) {
        mDividersRender = dividersRender == null ? new DividersRender() : dividersRender;
    }

    public EmptyRender getEmptyRender() {
        return mEmptyRender;
    }

    public void setEmptyRender(EmptyRender emptyRender) {
        mEmptyRender = emptyRender;
    }

    public HighlightRender getHighlightRender() {
        return mHighlightRender;
    }

    public void setHighlightRender(HighlightRender render) {
        mHighlightRender = render;
        mHighlightRender.attachTo(this);
    }

    public XAxis getXAxis() {
        return mXAxis;
    }

    public void setXAxis(XAxis XAxis) {
        mXAxis = XAxis;
        mXAxis.attachTo(this);
    }

    public XAxisRender getXAxisRender() {
        return mXAxisRender;
    }

    public void setXAxisRender(XAxisRender XAxisRender) {
        mXAxisRender = XAxisRender;
        mXAxisRender.attachTo(this);
    }

    public YAxis getYAxis() {
        return mYAxis;
    }

    public void setYAxis(YAxis axis) {
        mYAxis = axis;
        mYAxis.attachTo(this);
    }

    public YAxisRender getYAxisRender() {
        return mYAxisRender;
    }

    public void setYAxisRender(YAxisRender YAxisRender) {
        mYAxisRender = YAxisRender;
        mYAxisRender.attachTo(this);
    }

    public List<Line> getLineData() {
        return mLines;
    }

    public void setLineData(List<Line> lines) {
        mLines = lines == null ? new ArrayList<Line>() : lines;
        notifyDataSetChanged();
    }

    public void addLine(Line line) {
        mLines.add(line);
        notifyDataSetChanged();
    }

    public void removeLine(Line line) {
        mLines.remove(line);
        notifyDataSetChanged();
    }

    public void removeLine(Object tag) {
        if (tag != null) {
            boolean removed = false;
            Iterator<Line> iterator = mLines.iterator();
            while (iterator.hasNext()) {
                if (tag.equals(iterator.next().getTag())) {
                    iterator.remove();
                    removed = true;
                }
            }
            if (removed) {
                notifyDataSetChanged();
            }
        }
    }

    public void setLineChartInsets(float left, float top, float right, float bottom) {
        mInsetLeft = left;
        mInsetTop = top;
        mInsetRight = right;
        mInsetBottom = bottom;
    }

    /**
     * Notify the data was changed.
     * This method need be called after the line data or any one render was changed.
     */
    public void notifyDataSetChanged() {
        mHighlightPointValue = null;
        mXAxis.calcMinMaxIfNotCustom();
        mYAxis.calcMinMaxIfNotCustom();
        updateRendersDrawRect();
        prepareLinePoints();
        if (mAnimated && !getLineData().isEmpty()) {
            mAnimatorProgress = 0;
            mAnimator.cancel();
            mAnimator.start();
        } else {
            mAnimatorProgress = PROGRESS_COMPLETE;
            invalidate();
        }
    }

    private void updateRendersDrawRect() {
        mDataRender.setDrawRect(mInsetLeft, 0,
                getRight() - Math.max(mInsetRight, mYAxisRender.getWidth()),
                getHeight() - Math.max(mInsetBottom, mXAxisRender.getHeight()));
        RectF dataRect = getDataRender().getDrawRect();
        RectF rectF = mDataRender.getDrawRect();
        mDividersRender.setDrawRect(0, 0, getWidth(), getHeight());
        mXAxisRender.setDrawRect(0, dataRect.top + mInsetTop, getWidth(), getHeight());
        mYAxisRender.setDrawRect(dataRect.left, dataRect.top + mInsetTop, getWidth(), dataRect.bottom);
        if (mEmptyRender != null) {
            mEmptyRender.setDrawRect(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
        mHighlightRender.setDrawRect(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDividersRender.draw(canvas);
        mXAxisRender.draw(canvas);
        mYAxisRender.draw(canvas);
        if (getLineData().isEmpty()) {
            drawEmpty(canvas);
        } else {
            mDataRender.draw(canvas);
            if (mAnimatorProgress == PROGRESS_COMPLETE && mHighlightPointValue != null) {
                mHighlightRender.draw(canvas);
            }
        }
    }

    private void prepareLinePoints() {
        if (mXAxis.getRange() == 0 || mYAxis.getRange() == 0) {
            return;
        }
        RectF yAxisDrawRect = mYAxisRender.getDrawRect();
        RectF drawRect = mDataRender.getDrawRect();
        float left = drawRect.left;
        float right = drawRect.right;
        float top = yAxisDrawRect.top;
        float bottom = yAxisDrawRect.bottom - mYAxisRender.getInsetBottom();

        float minX = mXAxis.getMinValue();
        float maxX = mXAxis.getMaxValue();
        float minY = mYAxis.getMinValue();
        float maxY = mYAxis.getMaxValue();

        float ratioX = (right - left) / (maxX - minX);
        float ratioY = (bottom - top) / (maxY - minY);

        for (Line line : getLineData()) {
            line.updatePointValues(minX, minY, left, bottom, ratioX, ratioY);
        }
    }

    private void drawEmpty(Canvas canvas) {
        if (mEmptyRender == null) {
            return;
        }
        mEmptyRender.draw(canvas);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateRendersDrawRect();
        }
    }

    public void highlightValue(int lineIndex, int pointIndex) {
        if (lineIndex < 0 || pointIndex < 0) {
            cancelHighlightValue();
        }
        onHighlightValue(lineIndex, pointIndex, getLineData().get(lineIndex).getPointValues().get(pointIndex));
    }

    private void cancelHighlightValue() {
        mHighlightRender.updateHighlightInfo(-1, -1, null);
        mHighlightPointValue = null;
        if (mOnChartValueSelectedListener != null) {
            mOnChartValueSelectedListener.onValueUnselected();
        }
    }

    /**
     * Set whether disallow parent view to intercept touch event.
     *
     * @param disallow True to disallow, false otherwise.
     */
    public void setDisallowParentIntercept(boolean disallow) {
        mDisallowParentIntercept = disallow;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mDisallowParentIntercept && !getLineData().isEmpty()) {
            getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        List<Line> lines = getLineData();
        if (lines.isEmpty()) {
            return false;
        }

        int lineIndex = 0;
        int pointIndex = 0;
        PointValue highlightPoint = null;
        float x = event.getX();
        float y = event.getY();
        float minDistance = Float.MAX_VALUE;
        for (int i = 0, lineSize = lines.size(); i < lineSize; i++) {
            List<PointValue> points = lines.get(i).getPointValues();
            for (int j = 0, size = points.size(); j < size; j++) {
                PointValue point = points.get(j);
                if (point.imaginary) {
                    // imaginary point
                    continue;
                }
                float distance = Math.abs(point.x - x);
                if (distance > minDistance) {
                    continue;
                }
                if (distance == minDistance) {
                    if (Math.abs(highlightPoint.y - y) < Math.abs(point.y - y)) {
                        continue;
                    }
                }
                lineIndex = i;
                pointIndex = j;
                highlightPoint = point;
                minDistance = distance;
            }
        }
        if (highlightPoint != mHighlightPointValue) {
            onHighlightValue(lineIndex, pointIndex, highlightPoint);
        }
        return true;
    }

    private void onHighlightValue(int lineIndex, int pointIndex, PointValue pointValue) {
        if (mHighlightRender.isEnabled()) {
            mHighlightRender.updateHighlightInfo(lineIndex, pointIndex, pointValue);
        }

        mHighlightPointValue = pointValue;
        if (mAnimatorProgress == PROGRESS_COMPLETE) {
            invalidate();
        }
        if (mOnChartValueSelectedListener != null) {
            mOnChartValueSelectedListener.onValueSelected(this, lineIndex, pointIndex, pointValue);
        }
    }

    public void setOnChartValueSelectedListener(OnChartValueSelectedListener listener) {
        mOnChartValueSelectedListener = listener;
    }
}
