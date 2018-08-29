package com.example.seungmin1216.team.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.data.item;

import java.util.ArrayList;

public class MapTest extends AppCompatActivity {
    ArrayList<item> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);



        /*Call<item> observ = RetrofitService.getInstance().getRetrofitRequest().getDocuments("백화점");
        observ.enqueue(new Callback<item>() {
            @Override
            public void onResponse(Call<item> call, Response<item> response) {

                if(response.isSuccessful()){


                    Log.d("로그","받아오기 성공!" +response.body());

                }
            }

            @Override
            public void onFailure(Call<item> call, Throwable t) {
                Log.d("로그","받아오기 실패!");

            }

        });*/

    }
}
