package com.example.seungmin1216.team.Map;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.Place_info;
import com.example.seungmin1216.team.event.Taxiplace2Event;
import com.example.seungmin1216.team.event.TaxiplaceEvent;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.OpenAPIKeyAuthenticationResultListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MapMainActivity extends AppCompatActivity implements net.daum.mf.map.api.MapView.MapViewEventListener,MapView.POIItemEventListener,OpenAPIKeyAuthenticationResultListener {

    @BindView(R.id.et_addr)
    EditText et_addr;
    @BindView(R.id.btn_ser)
    Button btn_ser;
    @BindView(R.id.btn_ser2)
    Button btn_ser2;
    @BindView(R.id.btn_gps)
    Button btn_gps;
    @BindView(R.id.mapview)
    RelativeLayout mapview;
    @BindView(R.id.txt_place_name)
    TextView txt_place_name;
    @BindView(R.id.txt_place_cate)
    TextView txt_place_cate;
    @BindView(R.id.txt_place_addr)
    TextView txt_place_addr;
    @BindView(R.id.btn_start_place)
    Button btn_start_place;
    @BindView(R.id.btn_arrive_place)
    Button btn_arrive_place;
    @BindView(R.id.btn_place_call)
    Button btn_place_call;
    @BindView(R.id.layout_ani)
    RelativeLayout layout_ani;


    ArrayList<Place_info> place_infos = new ArrayList<>();


    MapView mapView;
    int locationPermission = 0;
    SharedPreferences pref;
    boolean isCurrentTrack = true;
    MapPoint centerPoint;

    String bus_addr="";
    String bus_name="";


    Integer index;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapmainactivity);
        ButterKnife.bind(this);


        pref = getSharedPreferences("testApi", MODE_PRIVATE);

        locationPermission = pref.getInt("locationPermission", -1);

        locationPermission = ContextCompat.checkSelfPermission(MapMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (locationPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MapMainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        mapView = new net.daum.mf.map.api.MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.mapview);
        mapViewContainer.addView(mapView);

        mapView.setMapViewEventListener(this);

        et_addr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_ser2.setVisibility(VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



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

                } else {
//                    finish();
                }
                return;
            }

        }

    }

    @OnClick(R.id.btn_gps)
    public void onClickBtnPos(View view) {
        if (!isCurrentTrack) {
            if (locationPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MapMainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            if (locationPermission == PackageManager.PERMISSION_GRANTED) {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                btn_gps.setBackgroundResource(R.drawable.gps_click);
                isCurrentTrack = true;
            } else {
                Toast.makeText(MapMainActivity.this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOff);
            btn_gps.setBackgroundResource(R.drawable.gps_nonclick);
            isCurrentTrack = false;
        }

    }

        @OnClick(R.id.btn_ser)
        public void onClickBtnSerach(View view){

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_addr.getWindowToken(),0);

            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

            mapView.setPOIItemEventListener(MapMainActivity.this);

            mapView.removeAllPOIItems();

            centerPoint = mapView.getMapCenterPoint();
            String name = et_addr.getText().toString();
            place_infos.clear();

            Call<Item> observ = RetrofitService.getInstance().getRetrofitRequest().getSearch(name);

            observ.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {

                    mapView.removeAllPOIItems();


                    if (response.isSuccessful()) {
                        Log.d("jjj", response.toString());
                        for (int i = 0; i < response.body().getDocuments().size(); i++) {


                            String name = response.body().getDocuments().get(i).getPlace_name();
                            String addr = response.body().getDocuments().get(i).getAddress_name();
                            String num = response.body().getDocuments().get(i).getPhone();
                            String category = response.body().getDocuments().get(i).getCategory_group_name();

                            Log.d("ddd", name + " " + addr);
                            Place_info item = new Place_info(name, addr, num, category);
                            place_infos.add(item);

                            Double y = Double.parseDouble(response.body().getDocuments().get(i).getY());
                            Double x = Double.parseDouble(response.body().getDocuments().get(i).getX());


                            Log.d("jjj", "받아오기 성공!" + response.body().getDocuments().get(i).toString());


                            response.body().getDocuments().get(i).getDistance();
                            MapPOIItem marker = new MapPOIItem();
                            marker.setTag(i);
                            marker.setShowCalloutBalloonOnTouch(false);
                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(y, x));
                            marker.setItemName(response.body().getDocuments().get(i).getPlace_name());
                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

                            // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(y, x), 1, true);
                            mapView.addPOIItem(marker);
                            if (i == response.body().getDocuments().size() - 1) {
                                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(Double.parseDouble(response.body().getDocuments().get(0).getY()), Double.parseDouble(response.body().getDocuments().get(0).getX())), 1, true);


                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    Log.d("로그", "받아오기 실패!");

                }
            });


        }

        @OnClick(R.id.btn_ser2)
        public void onClickBtnSer2(View view){

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_addr.getWindowToken(),0);

            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

            mapView.removeAllPOIItems();
            mapView.setPOIItemEventListener(MapMainActivity.this);
            centerPoint = mapView.getMapCenterPoint();
            Double centerLongi = centerPoint.getMapPointGeoCoord().longitude;
            Double centerLati = centerPoint.getMapPointGeoCoord().latitude;
            place_infos.clear();

            Call<Item> observ = RetrofitService.getInstance().getRetrofitRequest().getRadius(et_addr.getText().toString(),centerLongi.toString(),centerLati.toString(),"150");
            btn_ser2.setVisibility(GONE);

            observ.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {


                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().getDocuments().size(); i++) {


                            String name = response.body().getDocuments().get(i).getAddress_name();
                            String addr = response.body().getDocuments().get(i).getPlace_name();
                            String num = response.body().getDocuments().get(i).getPhone();
                            String category = response.body().getDocuments().get(i).getCategory_group_name();

                            Log.d("ddd", name + " " + addr);
                            Place_info item = new Place_info(name, addr,num,category);
                            place_infos.add(item);

                            Double y = Double.parseDouble(response.body().getDocuments().get(i).getY());
                            Double x = Double.parseDouble(response.body().getDocuments().get(i).getX());

                            response.body().getDocuments().get(i).getDistance();

                            MapPOIItem marker = new MapPOIItem();
                            marker.setShowCalloutBalloonOnTouch(false);
                            marker.setItemName(response.body().getDocuments().get(i).getPlace_name());



                            marker.setTag(i);
                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(y, x));
                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                            mapView.addPOIItem(marker);
                            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(y, x), 1, true);


                        }
                    }
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    Log.d("로그", "받아오기 실패!");

                }




            });


        }
    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {


    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        layout_ani.setVisibility(GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_addr.getWindowToken(),0);
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(final MapView mapView, final MapPoint mapPoint) {
        btn_ser2.setVisibility(VISIBLE);
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        Log.d("lsm","ddd");
        layout_ani.setVisibility(VISIBLE);
        index = (Integer) mapPOIItem.getTag();
        txt_place_name.setText(place_infos.get(index).getPlace_name());
        txt_place_addr.setText(place_infos.get(index).getPlace_addr());
        txt_place_cate.setText(place_infos.get(index).getPlace_cate());

        bus_addr = place_infos.get(index).getPlace_addr();
        bus_name = place_infos.get(index).getPlace_name();

        mapPOIItem.setShowCalloutBalloonOnTouch(false);
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

    @OnClick(R.id.btn_place_call)
    public void onClickBtnPlaceCall(View view){

        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("tel: "+place_infos.get(index).getPlace_num()));
        startActivity(intent);
    }

    @OnClick(R.id.btn_start_place)
    public void onClickBtnStartPlace(View view){
        Intent intent = getIntent();

        TaxiplaceEvent taxiplaceEvent = new TaxiplaceEvent(bus_addr, bus_name);
        BusProvider.getInstance().getBus().post(taxiplaceEvent);
        Log.d("lsm", taxiplaceEvent.getPlace_name() + " " + taxiplaceEvent.getAddr());

        finish();


    }

    @OnClick(R.id.btn_arrive_place)
    public void onClickBtnArrivePlace(View view){
        Intent intent = getIntent();

        Taxiplace2Event taxiplace2Event = new Taxiplace2Event(bus_addr, bus_name);
        BusProvider.getInstance().getBus().post(taxiplace2Event);

        finish();

    }


}
