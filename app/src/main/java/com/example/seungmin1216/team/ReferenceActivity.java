package com.example.seungmin1216.team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReferenceActivity extends AppCompatActivity {

    @BindView(R.id.btn_close_reference) Button btn_close_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_close_reference)
    public void onClickBtnCloseReference(View view){
        finish();

    }
}
