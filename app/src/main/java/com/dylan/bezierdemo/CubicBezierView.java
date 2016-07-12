package com.dylan.bezierdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

/**
 * Description
 * author   Dylan.zhuang
 * Date:    16/7/12-上午10:40
 */
public class CubicBezierView extends View implements BloomOption {
    private static final String TAG = "CubicBezierView";

    private MyPoint mBloomCenterPoint = new MyPoint();
    /**
     * 用于保存花瓣
     */
    private ArrayList<PetalView> petals;

    public CubicBezierView(Context context) {
        this(context, null);
    }

    public CubicBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CubicBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        mBloomCenterPoint.set(screenWidth / 2,  screenHeight / 2 - 200);
        petals = new ArrayList<>();
        initPetalData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (petals != null && petals.size() > 0) {
            drawStem(canvas);
            int radius = RandomUtil.randomInt(minBloomRadius, maxBloomRadius);
            int size = petals.size();
            MyPoint point = new MyPoint(mBloomCenterPoint.x, mBloomCenterPoint.y);
            for (int i = 0; i < size; i++) {
                PetalView petal = petals.get(i);
                if (petal != null) {
                    petal.render(point, radius, canvas);
                }
            }
        }
    }

    private void drawStem(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(mBloomCenterPoint.x, mBloomCenterPoint.y);
        path.quadTo(mBloomCenterPoint.x + 50, mBloomCenterPoint.y + 200, mBloomCenterPoint.x - 50, mBloomCenterPoint.y + 600);
        canvas.drawPath(path, paint);
    }

    private void initPetalData() {
        int petalCount = RandomUtil.randomInt(minPetalCount, maxPetalCount);
        //每个花瓣应占用的角度
        float angle = 360f / petalCount;
        int startAngle = RandomUtil.randomInt(0, 90);

        for (int i = 0; i < petalCount; i++) {
            //随机产生第一个控制点的拉伸倍数
            float stretchA = RandomUtil.random(minPetalStretch, maxPetalStretch);
            //随机产生第二个控制地的拉伸倍数
            float stretchB = RandomUtil.random(minPetalStretch, maxPetalStretch);
            //计算每个花瓣的起始角度
            int beginAngle = startAngle + (int) (i * angle);

            PetalView petal = new PetalView(stretchA, stretchB, beginAngle, angle);
            petals.add(petal);
        }
    }

}
