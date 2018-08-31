package com.example.seungmin1216.team;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.seungmin1216.team.adapter.StationListAdapter;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.StationName;
import com.example.seungmin1216.team.event.DestinationStationName;
import com.example.seungmin1216.team.event.NameEvent;
import com.squareup.otto.Bus;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class SearchActivity extends AppCompatActivity {

    ArrayList<StationName> items = new ArrayList<>();
    ArrayList<StationName> real_list = new ArrayList<>();

    @BindView(R.id.et_search) EditText et_search;
    @BindView(R.id.lv_listview) ListView lv_listview;

    StationListAdapter listAdapter;


    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        items = new ArrayList<>();

        settingList();

        Log.d("ksj","items : " + items.size());

        real_list = new ArrayList<>();

        listAdapter = new StationListAdapter(real_list);
        lv_listview.setAdapter(listAdapter);


        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = getIntent();
                Integer type = intent.getIntExtra("type",-1);

                StationName item = real_list.get(position);
                String name = item.getSt_name();

                if(type == 1){
                    NameEvent nameEvent = new NameEvent(name);
                    BusProvider.getInstance().getBus().post(nameEvent);
                }else{
                    DestinationStationName destinationStationName = new DestinationStationName(name);
                    BusProvider.getInstance().getBus().post(destinationStationName);
                }


                finish();
            }
        });




        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = et_search.getText().toString();
                search(text);

            }
        });



    }

    public void search(String charText){

        real_list.clear();
        if (charText.toString().length() ==0){
            Log.d("ksj","1");
        }
        else{
            for (int i=0;i<items.size();i++){
                if(items.get(i).getSt_name().toLowerCase().contains(charText)){
                    real_list.add(items.get(i));
                }
            }

            Log.d("lsm","2");
        }

        Log.d("lsm","real_list : " + real_list.size());
        listAdapter.notifyDataSetChanged();
    }

    private void settingList(){


        StrictMode.enableDefaults();


        boolean inStationName = false, inLineNum = false;


        String stationName = null, lineNum = null;



        try{
            URL url = new URL("http://openapi.seoul.go.kr:8088/69434c626673657536326b41696348/xml/SearchInfoBySubwayNameService/1/1000"
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("STATION_NM")){
                            inStationName = true;
                        }
                        if(parser.getName().equals("LINE_NUM")){
                            inLineNum = true;
                        }

                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inStationName){ //inBusType이 true일 때 태그의 내용을 저장.
                            stationName = parser.getText();
                            inStationName = false;
                        }
                        if(inLineNum){ //inCongetion이 true일 때 태그의 내용을 저장.
                            lineNum = parser.getText();
                            inLineNum = false;
                        }





                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("row")){
                            StationName name= new StationName(stationName,lineNum);
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

    }

    @OnItemClick(R.id.lv_listview)
    public void onItemClick(AdapterView<?> parent, int position) {
        StationName stationName = real_list.get(position);


    }
}
