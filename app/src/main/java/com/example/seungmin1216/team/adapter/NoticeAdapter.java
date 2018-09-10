package com.example.seungmin1216.team.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.Notice;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_list, parent, false);
            holder.txt_notice_title = convertView.findViewById(R.id.txt_notice_title);
            holder.txt_notice_date = convertView.findViewById(R.id.txt_notice_date);
            holder.btn_notice_delete = convertView.findViewById(R.id.btn_notice_delete);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Notice item = (Notice) getItem(position);
        holder.txt_notice_title.setText(item.getNotice_title());
        holder.txt_notice_date.setText(item.getNotice_date());

        Log.d("login_id",SaveMember.getInstance().getMember().getMem_mid().toString());

        String id = SaveMember.getInstance().getMember().getMem_mid();

        Log.d("id",id);

        if(id.equals("admin_ljk")){
            holder.btn_notice_delete.setVisibility(View.VISIBLE);
        }

        holder.btn_notice_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Long position_i = notice.get(position).getId();

                Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().deleteNotice(position_i.toString());
                notice.remove(position);
                observ.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                notifyDataSetChanged();

            }
        });

        return convertView;

    }

    public class Holder {
        Button btn_arrow;
        TextView txt_notice_title;
        TextView txt_notice_date;
        Button btn_notice_delete;
    }
}

