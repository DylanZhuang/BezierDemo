package com.dylan.bezierdemo;

import android.animation.ValueAnimator;
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
 * Date:    16/7/11-下午6:04
 */
public class QuadraticBezierView extends View {
    private int mScreenWidth;

    private int mScreenHeight;

    private Point mStartPoint = new Point();

    private Point mEndPoint = new Point();

    private Point mControlPoint = new Point();

    private int mRadius;

    public QuadraticBezierView(Context context) {
        this(context, null);
    }

    public QuadraticBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuadraticBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;

        int height = mScreenHeight * 7 / 10;
        mStartPoint.set(mScreenWidth / 10, height);
        mEndPoint.set(mScreenWidth * 9 / 10, height);

        mRadius = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);

        int x = mControlPoint.x;
        int y = mControlPoint.y;
        int height = mStartPoint.y;
        if (y > mStartPoint.y && x > mScreenWidth * 2 / 5
                && x < mScreenWidth * 3 / 5) {
            height = y + y - mStartPoint.y;
        }

        Path path = new Path();
        path.moveTo(mStartPoint.x, mStartPoint.y);
        path.quadTo(mScreenWidth / 2, height, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y - mRadius, mRadius, paint);
    }

    private void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mControlPoint.y, -10);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mControlPoint.y = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) (event.getX());
                int moveY = (int) (event.getY());

                mControlPoint.x = moveX;
                mControlPoint.y = moveY;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                int x = mControlPoint.x;
                int y = mControlPoint.y;
                if (y > mStartPoint.y && x > mScreenWidth * 2 / 5
                        && x < mScreenWidth * 3 / 5) {
                    startAnim();
                }
                break;
        }
        return true;
    }
}
