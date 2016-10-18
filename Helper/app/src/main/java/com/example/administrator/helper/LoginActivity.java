package com.example.administrator.helper;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.helper.entity.User;
import com.example.administrator.helper.utils.UrlUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.iv_user)
    ImageView ivUser;
    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_psd)
    EditText etPsd;
    @InjectView(R.id.bt_login)
    Button btLogin;
    @InjectView(R.id.bt_forget)
    Button btForget;
    @InjectView(R.id.bt_creat)
    Button btCreat;
    @InjectView(R.id.bt_else)
    Button btElse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.bt_login, R.id.bt_forget, R.id.bt_creat, R.id.bt_else})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                Log.i("LoginActivity", "LoginActivity:onClick");
                String name = etName.getText().toString();
                String psd = etPsd.getText().toString();
                String url = UrlUtils.MYURL+"LoginServlet";
                RequestParams params = new RequestParams(url);
                params.addQueryStringParameter("userName",name);
                params.addQueryStringParameter("userPsd",psd);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("LoginActivity", "LoginActivity:onSuccess连入");
                        Gson gson=new Gson();
                        User user=gson.fromJson(result,User.class);
                        if (user!=null){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            MyApplication myApplication= (MyApplication) getApplication();
                            myApplication.setUser(user);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("LoginActivity", "LoginActivity:onError失败");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.i("LoginActivity", "LoginActivity:onCancelled 取消");
                    }

                    @Override
                    public void onFinished() {
                        Log.i("LoginActivity", "LoginActivity:onFinished 完成");
                    }
                });
                break;
            case R.id.bt_forget:
                break;
            case R.id.bt_creat:
                break;
            case R.id.bt_else:
                break;
        }
    }
}
