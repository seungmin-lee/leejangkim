package com.example.seungmin1216.team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends AppCompatActivity {

    @BindView(R.id.btn_close_about_us) Button btn_close_about_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_close_about_us)
    public void onClickBtnCloseAboutUs(View view){
        finish();
    }
}
