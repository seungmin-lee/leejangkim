package com.example.seungmin1216.team.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seungmin1216.team.BookmarkActivity;
import com.example.seungmin1216.team.MainActivity;
import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.SearchActivity;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.StationName;
import com.example.seungmin1216.team.event.DestinationStationName;
import com.example.seungmin1216.team.event.NameEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SubwayFragment extends Fragment {

    private static SubwayFragment curr = null;

    public static SubwayFragment getInstance() {
        if (curr == null) {
            curr = new SubwayFragment();
        }

        return curr;
    }

    Bus bus = BusProvider.getInstance().getBus();

    Spinner spinner;
    @BindView(R.id.btn_bookmark_nonclick) Button btn_bookmark_nonclick;
    @BindView(R.id.txt_subway_origin) TextView txt_subway_origin;
    @BindView(R.id.txt_subway_destination) TextView txt_subway_destination;


    private Unbinder unbinder;
    Integer star_set =0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subway, container, false);
        unbinder=ButterKnife.bind(this,view);
        btn_bookmark_nonclick = view.findViewById(R.id.btn_bookmark_nonclick);
        bus.register(this);

        spinner = view.findViewById(R.id.sp1);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.location,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);





        return view;
    }


    @OnClick(R.id.txt_subway_origin)
    public void onClickEtSubwayOrigin(View view){

        Intent intent = new Intent(getActivity(),SearchActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @OnClick(R.id.txt_subway_destination)
    public void onClickEtSubwayDestination(View view){
        Intent intent = new Intent(getActivity(),SearchActivity.class);
        intent.putExtra("type",2);
        startActivity(intent);
    }


    @OnClick(R.id.btn_bookmark_nonclick)
    public void onClickBtnBookmarkNonclick(View view){
        if(star_set==0){
            star_set =1;
            btn_bookmark_nonclick.setBackgroundResource(R.drawable.star_gold);
        }else {
            star_set=0;
            btn_bookmark_nonclick.setBackgroundResource(R.drawable.star2);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        bus.unregister(this);
    }

    @Subscribe
    public void setOriginStationName(NameEvent event){
        txt_subway_origin.setText(event.getName());
    }

    @Subscribe
    public void setDestinationStationName(DestinationStationName event){
        txt_subway_destination.setText(event.getName());

    }

}

