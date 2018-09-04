package com.example.seungmin1216.team.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.Bookmark;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkAdapter extends BaseAdapter {
    ArrayList<Bookmark> bookmark = new ArrayList<>();

    public BookmarkAdapter(ArrayList<Bookmark> bookmark) {
        this.bookmark = bookmark;
    }

    @Override
    public int getCount() {
        return bookmark.size();
    }

    @Override
    public Object getItem(int i) {
        return bookmark.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookmark_mold, viewGroup, false);
            holder.bm_image = view.findViewById(R.id.bm_image);
            holder.st_sta = view.findViewById(R.id.st_sta);
            holder.end_sta = view.findViewById(R.id.end_sta);
            holder.del_sta = view.findViewById(R.id.del_sta);
            holder.white_right = view.findViewById(R.id.white_right);
            view.setTag(holder);
        }else {
            holder = (Holder)view.getTag();
        }

        final Bookmark item = (Bookmark)getItem(i);
        if (item.getBook_kind() == 0){
            holder.bm_image.setBackgroundResource(R.drawable.transportation);
            holder.white_right.setVisibility(View.VISIBLE);
        }else if(item.getBook_kind() == 1){
            holder.bm_image.setBackgroundResource(R.drawable.bus);
            holder.white_right.setVisibility(View.GONE);
        }else if(item.getBook_kind() == 2){
            holder.bm_image.setBackgroundResource(R.drawable.taxi);
            holder.white_right.setVisibility(View.VISIBLE);
        }
        holder.st_sta.setText(item.getBook_start());
        holder.end_sta.setText(item.getBook_end());

        holder.del_sta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id = item.getId();
                Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().delBookmark(id.toString());
                bookmark.remove(item);
                observ.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("ksj","전송성공");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                notifyDataSetChanged();
            }
        });



        return view;
    }


    public class Holder{
        ImageView bm_image;
        TextView st_sta;
        TextView end_sta;
        Button del_sta;
        ImageView white_right;
    }
}
