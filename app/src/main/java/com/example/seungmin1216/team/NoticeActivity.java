package com.example.seungmin1216.team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeActivity extends AppCompatActivity {

    @BindView(R.id.btn_close_notice) Button btn_close_notice;
    @BindView(R.id.lv_notice) ListView lv_notice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_close_notice)
    public void onClickBtnCloseNotice(View view){
        finish();
    }
}
