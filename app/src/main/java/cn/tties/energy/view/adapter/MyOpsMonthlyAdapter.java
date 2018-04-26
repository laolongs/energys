package cn.tties.energy.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.Energy_Monthlybean;
import cn.tties.energy.utils.ShareUtils;
import cn.tties.energy.utils.ToastUtil;

/**
 * Created by li on 2018/4/6
 * description：
 * author：guojlli
 */

public class MyOpsMonthlyAdapter extends RecyclerView.Adapter<MyOpsMonthlyAdapter.ViewHolder> {
    private static final String TAG = "MyMonthlyAdapter";
    public Context context;
    public Energy_Monthlybean bean;
    public LayoutInflater inflater;
    public boolean flag = false;
    //------进度条
    private OutputStream outputStream;

    public MyOpsMonthlyAdapter(Context context, Energy_Monthlybean bean) {
        this.context = context;
        this.bean = bean;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.energy_opsmonth_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //替换反斜杠
        String reportPath = bean.getResult().get(position).getReportPath();
        reportPath = reportPath.replaceAll("\\\\", "/");
        Log.i(TAG, "run: " + reportPath);
        String encodepath = URLEncoder.encode(reportPath);
        String encodeName = URLEncoder.encode(bean.getResult().get(position).getReportName());
        final String httpurl = Constants.OpsBASE_RUL + "downfile.do?filePath=" + encodepath + "&fileName=" + encodeName;
        holder.energyOpsmonthlyTv.setText(bean.getResult().get(position).getReportName());
        holder.energyOpsmonthlyTime.setText(bean.getResult().get(position).getCreateTime());
        holder.energyOpsmonthlyPdfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.energyMonthlyLoad.setVisibility(View.VISIBLE);

            }
        });
        //确定下来改后缀
        String savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" + bean.getResult().get(position).getReportName();
        final File file = new File(savePathString);
        if (file.exists()) {
            holder.monthOpsprobar.setVisibility(View.GONE);
            holder.energyOpsmonthlyLoad.setVisibility(View.VISIBLE);
            holder.energyOpsmonthlyImgshare.setVisibility(View.VISIBLE);
            holder.energyOpsmonthlyLoadimg.setVisibility(View.GONE);
            holder.energyOpsmonthlyImgshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShareUtils.shareWeb((Activity) context, httpurl, "天天智电智慧能效管理平台"
                            , bean.getResult().get(position).getReportName(), "", R.mipmap.ic_launcher, SHARE_MEDIA.WEIXIN
                    );
                }
            });
        }
        holder.energyOpsmonthlyLoadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.monthOpsprobar.setVisibility(View.VISIBLE);
                //创建文件
                String savePAth = Environment.getExternalStorageDirectory() + "/DownFile/";
                File file1 = new File(savePAth);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                //确定下来改后缀
                String savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" + bean.getResult().get(position).getReportName();
                final File file = new File(savePathString);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                new Thread(new Runnable() {
                    private int percent;

                    @Override
                    public void run() {
                        try {
                            // 打开 URL 必须在子线程
                            final URL url = new URL(httpurl);
                            Log.i(TAG, "run: " + url);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setReadTimeout(5000);
                            conn.setConnectTimeout(5000);
                            int contentLength = conn.getContentLength();
                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                InputStream is = conn.getInputStream();
                                outputStream = new FileOutputStream(file);
                                byte[] buffer = new byte[1024];
                                int len = -1;
                                int sum = 0;
                                while ((len = is.read(buffer)) != -1) {
                                    outputStream.write(buffer);
                                    sum += len;
                                    // 注意强转方式，防止一直为0
                                    percent = (int) (100.0 * sum / contentLength);
                                    // 在主线程上运行的子线程
                                    ((Activity) context).runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            holder.monthOpsprobar.setProgress(percent);
//                                            textView1.setText(percent + "%");
                                            if (percent == holder.monthOpsprobar.getMax()) {
                                                ToastUtil.showShort(context, "下载完成,请从SD卡下DownFile文件夹下查找！");
                                                holder.monthOpsprobar.setVisibility(View.GONE);
                                                holder.energyOpsmonthlyLoad.setVisibility(View.VISIBLE);
                                                holder.energyOpsmonthlyImgshare.setVisibility(View.VISIBLE);
                                                holder.energyOpsmonthlyLoadimg.setVisibility(View.GONE);
                                                holder.energyOpsmonthlyImgshare.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
//                                                        ShareUtils.shareWeb((Activity) context, Defaultcontent.url, Defaultcontent.title
//                                                                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.WEIXIN
//                                                        );
                                                        ShareUtils.shareWeb((Activity) context, httpurl, "天天智电智慧能效管理平台"
                                                                , bean.getResult().get(position).getReportName(), "", R.mipmap.ic_launcher, SHARE_MEDIA.WEIXIN
                                                        );
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                                outputStream.close();
                                is.close();
                                conn.disconnect();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean != null ? bean.getResult().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.energy_opsmonthly_pdfimg)
        ImageView energyOpsmonthlyPdfimg;
        @BindView(R.id.energy_opsmonthly_load)
        TextView energyOpsmonthlyLoad;
        @BindView(R.id.energy_opsmonthly_tv)
        TextView energyOpsmonthlyTv;
        @BindView(R.id.energy_opsmonthly_time)
        TextView energyOpsmonthlyTime;
        @BindView(R.id.energy_opsmonthly_imgshare)
        ImageView energyOpsmonthlyImgshare;
        @BindView(R.id.energy_opsmonthly_loadimg)
        ImageView energyOpsmonthlyLoadimg;
        @BindView(R.id.month_opsprobar)
        ProgressBar monthOpsprobar;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
