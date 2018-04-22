package cn.tties.energy.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
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
    public Context context;
    public Energy_Monthlybean bean;
    public LayoutInflater inflater;

    //------进度条
    private OutputStream outputStream;

    public MyMonthlyAdapter(Context context, Energy_Monthlybean bean) {
        this.context = context;
        this.bean = bean;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.energy_energy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.energyMonthlyTv.setText(bean.getResult().get(position).getReportName());
        holder.energyMonthlyTime.setText(bean.getResult().get(position).getCreateTime());
        holder.energyMonthlyPdfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.energyMonthlyLoad.setVisibility(View.VISIBLE);

            }
        });
        holder.energyMonthlyLoadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.monthProbar.setVisibility(View.VISIBLE);
                String savePAth = Environment.getExternalStorageDirectory() + "/DownFile";
                File file1 = new File(savePAth);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                //确定下来改后缀
                String savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" + "DJ.doc";
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
                            URL url = new URL(
                                    "http://b.zol-img.com.cn/sjbizhi/images/9/540x960/1472549276394.jpg");
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
                                                        ShareUtils.shareWeb((Activity) context, Defaultcontent.url, Defaultcontent.title
                                                                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.WEIXIN
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
//

    }

    @Override
    public int getItemCount() {
        return bean != null ? bean.getResult().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.energy_monthly_pdfimg)
        ImageView energyMonthlyPdfimg;
        @BindView(R.id.energy_monthly_tv)
        TextView energyMonthlyTv;
        @BindView(R.id.energy_monthly_time)
        TextView energyMonthlyTime;
        @BindView(R.id.energy_monthly_loadimg)
        ImageView energyMonthlyLoadimg;
        @BindView(R.id.energy_monthly_load)
        TextView energyMonthlyLoad;
        @BindView(R.id.month_probar)
        ProgressBar monthProbar;
        @BindView(R.id.energy_monthly_imgshare)
        ImageView energyMonthlyImgshare;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
