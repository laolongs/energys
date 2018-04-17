package cn.tties.energy.utils;


import cn.tties.energy.application.MyApplication;

public class Dp2Utils {
    public static int Dp2Px(float dp) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}