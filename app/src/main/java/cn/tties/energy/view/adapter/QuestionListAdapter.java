package cn.tties.energy.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.utils.AppUtils;
import cn.tties.energy.view.activity.QuestionsActivity;

import static com.umeng.socialize.a.b.d.i;

/**
 * Created by li on 2018/4/2
 * description：
 * author：guojlli
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {
    private static final String TAG = "QuestionListAdapter";
    Opsbean.ResultBean.QuestionListBean listbean;
    LayoutInflater inflater;
    Context context;


    public QuestionListAdapter(Context context,Opsbean.ResultBean.QuestionListBean listbean) {
        this.context =context;
        this.listbean = listbean;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.listview_question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.listviewTitle.setText(listbean.getDescriptionList().get(position).getContent());
//        DescriptionListViewAdapter adapter = new DescriptionListViewAdapter(context, listbean.getDescriptionList().get(position));
//        holder.listViewDescription.setAdapter(adapter);
//
        final Opsbean.ResultBean.QuestionListBean.DescriptionListBean descriptionListBean = listbean.getDescriptionList().get(position);
        if (descriptionListBean.getImageList() != null) {
            final ImageAdapter adapter = new ImageAdapter(context, descriptionListBean.getImageList());
            OnRecyclerViewItemClickListener clickListener = new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, final int pos) {

                    String path = descriptionListBean.getImageList().get(pos).path;
                    //打开预览
                    Intent intentPreview = new Intent(context, ImagePreviewActivity.class);

                    ImageItem item = new ImageItem();
                    item.path = path;
                    List<ImageItem> list = new ArrayList<>();
                    list.add(item);
                    Log.i(TAG, "bind: " + item.path);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) list);
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    ((QuestionsActivity)context).startActivity(intentPreview);
                }
            };
            adapter.setOnItemClickListener(clickListener);
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context,3){
                @Override
                public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                    int measuredWidth = holder.recyclerView.getMeasuredWidth();
                    int measuredHeight = holder.recyclerView.getMeasuredHeight();
                    int myMeasureHeight = 0;
                    int count = state.getItemCount();
                    for (int i = 0; i < count; i++) {
                        View view = recycler.getViewForPosition(i);
                        if (view != null) {
                            if (myMeasureHeight < measuredHeight && i % 3 == 0) {
                                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                                        getPaddingLeft() + getPaddingRight(), p.width);
                                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                                        getPaddingTop() + getPaddingBottom(), p.height);
                                view.measure(childWidthSpec, childHeightSpec);
                                myMeasureHeight += view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                            }
                            recycler.recycleView(view);
                        }
                    }
//                    Log.i("Height", "" + Math.min(measuredHeight, myMeasureHeight));
                    setMeasuredDimension(measuredWidth, Math.min(measuredHeight, myMeasureHeight));
                }
            });
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setAdapter(adapter);
        }

//        AppUtils.setRecycleeViewHeight(holder.recyclerView, 200);
    }

    @Override
    public int getItemCount() {
        return listbean == null ? 0 : listbean.getDescriptionList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listviewTitle;
        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            listviewTitle = (TextView) itemView.findViewById(R.id.listview_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
    class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SelectedPicViewHolder> {
        private Context mContext;
        private List<ImageItem> mData;
        private LayoutInflater mInflater;
        private OnRecyclerViewItemClickListener listener;

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.listener = listener;
        }

        public void setImages(List<ImageItem> data) {
            mData = new ArrayList<>(data);
            notifyDataSetChanged();
        }

        public List<ImageItem> getImages() {
            return mData;
        }

        public ImageAdapter(Context mContext, List<ImageItem> data) {
            this.mContext = mContext;
            this.mInflater = LayoutInflater.from(mContext);
            setImages(data);
        }

        @Override
        public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SelectedPicViewHolder(mInflater.inflate(R.layout.listview_image, parent, false));
        }

        @Override
        public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            Log.i(TAG, "getItemCount: "+mData.size());
            return mData.size();
        }

        public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView iv_img;
            private int clickPosition;

            public SelectedPicViewHolder(View itemView) {
                super(itemView);
                iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            }

            public void bind(int position) {
                //设置条目的点击事件
                itemView.setOnClickListener(this);
                //根据条目位置设置图片
                ImageItem item = mData.get(position);
                ImagePicker.getInstance().getImageLoader().displayImage((Activity) context, item.path, iv_img, 0, 0);
                clickPosition = position;
            }

            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(v, clickPosition);
            }
        }
    }

}
