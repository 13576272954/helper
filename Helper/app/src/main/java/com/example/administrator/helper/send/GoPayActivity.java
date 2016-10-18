package com.example.administrator.helper.send;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helper.MyApplication;
import com.example.administrator.helper.R;
import com.example.administrator.helper.entity.Orders;
import com.example.administrator.helper.entity.Task;
import com.example.administrator.helper.entity.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;

public class GoPayActivity extends AppCompatActivity {

    @InjectView(R.id.gopay_prodmoney)
    TextView gopayProdmoney;
    @InjectView(R.id.gopay_youhuimoney)
    TextView gopayYouhuimoney;
    @InjectView(R.id.gopay_shifumoney)
    TextView gopayShifumoney;
    @InjectView(R.id.gopay_order_info)
    LinearLayout gopayOrderInfo;
    @InjectView(R.id.gopay_pay)
    Button gopayPay;

    Task task;
    User user;
    Orders order;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_pay);
        ButterKnife.inject(this);

        //支付时要先初始化
//        BP.init(this, "381e8949cca2851afa738898139f924a");

        user=((MyApplication)getApplication()).getUser();

        Intent intent =getIntent();
        String taskStr=intent.getStringExtra("task");
        String orderStr=intent.getStringExtra("order");
        Gson gson=new Gson();
        task=gson.fromJson(taskStr,Task.class);
        order=gson.fromJson(orderStr,Orders.class);

        Log.i("GoPayActivity", "onCreate:  "+order.getCoupon());
        gopayProdmoney.setText("￥"+(double)task.getMoney());
        gopayYouhuimoney.setText("￥"+order.getCoupon().getReduce());
        gopayShifumoney.setText("￥"+order.getPrice());
    }

    @OnClick(R.id.gopay_pay)
    public void onClick() {
        Log.i("GoPayActivity", "onClick:  点击了");
        showDialog("正在获取订单...");
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.bmob.app.sport",
                    "com.bmob.app.sport.wxapi.BmobActivity");
            intent.setComponent(cn);
            this.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        BP.pay("发布任务", task.getTaskDemand()+"  任务id"+task.getId(), order.getPrice(), order.getBuyWay(), new PListener() {
            @Override
            public void orderId(String s) {
                Log.i("GoPayActivity", "orderId:  订单id："+s);
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            @Override
            public void succeed() {
                Toast.makeText(MyApplication.getInstance(), "支付成功!", Toast.LENGTH_SHORT).show();
                Log.i("GoPayActivity", "succeed:  支付成功");
                Toast.makeText(MyApplication.getInstance(),"支付成功",Toast.LENGTH_SHORT).show();
                //修改状态
                hideDialog();
                finish();
            }

            @Override
            public void fail(int i, String s) {
                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (i == -3) {
                    Toast.makeText(
                            MyApplication.getInstance(),
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_SHORT).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText(MyApplication.getInstance(), "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
                hideDialog();
            }

            @Override
            public void unknow() {
                Toast.makeText(MyApplication.getInstance(), "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                hideDialog();
            }
        });
    }


    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    /**
     * 装插件
     * @param fileName
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
