package cn.tties.energy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import cn.tties.energy.application.MyApplication;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.Loginbean;

/**
 * Created by qizepu on 2017/2/22.
 * 跟App相关的辅助类
 */
public class AppUtils {

    private AppUtils() {
                /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    public static Loginbean getUsertInfo() {
        Loginbean ret = ACache.getInstance().getAsObject(Constants.CACHE_USERINFO);
        return ret;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = MyApplication.getInstance().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    MyApplication.getInstance().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return MyApplication.getInstance().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static int getVersionCode() {
        try {

            PackageManager packageManager = MyApplication.getInstance().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    MyApplication.getInstance().getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName() {
        try {
            PackageManager packageManager = MyApplication.getInstance().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    MyApplication.getInstance().getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    public static long getDownLoad() {
        Long id = ACache.getInstance().getAsObject(Constants.DOWNLOAD_ID);
        return id == null ? 0L : id;
    }

    public static void saveDownLoad(long id) {
        ACache.getInstance().put(Constants.DOWNLOAD_ID, id);
    }

    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static void setListViewHeight(ListView listView) {
        setListViewHeight(listView, 0);
    }

    public static void setListViewHeight(ListView listView, int heightFix) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1) + heightFix);
        listView.setLayoutParams(params);
    }
    public static void setRecycleeViewHeight(RecyclerView listView, int heightFix) {
        RecyclerView.Adapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getItemCount(); i++) {

            totalHeight += listView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getMeasuredHeight()*(adapter.getItemCount() - 1) + heightFix);
        listView.setLayoutParams(params);
    }


}