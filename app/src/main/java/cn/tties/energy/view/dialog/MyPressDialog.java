package cn.tties.energy.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by li on 2018/4/20
 * description：
 * author：guojlli
 */

public class MyPressDialog extends Dialog {
    private static int default_width = 160; //默认宽度
    private static int default_height = 200;//默认高度


    public MyPressDialog(Context context, View layout, int style) {
        this(context, default_width, default_height, layout, style);
    }

    public MyPressDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
//        WindowManager m =context.getWindowManager();
        Window window = getWindow();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams params = window.getAttributes();
//         p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
//        p.width = (int) (d.getWidth() * 0.25); // 宽度设置为屏幕的0.8
        params.gravity = Gravity.TOP;
        params.y=height;
        window.setAttributes(params);
    }
}
