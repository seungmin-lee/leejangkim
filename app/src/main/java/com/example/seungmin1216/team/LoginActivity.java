package com.example.seungmin1216.team;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_join) Button btn_join;
    @BindView(R.id.mainLayout) RelativeLayout mainLayout;
    @BindView(R.id.et_user_id) EditText et_user_id;
    @BindView(R.id.et_user_pw) EditText et_user_pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_user_id.getWindowToken(),0);
            }
        });


    }

    @OnClick(R.id.btn_login)
    public void onClickBtnLogin(View view){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_join)
    public void onClickBtnJoin(View view){
        Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
        startActivity(intent);
    }




}
