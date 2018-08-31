package com.example.seungmin1216.team.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.example.seungmin1216.team.BusPosActivity;
import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.adapter.BusAdapter;
import com.example.seungmin1216.team.data.Bus;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class BusFragment extends Fragment {


    private static BusFragment curr = null;

    public static BusFragment getInstance() {
        if (curr == null) {
            curr = new BusFragment();
        }

        return curr;
    }

    InputMethodManager imm;
    private Unbinder unbinder;

    @BindView(R.id.et_search_bus_num) EditText et_search_bus_num;
    @BindView(R.id.busLayout) RelativeLayout busLayout;
    @BindView(R.id.lv_bus) ListView lv_bus;

    ArrayList<Bus> items = new ArrayList<>();
    ArrayList<Bus> real_list = new ArrayList<>();

    String bus_num;

    BusAdapter busAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bus, container, false);
        unbinder = ButterKnife.bind(this, view);

        items = new ArrayList<>();

        settingList();

        real_list = new ArrayList<>();

        busAdapter = new BusAdapter(real_list);
        lv_bus.setAdapter(busAdapter);

        et_search_bus_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = et_search_bus_num.getText().toString();
                search(text);

            }
        });

        lv_bus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                Intent intent = new Intent(getActivity(),BusPosActivity.class);

                String busid =real_list.get(position).getBusRouteId();
                String busnum = real_list.get(position).getBusRouteNum();
                String bustype =real_list.get(position).getBusRouteType();
                String busstname = real_list.get(position).getBusStStaionName();
                String busednname = real_list.get(position).getBusEndStationName();

                intent.putExtra("id",busid);
                intent.putExtra("num",busnum);
                intent.putExtra("type",bustype);
                intent.putExtra("st",busstname);
                intent.putExtra("end",busednname);

                startActivity(intent);


            }
        });








        return view;


    }

    public void search(String charText){

        real_list.clear();
        if (charText.toString().length() ==0){
            Log.d("ksj","1");
        }
        else{
            for (int i=0;i<items.size();i++){
                if(items.get(i).getBusRouteNum().toLowerCase().contains(charText)){
                    real_list.add(items.get(i));
                }
            }

        }

        Log.d("lsm","real_list : " + real_list.size());
        busAdapter.notifyDataSetChanged();
    }

    private void settingList(){


        StrictMode.enableDefaults();


        boolean inbusRouteId = false, inbusRouteNm = false,inbusRouteType = false, stStationName =false, endStationName = false;


        String busRouteId = null, busNum = null, busRouteType = null, busStStationName = null, busEndStationName = null;

        // bus_num = et_search_bus_num.getText().toString();


        try{
            URL url = new URL("http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?ServiceKey=cOA%2FprnxwDncuq5BMNNhG%2FgPjmeJb2vWV1LxAsgBLqBA5YCW3UJ6eByldFugIO5UY5K%2FblpiWD3myvaIORi%2FIQ%3D%3D"); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("busRouteId")){
                            inbusRouteId = true;
                        }
                        if(parser.getName().equals("busRouteNm")){
                            inbusRouteNm= true;
                        }if(parser.getName().equals("routeType")){
                            inbusRouteType = true;
                        }if(parser.getName().equals("stStationNm")){
                            stStationName = true;
                        }if(parser.getName().equals("edStationNm")){
                            endStationName = true;
                        }

                        break;

                    case XmlPullParser.TEXT:
                        if(inbusRouteId){
                            busRouteId = parser.getText();
                            inbusRouteId = false;
                        }if(inbusRouteNm){
                            busNum = parser.getText();
                            inbusRouteNm = false;
                        }if(inbusRouteType){
                            busRouteType = parser.getText();
                            inbusRouteType = false;
                        }if(stStationName){
                            busStStationName = parser.getText();
                            stStationName = false;
                        }if(endStationName){
                            busEndStationName = parser.getText();
                            endStationName = false;
                        }





                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("itemList")){
                            Bus name= new Bus(busRouteId,busNum,busRouteType,busStStationName,busEndStationName);
                            items.add(name);
                            real_list.add(name);
                        }
                        break;
                }


                parserEvent = parser.next();

            }
        } catch(Exception e){
            Log.d("lsm","error");
        }

        Log.d("lsm","아이템 : " + items.size());

    }








    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(et_search_bus_num.getWindowToken(), 0);
    }





}
