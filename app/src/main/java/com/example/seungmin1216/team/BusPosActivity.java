package com.example.seungmin1216.team;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seungmin1216.team.adapter.BusStationAdapter;
import com.example.seungmin1216.team.data.BusPos;
import com.example.seungmin1216.team.data.BusStation;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusPosActivity extends AppCompatActivity {

    @BindView(R.id.txt_bus_type) TextView txt_bus_type;
    @BindView(R.id.txt_bus_num) TextView txt_bus_num;
    @BindView(R.id.txt_st_name) TextView txt_st_name;
    @BindView(R.id.txt_end_name) TextView txt_end_name;
    @BindView(R.id.lv_select_bus_list) ListView lv_select_bus_list;
    @BindView(R.id.btn_refresh) Button btn_refresh;
    @BindView(R.id.btn_bus_bookmark_nonclick) Button btn_bus_bookmark_nonclick;
    @BindView(R.id.btn_close) Button btn_close;


    BusStationAdapter busStationAdapter;

    ArrayList<BusStation> items = new ArrayList<>();
    ArrayList<BusPos> bus_items = new ArrayList<>();


    String id;
    String num;
    String type;
    String st_name;
    String end_name;
    String type_name;

    Integer lv_pos;
    Integer mCurrentX;
    Integer mCurrentY;
    Integer star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_pos);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        num = intent.getStringExtra("num");
        type = intent.getStringExtra("type");
        st_name = intent.getStringExtra("st");
        end_name = intent.getStringExtra("end");
        String mem_id = SaveMember.getInstance().getMember().getId().toString();

        Call<Integer> observ = RetrofitService.getInstance().getRetrofitRequest().chBookmark(num, mem_id);
        observ.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    star = response.body();
                    Log.d("kkkk", "onResponse: "+star);
                    if(star == 0){
                        btn_bus_bookmark_nonclick.setBackgroundResource(R.drawable.star2);
                    }else{
                        btn_bus_bookmark_nonclick.setBackgroundResource(R.drawable.star_gold);
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });




        busStationAdapter = new BusStationAdapter(items,bus_items);
        lv_select_bus_list.setAdapter(busStationAdapter);


        lv_select_bus_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lv_pos = firstVisibleItem;
                mCurrentX = view.getScrollX();
                mCurrentY = view.getScrollY();


            }


        });


        if(type.equals("1")){
            type_name="공항";
        }else if(type.equals("2")){
            type_name="마을";
        }else if(type.equals("3")){
            type_name="간선";
        }else if(type.equals("4")){
            type_name="지선";
        }else if(type.equals("5")){
            type_name="순환";
        }else if(type.equals("6")){
            type_name="광역";
        }else if(type.equals("7")){
            type_name="인천";
        }else if(type.equals("8")){
            type_name="경기";
        }

        txt_bus_type.setText(type_name+"버스");
        txt_bus_num.setText(num);
        txt_st_name.setText(st_name);
        txt_end_name.setText(end_name);


        settingList();
        settingBusList();



        Log.d("lsm", "onCreate: "+ id + " "+ num + " "+ type+" " + st_name + " "+ end_name);
        Log.d("lsm2","아이템 : " +bus_items.size());



    }



    private void settingList(){


        StrictMode.enableDefaults();


        boolean  inbusRouteNm = false,inseq = false, instation =false,
                instationNm = false, instationNo = false,inbeginTm = false, inlastTm =false,
                intrnstnid = false, ingpsX =false, ingpsY =false;


        String  busNum = null, busSeq = null,
                busStation = null, busStationNum = null,
                busStationNo = null, busBeginTm = null,
                busLastTm = null, busTrnstnId = null, busGpsX = null, busGpsY = null;


        try{
            URL url = new URL("http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?serviceKey=cOA%2FprnxwDncuq5BMNNhG%2FgPjmeJb2vWV1LxAsgBLqBA5YCW3UJ6eByldFugIO5UY5K%2FblpiWD3myvaIORi%2FIQ%3D%3D&busRouteId="+id); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("busRouteNm")){
                            inbusRouteNm = true;
                        }if(parser.getName().equals("seq")){
                            inseq = true;
                        }if(parser.getName().equals("station")){
                            instation = true;
                        }if(parser.getName().equals("stationNm")){
                            instationNm= true;
                        }if(parser.getName().equals("stationNo")){
                            instationNo = true;
                        }if(parser.getName().equals("beginTm")){
                            inbeginTm = true;
                        }if(parser.getName().equals("lastTm")){
                            inlastTm = true;
                        }if(parser.getName().equals("trnstnid")){
                            intrnstnid = true;
                        }if(parser.getName().equals("gpsX")){
                            ingpsX = true;
                        }if(parser.getName().equals("gpsY")){
                            ingpsY = true;
                        }

                        break;

                    case XmlPullParser.TEXT:
                      if(inbusRouteNm){
                            busNum = parser.getText();
                            inbusRouteNm = false;
                        }if(inseq){
                            busSeq = parser.getText();
                            inseq = false;
                        }if(instation){
                            busStation = parser.getText();
                            instation = false;
                        }if(instationNm){
                            busStationNum = parser.getText();
                            instationNm = false;
                        }if(instationNo){
                            busStationNo = parser.getText();
                            instationNo = false;
                        }if(inbeginTm){
                            busBeginTm = parser.getText();
                            inbeginTm = false;
                        }if(inlastTm){
                            busLastTm = parser.getText();
                            inlastTm = false;
                        }if(intrnstnid){
                            busTrnstnId = parser.getText();
                            intrnstnid = false;
                        }if(ingpsX){
                            busGpsX = parser.getText();
                            ingpsX = false;
                        }if(ingpsY){
                            busGpsY = parser.getText();
                            ingpsY = false;
                        }


                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("itemList")){
                            BusStation station= new BusStation(busNum,busSeq,busStation,busStationNum,busStationNo,busBeginTm,busLastTm,busTrnstnId,busGpsX,busGpsY);
                            items.add(station);

                        }
                        break;
                }


                parserEvent = parser.next();

            }
        } catch(Exception e){
            Log.d("lsm","error");
        }





    }


    private void settingBusList(){
        StrictMode.enableDefaults();

        boolean inSectOrd = false, inBusGpsX=false, inBusGpsY=false,
                inBusPlainNo=false, inBusType =false;

        String busSectOrd = null, busBusGpsX =null, busBusGpsY=null,
                busBusPlainNo =null, busBusType=null;

        try{
            URL url = new URL("http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey=cOA%2FprnxwDncuq5BMNNhG%2FgPjmeJb2vWV1LxAsgBLqBA5YCW3UJ6eByldFugIO5UY5K%2FblpiWD3myvaIORi%2FIQ%3D%3D&busRouteId="+id); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("sectOrd")){
                            inSectOrd = true;
                        }if(parser.getName().equals("gpsX")){
                            inBusGpsX = true;
                        }if(parser.getName().equals("gpsY")){
                            inBusGpsY = true;
                        }if(parser.getName().equals("plainNo")){
                            inBusPlainNo = true;
                        }if(parser.getName().equals("busType")){
                            inBusType = true;
                        }

                        break;

                    case XmlPullParser.TEXT:
                        if(inSectOrd){
                            busSectOrd = parser.getText();
                            inSectOrd = false;
                        }if(inBusGpsX){
                            busBusGpsX = parser.getText();
                            inBusGpsX = false;
                        }if(inBusGpsY){
                            busBusGpsY = parser.getText();
                            inBusGpsY = false;
                        }if(inBusPlainNo){
                            busBusPlainNo = parser.getText();
                            inBusPlainNo = false;
                        }if(inBusType){
                            busBusType = parser.getText();
                            inBusType = false;
                        }





                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("itemList")){
                            BusPos name= new BusPos(busSectOrd,busBusGpsX,busBusGpsY,busBusPlainNo,busBusType);
                            bus_items.add(name);
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            Log.d("lsm","error");
        }

        for(int i =0;i<bus_items.size();i++){
            Log.d("lsm22",bus_items.get(i).getSectOrd());
        }

    }

    @OnClick(R.id.btn_refresh)
    public void onClickBtnRefresh(View view){

        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        btn_refresh.startAnimation(shake);
        bus_items.clear();
        settingBusList();
        Log.d("lsm","affaaf");
        lv_select_bus_list.setAdapter(busStationAdapter);
        lv_select_bus_list.setSelection(lv_pos);
    }

    @OnClick(R.id.btn_bus_bookmark_nonclick)
    public void  onClickBtnBusBookmark(View view){
            String bus_id = txt_bus_num.getText().toString();
            String myid = SaveMember.getInstance().getMember().getId().toString();

            if(star == 0){
                Call<Void> insertBookmark = RetrofitService.getInstance().getRetrofitRequest().insertBookmark("1",bus_id,"",myid);

                insertBookmark.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("kkkk", "버스 즐겨찾기 전송성공");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                star = 1;
                btn_bus_bookmark_nonclick.setBackgroundResource(R.drawable.star_gold);
            }else if(star == 1){
                String mem_id = SaveMember.getInstance().getMember().getId().toString();
                Call<Void> delBusBookmark = RetrofitService.getInstance().getRetrofitRequest().delBusBookmark(num, mem_id);

                delBusBookmark.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("kkkk", "성공");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                star = 0;
                btn_bus_bookmark_nonclick.setBackgroundResource(R.drawable.star2);
            }

    }

    @OnClick(R.id.btn_close)
    public void  onClickBtnClose(View view){
        finish();
    }
}
