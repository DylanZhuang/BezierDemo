package com.dylan.bezierdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Description
 * author   Dylan.zhuang
 * Date:    16/7/12-上午11:07
 */
public class PetalView {
    private static final String TAG = "PetalView";

    private float stretchA;//第一个控制点延长线倍数
    private float stretchB;//第二个控制点延长线倍数
    private float startAngle;//起始旋转角，用于确定第一个端点
    private float angle;//两条线之间夹角，由起始旋转角和夹角可以确定第二个端点
    private int radius = 100;//花芯的半径
    private Path path = new Path();//用于保存三次贝塞尔曲线
    private Paint paint = new Paint();

    public PetalView(float stretchA, float stretchB, float startAngle, float angle) {
        this.stretchA = stretchA;
        this.stretchB = stretchB;
        this.startAngle = startAngle;
        this.angle = angle;
        paint.setColor(Color.RED);
    }

    public void render(MyPoint p, int radius, Canvas canvas) {
        if (this.radius <= radius) {
            this.radius += 25;
        }
        draw(p, canvas);
    }

    private void draw(MyPoint p, Canvas canvas) {
        path = new Path();
        //将向量（0，radius）旋转起始角度，第一个控制点根据这个旋转后的向量计算
        MyPoint t = new MyPoint(0, this.radius).rotate(RandomUtil.degrad(this.startAngle));
        //第一个端点，为了保证圆心不会随着radius增大而变大这里固定为3
        MyPoint v1 = new MyPoint(0, 3).rotate(RandomUtil.degrad(this.startAngle));
        //第二个端点
        MyPoint v2 = t.clone().rotate(RandomUtil.degrad(this.angle));
        //延长线，分别确定两个控制点
        MyPoint v3 = t.clone().mult(this.stretchA);
        MyPoint v4 = v2.clone().mult(this.stretchB);
        //由于圆心在p点，因此，每个点要加圆心坐标点
        v1.add(p);
        v2.add(p);
        v3.add(p);
        v4.add(p);

        path.moveTo(v1.x, v1.y);
        //参数分别是：第一个控制点，第二个控制点，终点
        path.cubicTo(v3.x, v3.y, v4.x, v4.y, v2.x, v2.y);
        canvas.drawPath(path, paint);
    }
}
