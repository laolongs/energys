package cn.tties.energy.common;

import android.content.Context;

import cn.tties.energy.view.dialog.CriHintDialog;

/**
 * Created by li on 2018/4/24
 * description：
 * author：guojlli
 */

public class MyHint {
    public static void myHintDialog(Context context){
        final CriHintDialog hintDialog=new CriHintDialog(context);
        hintDialog.loadDialog("暂无数据");
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hintDialog.removeDialog();
            }

        }.start();
    }
}
