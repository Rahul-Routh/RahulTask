package com.surya.suryamfinderproject.extra

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit


 class RetrofitInstance {

     companion object{

         private var BASE_URL : String = ApiList.BASE_URL
         private var retrofit : Retrofit? = null
         private var apiNames: SimpleApi

         init {
             apiNames = getClienet().create(SimpleApi::class.java)
         }

         fun getClienet() : Retrofit {

             val interceptor = HttpLoggingInterceptor()
             interceptor.level = HttpLoggingInterceptor.Level.BODY
             val client = OkHttpClient.Builder()
                 .addInterceptor(interceptor)
                 .connectTimeout(60, TimeUnit.SECONDS)
                 .readTimeout(60, TimeUnit.SECONDS)
                 .writeTimeout(60, TimeUnit.SECONDS)
                 .build()

             retrofit = Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                 .addConverterFactory(SimpleXmlConverterFactory.create())
                 .client(client)
                 .build()

             return retrofit as Retrofit

         }

         fun getApi() : SimpleApi{
             return apiNames
         }
     }

}
