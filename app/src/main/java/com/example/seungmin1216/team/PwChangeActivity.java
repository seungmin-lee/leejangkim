package com.example.seungmin1216.team;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class PwChangeActivity extends AppCompatActivity {

    @BindView(R.id.et_change_pw1) EditText et_change_pw1;
    @BindView(R.id.et_change_pw2) EditText et_change_pw2;
    @BindView(R.id.txt_error_pw1) TextView txt_error_pw1;
    @BindView(R.id.txt_error_pw2) TextView txt_error_pw2;
    @BindView(R.id.btn_close_pw_change) Button btn_close_pw_change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_change);
    }

    @OnClick(R.id.btn_close_pw_change)
    public void onClickBtnClosePwChange(View view){

    }
}
