package cn.tties.energy.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.model.result.Identity_Passbean;
import cn.tties.energy.presenter.Identity_PassPresenter;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.iview.IIdentity_PassView;

public class PasswordActivity extends BaseActivity<Identity_PassPresenter> implements IIdentity_PassView {
    private static final String TAG = "PasswordActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    EditText identityPassOldpwd;
    EditText identityPassNewpwd;
    Button identityPassConfirm;
    EditText identityPassNewpwd2;
    TextView identityPassCall;
    //打电话
    private String[] perms = {Manifest.permission.CALL_PHONE};
    private final int PERMS_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFindView();
        initView();
    }

    private void initFindView() {
        toolbarLl = findViewById(R.id.toolbar_ll);
        toolbarLeft = findViewById(R.id.toolbar_left);
        toolbarText = findViewById(R.id.toolbar_text);
        identityPassOldpwd = findViewById(R.id.identity_pass_oldpwd);
        identityPassNewpwd = findViewById(R.id.identity_pass_newpwd);
        identityPassConfirm = findViewById(R.id.identity_pass_confirm);
        identityPassNewpwd2 = findViewById(R.id.identity_pass_newpwd2);
        identityPassCall = findViewById(R.id.identity_pass_call);
    }

    private void initView() {

        toolbarText.setText("修改密码");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        identityPassConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getIdentity_Pass(PasswordActivity.this);
            }
        });
        identityPassCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {//Android 6.0以上版本需要获取权限
                    requestPermissions(perms, PERMS_REQUEST_CODE);//请求权限
                } else {
                    callPhone();
                }
            }
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new Identity_PassPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    public void setIdentity_PassData(Identity_Passbean bean) {
        if(bean.isFlag()==true){
            ToastUtil.showShort(PasswordActivity.this,"密码修改成功");
        }
    }

    @Override
    public String getOldPass() {
        return identityPassOldpwd.getText().toString();
    }

    @Override
    public String getNewPass() {
        return identityPassNewpwd.getText().toString();
    }

    @Override
    public String getNew2Pass() {
        return identityPassNewpwd2.getText().toString();
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case PERMS_REQUEST_CODE:
                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storageAccepted) {
                    callPhone();
                } else {
                    Log.i(TAG, "没有权限操作这个请求");
                }
                break;

        }
    }
    //拨打电话
    private void callPhone() {
        //检查拨打电话权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" +"4006682879"));
            startActivity(intent);
        }
    }
}
