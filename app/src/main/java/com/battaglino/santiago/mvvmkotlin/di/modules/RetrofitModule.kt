package com.battaglino.santiago.mvvmkotlin.di.modules

import com.battaglino.santiago.mvvmkotlin.BuildConfig
import com.battaglino.santiago.mvvmkotlin.data.service.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Santiago Battaglino.
 */
@Module
class RetrofitModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder()
                            .addHeader("Authorization", "Client-ID 126701cd8332f32").build())
                }
                .build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient).build()

        return retrofit.create(ApiService::class.java)
    }
}
