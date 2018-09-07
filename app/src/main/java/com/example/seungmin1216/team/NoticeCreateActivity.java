package com.example.seungmin1216.team;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seungmin1216.team.retrofit.RetrofitService;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeCreateActivity extends AppCompatActivity {

    @BindView(R.id.et_notice_title) EditText et_notice_title;
    @BindView(R.id.et_notice_contents) EditText et_notice_contents;
    @BindView(R.id.btn_notice_create) Button btn_notice_create;
    @BindView(R.id.btn_close_create_notice) Button btn_close_create_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_create);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_notice_create)
    public void onClickBtnNoticeCreate(View view){
        String title = et_notice_title.getText().toString();
        String contents = et_notice_title.getText().toString();

        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH)+1;
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);

        String date;

        if(month <10 && day >=10){
            date = year.toString()+"-0"+month.toString()+"-"+day.toString();
        }else if(month >= 10 && day < 10){
            date = year.toString()+"-"+month.toString()+"-0"+day.toString();
        }else if(month < 10 && day < 10){
            date = year.toString()+"-0"+month.toString()+"-0"+day.toString();
        }else{
            date = year.toString()+"-"+month.toString()+"-"+day.toString();
        }

        Log.d("date",year+" "+month+" "+day);





        Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().insertNotice(title,contents,date);

        observ.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

        finish();
    }

    @OnClick(R.id.btn_close_create_notice)
    public void onClickBtnCloseCreateNotice(View view){
        finish();
    }

}
