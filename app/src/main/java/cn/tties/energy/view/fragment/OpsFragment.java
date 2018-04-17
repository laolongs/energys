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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.presenter.OpsPresenter;
import cn.tties.energy.utils.PtrClassicFoot;
import cn.tties.energy.utils.PtrClassicHeader;
import cn.tties.energy.view.activity.QuestionsActivity;
import cn.tties.energy.view.adapter.MyOpsrightAdapter;
import cn.tties.energy.view.adapter.MyOpsrightNoDataAdapter;
import cn.tties.energy.view.iview.IOpsView;

/**
 * Created by li on 2018/3/21
 * description：
 * author：guojlli
 */

public class OpsFragment extends BaseFragment<OpsPresenter> implements IOpsView {

    @BindView(R.id.data_toolbar)
    Toolbar dataToolbar;
    Unbinder unbinder;
    @BindView(R.id.ops_rcy_left_bt1)
    RadioButton opsRcyLeftBt1;
    @BindView(R.id.ops_rcy_left_bt2)
    RadioButton opsRcyLeftBt2;
    @BindView(R.id.ops_rcy_left_bt3)
    RadioButton opsRcyLeftBt3;
    @BindView(R.id.ops_rcy_left_bt4)
    RadioButton opsRcyLeftBt4;
    @BindView(R.id.ops_rcy_left_bt5)
    RadioButton opsRcyLeftBt5;
    @BindView(R.id.ops_rcy_left_bt6)
    RadioButton opsRcyLeftBt6;
    @BindView(R.id.ops_rcy_left_rg)
    RadioGroup opsRcyLeftRg;
//    @BindView(R.id.ops_number)
    TextView opsNumber;
    List<Opsbean.ResultBean.QuestionListBean> list;
    List<String> listview;
    Opsbean opsbean;
    int pagenum = 1;
    @BindView(R.id.ops_right_RL)
    RelativeLayout opsRightRL;
    @BindView(R.id.ops_rcy_right)
    RecyclerView opsRcyRight;
    @BindView(R.id.ops_refreshLayout)
    PtrFrameLayout opsRefreshLayout;
    private MyOpsrightAdapter adapter;
    boolean flag;
    private static final String TAG = "OpsFragment";
    private int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_ops, null);
        opsNumber = inflate.findViewById(R.id.ops_number);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        initRefresh();
        return inflate;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        opsRcyRight.setLayoutManager(manager);
        list = new ArrayList<>();
        setClickButton(0);
//        mPresenter.setPageNum(pagenum);
//        mPresenter.setPatrolType(0);
//        mPresenter.getOpsRightData();
        opsRcyLeftRg.check(R.id.ops_rcy_left_bt1);
        opsRcyLeftRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    //里头patrolType对应左边竖列
//                  0  全部 1, 柜子 2 变压器 4 绝缘 5 房间  6 附属环境
                    //全部
                    case R.id.ops_rcy_left_bt1:
                        setClickButton(0);
                        break;
                    //房间
                    case R.id.ops_rcy_left_bt2:
                        setClickButton(5);
                        break;
                    //柜子
                    case R.id.ops_rcy_left_bt3:
                        setClickButton(1);
                        break;
                    //变压器
                    case R.id.ops_rcy_left_bt4:
                        setClickButton(2);
                        break;
                    //绝缘器
                    case R.id.ops_rcy_left_bt5:
                        setClickButton(4);
                        break;
                    //环境
                    case R.id.ops_rcy_left_bt6:
                        setClickButton(6);
                        break;

                }
            }
        });
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
//                Log.i("-----------", "onLoadMoreBegin: "+"111111");
                opsRefreshLayout.refreshComplete();
            }
        });
//
    }

    @Override
    protected void createPresenter() {
        mPresenter = new OpsPresenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setOpsRightData(Opsbean opsbean) {
        if (opsbean.getResult().getQuestionList().size() > 0) {
            count = opsbean.getResult().getQuestionList().size();
            Log.i(TAG, "setOpsRightData:countcountcount "+count);
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
            Log.i(TAG, "setOpsRightData: " + "当前bean里无数据");
        }
        if(opsbean.getResult().getCount()==0&&opsbean.getResult().getQuestionList().size()==0){
            MyOpsrightNoDataAdapter adapter=new MyOpsrightNoDataAdapter(getActivity(),opsbean.getResult());
            opsRcyRight.setAdapter(adapter);
        }

    }

    public void setClickButton(int patrolType) {
        flag=false;
        adapter = new MyOpsrightAdapter(getActivity());
        list.clear();
        opsNumber.setText("0");
        pagenum=1;
        mPresenter.setPageNum(pagenum);
        mPresenter.setPatrolType(patrolType);
        mPresenter.getOpsRightData();
        opsRcyRight.setAdapter(adapter);
    }

}
