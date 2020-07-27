package com.skysoft.kotlindemo.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by weihuajian on 16/6/22.
 */
@SuppressLint("StaticFieldLeak")
object RetrofitUtils {
    private var mContext: Context? = null
    var retrofit: Retrofit? = null

    val gson: Gson
        get() = GsonBuilder().excludeFieldsWithModifiers().create()

    fun getInstance(context: Context, url: String): Retrofit? {
        mContext = context
        if (retrofit == null) {
            val builder = OkHttpClient.Builder()
            //  builder.addInterceptor(addQueryParameterInterceptor());
            //   builder.addInterceptor(addHeaderInterceptor());
            // 设置缓存
            val cacheFile = File(mContext!!.externalCacheDir, "RetrofitCache")
            val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
            builder.cache(cache).addInterceptor(addCacheInterceptor())

            //设置超时
            builder.connectTimeout(1, TimeUnit.SECONDS)
            builder.readTimeout(6, TimeUnit.SECONDS)
            builder.writeTimeout(6, TimeUnit.SECONDS)
            //错误重连
            builder.retryOnConnectionFailure(true)

            val client = builder.build()
            retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
        }
        return retrofit
    }


    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("platform", "android")
                    .addQueryParameter("version", "1.0.0")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("AppType", "TPOS")
                    .header("Accept", "application/json")
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!isNetworkAvailable(mContext!!)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (isNetworkAvailable(mContext!!)) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时
                val cacheControl = request.cacheControl().toString()
                response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("Pragma")
                        .build()
            }
            response
        }
    }

    fun getAboutAndroidInstance(context: Context, url: String): Retrofit {
        mContext = context
        val builder = OkHttpClient.Builder()
        //  builder.addInterceptor(addQueryParameterInterceptor());
        //   builder.addInterceptor(addHeaderInterceptor());
        // 设置缓存
        val cacheFile = File(mContext!!.externalCacheDir, "AboutAndroidCache")
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        builder.cache(cache).addInterceptor(addCacheInterceptor())

        //设置超时
        builder.connectTimeout(1, TimeUnit.SECONDS)
        builder.readTimeout(6, TimeUnit.SECONDS)
        builder.writeTimeout(6, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)

        val client = builder.build()
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    /**
     * 判断网络
     */
    fun isNetworkAvailable(ct: Context): Boolean {
        val context = ct.applicationContext
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager == null) {
            return false
        } else {
            val networkInfo = connectivityManager.allNetworkInfo
            if (networkInfo != null && networkInfo.size > 0) {
                for (aNetworkInfo in networkInfo) {
                    if (NetworkInfo.State.CONNECTED == aNetworkInfo.state) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun getDouBanMoiveInstance(context: Context, url: String): Retrofit {
        mContext = context
        val builder = OkHttpClient.Builder()
        //  builder.addInterceptor(addQueryParameterInterceptor());
        //   builder.addInterceptor(addHeaderInterceptor());
        // 设置缓存
        val cacheFile = File(mContext!!.externalCacheDir, "DouBanMoiveCache")
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        builder.cache(cache).addInterceptor(addCacheInterceptor())

        //设置超时
        builder.connectTimeout(1, TimeUnit.SECONDS)
        builder.readTimeout(6, TimeUnit.SECONDS)
        builder.writeTimeout(6, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)

        val client = builder.build()

        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    fun getOtherInstance(context: Context): Retrofit {
        mContext = context
        val builder = OkHttpClient.Builder()
        //  builder.addInterceptor(addQueryParameterInterceptor());
        //   builder.addInterceptor(addHeaderInterceptor());
        // 设置缓存
        val cacheFile = File(mContext!!.externalCacheDir, "Other")
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        // builder.cache(cache).addInterceptor(addCacheInterceptor());

        //设置超时
        builder.connectTimeout(1, TimeUnit.SECONDS)
        builder.readTimeout(6, TimeUnit.SECONDS)
        builder.writeTimeout(6, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)

        val client = builder.build()

        return Retrofit.Builder()
                .baseUrl(ConstantApi.OtherUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }
}
