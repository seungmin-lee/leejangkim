package com.example.seungmin1216.team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoChangeActivity extends AppCompatActivity {

    @BindView(R.id.txt_id) TextView txt_id;
    @BindView(R.id.et_name) EditText et_name;
    @BindView(R.id.et_user_phone) EditText et_user_phone;
    @BindView(R.id.et_helper_phone) EditText et_helper_phone;
    @BindView(R.id.et_etc) EditText et_etc;
    @BindView(R.id.et_age) EditText et_age;
    @BindView(R.id.btn_change_pw) Button btn_change_pw;
    @BindView(R.id.btn_change_info) Button btn_change_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_change_pw)
    public void onClickBtnChangePw(View view) {

    }

    @OnClick(R.id.btn_change_info)
    public void onClickBtnChangeInfo(View view) {

    }



}
