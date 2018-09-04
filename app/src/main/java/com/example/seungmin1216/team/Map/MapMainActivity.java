package com.example.seungmin1216.team.Map;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.StationName;
import com.example.seungmin1216.team.data.item;
import com.example.seungmin1216.team.event.DestinationStationName;
import com.example.seungmin1216.team.event.NameEvent;
import com.example.seungmin1216.team.event.Taxiplace2Event;
import com.example.seungmin1216.team.event.TaxiplaceEvent;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MapMainActivity extends AppCompatActivity implements net.daum.mf.map.api.MapView.MapViewEventListener,MapView.POIItemEventListener{
    ArrayList<Item> load = new ArrayList<>();
    Item tmpItem;
    Point point = new Point();
    Button btn_pos;
    int locationPermission = 0;
    boolean isCurrentTrack = true;
    SharedPreferences pref;
    MapPOIItem selectPoint = new MapPOIItem();
    EditText et_addr;
    Button btn_ser;
    Button btn_ser2;
    RelativeLayout layout_ani;
    TextView mapAddrs;
    net.daum.mf.map.api.MapView mapView;
    MapReverseGeoCoder reverseGeoCoder;
    ArrayList<item> items = new ArrayList<>();
    MapPoint centerPoint;
    MapPOIItem mapPOIItem;
    Button btn_start;
    Button btn_arrive;
    String add;
    String place_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         final ArrayList<Item> items = new ArrayList<>();
        setContentView(R.layout.mapmainactivity);
        et_addr = findViewById(R.id.et_addr);
        btn_pos = findViewById(R.id.btn_pos);
        btn_ser = findViewById(R.id.btn_ser);
        btn_ser2 = findViewById(R.id.btn_ser2);
        layout_ani = findViewById(R.id.layout_ani);
        mapAddrs = findViewById(R.id.mapAddrs);
        btn_start = findViewById(R.id.btn_start);
        btn_arrive = findViewById(R.id.btn_arrive);
        final Item tmpItem;
        Button button;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                    TaxiplaceEvent taxiplaceEvent = new TaxiplaceEvent(add,place_name);
                    BusProvider.getInstance().getBus().post(taxiplaceEvent);
                    Log.d("lsm",taxiplaceEvent.getPlace_name() +" " +taxiplaceEvent.getAddr());

                finish();
            }
        });

        btn_arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                Taxiplace2Event taxiplace2Event = new Taxiplace2Event(add,place_name);
                BusProvider.getInstance().getBus().post(taxiplace2Event);

                finish();
            }
        });




        btn_ser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerPoint = mapView.getMapCenterPoint();
                Double centerLongi = centerPoint.getMapPointGeoCoord().longitude;
                Double centerLati = centerPoint.getMapPointGeoCoord().latitude;
                Call<item> observ = RetrofitService.getInstance().getRetrofitRequest().getDocuments(et_addr.getText().toString(),centerLongi.toString(),
                        centerLati.toString(),"1000" , centerPoint.toString());

                observ.enqueue(new Callback<item>() {
                    @Override
                    public void onResponse(Call<item> call, Response<item> response) {

                        mapView.removeAllPOIItems();

                        if(response.isSuccessful()){
                            for(int i=0;i<response.body().getDocuments().size();i++){
                                Double y = Double.parseDouble(response.body().getDocuments().get(i).getY());
                                Double x = Double.parseDouble(response.body().getDocuments().get(i).getX());

                                Log.d("로그","받아오기 성공!" +response.body());


                                response.body().getDocuments().get(i).getDistance();
                                MapPOIItem marker = new MapPOIItem();
                                marker.setItemName(response.body().getDocuments().get(i).getPlace_name());
                                marker.setTag(30);
                                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(y,x));
                                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                                mapView.addPOIItem(marker);
                                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(y, x), 1, true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<item> call, Throwable t) {
                        Log.d("로그","받아오기 실패!");

                    }


                });



            }
        });



        pref = getSharedPreferences("testApi", MODE_PRIVATE);

        locationPermission = pref.getInt("locationPermission", -1);

        locationPermission = ContextCompat.checkSelfPermission(MapMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(locationPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MapMainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        mapView = new net.daum.mf.map.api.MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.mapview);
        mapViewContainer.addView(mapView);

        mapView.setMapViewEventListener(this);

        MapPoint madooSta = MapPoint.mapPointWithGeoCoord(37.6522587,126.7753933);

        if (locationPermission == PackageManager.PERMISSION_GRANTED) {
            mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
        } else {
            Toast.makeText(MapMainActivity.this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MapMainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        MapPOIItem mapPOIItem = new MapPOIItem();
        mapPOIItem.setItemName("marker");
        mapPOIItem.setTag(0);
        mapPOIItem.setMapPoint(madooSta);
        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin

        mapView.addPOIItem(mapPOIItem);


        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCurrentTrack) {
                    if (locationPermission == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(MapMainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }

                    if (locationPermission == PackageManager.PERMISSION_GRANTED) {
                        mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                        btn_pos.setText("내 위치 끄기");
                        isCurrentTrack = true;
                    } else {
                        Toast.makeText(MapMainActivity.this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOff);
                    btn_pos.setText("내 위치");
                    isCurrentTrack = false;
                }
            }
        });

        btn_ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               point = getPointFromGeoCoder(et_addr.getText().toString());

                Double Y = point.y;
                Log.d("jang",Y.toString());
                Double X = point.x;
                Log.d("jang",X.toString());

                MapPOIItem marker = new MapPOIItem();
                marker.setItemName("Default Marker");
                marker.setTag(200);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Y, X));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                mapView.addPOIItem(marker);

                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(Y, X), 1, true);
            }
        });

        mapView.setPOIItemEventListener(MapMainActivity.this);


    }






    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermission = grantResults[0];
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("locationPermission", locationPermission);
                    editor.apply();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

//    private void getHashKey(){
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("KeyHash", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//    }





    @Override
    public void onMapViewInitialized(net.daum.mf.map.api.MapView mapView) {


    }

    @Override
    public void onMapViewCenterPointMoved(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {
//        Point point = getPointFromGeoCoder(et_addr.getText().toString());
//
//        Double Y = point.y;
//        Log.d("jang", "y좌표 :" + Y.toString());
//        Double X = point.x;
//        Log.d("jang", "x좌표 :" + X.toString());
//        this.mapView = mapView;
//
//
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("Default Marker");
//        marker.setTag(200);
//        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Y, X));
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker);
//
//        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(Y, X), 1, true);



    }

    @Override
    public void onMapViewZoomLevelChanged(net.daum.mf.map.api.MapView mapView, int i) {


    }
    @Override
    public void onMapViewSingleTapped(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {
//        if(selectPoint.getItemName() != null && selectPoint.getItemName().equals("Selected")) {
//            mapView.removePOIItem(selectPoint);
//        }
//
//        selectPoint.setMapPoint(mapPoint);
//        selectPoint.setTag(1);
//        selectPoint.setItemName("Selected");
//        selectPoint.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        selectPoint.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//
//
//        mapView.addPOIItem(selectPoint);




    }

    @Override
    public void onMapViewDoubleTapped(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(final net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {
        btn_ser2.setVisibility(VISIBLE);
        btn_ser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerPoint = mapView.getMapCenterPoint();
                Double centerLongi = centerPoint.getMapPointGeoCoord().longitude;
                Double centerLati = centerPoint.getMapPointGeoCoord().latitude;
                Call<item> observ = RetrofitService.getInstance().getRetrofitRequest().getDocuments(et_addr.getText().toString(),centerLongi.toString(),
                        centerLati.toString(),"1000" , centerPoint.toString());
                btn_ser2.setVisibility(GONE);
                observ.enqueue(new Callback<item>() {
                    @Override
                    public void onResponse(Call<item> call, Response<item> response) {

                        mapView.removeAllPOIItems();

                        if(response.isSuccessful()){
                            for(int i=0;i<response.body().getDocuments().size();i++){
                                Double y = Double.parseDouble(response.body().getDocuments().get(i).getY());
                                Double x = Double.parseDouble(response.body().getDocuments().get(i).getX());

                                Log.d("로그","받아오기 성공!" +response.body());


                                response.body().getDocuments().get(i).getDistance();
                                MapPOIItem marker = new MapPOIItem();
                                marker.setItemName(response.body().getDocuments().get(i).getPlace_name());
                                marker.setTag(30);
                                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(y,x));
                                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                                mapView.addPOIItem(marker);
                                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(y, x), 1, true);


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<item> call, Throwable t) {
                        Log.d("로그","받아오기 실패!");

                    }


                });

            }

        });

    }

    @Override
    public void onMapViewDragEnded(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    public ArrayList<Item> getData(String query) {
        StrictMode.enableDefaults();
        ArrayList<Item> load  = new ArrayList<>();

        boolean initem = false, inroad_name = false, inregion_2depth_name = false, inX = false
                , inY = false;
        String road_name = "", region_2depth_name = "", X = ""
                , Y = "";
        try {
            URL url = new URL("https://dapi.kakao.com/v2/local/search/address.xml?query="+query
            ); //검색 URL부분
            HttpURLConnection huc = (HttpURLConnection)url.openConnection();

            huc.setRequestProperty("Authorization","KakaoAK 571c1dd05392b8816be19ef4ba2a356f");
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(huc.getInputStream(), null);

            int parserEvent = parser.getEventType();
            Log.d("jang","파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("road_name")) inroad_name= true;
                        if (parser.getName().equals("region_2depth_name"))  inregion_2depth_name = true;
                        if (parser.getName().equals("x")) inX = true;
                        if (parser.getName().equals("y")) inY = true;

                        break;

                    case XmlPullParser.TEXT:

                        if (inroad_name ) {  road_name  = parser.getText(); inroad_name  = false; }
                        if (inregion_2depth_name ) {  region_2depth_name  = parser.getText(); inregion_2depth_name  = false; }
                        if (inX ) { Log.d("jang","발견1"); X  = parser.getText(); inX  = false; }
                        if (inY ) {  Y  = parser.getText(); inY  = false; }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("documents")) {

                            initem = false;
                            Log.d("jang","결과 : " + road_name + ", " + region_2depth_name + " , " + X + " , " + Y );
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            Log.d("jang", "에러가...났습니다..");
        }

        return load;
    }


    private Point getPointFromGeoCoder(String addr) {
        Point point = new Point();
        point.addr = addr;

        Geocoder geocoder = new Geocoder(MapMainActivity.this);
        List<Address> listAddress;
        try {
            listAddress = geocoder.getFromLocationName(addr, 1);
        } catch (IOException e) {
            e.printStackTrace();
            point.havePoint = false;
            return point;
        }

        if (listAddress.isEmpty()) {
            point.havePoint = false;
            return point;
        }

        point.havePoint = true;
        point.x = listAddress.get(0).getLongitude();
        point.y = listAddress.get(0).getLatitude();
        return point;

    }

    @Override
    public void onPOIItemSelected(final MapView mapView, MapPOIItem mapPOIItem) {

        Log.d("yjh", "test" + mapPOIItem.getItemName());
        layout_ani.setVisibility(VISIBLE);
        Double centerLongi = centerPoint.getMapPointGeoCoord().longitude;
        Double centerLati = centerPoint.getMapPointGeoCoord().latitude;
        Call<item> observ = RetrofitService.getInstance().getRetrofitRequest().getDocuments(et_addr.getText().toString(),centerLongi.toString(),centerLati.toString(),
                "1000", centerPoint.toString());
        observ.enqueue(new Callback<item>() {
            @Override
            public void onResponse(Call<item> call, Response<item> response) {

                mapView.removeAllPOIItems();

                if(response.isSuccessful()) {
                    for (int i = 0; i < response.body().getDocuments().size(); i++) {
                        Double y = Double.parseDouble(response.body().getDocuments().get(i).getY());
                        Double x = Double.parseDouble(response.body().getDocuments().get(i).getX());

                        Log.d("로그", "받아오기 성공!" + response.body());

                        add = response.body().getDocuments().get(i).getAddress_name();
                        place_name = response.body().getDocuments().get(i).getPlace_name();

                        mapAddrs.setText(response.body().getDocuments().get(i).getAddress_name() + "\n" + response.body().getDocuments().get(i).getPlace_name() + "\n" + response.body().getDocuments().get(i).getDistance());

                    }
                }
            }

            @Override
            public void onFailure(Call<item> call, Throwable t) {
                Log.d("로그","받아오기 실패!");

            }

        });
    }
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
