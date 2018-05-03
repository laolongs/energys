package cn.tties.energy.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.utils.StringUtil;


/**
 * 圆形进度条
 * author chensi
 */
public class CriProgressDialog {
    private Context mContext;
    private Dialog mDialog;
    private TextView text;

    public CriProgressDialog() {
        mContext = MyApplication.getInstance();
    }

    public CriProgressDialog(Context context) {
        mContext = context;
    }

    public Dialog loadDialog(String str) {
        mDialog = new Dialog(mContext, R.style.dialog);
        LayoutInflater in = LayoutInflater.from(mContext);
        View viewDialog = in.inflate(R.layout.dialog_progressall, null);
//        viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 这里可以设置dialog的大小，当然也可以设置dialog title等
        // LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);
        // mDialog.setContentView(viewDialog, layoutParams);
        if (!StringUtil.isEmpty(str)) {
            text = (TextView) viewDialog.findViewById(R.id.textView);
            text.setText(str);
        }
        mDialog.setContentView(viewDialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
        return mDialog;
    }

    public void removeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setText(String str) {
        text.setText(str);
    }
}