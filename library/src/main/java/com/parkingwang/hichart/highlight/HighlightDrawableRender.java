/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.parkingwang.hichart.highlight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.parkingwang.hichart.data.Line;
import com.parkingwang.hichart.data.LineStyle;
import com.parkingwang.hichart.data.PointValue;

import java.util.List;

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-06-23 0.1
 */
public class HighlightDrawableRender extends HighlightRender {

    private final UpdateCallback mCallback;

    private boolean mSelectAllPoint;
    private boolean mShowOnHighPoint;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    public HighlightDrawableRender(UpdateCallback callback) {
        mCallback = callback;
    }

    /**
     * 是否选择所有点。
     *
     * @param selectAllPoint true将把所有点绘制为实心点，false则只把选中点绘制为实心点。
     */
    public void setSelectAllPoint(boolean selectAllPoint) {
        mSelectAllPoint = selectAllPoint;
    }

    public void setShowOnHighPoint(boolean showOnHighPoint) {
        mShowOnHighPoint = showOnHighPoint;
    }

    @Override
    public void draw(Canvas canvas) {
        PointValue point = getPointValue();
        if (!isEnabled() || getPointValue() == null) {
            return;
        }

        List<Line> lines = getData();
        int lineIndex = getLineIndex();
        int pointIndex = getPointIndex();
        if (mSelectAllPoint) {
            for (Line line : lines) {
                drawSolidCircle(canvas, line, line.getPointValues().get(pointIndex));
            }
        } else {
            drawSolidCircle(canvas, lines.get(lineIndex), point);
        }

        if (mShowOnHighPoint) {
            for (Line line : lines) {
                PointValue it = line.getPointValues().get(pointIndex);
                if (it.y < point.y) {
                    point = it;
                }
            }
        }

        RectF drawRect = getDrawRect();
        Drawable drawable = mCallback.onHighlightUpdate(lineIndex, pointIndex, point, lines);
        float width = drawable.getIntrinsicWidth();
        float centerHeight = drawable.getIntrinsicHeight() / 2;
        float translateX = (point.x - width) > drawRect.left ? (point.x - width) : point.x;
        float translateY = point.y - centerHeight;
        if (translateY < drawRect.top) {
            translateY = drawRect.top;
        } else if (point.y + centerHeight > drawRect.bottom) {
            translateY = drawRect.bottom - drawable.getIntrinsicHeight();
        }
        canvas.save();
        canvas.translate(translateX, translateY);
        drawable.draw(canvas);
        canvas.restore();
    }

    private void drawSolidCircle(Canvas canvas, Line line, PointValue p) {
        LineStyle style = line.getStyle();
        mPaint.setColor(style.getCircleColor());
        canvas.drawCircle(p.x, p.y, style.getCircleRadius(), mPaint);
    }


    public interface UpdateCallback {
        Drawable onHighlightUpdate(int lineIndex, int pointIndex, PointValue point, List<Line> lines);
    }
}
