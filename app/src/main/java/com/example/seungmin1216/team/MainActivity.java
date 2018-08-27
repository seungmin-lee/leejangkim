package com.example.seungmin1216.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.example.seungmin1216.team.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_view) ViewPager main_view;
    ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.subway_btn) Button subway_btn;
    @BindView(R.id.bus_btn) Button bus_btn;
    @BindView(R.id.taxi_btn) Button taxi_btn;
    @BindView(R.id.btn_setting) Button btn_setting;
    @BindView(R.id.bookmark_btn) Button bookmark_btn;
    @BindView(R.id.btn_apply_list) Button btn_apply_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        main_view.setAdapter(viewPagerAdapter);





        main_view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
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


}
