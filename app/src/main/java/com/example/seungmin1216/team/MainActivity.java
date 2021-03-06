package com.example.seungmin1216.team;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.seungmin1216.team.adapter.ViewPagerAdapter;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.event.BookmarkEvent;
import com.example.seungmin1216.team.event.BusBookmarkEvent;
import com.example.seungmin1216.team.event.SubwayBookmarkEvent;
import com.example.seungmin1216.team.event.TaxiplaceEvent;
import com.example.seungmin1216.team.retrofit.RetrofitService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_view) ViewPager main_view;
    ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.subway_btn) Button subway_btn;
    @BindView(R.id.bus_btn) Button bus_btn;
    @BindView(R.id.taxi_btn) Button taxi_btn;
    @BindView(R.id.btn_setting) Button btn_setting;
    @BindView(R.id.bookmark_btn) Button bookmark_btn;
    @BindView(R.id.btn_apply_list) Button btn_apply_list;
    @BindView(R.id.main_view2) LinearLayout main_view2;
    SharedPreferences pref;

    Bus bus = BusProvider.getInstance().getBus();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bus.register(this);
        pref = getSharedPreferences("key", MODE_PRIVATE);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        main_view.setAdapter(viewPagerAdapter);

        Long id = SaveMember.getInstance().getMember().getId();
        String key = FirebaseInstanceId.getInstance().getToken();

        Log.d("mainkk",id+ " "+ key);

        Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().updateKey(key,id.toString());

        observ.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("lsm","전송성공");

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });



        main_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        main_view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("ksj","pos2 : " + position);
                if (position == 0){
                    onClickBtnSubway(main_view);
                }else if (position == 1){
                    onClickBtnBus(main_view);
                }else if (position == 2){
                    onClickBtnTaxi(main_view);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Subscribe
    public void movBook(BookmarkEvent event){
        main_view.setCurrentItem(event.getKind());
        if(event.getKind() == 0){
            SubwayBookmarkEvent subwayBookmarkEvent = new SubwayBookmarkEvent(event.getStart(),event.getEnd());
            BusProvider.getInstance().getBus().post(subwayBookmarkEvent);
        }else if(event.getKind() == 1){
            BusBookmarkEvent busBookmarkEvent = new BusBookmarkEvent(event.getStart());
            BusProvider.getInstance().getBus().post(busBookmarkEvent);
        }
    }

    @OnClick(R.id.subway_btn)
    public void onClickBtnSubway(View view){
        main_view.setCurrentItem(0);
        subway_btn.setBackgroundResource(R.drawable.click_edittext);
        taxi_btn.setBackgroundResource(R.drawable.nonclick_edittext);
        bus_btn.setBackgroundResource(R.drawable.nonclick_edittext);
    }


    @OnClick(R.id.bus_btn)
    public void onClickBtnBus(View view){
        main_view.setCurrentItem(1);
        bus_btn.setBackgroundResource(R.drawable.click_edittext);
        taxi_btn.setBackgroundResource(R.drawable.nonclick_edittext);
        subway_btn.setBackgroundResource(R.drawable.nonclick_edittext);
    }

    @OnClick(R.id.taxi_btn)
    public void onClickBtnTaxi(View view){
        main_view.setCurrentItem(2);
        taxi_btn.setBackgroundResource(R.drawable.click_edittext);
        bus_btn.setBackgroundResource(R.drawable.nonclick_edittext);
        subway_btn.setBackgroundResource(R.drawable.nonclick_edittext);
    }

    @OnClick(R.id.btn_setting)
    public void onClickBtnSetting(View view){
        Intent intent = new Intent(MainActivity.this,MypageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bookmark_btn)
    public void onClickBtnBookMark(View view){
        Intent intent = new Intent(MainActivity.this,BookmarkActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_apply_list)
    public void onClickBtnApplyList(View view){
        Intent intent = new Intent(MainActivity.this,RequsetMain.class);
        startActivity(intent);
    }

    /*
    private long pressedTime;
    @Override
    public void onBackPressed() {
        if (pressedTime == 0) {
            Toast.makeText(MainActivity.this, "한번 더 누르면 로그아웃 됩니다.", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();

        } else {
            int sec = (int) (System.currentTimeMillis() - pressedTime);

            if (sec > 1500) {
                Toast.makeText(MainActivity.this, "한번 더 누르면 로그아웃 됩니다.", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor edit = pref.edit();
                edit.remove("ch").apply();
                super.onBackPressed();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        }
    }
    */

    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=1500){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"한번 더 누르면 로그아웃 됩니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<1500){
            SharedPreferences.Editor edit = pref.edit();
            edit.remove("ch").apply();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }


}
