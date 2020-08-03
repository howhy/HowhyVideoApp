package com.howhy.video;

import android.util.Log;

public class testDemo {
    public static void main(String[] args) {
        String str="第01集$https://iqiyi.cdn9-okzy.com/share/afd0be2fd16b0101d8926769343c7950#第02集$https://iqiyi.cdn9-okzy.com/share/7b497aa1b2a83ec63d1777a88676b0c2#第03集$https://iqiyi.cdn9-okzy.com/share/e06db73b5702b904f41ccd406ac8f4ba#";
        Log.i("11111", "addition_isCorrect: "+str.split("$").toString());
    }
}
