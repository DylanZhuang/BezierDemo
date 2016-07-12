package com.dylan.bezierdemo;

/**
 * Description
 * author   Dylan.zhuang
 * Date:    16/7/12-上午10:58
 */
public interface BloomOption {
    //用于控制产生随机花瓣个数范围
    int minPetalCount = 8;
    int maxPetalCount = 12;
    //用于控制产生延长线倍数范围
    float minPetalStretch = 2f;
    float maxPetalStretch = 3.5f;
    //用于控制产生花朵半径随机数范围
    int minBloomRadius = 100;
    int maxBloomRadius = 300;
}
