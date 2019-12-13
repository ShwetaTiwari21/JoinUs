package com.example.joinus.joinusapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.joinus.joinusapp.servcice.NetworkService
import com.example.joinus.joinusapp.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by shwetatiwari on 13/12/19.
 */

 class MyApplication : Application() {

    private val baseurl = "http://shikharsharma-gius.localhost.run"
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var networkService: NetworkService
        lateinit var httpClient: OkHttpClient.Builder
        var context : Context ? = null
        fun applicationContext() : Context {
            return context!!.applicationContext
        }
    }

    override fun onCreate() {

        super.onCreate()
        try {
            context = applicationContext

            Log.e("appp","started");

            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(Const.RETROFIT_NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            httpClient.writeTimeout(Const.RETROFIT_NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            httpClient.connectTimeout(Const.RETROFIT_NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            // add your other interceptors …
            // add logging as last interceptor
            httpClient.addInterceptor(logging);
            retrofit = Retrofit.Builder()
                    .baseUrl(baseurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            networkService = retrofit.create(NetworkService::class.java)

        } catch (e: Exception){
            e.printStackTrace()
        }

    }


}

