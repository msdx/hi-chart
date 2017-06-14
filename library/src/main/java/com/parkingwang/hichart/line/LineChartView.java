/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.line;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.parkingwang.hichart.data.Entry;
import com.parkingwang.hichart.divider.DividersRender;
import com.parkingwang.hichart.empty.EmptyRender;
import com.parkingwang.hichart.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class LineChartView extends FrameLayout {

    private static final float DEFAULT_DRAW_PERCENT = 1f;
    private static final int DEFAULT_ANIMATOR_TIME = 1000;
    private static final int INVALID_HIGHLIGHT_POSITION = -1;

    private List<Entry> mEntryList;

    private DividersRender mDividersRender;
    private EmptyRender mEmptyRender;
    private YAxis mYAxis;
    private LineFillRender mLineFillRender;
    private LineRender mLineRender;
    private HighlightRender mHighlightRender;

    private float mInsetLeft;
    private float mInsetTop;
    private float mInsetRight;
    private float mInsetBottom;
    private int mWidth;

    private int mCurrentHighlightPosition = INVALID_HIGHLIGHT_POSITION;
    private OnChartValueSelectedListener mOnChartValueSelectedListener;

    private float mDrawPercent = DEFAULT_DRAW_PERCENT;
    private ValueAnimator mAnimator;
    private boolean mAnimated = false;

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
        mYAxis = new YAxis();
        mDividersRender = new DividersRender();
        mLineFillRender = new LineFillRender();
        mLineRender = new LineRender(mYAxis);
        mHighlightRender = new HighlightRender();

        mEntryList = new ArrayList<>();
        mAnimator = ValueAnimator.ofFloat(0, DEFAULT_DRAW_PERCENT);
        mAnimator.setDuration(DEFAULT_ANIMATOR_TIME);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public void setAnimated(boolean animated) {
        mAnimated = animated;
    }

    public void setAnimatorTime(int duration) {
        mAnimator.setDuration(duration);
    }

    public void setAnimatorStartDelay(int delay) {
        mAnimator.setStartDelay(delay);
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        mAnimator.setInterpolator(interpolator);
    }

    public YAxis getYAxis() {
        return mYAxis;
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

    public LineRender getLineRender() {
        return mLineRender;
    }

    public LineFillRender getLineFillRender() {
        return mLineFillRender;
    }

    public HighlightRender getHighlightRender() {
        return mHighlightRender;
    }

    public List<Entry> getLineData() {
        return mEntryList;
    }

    public void setLineData(List<Entry> entries) {
        mEntryList = entries == null ? new ArrayList<Entry>() : entries;
        mCurrentHighlightPosition = INVALID_HIGHLIGHT_POSITION;
        notifyDataSetChanged();
    }

    public void setLineChartInsets(float left, float top, float right, float bottom) {
        mInsetLeft = left;
        mInsetTop = top;
        mInsetRight = right;
        mInsetBottom = bottom;
        mYAxis.setOffset(left, top, right, bottom);
        mLineFillRender.setFillContent(mInsetLeft, mInsetTop, getWidth() - right, getBottom());
    }

    private void notifyDataSetChanged() {
        mYAxis.calcMinMax(mEntryList);
        mLineRender.update(mEntryList);
        mLineFillRender.updateFillArea(mLineRender.getLinePath());
        if (mAnimated && !mEntryList.isEmpty()) {
            mDrawPercent = 0;
            mAnimator.cancel();
            mAnimator.start();
        } else {
            mDrawPercent = DEFAULT_DRAW_PERCENT;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDividers(canvas);
        if (mEntryList.isEmpty()) {
            drawEmpty(canvas);
            return;
        }
        mYAxis.draw(canvas);
        if (mDrawPercent == DEFAULT_DRAW_PERCENT) {
            mLineFillRender.draw(canvas);
        }
        mLineRender.draw(canvas, mDrawPercent);
        if (mDrawPercent == DEFAULT_DRAW_PERCENT && mCurrentHighlightPosition >= 0) {
            List<PointF> pointList = mLineRender.getPoints();
            if (pointList.size() > mCurrentHighlightPosition) {
                mHighlightRender.draw(canvas, pointList.get(mCurrentHighlightPosition), getBottom());
            }
        }
    }

    private void drawDividers(Canvas canvas) {
        mDividersRender.setDrawRect(0, 0, getWidth(), getHeight());
        mDividersRender.draw(canvas);
    }

    private void drawEmpty(Canvas canvas) {
        if (mEmptyRender == null) {
            return;
        }
        mEmptyRender.setDrawRect(0, 0, getWidth(), getHeight());
        mEmptyRender.draw(canvas);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = right - left;
            mYAxis.setSize(mWidth, bottom - top);
        }
    }

    public void highlighValue(int position) {
        onHighlightValue(position);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mEntryList.isEmpty()) {
            return false;
        }

        List<PointF> points = mLineRender.getPoints();
        if (points.isEmpty()) {
            return true;
        }

        int size = points.size();
        float avg = (mWidth - mInsetLeft - mInsetRight) / (size - 1);
        int position = (int) ((event.getX() - mInsetLeft + avg / 2) / avg);
        if (position < 0) {
            position = 0;
        } else if (position >= size) {
            position = size - 1;
        }

        if (position != mCurrentHighlightPosition) {
            onHighlightValue(position);
        }
        return true;
    }

    private void onHighlightValue(int position) {
        mCurrentHighlightPosition = position;
        if (mDrawPercent == DEFAULT_DRAW_PERCENT) {
            invalidate();
        }
        if (mOnChartValueSelectedListener != null) {
            mOnChartValueSelectedListener.onValueSelected(mEntryList.get(position));
        }
    }

    public void setOnChartValueSelectedListener(OnChartValueSelectedListener listener) {
        mOnChartValueSelectedListener = listener;
    }
}
