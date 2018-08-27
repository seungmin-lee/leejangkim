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

public class MypageActivity extends AppCompatActivity {

    @BindView(R.id.txt_change_info) TextView txt_change_info;
    @BindView(R.id.txt_logout) TextView txt_logout;
    @BindView(R.id.txt_notice2) TextView txt_notice2;
    @BindView(R.id.txt_direct_ques) TextView txt_direct_ques;
    @BindView(R.id.txt_reference) TextView txt_reference;
    @BindView(R.id.txt_info_us) TextView txt_info_us;
    @BindView(R.id.btn_close) Button btn_close;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.txt_change_info)
    public void onClickTxtName(View view) {
        Intent intent = new Intent(MypageActivity.this,InfoChangeActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.txt_logout)
    public void onClickTxtLogout(View view) {

    }

    @OnClick(R.id.txt_notice2)
    public void onClickTxtNotice2(View view) {

    }

    @OnClick(R.id.txt_direct_ques)
    public void onClickTxtDirectQues(View view) {

    }

    @OnClick(R.id.txt_reference)
    public void onClickTxtReference(View view) {

    }

    @OnClick(R.id.txt_info_us)
    public void onClickTxtInfoUs(View view) {

    }

    @OnClick(R.id.btn_close)
    public void onClickBtnClose(View view){
        finish();
    }

}
