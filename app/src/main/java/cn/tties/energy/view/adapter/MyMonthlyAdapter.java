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
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.Energy_Monthlybean;
import cn.tties.energy.utils.ShareUtils;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.wxapi.Defaultcontent;

/**
 * Created by li on 2018/4/6
 * description：
 * author：guojlli
 */

public class MyMonthlyAdapter extends RecyclerView.Adapter<MyMonthlyAdapter.ViewHolder> {
    private static final String TAG = "MyMonthlyAdapter";
    public Context context;
    public Energy_Monthlybean bean;
    public LayoutInflater inflater;
    public boolean flag=false;
    //------进度条
    private OutputStream outputStream;

    public MyMonthlyAdapter(Context context,Energy_Monthlybean bean) {
        this.context = context;
        this.bean = bean;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.energy_energy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //替换反斜杠
        String reportPath=bean.getResult().get(position).getReportPath();
        reportPath = reportPath.replaceAll("\\\\", "/");
        Log.i(TAG, "run: "+reportPath);
        String encodepath = URLEncoder.encode(reportPath);
        String encodeName = URLEncoder.encode(bean.getResult().get(position).getReportName());
        final String httpurl=Constants.OpsBASE_RUL+"downfile.do?filePath="+encodepath+"&fileName="+encodeName;
        holder.energyMonthlyTv.setText(bean.getResult().get(position).getReportName());
        holder.energyMonthlyTime.setText(bean.getResult().get(position).getCreateTime());
        holder.energyMonthlyPdfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.energyMonthlyLoad.setVisibility(View.VISIBLE);

            }
        });
        //确定下来改后缀
        String savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" +  bean.getResult().get(position).getReportName();
        final File file = new File(savePathString);
        if (file.exists()) {
            holder.monthProbar.setVisibility(View.GONE);
            holder.energyMonthlyLoad.setVisibility(View.VISIBLE);
            holder.energyMonthlyImgshare.setVisibility(View.VISIBLE);
            holder.energyMonthlyLoadimg.setVisibility(View.GONE);
            holder.energyMonthlyImgshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShareUtils.shareWeb((Activity) context, httpurl, "天天智电智慧能效管理平台"
                            , bean.getResult().get(position).getReportName(),"", R.mipmap.ic_launcher, SHARE_MEDIA.WEIXIN
                    );
                }
            });
        }
        holder.energyMonthlyLoadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.monthProbar.setVisibility(View.VISIBLE);
                //创建文件
                String savePAth = Environment.getExternalStorageDirectory() +"/DownFile/";
                File file1 = new File(savePAth);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                //确定下来改后缀
                String savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" +  bean.getResult().get(position).getReportName();
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
                            Log.i(TAG, "run: "+url);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setReadTimeout(5000);
                            conn.setConnectTimeout(5000);
                            conn.setRequestProperty("Charset", "gb2312");//设置编码utf-8   pdfgb2312
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
                                            holder.monthProbar.setProgress(percent);
//                                            textView1.setText(percent + "%");
                                            if (percent == holder.monthProbar.getMax()) {
                                                ToastUtil.showShort(context, "下载完成,请从SD卡下DownFile文件夹下查找！");
                                                holder.monthProbar.setVisibility(View.GONE);
                                                holder.energyMonthlyLoad.setVisibility(View.VISIBLE);
                                                holder.energyMonthlyImgshare.setVisibility(View.VISIBLE);
                                                holder.energyMonthlyLoadimg.setVisibility(View.GONE);
                                                holder.energyMonthlyImgshare.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
//                                                        ShareUtils.shareWeb((Activity) context, Defaultcontent.url, Defaultcontent.title
//                                                                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.WEIXIN
//                                                        );
                                                        ShareUtils.shareWeb((Activity) context, httpurl, "天天智电智慧能效管理平台"
                                                                , bean.getResult().get(position).getReportName(),"", R.mipmap.ic_launcher, SHARE_MEDIA.WEIXIN
                                                        );
//                                                        ToastUtil.showShort(context,"暂无分享功能！");
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
        ImageView energyMonthlyPdfimg;
        TextView energyMonthlyTv;
        TextView energyMonthlyTime;
        ImageView energyMonthlyLoadimg;
        TextView energyMonthlyLoad;
        ProgressBar monthProbar;
        ImageView energyMonthlyImgshare;
        public ViewHolder(View itemView) {
            super(itemView);
            energyMonthlyPdfimg = (ImageView) itemView.findViewById(R.id.energy_monthly_pdfimg);
            energyMonthlyTv = (TextView) itemView.findViewById(R.id.energy_monthly_tv);
            energyMonthlyTime = (TextView) itemView.findViewById(R.id.energy_monthly_time);
            energyMonthlyLoadimg = (ImageView) itemView.findViewById(R.id.energy_monthly_loadimg);
            energyMonthlyLoad = (TextView) itemView.findViewById(R.id.energy_monthly_load);
            monthProbar = (ProgressBar) itemView.findViewById(R.id.month_probar);
            energyMonthlyImgshare = (ImageView) itemView.findViewById(R.id.energy_monthly_imgshare);
        }
    }
}
