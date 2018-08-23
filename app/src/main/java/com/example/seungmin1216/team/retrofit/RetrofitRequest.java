package com.example.seungmin1216.team.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-01-29.
 */

public interface RetrofitRequest {

    @FormUrlEncoded
    @POST("inputlist.do")//출발 도착
    Call<Void> inputlist(@Field("start_st") String start_st, @Field("end_st") String end_st, @Field("year") String year
            , @Field("month") String month, @Field("day") String day, @Field("hour") String hour
            , @Field("minute") String minute, @Field("post") String post, @Field("mem_id") String mem_id);

    @GET("checkMember.do")//아이디 중복 체크
    Call<Integer> calList(@Query("mem_mid") String mem_mid);

    @GET("chEmail.do")//이메일 중복 체크
    Call<Integer> chEmail(@Query("mem_email") String mem_email);

    @FormUrlEncoded
    @POST("inputMember.do")//가입
    Call<Void> inputMember(@Field("mem_name") String mem_name, @Field("mem_mid") String mem_mid, @Field("mem_pw") String mem_pw,
                           @Field("mem_myp") String mem_myp, @Field("mem_email") String mem_addr, @Field("mem_subp") String mem_subp,
                           @Field("mem_etc") String mem_kind,@Field("mem_age") String mem_age);



}
