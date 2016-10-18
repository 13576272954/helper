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

public class SendSellActivity extends AppCompatActivity {

    @InjectView(R.id.et_xuqiu_sell)
    EditText etXuqiuSell;
    @InjectView(R.id.tv_show_time_sell)
    TextView tvShowTimeSell;
    @InjectView(R.id.et_phone_sell)
    EditText etPhoneSell;
    @InjectView(R.id.tv_buy_sell)
    TextView tvBuySell;
    @InjectView(R.id.rl_buy_sell)
    RelativeLayout rlBuySell;
    @InjectView(R.id.tv_youhuiquan_sell)
    TextView tvYouhuiquanSell;
    @InjectView(R.id.rl_youhui_sell)
    RelativeLayout rlYouhuiSell;
    @InjectView(R.id.et_money_sell)
    EditText etMoneySell;
    @InjectView(R.id.but_send_study_sell)
    Button butSendStudySell;
    @InjectView(R.id.tv_tixing_sell)
    TextView tvTixingSell;

    //优惠券选择请求码
    public static final int REQUEST = 12;
    //优惠券
    Coupon coupon =null;

    //用户
    User user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sell);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        String userJson = intent.getStringExtra("user");
        Gson gson = new Gson();
        user=gson.fromJson(userJson,User.class);

    }

    //请求返回界面回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST&&resultCode==RESULT_OK){
            String couponStr = data.getStringExtra("coupon");
            Gson gson = new Gson();
            coupon=gson.fromJson(couponStr,Coupon.class);
        }

    }

    @OnClick({R.id.tv_show_time_sell, R.id.rl_buy_sell, R.id.rl_youhui_sell, R.id.but_send_study_sell})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show_time_sell:
                break;
            case R.id.rl_buy_sell:
                break;
            case R.id.rl_youhui_sell:
                break;
            case R.id.but_send_study_sell:
                String xuqiu=null;//需求
                Timestamp creatTime = new Timestamp(System.currentTimeMillis());//创建日期
                Timestamp time=null;//任务要求时间
                String phone=null;//联系电话
                boolean buyway=true;//付款方式
                Integer money=null;//任务赏金
                //获取用户输入的信息
                String buyWayStr=tvBuySell.getText().toString();
                //需求
                if (etXuqiuSell.getText().toString() == null) {
                    tvTixingSell.setText("请输入具体需求");
                    return;
                }else{
                    xuqiu=etXuqiuSell.getText().toString();
                }
                //时间
                if (tvShowTimeSell.getText().toString()==null){
                    tvTixingSell.setText("请选择任务时限");
                    return;
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String timeStr=tvShowTimeSell.getText().toString();
                        time=Timestamp.valueOf(timeStr);
                }
                //联系电话
                if (etPhoneSell.getText().toString()==null){
                    tvTixingSell.setText("请输入联系电话");
                    return;
                }else {
                    phone=etPhoneSell.getText().toString();
                }
                //支付方式
                switch (buyWayStr) {
                    case "支付宝":
                        buyway = true;
                        break;
                    case "微信支付":
                        buyway = false;
                        break;
                }
                /**
                 * 优惠券
                 */
                //赏金
                if (etMoneySell.getText().toString()==null){
                    tvTixingSell.setText("请输入你预期的赏金");
                    return;
                }else {
                    money=Integer.parseInt(etMoneySell.getText().toString());
                }
                /**
                封装对象
                 */
                //任务
                Task task = new Task(user,null,time,null,null,phone,new TaskType(5,"销售"),xuqiu,money,1);
                String taskJson = toJson(task);
                //订单
                Orders order = new Orders(null,task,coupon,money-coupon.getReduce(),buyway,creatTime,time,new OrderStaus(1,"待付款"),null);
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
                        Toast.makeText(SendSellActivity.this, "恭喜您，发布成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(SendSellActivity.this, "抱歉，创建任务失败", Toast.LENGTH_SHORT).show();
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
