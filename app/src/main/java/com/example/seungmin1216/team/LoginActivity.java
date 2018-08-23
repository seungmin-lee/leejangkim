package com.example.seungmin1216.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_join) Button btn_join;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


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
