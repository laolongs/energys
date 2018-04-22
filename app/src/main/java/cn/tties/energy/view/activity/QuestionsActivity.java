package cn.tties.energy.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.enums.FaultType;
import cn.tties.energy.model.result.Discussbean;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.presenter.QuestionsPresenter;
import cn.tties.energy.utils.AppUtils;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.adapter.DescriptionListViewAdapter;
import cn.tties.energy.view.adapter.ImagePickerAdapter;
import cn.tties.energy.view.adapter.MyQurestionTabAdapter;
import cn.tties.energy.view.dialog.MyPressDialog;
import cn.tties.energy.view.fragment.Questions_discussFragment;
import cn.tties.energy.view.fragment.Questions_progressFragment;
import cn.tties.energy.view.iview.IQuestionsView;

/**
 * 问题详情
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class QuestionsActivity extends BaseActivity<QuestionsPresenter> implements IQuestionsView {

    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.qu_tab)
    TabLayout quTab;
    @BindView(R.id.qu_linkman)
    LinearLayout quLinkman;
    @BindView(R.id.qu_reply)
    LinearLayout quReply;
    @BindView(R.id.qu_vp)
    ViewPager quVp;
    @BindView(R.id.ques_index_title)
    TextView quesIndexTitle;
    @BindView(R.id.ques_index)
    TextView quesIndex;
    @BindView(R.id.ques_rank)
    TextView quesRank;
    @BindView(R.id.ques_createtime)
    TextView quesCreatetime;
    @BindView(R.id.ques_address)
    TextView quesAddress;
    @BindView(R.id.list_question_list)
    ListView listQuestionList;
    //    @BindView(R.id.ptrlayout_question)
//    FrameLayout ptrlayoutQuestion;
    @BindView(R.id.data_time_tv)
    TextView dataTimeTv;
    @BindView(R.id.qu_bt_ll)
    LinearLayout quBtLl;
    @BindView(R.id.ques_img)
    ImageView quesImg;
    @BindView(R.id.qu_ll_top)
    LinearLayout quLlTop;
    @BindView(R.id.qu_ll_cneter)
    View quLlCneter;
    @BindView(R.id.ques_value_tv1)
    TextView quesValueTv1;
    @BindView(R.id.ques_value_title1)
    TextView quesValueTitle1;
    @BindView(R.id.ques_value_tv2)
    TextView quesValueTv2;
    @BindView(R.id.ques_value_title2)
    TextView quesValueTitle2;
    @BindView(R.id.ques_value_tv3)
    TextView quesValueTv3;
    @BindView(R.id.ques_value_title3)
    TextView quesValueTitle3;
    @BindView(R.id.ques_title)
    TextView quesTitle;
    private String questionId;
    private static final String TAG = "QuestionsActivity";
    //打电话
    private String[] perms = {Manifest.permission.CALL_PHONE};
    private final int PERMS_REQUEST_CODE = 200;
    private String staffTel;
    Opsbean.ResultBean.QuestionListBean listbean;
    List<Fragment> listFragment;
    private Questions_discussFragment discussFragment;
    private Questions_progressFragment progressFragment;
    public String name = "暂无";
    String[] array = {"维修进度", "问题讨论"};
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);

        toolbarText.setText("问题详情");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listFragment = new ArrayList<>();
        //问题讨论
        discussFragment = new Questions_discussFragment();
        //问题进度
        progressFragment = new Questions_progressFragment();
        Intent intent = getIntent();
        questionId = intent.getStringExtra("questionId");
        Log.i(TAG, "initView: " + questionId);
        mPresenter.getQuestion(questionId);
        //联系人
        quLinkman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {//Android 6.0以上版本需要获取权限
                    requestPermissions(perms, PERMS_REQUEST_CODE);//请求权限
                } else {
                    callPhone();
                }
            }
        });
        //回复
        quReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mPresenter.getDiscuss(questionId,"");
                View inflate = View.inflate(QuestionsActivity.this, R.layout.dialog_schedule, null);
                final EditText editText = inflate.findViewById(R.id.message);
                TextView staffname = inflate.findViewById(R.id.question_dialog_name);
                Button confirm = inflate.findViewById(R.id.question_btn_confirm);
                Button cancel = inflate.findViewById(R.id.question_btn_cancel);
                Loginbean userInfo = MyApplication.getUserInfo();
               final MyPressDialog builder = new MyPressDialog(QuestionsActivity.this,0,130,inflate,R.style.myCorDialog);
//                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this,R.style.myCorDialog);
                builder.show();
                if (!name.equals("暂无")) {
                    staffname.setText(name);
                } else {
                    ToastUtil.showShort(QuestionsActivity.this, "暂无运维负责人信息");
                    return;
                }
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = editText.getText().toString();
                        if (!s.isEmpty()) {
                            mPresenter.getDiscuss(questionId, s);
                            builder.dismiss();
                        } else {
                            ToastUtil.showShort(QuestionsActivity.this, "回复信息不能为空！");
                        }

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });
//                Log.i(TAG, "onClick: "+userInfo.g);
//                ToastUtil.showShort(QuestionsActivity.this, "回复");

            }
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new QuestionsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_questions;
    }

    public void setTabData(String[] array) {
        listFragment.add(progressFragment);
        listFragment.add(discussFragment);
        MyQurestionTabAdapter adapter = new MyQurestionTabAdapter(getSupportFragmentManager(), array, listFragment);
        quVp.setAdapter(adapter);
        quTab.setupWithViewPager(quVp);
        //设置可以滑动
        quTab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void setQuestionData(Opsbean bean) {
        if (bean.getResult().getQuestionList().size() > 0) {
            getImageSelector(bean);
            getValueType(bean);
            listbean = bean.getResult().getQuestionList().get(0);
            Log.i(TAG, "setQuestionData: " + listbean.getEquipmentName());
            quesIndexTitle.setText(listbean.getEquipmentName());
            staffTel = listbean.getMaintUser().getStaffTel();
            quesIndex.setText(listbean.getQuestionIndex());
            quesCreatetime.setText(listbean.getCreateTime());
            String substring = StringUtil.substring(listbean.getDescription(), 1, listbean.getDescription().length() - 1);
            quesAddress.setText(substring);
            quesRank.setText(FaultType.getInfo(listbean.getFaultType()) + "");
            initRecycleView(listbean);
            discussFragment.setQuestionId(questionId);
            progressFragment.setQuestionId(questionId);
            setTabData(array);
//            setQuTabData();
//            if(listbean.getAdviceList().size()>0) {
            name = listbean.getMaintUser().getStaffName();
//            }else{
//                Log.i(TAG, "setQuestionData: "+"当前问题详情无运维人员信息");
//            }
        } else {
            Log.i(TAG, "setQuestionData: " + "无数据");
        }
    }


    @Override
    public void setQuestionDiscussData(Discussbean discussbean) {
        if (discussbean.getErrorCode() == 0) {
            //这里调用fragment里的刷新方法
            discussFragment.initData();
//            ToastUtil.showShort(QuestionsActivity.this, "回复成功");
        } else {
            Log.i(TAG, "setQuestionDiscussData: 回复失败");
//            ToastUtil.showShort(QuestionsActivity.this, "回复失败");
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case PERMS_REQUEST_CODE:
                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storageAccepted) {
                    callPhone();
                } else {
                    Log.i("MainActivity", "没有权限操作这个请求");
                }
                break;

        }
    }

    //拨打电话
    private void callPhone() {
        //检查拨打电话权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + staffTel));
            startActivity(intent);
        }
    }

    public void initRecycleView(Opsbean.ResultBean.QuestionListBean listbean) {
//        listQuestionList.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this));
//        QuestionListAdapter adapter = new QuestionListAdapter(QuestionsActivity.this, listbean);
//        listQuestionList.setAdapter(adapter);
        quesTitle.setText(listbean.getTitle()+"");
        DescriptionListViewAdapter adapter = new DescriptionListViewAdapter(QuestionsActivity.this, listbean);
        listQuestionList.setAdapter(adapter);
        AppUtils.setListViewHeight(listQuestionList, 30);
    }

    public void getImageSelector(Opsbean opsbean) {
        if (opsbean.getResult().getQuestionList().get(0).getStatus() == 1) {
            switch (opsbean.getResult().getQuestionList().get(0).getPatrolType()) {
                //柜体
                case 1:
                    quesImg.setImageResource(R.mipmap.ops_cabinet_processed);
                    break;
                //温度
                case 2:
                    quesImg.setImageResource(R.mipmap.ops_temperature_processed);
                    break;
                //普通
                case 3:
                    quesImg.setImageResource(R.mipmap.ops_common_processed);
                    break;
                //电表
                case 4:
                    quesImg.setImageResource(R.mipmap.ops_electricity_processed);
                    break;
                //变压器
                case 5:
                    quesImg.setImageResource(R.mipmap.ops_temperature_processed);
                    break;
                //卫生
                case 6:
                    quesImg.setImageResource(R.mipmap.ops_sanitation_processed);
                    break;
            }
        }
        //有问题的  未处理过的
        if (opsbean.getResult().getQuestionList().get(0).getStatus() == 0) {
            switch (opsbean.getResult().getQuestionList().get(0).getPatrolType()) {
                //柜体
                case 1:
                    quesImg.setImageResource(R.mipmap.ops_cabinet_pending);
                    break;
                //温度
                case 2:
                    quesImg.setImageResource(R.mipmap.ops_temperature_pending);
                    break;
                //普通
                case 3:
                    quesImg.setImageResource(R.mipmap.ops_common_pending);
                    break;
                //电表
                case 4:
                    quesImg.setImageResource(R.mipmap.ops_electricity_pending);
                    break;
                //变压器
                case 5:
                    quesImg.setImageResource(R.mipmap.ops_temperature_pending);
                    break;
                //卫生
                case 6:
                    quesImg.setImageResource(R.mipmap.ops_sanitation_pending);
                    break;

            }
        }

    }

    public void getValueType(Opsbean opsbean) {
        switch (opsbean.getResult().getQuestionList().get(0).getStatus()) {
            case 0:
                quesValueTv1.setVisibility(View.GONE);
                quesValueTv2.setVisibility(View.GONE);
                quesValueTv3.setVisibility(View.GONE);
                quesValueTitle1.setVisibility(View.GONE);
                quesValueTitle2.setVisibility(View.GONE);
                quesValueTitle3.setVisibility(View.GONE);
                break;
            case 1:
                quesValueTv1.setVisibility(View.GONE);
                quesValueTv2.setVisibility(View.GONE);
                quesValueTv3.setVisibility(View.GONE);
                quesValueTitle1.setVisibility(View.GONE);
                quesValueTitle2.setVisibility(View.GONE);
                quesValueTitle3.setVisibility(View.GONE);
                break;
            case 2:
                quesValueTv1.setText("温度");
                quesValueTv2.setVisibility(View.GONE);
                quesValueTv3.setVisibility(View.GONE);
                quesValueTitle1.setText(opsbean.getResult().getQuestionList().get(0).getValue1() + "℃");
                quesValueTitle2.setVisibility(View.GONE);
                quesValueTitle3.setVisibility(View.GONE);
                break;
            case 3:
                quesValueTv1.setText("A");
                quesValueTv2.setText("B");
                quesValueTv3.setText("C");
                quesValueTitle1.setText(opsbean.getResult().getQuestionList().get(0).getValue1() + "℃");
                quesValueTitle2.setText(opsbean.getResult().getQuestionList().get(0).getValue2() + "℃");
                quesValueTitle3.setText(opsbean.getResult().getQuestionList().get(0).getValue3() + "℃");
                break;
            case 4:
                quesValueTv1.setText("电压");
                quesValueTv2.setText("电压");
                quesValueTv3.setText("电压");
                quesValueTitle1.setText(opsbean.getResult().getQuestionList().get(0).getValue1() + "V");
                quesValueTitle2.setText(opsbean.getResult().getQuestionList().get(0).getValue2() + "V");
                quesValueTitle3.setText(opsbean.getResult().getQuestionList().get(0).getValue3() + "V");
                break;
            case 5:
                quesValueTv1.setText("电流");
                quesValueTv2.setText("电流");
                quesValueTv3.setText("电流");
                quesValueTitle1.setText(opsbean.getResult().getQuestionList().get(0).getValue1() + "A");
                quesValueTitle2.setText(opsbean.getResult().getQuestionList().get(0).getValue2() + "A");
                quesValueTitle3.setText(opsbean.getResult().getQuestionList().get(0).getValue3() + "A");
                break;
            case 7:
                quesValueTv1.setVisibility(View.GONE);
                quesValueTv2.setVisibility(View.GONE);
                quesValueTv3.setVisibility(View.GONE);
                quesValueTitle1.setVisibility(View.GONE);
                quesValueTitle2.setVisibility(View.GONE);
                quesValueTitle3.setVisibility(View.GONE);
                break;
            case 8:
                quesValueTv1.setText("上月有功功率");
                quesValueTv2.setText("本期有功功率");
                quesValueTv3.setVisibility(View.GONE);
                quesValueTitle1.setText(opsbean.getResult().getQuestionList().get(0).getValue1() + "kw");
                quesValueTitle2.setText(opsbean.getResult().getQuestionList().get(0).getValue2() + "kw");
                quesValueTitle3.setVisibility(View.GONE);
                break;

        }
    }
}
