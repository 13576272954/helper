package com.example.administrator.helper.send;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.administrator.helper.entity.bean.InsertOrderBean;
import com.example.administrator.helper.send.map.getMap;
import com.example.administrator.helper.utils.UrlUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SendStudyActivity extends AppCompatActivity {

    @InjectView(R.id.et_xuqiu)
    EditText etXuqiu;
    @InjectView(R.id.tv_renwudizhi)
    TextView tvRenwudizhi;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.tv_buy)
    TextView tvBuy;
    @InjectView(R.id.et_money)
    EditText etMoney;
    @InjectView(R.id.but_send_study)
    Button butSendStudy;
    @InjectView(R.id.rl_city)
    RelativeLayout rlCity;
    @InjectView(R.id.rl_map_all)
    RelativeLayout rlMapAll;
    @InjectView(R.id.rl_buy)
    RelativeLayout rlBuy;
    @InjectView(R.id.tv_show_time)
    TextView tvShowTime;
    @InjectView(R.id.tv_youhuiquan)
    TextView tvYouhuiquan;
    //优惠券选择请求码
    public static final int REQUEST = 11;
    //地址选择请求码
    public static final int MAP_REQUEST=12;
    //优惠券
    Coupon coupon = null;
    @InjectView(R.id.tv_tixing_study)
    TextView tvTixingStudy;

    //用户
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_study);
        ButterKnife.inject(this);

        user = ((MyApplication) getApplication()).getUser();
        if (coupon == null) {
            coupon = new Coupon(-1, null, 0.0, null, null, null);
        }

    }


    @OnClick(R.id.but_send_study)
    public void onClick() {
        //获取任务的详情
        //需求
        String xiuqiu = null;
        if (etXuqiu.getText().toString() == null || "".equals(etPhone.getText().toString())) {
            tvTixingStudy.setText("请输入具体需求");
            return;
        } else {
            xiuqiu = etXuqiu.getText().toString();
        }
        //时间
        Timestamp lastTime = null;
        if (tvShowTime.getText().toString() == null || "".equals(tvShowTime.getText().toString())) {
            tvTixingStudy.setText("请选择任务时限");
            return;
        } else {
            String timeStr = tvShowTime.getText().toString();
            lastTime = Timestamp.valueOf(timeStr);
        }
        //联系电话
        String phone = null;
        if (etPhone.getText().toString() == null || "".equals(etPhone.getText().toString())) {
            tvTixingStudy.setText("请输入联系电话");
            return;
        } else {
            phone = etPhone.getText().toString();
        }
        String makePlace = null;
        if (tvRenwudizhi.getText().toString() != null && !"".equals(tvRenwudizhi.getText().toString())) {
            Log.i("SendStudyActivity", "onClick:  我执行了");
            makePlace = tvRenwudizhi.getText().toString();//任务地址
        }
        Log.i("SendStudyActivity", "onClick:  makePlace:" + makePlace);

        String buyWay = tvBuy.getText().toString();//支付方式
        //赏金
        Integer money = null;
        if (etMoney.getText().toString() == null && "".equals(etPhone.getText().toString())) {
            tvTixingStudy.setText("请输入你预期的赏金");
            return;
        } else {
            money = Integer.parseInt(etMoney.getText().toString());
        }
        final Double price = money - coupon.getReduce();

        boolean buyway = true;
        switch (buyWay) {
            case "支付宝":
                buyway = true;
                break;
            case "微信支付":
                buyway = false;
                break;
        }
        /*
        将赏金、优惠券、支付方式封装成Orders类对象
         */
        Task task = new Task(user, null, lastTime, makePlace, null, phone, new TaskType(1, "学习类"), xiuqiu, money, 1);
        final String taskJson = toJson(task);
        Log.i("SendStudyActivity", "onClick:  " + makePlace);
        Log.i("SendStudyActivity", "onClick:  " + task);
        Orders order = new Orders(null, task, coupon, price, buyway, new Timestamp(System.currentTimeMillis()), null, new OrderStaus(1, "待付款"), null);
        final String orderJson = toJson(order);
        InsertOrderBean insertOrderBean = new InsertOrderBean(coupon.getId(), price);
        String insertOrderBeanJson = toJson(insertOrderBean);

        //网络访问
        String url = UrlUtils.MYURL + "SendServlet";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("task", taskJson);
        params.addBodyParameter("insertOrderBean", insertOrderBeanJson);


        Log.i("SendStudyActivity", "onClick:  开始发布   " + url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("SendStudyActivity", "onSuccess:  发布成功");
                Toast.makeText(SendStudyActivity.this, "恭喜您，发布成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                final Intent intent = new Intent(SendStudyActivity.this, GoPayActivity.class);
                intent.putExtra("task", taskJson);
                intent.putExtra("order", orderJson);
                startActivity(intent);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(SendStudyActivity.this, "抱歉，创建任务失败", Toast.LENGTH_SHORT).show();
                Log.i("SendStudyActivity", "onError:  " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.i("SendStudyActivity", "onFinished" + "网络访问完成");
            }
        });
    }

    @OnClick({R.id.rl_city, R.id.rl_map_all, R.id.rl_buy,R.id.rl_youhui})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_city:
                //选择时间
                break;
            case R.id.rl_map_all:
                //选择地址
                Intent intent =new Intent(this,getMap.class);
                startActivityForResult(intent,MAP_REQUEST);
                break;
            case R.id.rl_buy:
                //支付方式
                CharSequence[] item = {"微信支付","支付宝"};
                new AlertDialog.Builder(this).setTitle("选择支付方式")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        tvBuy.setText("微信支付");
                                        break;
                                    case 1:
                                        tvBuy.setText("支付宝");
                                        break;
                                }
                            }
                        }).create().show();
                break;
            case R.id.rl_youhui:
                //优惠券
                break;
            default:
                break;
        }
    }

    //请求返回界面回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            //优惠券返回
            String couponStr = data.getStringExtra("coupon");
            Gson gson = new Gson();
            coupon = gson.fromJson(couponStr, Coupon.class);
            if (coupon == null) {
                coupon = new Coupon(-1, null, 0.0, null, null, null);
            }
        }else if (requestCode==MAP_REQUEST && resultCode ==RESULT_OK){
            //地图界面返回
            String address = data.getStringExtra("address");
            tvRenwudizhi.setText(address);
        }

    }

    public String toJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }


}
