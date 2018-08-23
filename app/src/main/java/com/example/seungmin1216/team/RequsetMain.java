package com.example.seungmin1216.team;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seungmin1216.team.adapter.RequestAdapter2;
import com.example.seungmin1216.team.adapter.ViewPagerAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RequsetMain extends AppCompatActivity {

    @BindView(R.id.request_view) ViewPager request_view;
    @BindView(R.id.bus_requset) Button bus_request;
    @BindView(R.id.subway_requset) Button subway_request;
    @BindView(R.id.taxi_requset) Button taxi_request;
    RequestAdapter2 requestAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requset_main);
        ButterKnife.bind(this);

        requestAdapter2 = new RequestAdapter2(getSupportFragmentManager());
        request_view.setAdapter(requestAdapter2);

        request_view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    RequestSubway(request_view);
                } else if (position == 1) {
                    RequestBus(request_view);
                } else if (position == 2) {
                    RequestTaxi(request_view);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

        @OnClick(R.id.subway_requset)
        public void RequestSubway(View view){
            request_view.setCurrentItem(0);
            subway_request.setBackgroundResource(R.drawable.click_edittext);
            taxi_request.setBackgroundResource(R.drawable.nonclick_edittext);
            bus_request.setBackgroundResource(R.drawable.nonclick_edittext);
        }

        @OnClick(R.id.bus_requset)
            public void RequestBus(View view){
                request_view.setCurrentItem(1);
                subway_request.setBackgroundResource(R.drawable.nonclick_edittext);
                taxi_request.setBackgroundResource(R.drawable.nonclick_edittext);
                bus_request.setBackgroundResource(R.drawable.click_edittext);
        }


        @OnClick(R.id.taxi_requset)
            public void RequestTaxi(View view){
                request_view.setCurrentItem(2);
                subway_request.setBackgroundResource(R.drawable.nonclick_edittext);
                taxi_request.setBackgroundResource(R.drawable.click_edittext);
                bus_request.setBackgroundResource(R.drawable.nonclick_edittext);
        }

}
