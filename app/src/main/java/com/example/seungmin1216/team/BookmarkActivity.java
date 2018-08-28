package com.example.seungmin1216.team;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.seungmin1216.team.adapter.BookmarkAdapter;
import com.example.seungmin1216.team.data.Bookmark;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkActivity extends AppCompatActivity {


    ArrayList<Bookmark> request = new ArrayList<>();
    BookmarkAdapter bookmarkAdapter;
    @BindView(R.id.bookmark_list)ListView bookmark_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    public void loadList() {
        Long mem_id = SaveMember.getInstance().getMember().getId();
        Call<ArrayList<Bookmark>> observ = RetrofitService.getInstance().getRetrofitRequest().getBookmark(mem_id.toString());
        observ.enqueue(new Callback<ArrayList<Bookmark>>() {
            @Override
            public void onResponse(Call<ArrayList<Bookmark>> call, Response<ArrayList<Bookmark>> response) {
                request = response.body();
                bookmarkAdapter = new BookmarkAdapter(request);
                bookmark_list.setAdapter(bookmarkAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Bookmark>> call, Throwable t) {

            }
        });
    }
}
