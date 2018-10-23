package com.cjmmy.vxordersystem.utils;

import java.util.Random;

/**
 * 随机生成主键的工具
 */
public class KeyUtil {
    //防止多线程并发产生重复key，所以方法设置为synchronized
    public static synchronized String generateKey(){
        Random random = new Random();
        int foot = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(foot);
    }
}
