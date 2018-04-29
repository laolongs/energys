package cn.tties.energy.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.presenter.OpsPresenter;
import cn.tties.energy.utils.PtrClassicFoot;
import cn.tties.energy.utils.PtrClassicHeader;
import cn.tties.energy.view.activity.QuestionsActivity;
import cn.tties.energy.view.adapter.MyOpsrightAdapter;
import cn.tties.energy.view.adapter.MyOpsrightNoDataAdapter;
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.iview.IOpsView;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by li on 2018/3/21
 * description：
 * author：guojlli
 */

public class OpsFragment extends BaseFragment<OpsPresenter> implements IOpsView, View.OnClickListener {
    private static final String TAG = "OpsFragment";
    Toolbar dataToolbar;
    TextView opsNumber;
    List<Opsbean.ResultBean.QuestionListBean> list;
    int pagenum = 1;
    RelativeLayout opsRightRL;
    RecyclerView opsRcyRight;
    PtrFrameLayout opsRefreshLayout;
    ImageView iv1;
    TextView tv1;
    LinearLayout llQues1;
    ImageView iv2;
    TextView tv2;
    LinearLayout llQues2;
    ImageView iv3;
    TextView tv3;
    LinearLayout llQues3;
    ImageView iv4;
    TextView tv4;
    LinearLayout llQues4;
    ImageView iv5;
    TextView tv5;
    LinearLayout llQues5;
    ImageView iv6;
    TextView tv6;
    LinearLayout llQues6;
    TextView opsRcyHint;
    private MyOpsrightAdapter adapter;
    boolean flag;
    public int count;
    private ImageView ivCurrent;
    private TextView tvCurrent;
    CriProgressDialog dialog;
    private LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_ops, null);
        initFindView(inflate);
        initView();
        initRefresh();
        return inflate;
    }

    private void initFindView(View inflate) {
        dataToolbar = inflate.findViewById(R.id.data_toolbar);
        opsRightRL = inflate.findViewById(R.id.ops_right_RL);
        opsRcyRight = inflate.findViewById(R.id.ops_rcy_right);
        opsRefreshLayout = inflate.findViewById(R.id.ops_refreshLayout);
        iv1 = inflate.findViewById(R.id.iv1);
        tv1 = inflate.findViewById(R.id.tv1);
        llQues1 = inflate.findViewById(R.id.llQues1);
        iv2 = inflate.findViewById(R.id.iv2);
        tv2 = inflate.findViewById(R.id.tv2);
        llQues2 = inflate.findViewById(R.id.llQues2);
        iv3 = inflate.findViewById(R.id.iv3);
        tv3 = inflate.findViewById(R.id.tv3);
        llQues3 = inflate.findViewById(R.id.llQues3);
        iv4 = inflate.findViewById(R.id.iv4);
        tv4 = inflate.findViewById(R.id.tv4);
        llQues4 = inflate.findViewById(R.id.llQues4);
        iv5 = inflate.findViewById(R.id.iv5);
        tv5 = inflate.findViewById(R.id.tv5);
        llQues5 = inflate.findViewById(R.id.llQues5);
        iv6 = inflate.findViewById(R.id.iv6);
        tv6 = inflate.findViewById(R.id.tv6);
        llQues6 = inflate.findViewById(R.id.llQues6);
        opsRcyHint = inflate.findViewById(R.id.ops_rcy_hint);
        opsNumber = inflate.findViewById(R.id.ops_number);
        llQues1.setOnClickListener(this);
        llQues2.setOnClickListener(this);
        llQues3.setOnClickListener(this);
        llQues4.setOnClickListener(this);
        llQues5.setOnClickListener(this);
        llQues6.setOnClickListener(this);
    }


    private void initView() {
        dialog = new CriProgressDialog(getActivity());
        manager = new LinearLayoutManager(getActivity());
        opsRcyRight.setLayoutManager(manager);
        list = new ArrayList<>();
        iv1.setSelected(true);
        tv1.setSelected(true);
        ivCurrent = iv1;
        tvCurrent = tv1;
        setClickButton(0);
    }

    private void initRefresh() {
        //上拉加载 下拉刷新
        PtrClassicFoot foot = new PtrClassicFoot(getActivity());
        PtrClassicHeader header = new PtrClassicHeader(getActivity());
        opsRefreshLayout.setHeaderView(header);
        opsRefreshLayout.setFooterView(foot);
        opsRefreshLayout.addPtrUIHandler(header);
        opsRefreshLayout.addPtrUIHandler(foot);
        opsRefreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                flag = true;
                pagenum++;
                mPresenter.setPageNum(pagenum);
                mPresenter.getOpsRightData();
                adapter.notifyDataSetChanged();
                opsRefreshLayout.refreshComplete();
                Log.i("-----------", "onLoadMoreBegin: " + "111111");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                opsRefreshLayout.refreshComplete();
            }

        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new OpsPresenter(this);
    }

    @Override
    public void setOpsRightData(Opsbean opsbean) {
        if (opsbean.getResult().getQuestionList().size() > 0) {
            dialog.removeDialog();
            count = opsbean.getResult().getQuestionList().size();
            Log.i(TAG, "setOpsRightData:countcountcount " + count);
            if (flag) {
                list.addAll(list);
            } else {
                list = opsbean.getResult().getQuestionList();
            }
            adapter.setOpsbean(opsbean.getResult());
            adapter.setApapterData(list);
            adapter.notifyDataSetChanged();
            opsNumber.setText(opsbean.getResult().getCount() + "");
            adapter.setonClickListener(new MyOpsrightAdapter.onClickListener() {
                @Override
                public void onClickItemListener(int postion) {
                    Intent intent = new Intent(getActivity(), QuestionsActivity.class);
                    intent.putExtra("questionId", list.get(postion).getQuestionId() + "");
                    startActivity(intent);
                }
            });
        } else {
            adapter.getFlag(true);
            adapter.notifyDataSetChanged();
            Log.i(TAG, "setOpsRightData: " + "当前bean里无数据");
        }
        if (opsbean.getResult().getCount() == 0 && opsbean.getResult().getQuestionList().size() == 0) {
            MyOpsrightNoDataAdapter adapter = new MyOpsrightNoDataAdapter();
            opsRcyRight.setAdapter(adapter);
            dialog.removeDialog();
        }

    }

    public void setClickButton(int patrolType) {
        dialog.loadDialog("加载中");
        flag = false;
        adapter = new MyOpsrightAdapter();
        list.clear();
        opsNumber.setText("0");
        pagenum = 1;
        mPresenter.setPageNum(pagenum);
        mPresenter.setPatrolType(patrolType);
        mPresenter.getOpsRightData();
        opsRcyRight.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (view.getId()) {
            //里头patrolType对应左边竖列
//                  0  全部 1, 柜子 2 变压器 4 绝缘 5 房间  6 附属环境
//                    //全部
            case R.id.llQues1:
                setClickButton(0);
                iv1.setSelected(true);
                tv1.setSelected(true);
                ivCurrent = iv1;
                tvCurrent = tv1;
                break;
            case R.id.llQues2:
                setClickButton(5);
                iv2.setSelected(true);
                tv2.setSelected(true);
                ivCurrent = iv2;
                tvCurrent = tv2;
                break;
            case R.id.llQues3:
                setClickButton(1);
                iv3.setSelected(true);
                tv3.setSelected(true);
                ivCurrent = iv3;
                tvCurrent = tv3;
                break;
            case R.id.llQues4:
                setClickButton(2);
                iv4.setSelected(true);
                tv4.setSelected(true);
                ivCurrent = iv4;
                tvCurrent = tv4;
                break;
            case R.id.llQues5:
                setClickButton(4);
                iv5.setSelected(true);
                tv5.setSelected(true);
                ivCurrent = iv5;
                tvCurrent = tv5;
                break;
            case R.id.llQues6:
                setClickButton(6);
                iv6.setSelected(true);
                tv6.setSelected(true);
                ivCurrent = iv6;
                tvCurrent = tv6;
                break;
            default:
                break;
        }
    }
}
