package com.battaglino.santiago.globallogic.data.service

import com.battaglino.santiago.globallogic.data.model.ApiResponse
import com.battaglino.santiago.globallogic.db.entity.Data
import com.battaglino.santiago.globallogic.global.Constants

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
            @Query("mature") mature: Boolean,
            @Query("q_type") qType: String?,
            @Query("perPage") perPage: Int = Constants.CONFIG_RESULTS_PER_PAGE
    ): Observable<ApiResponse<Data>>
}
