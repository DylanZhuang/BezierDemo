package com.dylan.bezierdemo;

/**
 * Description
 * author   Dylan.zhuang
 * Date:    16/7/12-上午11:12
 */
public class MyPoint {
    public int x;
    public int y;

    public MyPoint() {
    }

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //旋转
    public MyPoint rotate(float theta) {
        int x = this.x;
        int y = this.y;
        this.x = (int) (Math.cos(theta) * x - Math.sin(theta) * y);
        this.y = (int) (Math.sin(theta) * x + Math.cos(theta) * y);
        return this;
    }

    //乘以一个常数
    public MyPoint mult(float f) {
        this.x *= f;
        this.y *= f;
        return this;
    }

    //复制
    public MyPoint clone() {
        return new MyPoint(this.x, this.y);
    }

    //向量相减
    public MyPoint subtract(MyPoint p) {
        this.x -= p.x;
        this.y -= p.y;
        return this;
    }

    //向量相加
    public MyPoint add(MyPoint p) {
        this.x += p.x;
        this.y += p.y;
        return this;
    }

    public MyPoint set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "MyPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
