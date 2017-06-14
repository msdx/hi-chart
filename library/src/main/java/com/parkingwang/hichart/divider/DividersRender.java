/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.divider;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.parkingwang.hichart.render.BaseRender;

/**
 * 图表分割线
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-14 0.1
 */
public class DividersRender extends BaseRender {
    private Divider mLeftDivider;
    private Divider mTopDivider;
    private Divider mRightDivider;
    private Divider mBottomDivider;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    public DividersRender() {
        mPaint.setStyle(Paint.Style.FILL);
    }

    public Divider getLeftDivider() {
        return mLeftDivider;
    }

    public void setLeftDivider(Divider leftDivider) {
        mLeftDivider = leftDivider;
    }

    public Divider getTopDivider() {
        return mTopDivider;
    }

    public void setTopDivider(Divider topDivider) {
        mTopDivider = topDivider;
    }

    public Divider getRightDivider() {
        return mRightDivider;
    }

    public void setRightDivider(Divider rightDivider) {
        mRightDivider = rightDivider;
    }

    public Divider getBottomDivider() {
        return mBottomDivider;
    }

    public void setBottomDivider(Divider bottomDivider) {
        mBottomDivider = bottomDivider;
    }

    public float getLeftWidth() {
        return mLeftDivider == null ? 0 : mLeftDivider.getWidth();
    }

    public float getTopWidth() {
        return mTopDivider == null ? 0 : mTopDivider.getWidth();
    }

    public float getRightWidth() {
        return mRightDivider == null ? 0 : mRightDivider.getWidth();
    }

    public float getBottomWidth() {
        return mBottomDivider == null ? 0 : mBottomDivider.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mLeftDivider != null) {
            float top = mDrawRect.top + mLeftDivider.getPadding();
            float right = mDrawRect.left + mLeftDivider.getWidth();
            float bottom = mDrawRect.bottom - mLeftDivider.getPadding();
            mPaint.setColor(mLeftDivider.getColor());
            canvas.drawRect(mDrawRect.left, top, right, bottom, mPaint);
        }
        if (mTopDivider != null) {
            float left = mDrawRect.left + mTopDivider.getPadding();
            float right = mDrawRect.right - mTopDivider.getPadding();
            float bottom = mDrawRect.top + mTopDivider.getWidth();
            mPaint.setColor(mTopDivider.getColor());
            canvas.drawRect(left, mDrawRect.top, right, bottom, mPaint);
        }
        if (mRightDivider != null) {
            float left = mDrawRect.right - mRightDivider.getWidth();
            float top = mDrawRect.top + mRightDivider.getPadding();
            float bottom = mDrawRect.bottom - mRightDivider.getPadding();
            mPaint.setColor(mRightDivider.getColor());
            canvas.drawRect(left, top, mDrawRect.right, bottom, mPaint);
        }
        if (mBottomDivider != null) {
            float left = mDrawRect.left + mBottomDivider.getPadding();
            float top = mDrawRect.bottom - mBottomDivider.getWidth();
            float right = mDrawRect.right - mBottomDivider.getPadding();
            mPaint.setColor(mBottomDivider.getColor());
            canvas.drawRect(left, top, right, mDrawRect.bottom, mPaint);
        }
    }
}
