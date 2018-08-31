package com.example.seungmin1216.team.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.Bus;

import java.util.ArrayList;

public class BusAdapter extends BaseAdapter{
    ArrayList<Bus> items;

    public BusAdapter(ArrayList<Bus> itmes){
        this.items=itmes;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buslist, parent, false);
            holder.txt_bus_list = convertView.findViewById(R.id.txt_bus_list);
            holder.txt_bus_sort = convertView.findViewById(R.id.txt_bus_sort);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Bus item = (Bus)getItem(position);

        holder.txt_bus_list.setText(item.getBusRouteNum());


        if(item.getBusRouteType().equals("1")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#FF977A6B"));
            holder.txt_bus_sort.setText("공항 버스");
        }else if(item.getBusRouteType().equals("2")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#000000"));
            holder.txt_bus_sort.setText("마을 버스");
        }else if(item.getBusRouteType().equals("3")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#FF6B75D9"));
            holder.txt_bus_sort.setText("간선 버스");
        }else if(item.getBusRouteType().equals("4")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#FF2E7E20"));
            holder.txt_bus_sort.setText("지선 버스");
        }else if(item.getBusRouteType().equals("5")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#FFff00"));
            holder.txt_bus_sort.setText("순환 버스");
        }else if(item.getBusRouteType().equals("6")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#FFDE6B56"));
            holder.txt_bus_sort.setText("광역 버스");
        }else if(item.getBusRouteType().equals("7")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#FF6B75D9"));
            holder.txt_bus_sort.setText("인천 버스");
        }else if(item.getBusRouteType().equals("8")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#FFFF00D0"));
            holder.txt_bus_sort.setText("경기 버스");
        }else if(item.getBusRouteType().equals("9")){
            holder.txt_bus_list.setTextColor(Color.parseColor("#000000"));
            holder.txt_bus_sort.setText("폐지 버스");
        }




        return convertView;


    }

    public class Holder {
        TextView txt_bus_list;
        TextView txt_bus_sort;
    }
}