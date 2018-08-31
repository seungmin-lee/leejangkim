package com.example.seungmin1216.team.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.BusPos;
import com.example.seungmin1216.team.data.BusStation;

import java.util.ArrayList;

public class BusStationAdapter extends BaseAdapter {
    ArrayList<BusStation> items = new ArrayList<>();
    ArrayList<BusPos> bus_items = new ArrayList<>();



    public BusStationAdapter(ArrayList<BusStation> itmes,ArrayList<BusPos> bus_items){
        this.items = itmes;
        this.bus_items = bus_items;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_busstation, parent, false);
            holder.txt_station_name = convertView.findViewById(R.id.txt_station_name);
            holder.txt_station_num = convertView.findViewById(R.id.txt_station_num);
            holder.txt_station_st_tm = convertView.findViewById(R.id.txt_station_st_tm);
            holder.txt_station_ed_tm = convertView.findViewById(R.id.txt_station_ed_tm);
            holder.img_bus_pos = convertView.findViewById(R.id.img_bus_pos);
            holder.txt_bus_num = convertView.findViewById(R.id.txt_bus_num);
            holder.img_turn_st = convertView.findViewById(R.id.img_turn_st);
            holder.txt_bus_type = convertView.findViewById(R.id.txt_bus_type);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final BusStation item = (BusStation)getItem(position);

        holder.txt_station_name.setText(item.getStationNm());
        holder.txt_station_num.setText(item.getStationNo());
        holder.txt_station_st_tm.setText(item.getBeginTm());
        holder.txt_station_ed_tm.setText(item.getLastTm());



        boolean check = false;
        String num;
        String bus_real_num = "";
        Integer bus_type = -1;

        for(int i = 0;i<bus_items.size();i++){
            Integer bus_it = Integer.parseInt(bus_items.get(i).getSectOrd());
            Log.d("lsmtt",bus_type+" ");

            if(position+1 == bus_it){
                Log.d("ksj"," position : " + (position+1));
                num = bus_items.get(i).getBus_plainNo();
                bus_real_num = num.substring(5,9);
                bus_type = Integer.parseInt(bus_items.get(i).getBusType());

                check = true;

            } else {

            }
        }

        holder.txt_bus_type.setText("");

        if (check) {
            holder.img_bus_pos.setVisibility(View.VISIBLE);
            holder.txt_bus_num.setVisibility(View.VISIBLE);
            if (bus_type == 1) {
                holder.txt_bus_type.setText("저상");
            }
        }else {
            holder.img_bus_pos.setVisibility(View.INVISIBLE);
            holder.txt_bus_num.setVisibility(View.INVISIBLE);

        }
        holder.txt_bus_num.setText(bus_real_num);
        if(items.get(position).getTrnstnid().equals(items.get(position).getStation())){
            holder.img_turn_st.setVisibility(View.VISIBLE);
            holder.img_turn_st.setBackgroundResource(R.drawable.trunst);
        }else{
            holder.img_turn_st.setVisibility(View.INVISIBLE);
        }




        return convertView;


    }

    public class Holder {
        TextView txt_station_name;
        TextView txt_station_num;
        TextView txt_station_st_tm;
        TextView txt_station_ed_tm;
        ImageView img_bus_pos;
        TextView txt_bus_num;
        ImageView img_turn_st;
        TextView txt_bus_type;

    }
}