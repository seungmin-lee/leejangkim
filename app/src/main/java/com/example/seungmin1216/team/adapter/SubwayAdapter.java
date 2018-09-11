package com.example.seungmin1216.team.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.Request;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class SubwayAdapter extends BaseAdapter{
    ArrayList<Request> items ;

    public SubwayAdapter(ArrayList<Request> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subway, parent, false);

            holder.txt_origin = convertView.findViewById(R.id.txt_origin);
            holder.txt_destination = convertView.findViewById(R.id.txt_destination);
            holder.txt_check = convertView.findViewById(R.id.txt_check);
            holder.txt_station_check = convertView.findViewById(R.id.txt_station_check);
            holder.txt_date = convertView.findViewById(R.id.txt_date);
            convertView.setTag(holder);

        }else {
            holder = (Holder) convertView.getTag();
        }

        Request item = (Request) getItem(position);

        Integer month = (int)(long)item.getLi_month();
        Integer day = (int)(long)item.getLi_day();
        String date;

        if(month <10 && day >=10){
            date = "0"+month.toString()+"-"+day.toString();
        }else if(month >= 10 && day < 10){
            date = month.toString()+"-0"+day.toString();
        }else if(month < 10 && day < 10){
            date = "0"+month.toString()+"-0"+day.toString();
        }else{
            date = month.toString()+"-"+day.toString();
        }

        holder.txt_date.setText(date);


        if(item.getLi_kind() == 0){
            holder.txt_origin.setText(item.getLi_station());
            holder.txt_destination.setText(item.getLi_otherSta());
            holder.txt_station_check.setText("출발");
        }else if (item.getLi_kind() == 1){
            holder.txt_origin.setText(item.getLi_otherSta());
            holder.txt_destination.setText(item.getLi_station());
            holder.txt_station_check.setText("도착");
        }

        if(item.getLi_appr() ==0){
            holder.txt_check.setText("미승인");
            holder.txt_check.setTextColor(Color.parseColor("#ffffff"));
        }else if (item.getLi_appr() == 1){
            holder.txt_check.setText("승인");
            holder.txt_check.setTextColor(Color.parseColor("#FFFF00"));
        }else if (item.getLi_appr() == 2){
            holder.txt_check.setText("거절");
            holder.txt_check.setTextColor(Color.parseColor("#ff0000"));
        }


        return convertView;
    }
    public class Holder{
            TextView txt_origin;
            TextView txt_destination;
            TextView txt_check;
            TextView txt_station_check;
            TextView txt_date;
    }
}
