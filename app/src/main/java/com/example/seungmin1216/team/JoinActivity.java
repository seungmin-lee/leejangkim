package com.example.seungmin1216.team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.seungmin1216.team.adapter.JoinAdapter;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.event.JoinEvent;
import com.example.seungmin1216.team.fragment.Join1Fragment;
import com.example.seungmin1216.team.fragment.Join2Fragment;
import com.example.seungmin1216.team.fragment.Join3Fragment;
import com.example.seungmin1216.team.fragment.Join4Fragment;
import com.example.seungmin1216.team.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    Integer pag = 0;
    Integer probar = 0;

    String id;
    String pw;
    Integer id_check;
    Integer pw_check;

    String name;
    String user_phone;

    String email;
    String helper_phone;
    int num_verity;

    String etc;
    Integer age;

    JoinAdapter joinAdapter;

    @BindView(R.id.btn_left)
    Button btn_left;
    @BindView(R.id.btn_right)
    Button btn_right;
    @BindView(R.id.join_progressbar)
    ProgressBar join_progressbar;
    @BindView(R.id.join_viewpager)
    SwipeViewPager join_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);

        joinAdapter = new JoinAdapter(getSupportFragmentManager());
        join_viewpager.setAdapter(joinAdapter);
        join_viewpager.setPagingEnabled(false);

        if (pag == 0) {
            btn_left.setBackgroundResource(R.drawable.close);
        }

    }


    @OnClick(R.id.btn_left)
    public void onClickBtnLeft(View view) {
        if (pag == 0) {
            finish();
        }
        if (probar != 0) {
            probar -= 25;
            join_progressbar.setProgress(probar);
            pag--;
            if (pag == 0) {
                btn_left.setBackgroundResource(R.drawable.close);
            }
            join_viewpager.setCurrentItem(pag);
        }

    }

    @OnClick(R.id.btn_right)
    public void onClickBtnRight(View view) {

        if (pag == 0) {
            id = Join1Fragment.getInstance().input_id();
            pw = Join1Fragment.getInstance().input_pw();
            id_check = Join1Fragment.getInstance().id_check();
            pw_check = Join1Fragment.getInstance().pw_check();

            if (id_check == 0 && pw_check == 0) {
                if (probar != 100) {
                    probar += 25;
                    join_progressbar.setProgress(probar);
                    pag++;
                    if (pag != 0) {
                        btn_left.setBackgroundResource(R.drawable.left);
                    }
                    join_viewpager.setCurrentItem(pag);
                }
            }
        } else if (pag == 1) {
            name = Join2Fragment.getInstance().input_name();
            user_phone = Join2Fragment.getInstance().input_phone();

            if (!name.equals("") && !user_phone.equals("")) {
                if (probar != 100) {
                    probar += 25;
                    join_progressbar.setProgress(probar);
                    pag++;
                    if (pag != 0) {
                        btn_left.setBackgroundResource(R.drawable.left);
                    }
                    join_viewpager.setCurrentItem(pag);
                }
            }
        } else if (pag == 2) {
            email = Join3Fragment.getInstance().input_email();
            helper_phone = Join3Fragment.getInstance().input_helper_phone();
            num_verity = Join3Fragment.getInstance().num_verity();

            if( !email.equals("") && !helper_phone.equals("")){
                if(num_verity == 1 ) {
                    if (probar != 100) {
                        probar += 25;
                        join_progressbar.setProgress(probar);
                        pag++;
                        if (pag != 0) {
                            btn_left.setBackgroundResource(R.drawable.left);
                        }
                        join_viewpager.setCurrentItem(pag);
                    }
                }

            }

        } else if (pag == 3) {
            etc = Join4Fragment.getInstance().input_etc();
            age = Join4Fragment.getInstance().input_age();
            String age2 = age.toString();

            if(!age2.equals("")){

                Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().inputMember(name,id,pw,user_phone,email,helper_phone,etc,age2);
                observ.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("lsm","전송성공");

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                if (probar != 100) {
                    probar += 25;
                    join_progressbar.setProgress(probar);
                    pag++;
                    if (pag != 0) {
                        btn_left.setBackgroundResource(R.drawable.left);
                    }
                    join_viewpager.setCurrentItem(pag);
                }


            }



        } else if (pag == 4) {

            finish();

        }

        Log.d("lsm","id" + id_check);



    }
}






