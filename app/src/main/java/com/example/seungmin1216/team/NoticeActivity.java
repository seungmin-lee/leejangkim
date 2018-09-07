package com.example.seungmin1216.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.seungmin1216.team.adapter.NoticeAdapter;
import com.example.seungmin1216.team.data.Notice;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {

    @BindView(R.id.btn_close_notice)
    Button btn_close_notice;
    @BindView(R.id.lv_notice)
    ListView lv_notice;
    @BindView(R.id.btn_create_notice)
    Button btn_create_notice;

    NoticeAdapter noticeAdapter;

    ArrayList<Notice> itmes = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);




        getInfoNotice();




        String id = SaveMember.getInstance().getMember().getMem_mid();
        if(id.equals("admin_ljk")){
            btn_create_notice.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.btn_create_notice)
    public void onClickBtnCreateNotice(View view){
        Intent intent = new Intent(NoticeActivity.this,NoticeCreateActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_close_notice)
    public void onClickBtnCloseNotice(View view){
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();

        refreshList();
    }

    public void refreshList() {
        getInfoNotice();

        noticeAdapter = new NoticeAdapter(itmes);
        lv_notice.setAdapter(noticeAdapter);
    }

    public void getInfoNotice(){

        Call<ArrayList<Notice>> observ = RetrofitService.getInstance().getRetrofitRequest().selectNotice("ok");
        observ.enqueue(new Callback<ArrayList<Notice>>() {
            @Override
            public void onResponse(Call<ArrayList<Notice>> call, Response<ArrayList<Notice>> response) {
                itmes = response.body();
                noticeAdapter = new NoticeAdapter(itmes);
                lv_notice.setAdapter(noticeAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Notice>> call, Throwable t) {

            }
        });

    }


}
