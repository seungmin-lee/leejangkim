package com.example.seungmin1216.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeDetailActivity extends AppCompatActivity {

    @BindView(R.id.txt_notice_title) TextView txt_notice_title;
    @BindView(R.id.txt_notice_date) TextView txt_notice_date;
    @BindView(R.id.txt_notice_contents) TextView txt_notice_contents;
    @BindView(R.id.btn_close_notice_detail) Button btn_close_notice_detail;

    String title;
    String date;
    String contents;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        contents = intent.getStringExtra("contents");

        txt_notice_title.setText(title);
        txt_notice_date.setText(date);
        txt_notice_contents.setText(contents);

    }

    @OnClick(R.id.btn_close_notice_detail)
    public void onClickBtnCloseNoticeDetail(View view){
        finish();
    }
}
