package cn.tties.energy.view.dialog;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.Versionbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.StringUtil;


/**
 * 确认弹出框
 * author chensi
 */
public class ConfirmDialog {
    private Context mContext;
    private AlertDialog mDialog;

    public ConfirmDialog(Context context) {
        mContext = context;
    }

    public void loadDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, final DialogInterface.OnClickListener onClickListener) {
        loadDialog(title, message, onClickListener, false);
    }

    public void loadDialog(String title, String message, final DialogInterface.OnClickListener onClickListener, Boolean btn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton("确定", onClickListener);
        if (!btn) {
            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {//响应事件
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, String btn1, final DialogInterface.OnClickListener onClickListener1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton(btn1, onClickListener1);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, String btn1, String btn2, final DialogInterface.OnClickListener onClickListener1, final DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton(btn1, onClickListener1);
        builder.setNegativeButton(btn2, onClickListener2);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, String btn1, String btn2, String btn3,
                           final DialogInterface.OnClickListener onClickListener1,
                           final DialogInterface.OnClickListener onClickListener2,
                           final DialogInterface.OnClickListener onClickListener3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton(btn1, onClickListener1);
        builder.setNegativeButton(btn2, onClickListener2);
        builder.setNeutralButton(btn3, onClickListener3);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void removeDialog() {
        mDialog.dismiss();
    }

    public void loadUpdateDialog(final BaseActivity activity, final Versionbean ret, Boolean showFlag) {
        try {
            int version = 1;
            PackageManager manager = activity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            version = info.versionCode;
            final DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String serviceString = Context.DOWNLOAD_SERVICE;
                    DownloadManager downloadManager;
                    downloadManager = (DownloadManager) activity.getSystemService(serviceString);

                    String fileName = ret.getUrl().substring(ret.getUrl().lastIndexOf("/") + 1);
                    Uri uri = Uri.parse(ret.getUrl());
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle(ret.getVersionName());
                    request.setDescription("资源包正在下载");
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setVisibleInDownloadsUi(true);
                    // 设置下载后文件存放的位置
//                    request.setDestinationInExternalPublicDir(DOWNLOADPATH, "energy.apk");
                    long reference = downloadManager.enqueue(request);
                    //ACache.getInstance().put(Constants.KEY_DOWNLOAD_UPDATE, reference);
                }
            };

            int newVersion = ret.getVersionCode();
            int limitVersion = ret.getMinVersionCode();
            Log.d("版本", "oldVersion:" + version + " newVersion:" + newVersion + " limitVersion" + limitVersion);
            if (version < limitVersion) {
                this.loadDialog("版本过低请更新", ret.getDescription(), "更新", clickListener);
            } else if (newVersion > version) {
                this.loadDialog("发现新版本", ret.getDescription(), "更新", "取消", "稍后", clickListener, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ACache.getInstance().put(Constants.CACHEE_VERSION_ALERT, "true", Constants.UPDATE_INTERVALS);
                            }
                        });
            } else {
                if (showFlag) {
                    this.loadDialog("已是最新版本", "", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}