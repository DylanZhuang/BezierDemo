package com.dylan.bezierdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description
 * author   Dylan.zhuang
 * Date:    16/7/8-下午6:11
 */
public class BezierView extends View {
    private static final String TAG = "BezierView";

    public static final int QUADRATIC_BEZIER = 2;

    public static final int CUBIC_BEZIER = 3;

    private Paint mPaint;

    private Point mStartPoint = new Point();

    private Point mControlPoint = new Point();

    private Point mControlPoint1 = new Point();

    private Point mEndPoint = new Point();

    private Point mBloomCenterPoint = new Point();

    private int mBezierType;

    private int mWidth;

    private int mHeight;

    private int mRadius;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setBezierType(int type) {
        mBezierType = type;

        if (mBezierType == QUADRATIC_BEZIER) {
            mStartPoint.set(100, mHeight / 2);
            mEndPoint.set(mWidth - 100, mHeight / 2);
            mControlPoint.set(mWidth / 2, 100);
        } else {
            mBloomCenterPoint.set(mWidth / 2, mHeight / 2);
            mStartPoint.set(mWidth / 2, mHeight / 2);
            mEndPoint.set(mWidth / 2, mHeight / 2);
            mControlPoint.set(mWidth / 2 - 200, 100);
            mControlPoint1.set(mWidth / 2 + 200, 100);
        }
    }

    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBezierType == QUADRATIC_BEZIER) {
            drawQuadraticBezier(canvas);
        } else if (mBezierType == CUBIC_BEZIER) {
            drawCubicBezier(canvas);
        }
    }

    private void drawQuadraticBezier(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mControlPoint.x, mControlPoint.y, 10, mPaint);

        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        float[] lines = {mStartPoint.x, mStartPoint.y, mControlPoint.x, mControlPoint.y,
                mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y,
                mEndPoint.x, mEndPoint.y, mStartPoint.x, mStartPoint.y};
        canvas.drawLines(lines, mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(mStartPoint.x, mStartPoint.y);
        path.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(path, mPaint);
    }

    private void drawCubicBezier(Canvas canvas) {
        Point topPoint = new Point(mBloomCenterPoint.x, mBloomCenterPoint.y - mRadius);
        float angle1 = (mBloomCenterPoint.x - mControlPoint.x) * 1.0f / (mBloomCenterPoint.y - mControlPoint.y);
        float angle2 = (mBloomCenterPoint.x - mControlPoint1.x) * 1.0f / (mBloomCenterPoint.y - mControlPoint1.y);

        boolean isBig1 = false;
        boolean isBig2 = false;
        if (mControlPoint.y > mBloomCenterPoint.y) {
            isBig1 = true;
        }
        if (mControlPoint1.y > mBloomCenterPoint.y) {
            isBig2 = true;
        }
        //获取三阶贝塞尔曲线的起始点的值
        mStartPoint = getFixPoint(topPoint, angle1, isBig1);
        mEndPoint = getFixPoint(topPoint, angle2, isBig2);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mControlPoint.x, mControlPoint.y, 10, mPaint);
        canvas.drawCircle(mControlPoint1.x, mControlPoint1.y, 10, mPaint);
        canvas.drawCircle(mBloomCenterPoint.x, mBloomCenterPoint.y, mRadius, mPaint);

        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        float[] lines = {mStartPoint.x, mStartPoint.y, mControlPoint.x, mControlPoint.y,
                mControlPoint.x, mControlPoint.y, mControlPoint1.x, mControlPoint1.y,
                mControlPoint1.x, mControlPoint1.y, mEndPoint.x, mEndPoint.y,
                mEndPoint.x, mEndPoint.y, mStartPoint.x, mStartPoint.y};
        canvas.drawLines(lines, mPaint);

        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(mStartPoint.x, mStartPoint.y);
        path.cubicTo(mControlPoint.x, mControlPoint.y, mControlPoint1.x, mControlPoint1.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(path, mPaint);
    }

    private Point getFixPoint(Point topPoint, float angle, boolean isBig) {
        double radian = Math.atan(angle);
        if (isBig) {
            radian += Math.PI;
        }
        double sin = Math.sin(radian);
        double cos = Math.cos(radian);
        int x = (int) (topPoint.x - mRadius * sin);
        int y = (int) (topPoint.y + mRadius * (1 - cos));

        Point point = new Point(x, y);
        return point;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) (event.getX());
                int moveY = (int) (event.getY());

                int distanceX = Math.abs(mControlPoint.x - moveX);
                int distanceY = Math.abs(mControlPoint.y - moveY);

                int distanceX1 = Math.abs(mControlPoint1.x - moveX);
                int distanceY1 = Math.abs(mControlPoint1.y - moveY);
                if (distanceX < 50 && distanceY < 50) {
                    mControlPoint.x = moveX;
                    mControlPoint.y = moveY;
                } else if (distanceX1 < 50 && distanceY1 < 50) {
                    mControlPoint1.x = moveX;
                    mControlPoint1.y = moveY;
                }
                invalidate();
                break;
        }
        return true;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int moveX = (int) (event.getX());
//                int moveY = (int) (event.getY());
//                mControlPoint.x = moveX;
//                mControlPoint.y = moveY;
//                invalidate();
//                break;
//        }
//        return true;
//    }
}
