package com.skysoft.kotlindemo.retrofit

import com.skysoft.kotlindemo.bean.*

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
//
    @GET("data/{type}/{count}/{page}")
    fun getBelleData(@Path("type") type: String, @Path("count") count: Int, @Path("page") page: Int): Observable<BelleBean>

    @GET
   fun downloadPicFromNet(@Url fileUrl: String): Observable<ResponseBody>
//
    @GET("tree/json")
    fun getHotData(): Observable<HotBean>

    @GET("banner/json")
    fun getBanner(): Observable<WanAndroidBena>

    @GET("article/list/{page}/json")
    fun getHomeData(@Path("page")page: Int):Observable<HomeBean>
    //    Observable<AndroidBannerBean> getAndroidBanner();
    //    @GET("search/query/{type}/category/{types}/count/{count}/page/{page}")
    //    Observable<GankModel> getAboutAndroid(@Path("type") String type, @Path("types") String types, @Path("count") int count, @Path("page") int page);
    //    @POST("user/register")
    //    Observable<RegisterBean> register(@Query("username") String username, @Query("password") String password, @Query("repassword") String repassword);
    //    @POST("user/login")
    //    Observable<RegisterBean> login(@Query("username") String username, @Query("password") String password);
    //    @GET("satinApi?")
    //    Observable<FirstVideoBean> getFirstVideo(@Query("type") String type, @Query("page") String page);
    @GET("channel/listjson?")
    //{pn}&{rn}&{tag1}&{tag2}&{ftags}&{ie}
    fun getPicture(@Query("pn")pn :Int, @Query("rn")rn :Int, @Query("tag1")tag1 :String, @Query("tag2")tag2 :String, @Query("ftags")ftags :String, @Query("ie")ie :String):Observable<PictureBean>
}
