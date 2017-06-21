/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.hichart.line;

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
import com.parkingwang.hichart.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class LineChartView extends FrameLayout {

    private static final float VALUE_COMPLETE = 1f;
    private static final int DEFAULT_ANIMATOR_TIME = 1000;

    private List<Line> mLines = new ArrayList<>();
    private DataRender mDataRender;
    private DividersRender mDividersRender;
    private EmptyRender mEmptyRender;
    private LineFillRender mLineFillRender;
    private HighlightRender mHighlightRender;
    private XAxis mXAxis;
    private XAxisRender mXAxisRender;
    private YAxis mYAxis;
    private YAxisRender mYAxisRender;

    private float mInsetLeft;
    private float mInsetTop;
    private float mInsetRight;
    private float mInsetBottom;
    private int mWidth;

    private PointValue mPointValue;
    private OnChartValueSelectedListener mOnChartValueSelectedListener;

    private float mDrawPercent = VALUE_COMPLETE;
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

        mDataRender = new DataRender();
        mDataRender.attachTo(this);
        mDividersRender = new DividersRender();
        mLineFillRender = new LineFillRender();
        mHighlightRender = new HighlightRender();
        setXAxis(new XAxis());
        setXAxisRender(new XAxisRender());
        setYAxis(new YAxis());
        setYAxisRender(new YAxisRender());

        mAnimator = ValueAnimator.ofFloat(0, VALUE_COMPLETE);
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

    public LineFillRender getLineFillRender() {
        return mLineFillRender;
    }

    public HighlightRender getHighlightRender() {
        return mHighlightRender;
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

    public void notifyDataSetChanged() {
        mPointValue = null;
        mXAxis.calcMinMax();
        mYAxis.calcMinMax();
        if (mAnimated && !getLineData().isEmpty()) {
            mDrawPercent = 0;
            mAnimator.cancel();
            mAnimator.start();
        } else {
            mDrawPercent = VALUE_COMPLETE;
            invalidate();
        }
    }

    private void updateRendersDrawRect() {
        mDataRender.setDrawRect(mInsetLeft, 0,
                getRight() - Math.max(mInsetRight, mYAxisRender.getWidth()), getHeight() - mXAxisRender.getHeight());
        RectF rectF = mDataRender.getDrawRect();
        mLineFillRender.setFillContent(rectF.left, rectF.top, rectF.right, rectF.bottom);
        mDividersRender.setDrawRect(0, 0, getWidth(), getHeight());
        mXAxisRender.setDrawRect(0, 0, getWidth(), getHeight() - mDataRender.getDrawRect().bottom);
        RectF dataRect = getDataRender().getDrawRect();
        mYAxisRender.setDrawRect(dataRect.left, dataRect.top + mInsetTop, getWidth(), dataRect.bottom);
        if (mEmptyRender != null) {
            mEmptyRender.setDrawRect(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        updateRendersDrawRect();
        mDividersRender.draw(canvas);
        drawXAxis(canvas);
        mYAxisRender.draw(canvas);
        if (getLineData().isEmpty()) {
            drawEmpty(canvas);
        } else {
            prepareLinePoints();
            mDataRender.draw(canvas);
            if (mDrawPercent == VALUE_COMPLETE) {
                mLineFillRender.draw(canvas);
            }
//            if (mDrawPercent == VALUE_COMPLETE && mCurrentHighlightPosition >= 0) {
//                List<PointF> pointList = mLineRender.getPoints();
//                if (pointList.size() > mCurrentHighlightPosition) {
//                    mHighlightRender.draw(canvas, pointList.get(mCurrentHighlightPosition), getBottom());
//                }
//            }
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

    private void drawXAxis(Canvas canvas) {
        canvas.save();
        canvas.translate(0, mDataRender.getDrawRect().bottom);
        mXAxisRender.draw(canvas);
        canvas.restore();
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
            mWidth = right - left;
        }
    }

    public void highlightValue(int lineIndex, int pointIndex) {
        // TODO: 17-6-20 calc point value
//        onHighlightValue(lineIndex, pointIndex);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getLineData().isEmpty()) {
            return false;
        }
//
//        List<PointF> points = mLineRender.getPoints();
//        if (points.isEmpty()) {
//            return true;
//        }
//
//        int size = points.size();
//        float avg = (mWidth - mInsetLeft - mInsetRight) / (size - 1);
//        int position = (int) ((event.getX() - mInsetLeft + avg / 2) / avg);
//        if (position < 0) {
//            position = 0;
//        } else if (position >= size) {
//            position = size - 1;
//        }
//
//        if (position != mCurrentHighlightPosition) {
//            onHighlightValue(position);
//        }
        return true;
    }

    private void onHighlightValue(int lineIndex, int pointIndex, PointValue pointValue) {
        mPointValue = pointValue;
        if (mDrawPercent == VALUE_COMPLETE) {
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
