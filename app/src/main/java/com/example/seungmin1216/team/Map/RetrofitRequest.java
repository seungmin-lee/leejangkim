package com.example.seungmin1216.team.Map;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


/**
 * Created by Administrator on 2018-01-29.
 */

public interface RetrofitRequest {
   /* @GET("getBookInfo.do")
    Call<Book> getBookInfo();

    @GET("getBookList.do")
    Call<ArrayList<Book>> getBookList();

    @GET("cal.do")
    Call<Result> cal(@Query("num1") Integer n1, @Query("num2") Integer n2);

    @GET("getRandomList.do")
    Call<ArrayList<Integer>> getRandomList(@Query("num") Integer n);

    @FormUrlEncoded
    @POST("inputMember.do")
    Call<Void> inputMember(@Field("name") String name, @Field("age") Integer age);



    @FormUrlEncoded
    @POST("inputMemo.do")
    Call<Void> inputMemo(@Field("content") String content);
*/

    @Headers({"Authorization: KakaoAK c43d731e351677497948e3d4d9ceca2d"})
    @GET("/v2/local/search/address.json")
    Call<Item> getItemList(@Query("query") String query);


}
