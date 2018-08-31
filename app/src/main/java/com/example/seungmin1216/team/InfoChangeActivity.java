package com.example.seungmin1216.team;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
    @BindView(R.id.infochange_main_view) ScrollView infochange_main_view;

    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);
        ButterKnife.bind(InfoChangeActivity.this);

        infochange_main_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_name.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(et_user_phone.getWindowToken(),0);
                imm.hideSoftInputFromWindow(et_helper_phone.getWindowToken(),0);
                imm.hideSoftInputFromWindow(et_etc.getWindowToken(),0);
                imm.hideSoftInputFromWindow(et_age.getWindowToken(),0);
            }
        });


        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @OnClick(R.id.btn_change_pw)
    public void onClickBtnChangePw(View view) {
        Intent intent = new Intent(InfoChangeActivity.this,PwChangeActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.btn_change_info)
    public void onClickBtnChangeInfo(View view) {

    }



}
