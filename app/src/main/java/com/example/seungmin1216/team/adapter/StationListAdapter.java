package com.example.seungmin1216.team.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.StationName;

import java.util.ArrayList;


public class StationListAdapter extends BaseAdapter {
    ArrayList<StationName> items;

    public StationListAdapter(ArrayList<StationName> itmes){
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            holder.img_line_num = convertView.findViewById(R.id.img_line_num);
            holder.name_station = convertView.findViewById(R.id.name_station);


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final StationName item = (StationName)getItem(position);

        if (item.getLine_num().equals("1")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_1);
        }else if(item.getLine_num().equals("2")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_2);
        }else if(item.getLine_num().equals("3")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_3);
        }else if(item.getLine_num().equals("4")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_4);
        }else if(item.getLine_num().equals("5")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_5);
        }else if(item.getLine_num().equals("6")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_6);
        }else if(item.getLine_num().equals("7")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_7);
        }else if(item.getLine_num().equals("8")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_8);
        }else if(item.getLine_num().equals("9")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_9);
        }else if(item.getLine_num().equals("A")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_a);
        }else if(item.getLine_num().equals("B")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_b);
        }else if(item.getLine_num().equals("I")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_i);
        }else if(item.getLine_num().equals("I2")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_i2);
        }else if(item.getLine_num().equals("K")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_k);
        }else if(item.getLine_num().equals("G")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_g);
        }else if(item.getLine_num().equals("KK")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_kk);
        }else if(item.getLine_num().equals("S")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_s);
        }else if(item.getLine_num().equals("SU")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_su);
        }else if(item.getLine_num().equals("UI")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_ui);
        }else if(item.getLine_num().equals("U")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_u);
        }else if(item.getLine_num().equals("E")){
            holder.img_line_num.setBackgroundResource(R.drawable.line_e);
        }

        holder.name_station.setText(item.getSt_name());


        return convertView;


    }

    public class Holder {
        ImageView img_line_num;
        TextView name_station;
    }
}
