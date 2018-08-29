package com.example.seungmin1216.team.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.seungmin1216.team.Map.MapMainActivity;
import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.SearchActivity;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.event.SubwayBookmarkEvent;
import com.example.seungmin1216.team.event.Taxiplace2Event;
import com.example.seungmin1216.team.event.TaxiplaceEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TaxiFragment extends Fragment {

    private static TaxiFragment curr = null;
    public static TaxiFragment getInstance() {
        if (curr == null) {
            curr = new TaxiFragment();
        }

        return curr;

    }

    Bus bus = BusProvider.getInstance().getBus();

    Spinner spinner;
    @BindView(R.id.txt_taxi_origin) TextView txt_taxi_origin;
    @BindView(R.id.txt_taxi_destination) TextView txt_taxi_destination;
    private Unbinder unbinder;
    Integer star_set =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taxi, container, false);
        unbinder = ButterKnife.bind(this,view);
        bus.register(this);


        spinner = view.findViewById(R.id.sp3);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.location,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.txt_taxi_origin)
    public void onClickTxtTaxiOrigin(View view){

        Intent intent = new Intent(getActivity(),MapMainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.txt_taxi_destination)
    public void onClickTxtTaxiDestination(View view){
        Intent intent = new Intent(getActivity(),MapMainActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void getTaxi (TaxiplaceEvent taxiplaceEvent){
        Log.d("lsm","2 : "+ taxiplaceEvent.getPlace_name() +" " +taxiplaceEvent.getAddr());
        txt_taxi_origin.setText(taxiplaceEvent.getPlace_name() + "\n" + taxiplaceEvent.getAddr());

    }

    @Subscribe
    public void getTaxi2 (Taxiplace2Event taxiplace2Event){

        txt_taxi_destination.setText(taxiplace2Event.getPlace_name2() + "\n" + taxiplace2Event.getAddr2());

    }



}
