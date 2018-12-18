package com.battaglino.santiago.globallogic.data.service

import com.battaglino.santiago.globallogic.db.entity.Data
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Santiago Battaglino.
 */
interface ApiService {

    @GET("list")
    fun getDataList(
    ): Observable<List<Data>>
}
