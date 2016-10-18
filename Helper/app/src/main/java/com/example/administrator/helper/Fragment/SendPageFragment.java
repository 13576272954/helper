package com.example.administrator.helper.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.helper.R;
import com.example.administrator.helper.send.SendBorrowActivity;
import com.example.administrator.helper.send.SendJobActivity;
import com.example.administrator.helper.send.SendLifeActivity;
import com.example.administrator.helper.send.SendOtherActivity;
import com.example.administrator.helper.send.SendSellActivity;
import com.example.administrator.helper.send.SendStudyActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bin on 2016/9/19.
 */
public class SendPageFragment extends Fragment {
    public static final int REQUECT = 1;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.but_xuexi)
    Button butXuexi;
    @InjectView(R.id.but_shenghuo)
    Button butShenghuo;
    @InjectView(R.id.but_jieyong)
    Button butJieyong;
    @InjectView(R.id.but_xiaoshou)
    Button butXiaoshou;
    @InjectView(R.id.but_jianzhi)
    Button butJianzhi;
    @InjectView(R.id.but_qita)
    Button butQita;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send_page, null);

        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.but_xuexi, R.id.but_shenghuo, R.id.but_jieyong, R.id.but_xiaoshou, R.id.but_jianzhi, R.id.but_qita})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_xuexi:
                Intent intent1 = new Intent(getActivity(),SendStudyActivity.class);
                getActivity().startActivityForResult(intent1,REQUECT);
                break;
            case R.id.but_shenghuo:
                Intent intent2 = new Intent(getActivity(),SendLifeActivity.class);
                getActivity().startActivityForResult(intent2,REQUECT);
                break;
            case R.id.but_jieyong:
                Intent intent3 = new Intent(getActivity(),SendBorrowActivity.class);
                getActivity().startActivityForResult(intent3,REQUECT);
                break;
            case R.id.but_xiaoshou:
                Intent intent4 = new Intent(getActivity(),SendSellActivity.class);
                getActivity().startActivityForResult(intent4,REQUECT);
                break;
            case R.id.but_jianzhi:
                Intent intent5 = new Intent(getActivity(),SendJobActivity.class);
                getActivity().startActivityForResult(intent5,REQUECT);
                break;
            case R.id.but_qita:
                Intent intent6 = new Intent(getActivity(),SendOtherActivity.class);
                getActivity().startActivityForResult(intent6,REQUECT);
                break;
        }
    }
}
