package com.dylan.bezierdemo;

/**
 * Description
 * author   Dylan.zhuang
 * Date:    16/7/12-上午10:57
 */
public class RandomUtil {
    private static float circle = (float) (2 * Math.PI);

    public static int randomInt(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    public static float random(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    public static float degrad(float angle) {
        return circle / 360 * angle;
    }
}
