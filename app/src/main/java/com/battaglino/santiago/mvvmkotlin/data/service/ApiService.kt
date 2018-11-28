package com.battaglino.santiago.mvvmkotlin.data.service

import com.battaglino.santiago.mvvmkotlin.data.model.ApiResponse
import com.battaglino.santiago.mvvmkotlin.db.entity.Data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Santiago Battaglino.
 */
interface ApiService {

    @GET("3/gallery/search/time/{page}")
    fun getPagedImagesByQuery(
            @Path("page") page: Int,
            @Query("q") q: String,
            @Query("mature") mature: Boolean
    ): Observable<ApiResponse<Data>>
}
