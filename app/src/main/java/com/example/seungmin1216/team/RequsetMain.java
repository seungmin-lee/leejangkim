package com.example.seungmin1216.team;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;


import com.example.seungmin1216.team.adapter.SubwayAdapter;
import com.example.seungmin1216.team.adapter.ViewPagerAdapter;
import com.example.seungmin1216.team.data.Request;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequsetMain extends AppCompatActivity {


    @BindView(R.id.lv_subRequest)ListView lv_subRequest;
    @BindView(R.id.btn_refresh) Button btn_refresh;
    @BindView(R.id.btn_close_bookmark) Button btn_close_bookmark;
    ArrayList<Request> items = new ArrayList<>();
    SubwayAdapter subwayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requset_main);
        ButterKnife.bind(this);

        SettingRequestList();


    }

    @OnClick(R.id.btn_refresh)
    public void onClickBtnRefresh(View view){
        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        btn_refresh.startAnimation(shake);
        SettingRequestList();

    }

    @OnClick(R.id.btn_close_bookmark)
    public void onClickClose(View view){
        finish();
    }

    public void SettingRequestList(){
        String mem_id = SaveMember.getInstance().getMember().getId().toString();

        Call<ArrayList<Request>> observ = RetrofitService.getInstance().getRetrofitRequest().returnRequest(mem_id);

        observ.enqueue(new Callback<ArrayList<Request>>() {
            @Override
            public void onResponse(Call<ArrayList<Request>> call, Response<ArrayList<Request>> response) {
                items = response.body();
                subwayAdapter = new SubwayAdapter(items);
                lv_subRequest.setAdapter(subwayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Request>> call, Throwable t) {

            }
        });
    }




}
