package com.example.seungmin1216.team.retrofit;

import com.example.seungmin1216.team.data.Bookmark;
import com.example.seungmin1216.team.data.Member;
import com.example.seungmin1216.team.data.Notice;
import com.example.seungmin1216.team.data.Request;

import java.util.ArrayList;

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
    @POST("updateMember.do")//가입자 정보 수정
    Call<Void> updateMember(@Field("mem_name") String mem_name,@Field("mem_myp") String mem_myp,@Field("mem_subp") String mem_subp,
                            @Field("mem_etc") String mem_etc,@Field("mem_age") String mem_age,@Field("id") String id);

    @FormUrlEncoded
    @POST("updatePw.do")//가입자 비밀번호 수정
    Call<Void> updatePw(@Field("mem_pw") String mem_pw,@Field("id") String id);

    @FormUrlEncoded
    @POST("inputMember.do")//가입
    Call<Void> inputMember(@Field("mem_name") String mem_name, @Field("mem_mid") String mem_mid, @Field("mem_pw") String mem_pw,
                           @Field("mem_myp") String mem_myp, @Field("mem_email") String mem_addr, @Field("mem_subp") String mem_subp,
                           @Field("mem_etc") String mem_kind, @Field("mem_age") String mem_age);

    @FormUrlEncoded
    @POST("logMember.do")//로그인
    Call<Member> logMember(@Field("mem_mid") String mem_mid, @Field("mem_pw") String mem_pw);

    @FormUrlEncoded
    @POST("insertBookmark.do")//즐겨찾기 추가
    Call<Void> insertBookmark(@Field("book_kind") String book_kind, @Field("book_start") String book_start, @Field("book_end") String book_end
            , @Field("mem_id") String mem_id);

    @FormUrlEncoded
    @POST("delBookmark.do")//즐겨찾기 삭제
    Call<Void> delBookmark(@Field("id") String id);

    @GET("getBookmark.do")//즐겨찾기 리스트
    Call<ArrayList<Bookmark>> getBookmark(@Query("mem_id") String mem_id);

    @FormUrlEncoded
    @POST("updateKey.do")//키 업데이트
    Call<Void> updateKey(@Field("mem_key") String mem_key, @Field("id") String id);

    @GET("chBookmark.do")//즐겨찾기 체크
    Call<Integer> chBookmark(@Query("book_start") String book_start, @Query("mem_id") String mem_id);

    @FormUrlEncoded
    @POST("delBusBookmark.do")//즐겨찾기 삭제
    Call<Void> delBusBookmark(@Field("book_start") String book_start, @Query("mem_id") String mem_id);

    @FormUrlEncoded
    @POST("insertNotice.do")//공지사항 생성
    Call<Void> insertNotice(@Field("notice_title") String notice_title, @Field("notice_contents") String notice_contents, @Field("notice_date") String notice_date);

    @FormUrlEncoded
    @POST("selectNotice.do")//공지사항 리스트
    Call<ArrayList<Notice>> selectNotice(@Field("ok") String ok);

    @FormUrlEncoded
    @POST("updateNotice.do")//공지사항 고치기
    Call<Void> updateNotice(@Field("notice_title") String notice_title, @Field("notice_contents") String notice_contents,
                            @Field("notice_date") String notice_date, @Field("id") String id);

    @GET("returnRequest.do")//즐겨찾기 리스트
    Call<ArrayList<Request>> returnRequest(@Query("mem_id") String mem_id);

    @FormUrlEncoded
    @POST("deleteNotice.do")//즐겨찾기 리스트
    Call<Void> deleteNotice(@Field("id") String id);

    @FormUrlEncoded
    @POST("selectDelBookmark.do")//지하철 즐겨찾기 삭제
    Call<Void> selectDelBookmark(@Field("mem_id") String mem_id, @Field("book_start") String book_start, @Field("book_end") String book_end);
}
