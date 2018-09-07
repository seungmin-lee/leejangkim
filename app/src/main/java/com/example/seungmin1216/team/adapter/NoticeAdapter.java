package com.example.seungmin1216.team.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.Notice;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {
    ArrayList<Notice> notice;

    public NoticeAdapter(ArrayList<Notice> notice) {
        this.notice = notice;
    }
    @Override
    public int getCount() {
        return notice.size();
    }

    @Override
    public Object getItem(int position) {
        return notice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_list, parent, false);
            holder.btn_arrow = convertView.findViewById(R.id.btn_arrow);
            holder.txt_notice_title = convertView.findViewById(R.id.txt_notice_title);
            holder.txt_notice_date = convertView.findViewById(R.id.txt_notice_date);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Notice item = (Notice) getItem(position);
        holder.txt_notice_title.setText(item.getNotice_title());
        holder.txt_notice_date.setText(item.getNotice_date());

        return convertView;

    }

    public class Holder {
        Button btn_arrow;
        TextView txt_notice_title;
        TextView txt_notice_date;
    }
}

