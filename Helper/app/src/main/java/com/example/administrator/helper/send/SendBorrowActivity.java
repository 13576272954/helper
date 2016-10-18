package com.example.administrator.helper.send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helper.MyApplication;
import com.example.administrator.helper.R;
import com.example.administrator.helper.entity.Coupon;
import com.example.administrator.helper.entity.OrderStaus;
import com.example.administrator.helper.entity.Orders;
import com.example.administrator.helper.entity.Task;
import com.example.administrator.helper.entity.TaskType;
import com.example.administrator.helper.entity.User;
import com.example.administrator.helper.utils.UrlUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 发布
 * 借用
 */
public class SendBorrowActivity extends AppCompatActivity {
    @InjectView(R.id.et_xuqiu_borrow)
    EditText etXuqiuBorrow;
    @InjectView(R.id.tv_show_time_borrow1)
    TextView tvShowTimeBorrow1;
    @InjectView(R.id.tv_show_time_borrow2)
    TextView tvShowTimeBorrow2;
    @InjectView(R.id.et_phone_borrow)
    EditText etPhoneBorrow;
    @InjectView(R.id.tv_buy_borrow)
    TextView tvBuyBorrow;
    @InjectView(R.id.rl_buy_borrow)
    RelativeLayout rlBuyBorrow;
    @InjectView(R.id.tv_youhuiquan_borrow)
    TextView tvYouhuiquanBorrow;
    @InjectView(R.id.rl_youhui_borrow)
    RelativeLayout rlYouhuiBorrow;
    @InjectView(R.id.et_money_borrow)
    EditText etMoneyBorrow;
    @InjectView(R.id.but_send_study_borrow)
    Button butSendStudyBorrow;
    @InjectView(R.id.tv_tixing_borrow)
    TextView tvTixingBorrow;

    //优惠券选择请求码
    public static final int REQUEST = 16;
    //优惠券
    Coupon coupon = null;

    //用户
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_borrow);
        ButterKnife.inject(this);
        user = ((MyApplication)getApplication()).getUser();
    }

    //请求返回界面回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            String couponStr = data.getStringExtra("coupon");
            Gson gson = new Gson();
            coupon = gson.fromJson(couponStr, Coupon.class);
        }

    }

    @OnClick({R.id.tv_show_time_borrow1, R.id.tv_show_time_borrow2, R.id.rl_buy_borrow, R.id.rl_youhui_borrow, R.id.but_send_study_borrow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show_time_borrow1:
                break;
            case R.id.tv_show_time_borrow2:
                break;
            case R.id.rl_buy_borrow:
                break;
            case R.id.rl_youhui_borrow:
                break;
            case R.id.but_send_study_borrow:
                //获取用户输入信息
                String xuqiu=null;//需求
                Timestamp creatTime = null;//开始时间
                Timestamp time=null;//任务要求时间
                String phone = null;//联系电话
                boolean buyway = true;//付款方式
                Integer money=null;//任务赏金

                //需求
                if (etXuqiuBorrow.getText().toString() == null) {
                    tvTixingBorrow.setText("请输入具体需求");
                    return;
                }else{
                    xuqiu=etXuqiuBorrow.getText().toString();
                }
                //借用时间
                if(tvShowTimeBorrow1.getText().toString()==null){
                    tvTixingBorrow.setText("请选择兼职开始时间");
                    return;
                }else{
                    String timeStr=tvShowTimeBorrow1.getText().toString();
                        creatTime=Timestamp.valueOf(timeStr);
                }
                //联系电话
                if (etPhoneBorrow.getText().toString()==null){
                    tvTixingBorrow.setText("请输入联系电话");
                    return;
                }else {
                    phone=etPhoneBorrow.getText().toString();
                }
                //归还时间
                String timeStr=tvShowTimeBorrow2.getText().toString();
                    time=Timestamp.valueOf(timeStr);
                //支付方式
                String buyWayStr=tvBuyBorrow.getText().toString();
                switch (buyWayStr){
                    case "支付宝":
                        buyway=true;
                        break;
                    case  "微信支付":
                        buyway=false;
                        break;
                }
                //赏金
                if (etMoneyBorrow.getText().toString()==null){
                    tvTixingBorrow.setText("请输入你预期的赏金");
                    return;
                }else {
                    money=Integer.parseInt(etMoneyBorrow.getText().toString());
                }
                /**
                 封装对象
                 */
                //任务
                Task task = new Task(user,creatTime,time,null,null,phone,new TaskType(3,"借用"),xuqiu,money,1);
                String taskJson = toJson(task);
                //订单
                Orders order = new Orders(null,task,coupon,money-coupon.getReduce(),buyway,new Timestamp(System.currentTimeMillis()),null,new OrderStaus(1,"待付款"),null);
                String orderJson = toJson(order);

                //网络访问
                String url = UrlUtils.MYURL + "SendServlet";
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("task", taskJson);
                params.addBodyParameter("order", orderJson);

                final Intent intent =new Intent(this,GoPayActivity.class);
                intent.putExtra("task",taskJson);
                intent.putExtra("order",orderJson);
                x.http().post(params, new Callback.CommonCallback<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        Toast.makeText(SendBorrowActivity.this, "恭喜您，发布成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(SendBorrowActivity.this, "抱歉，创建任务失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        Log.i("SendStudyActivity", "onFinished" + "网络访问完成");
                    }
                });

                break;
        }
    }

    public String toJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }
}
