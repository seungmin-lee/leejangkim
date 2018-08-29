package com.example.seungmin1216.team;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.seungmin1216.team.adapter.BookmarkAdapter;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.Bookmark;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.event.BookmarkEvent;
import com.example.seungmin1216.team.fragment.SubwayFragment;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkActivity extends AppCompatActivity {


    ArrayList<Bookmark> request = new ArrayList<>();
    BookmarkAdapter bookmarkAdapter;
    @BindView(R.id.bookmark_list) ListView bookmark_list;
    @BindView(R.id.btn_close_bookmark) Button btn_close_bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);


        bookmark_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("kkkk", "onItemClick: ");
                Bookmark item = request.get(i);
                Integer kind = (int)(long) item.getBook_kind();
                BookmarkEvent bookmarkEvent = new BookmarkEvent(kind,item.getBook_start(),item.getBook_end());
                BusProvider.getInstance().getBus().post(bookmarkEvent);
                finish();
            }
        });

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

    @OnClick(R.id.btn_close_bookmark)
    public void onClickBtnCloseBookmark(View view){
        finish();
    }
}
