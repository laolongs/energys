package cn.tties.energy.application;

import android.app.Activity;
import android.app.Application;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.NetWorkUtils;


public class MyApplication extends Application {

    private static MyApplication instance;
    private List<Activity> activitys;
    public static Boolean mNetWorkState;
    public static Activity curActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        Config.DEBUG = true;
        instance = this;
        mNetWorkState = NetWorkUtils.isNetworkConnected(MyApplication.getInstance());
        init();
        initImageLoad();
    }
    //各个平台的配置
    {
        //微信
        PlatformConfig.setWeixin("wx373d9c107c84ae60", "d5b070aef80059f95f8b0321dc46fb99");
//        //新浪微博(第三个参数为回调地址)
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com/sina2/callback");
//        //QQ
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    public static Loginbean getUserInfo() {
        Loginbean userInfo = ACache.getInstance().getAsObject(Constants.CACHE_USERINFO);
        return userInfo;
    }
    private void init() {
        activitys = new ArrayList<>();
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    /**
     * 添加activity
     */
    public void addActivity(Activity act) {
        if (activitys == null) {
            activitys.add(act);
        }
       
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity act) {
        if (activitys != null) {
            activitys.remove(act);
        }
    }

    /**
     * 退出app
     */
    public void exitApp() {
        if (activitys != null) {
            synchronized (activitys) {
                for (Activity act : activitys) {
                    act.finish();
                }
            }
        }
        System.exit(0);
    }
    private void initImageLoad() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                Glide.with(activity).load(path).into(imageView);

        }

            @Override
            public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
                Glide.with(activity).load(path).into(imageView);
            }

            @Override
            public void clearMemoryCache() {

            }
        });   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);
    }

}
