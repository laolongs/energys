package cn.tties.energy.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import cn.tties.energy.R;
import cn.tties.energy.common.Constants;
import cn.tties.energy.manager.AppUpgradeManager;
import cn.tties.energy.model.result.Versionbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.AppUtils;
import cn.tties.energy.view.dialog.ConfirmDialog;
import cn.tties.energy.view.dialog.HorProgressDialog;

import static cn.tties.energy.R.drawable.versetion_text_shop_select;

public class UpdateActivity extends Activity implements View.OnClickListener,EasyPermissions.PermissionCallbacks {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    private HorProgressDialog dialog;

    private Versionbean mVersion;

    private Boolean isMminVersion;

    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    public static void show(Activity activity) {
        Intent intent = new Intent(activity, UpdateActivity.class);
        activity.startActivityForResult(intent, 0x01);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        mVersion = ACache.getInstance().getAsObject(Constants.CACHEE_VERSION);
        isMminVersion = false;
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置顶部状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
        }
//        x.view().inject(this);
        description.setText(Html.fromHtml(mVersion.getDescription()));
        title.setText("检测到新版本 " + mVersion.getVersionName());
        if (AppUtils.getVersionCode() < mVersion.getMinVersionCode()) {
            isMminVersion = true;
            btnCancel.setVisibility(View.GONE);
        }
        if(AppUtils.getVersionCode() == mVersion.getMinVersionCode()){
            title.setText("已是最新版,无需更新!");
            btnConfirm.setVisibility(View.GONE);
            btnCancel.setText("知道了");
        }

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (!AppUtils.isWifiConnected()) {
                    ConfirmDialog confirmDialog = new ConfirmDialog(UpdateActivity.this);
                    final DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestExternalStorage();
                        }
                    };
                    confirmDialog.loadDialog("当前非wifi环境，是否升级？", "", onClickListener);
                } else {
                    requestExternalStorage();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AppUpgradeManager.getInstance(this, mVersion).startDown();
            showProgressBar();
            //finish();
        } else {
            EasyPermissions.requestPermissions(this, "需要开启对您手机的存储权限才能下载安装", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private void showProgressBar() {
        final Timer myTimer = new Timer();
        dialog = new HorProgressDialog(UpdateActivity.this);
        dialog.loadDialog("正在下载 " + mVersion.getVersionName(), 100, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myTimer.cancel();
                DownloadManager downloadManager = (DownloadManager) UpdateActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
                downloadManager.remove(AppUtils.getDownLoad());
                finish();
            }
        });

        myTimer.schedule(new TimerTask() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById(AppUtils.getDownLoad());
                Cursor cursor = ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).query(q);
                cursor.moveToFirst();
                int bytes_downloaded = cursor
                        .getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                cursor.close();
                final int dl_progress = (bytes_downloaded * 100 / bytes_total);
                if (dl_progress == 100) {
                    myTimer.cancel();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
//                            mDownloadFileBtn.setText("下载完成");
                            finish();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setValue(dl_progress);
//                            mDownloadFileBtn.setText(dl_progress + "%");
                        }
                    });
                }
            }

        }, 0, 10);

    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //当权限窗口不能弹出式调用-用户勾选了不再提醒
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            ConfirmDialog confirmDialog = new ConfirmDialog(UpdateActivity.this);
            final DialogInterface.OnClickListener onClickListener1 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                }
            };
            final DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            };
            confirmDialog.loadDialog("请手动开启下载权限？", "", "去开启", "取消", onClickListener1, onClickListener2);
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isMminVersion) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
