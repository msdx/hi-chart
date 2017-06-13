package com.parkingwang.hichart.line;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.parkingwang.hichart.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线绘制
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-18 0.1
 */
public class LineRender {

    private static final int DEFAULT_LINE_WIDTH = 4;
    private static final int DEFAULT_LINE_COLOR = Color.WHITE;
    private static final int DEFAULT_CIRCLE_STROKE_COLOR = Color.WHITE;
    private static final int DEFAULT_CIRCLE_FILL_COLOR = Color.parseColor("#1D8FEE");
    private static final int DEFAULT_CIRCLE_RADIUS = 7;
    private static final int DEFAULT_CIRCLE_HOLE_RADIUS = 3;

    private final YAxis mYAxis;

    private Bitmap mLineBitmap;

    private Paint mBitmapPaint;

    private Paint mLinePaint;
    private Paint mCirclePaint;
    private Paint mCircleHolePaint;

    private float mCircleRadius = DEFAULT_CIRCLE_RADIUS;
    private float mCircleHoleRadius = DEFAULT_CIRCLE_HOLE_RADIUS;

    private Path mLinePath;

    private final Path mClipCanvasPath;
    private List<PointF> mPoints;

    public LineRender(YAxis yAxis) {
        mYAxis = yAxis;
        mLinePath = new Path();
        mClipCanvasPath = new Path();
        mPoints = new ArrayList<>();

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(DEFAULT_LINE_WIDTH);
        mLinePaint.setColor(DEFAULT_LINE_COLOR);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(DEFAULT_CIRCLE_STROKE_COLOR);

        mCircleHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mCircleHolePaint.setStyle(Paint.Style.FILL);
        mCircleHolePaint.setColor(DEFAULT_CIRCLE_FILL_COLOR);
    }

    public void setLineWidth(float lineWidth) {
        mLinePaint.setStrokeWidth(lineWidth);
    }

    public void setLineColor(int color) {
        mLinePaint.setColor(color);
    }

    public void setCircleColor(int color) {
        mCirclePaint.setColor(color);
    }

    public void setCircleRadius(float radius) {
        mCircleRadius = radius;
    }

    public void setCircleHoleColor(int color) {
        mCircleHolePaint.setColor(color);
    }

    public void setCircleHoleRadius(float radius) {
        mCircleHoleRadius = radius;
    }

    void update(List<Entry> entries) {
        final int canvasWidth = mYAxis.getCanvasWidth();
        final int canvasHeight = mYAxis.getCanvasHeight();

        if (canvasWidth == 0 || canvasHeight == 0) {
            return;
        }

        if (mLineBitmap == null
                || mLineBitmap.getWidth() != canvasWidth
                || mLineBitmap.getHeight() != canvasHeight) {
            if (mLineBitmap != null) {
                mLineBitmap.recycle();
            }
            mLineBitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        }

        mLineBitmap.eraseColor(Color.TRANSPARENT);

        mPoints.clear();
        if (entries == null || entries.isEmpty()) {
            return;
        }

        Canvas canvas = new Canvas(mLineBitmap);

        final float contentWidth = mYAxis.getContentWidth();
        final float contentHeight = mYAxis.getContentHeight();
        final float distanceX = contentWidth / (entries.size() - 1);

        float pointX = mYAxis.getOffsetLeft();
        float startY = mYAxis.getOffsetTop();
        float maxValue = mYAxis.getMaxValue();

        mLinePath.reset();
        PointF previous = null;
        for (Entry entry : entries) {
            float pointY = startY + (maxValue - entry.y) * contentHeight / maxValue;
            if (previous == null) {
                mLinePath.moveTo(pointX, pointY);
            } else {
                pointX += distanceX;
                mLinePath.lineTo(pointX, pointY);
            }
            PointF current = new PointF(pointX, pointY);
            mPoints.add(current);
            if (previous != null) {
                canvas.drawLine(previous.x, previous.y, current.x, current.y, mLinePaint);
                canvas.drawCircle(previous.x, previous.y, mCircleRadius, mCirclePaint);
                canvas.drawCircle(previous.x, previous.y, mCircleHoleRadius, mCircleHolePaint);
            }
            previous = current;
        }

        canvas.drawCircle(previous.x, previous.y, mCircleRadius, mCirclePaint);
        canvas.drawCircle(previous.x, previous.y, mCircleHoleRadius, mCircleHolePaint);
    }

    public Path getLinePath() {
        return mLinePath;
    }

    public List<PointF> getPoints() {
        return mPoints;
    }

    void draw(Canvas canvas, float percent) {
        if (mLineBitmap != null && !mLineBitmap.isRecycled()) {
            mClipCanvasPath.reset();
            mClipCanvasPath.moveTo(0, 0);
            mClipCanvasPath.lineTo(mLineBitmap.getWidth() * percent, 0);
            mClipCanvasPath.lineTo(mLineBitmap.getWidth() * percent, mLineBitmap.getHeight());
            mClipCanvasPath.lineTo(0, mLineBitmap.getHeight());
            mClipCanvasPath.close();

            canvas.save();
            canvas.clipPath(mClipCanvasPath);
            canvas.drawBitmap(mLineBitmap, 0, 0, mBitmapPaint);
            canvas.restore();
        }
    }
}
