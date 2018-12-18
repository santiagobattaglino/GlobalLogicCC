package com.battaglino.santiago.globallogic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Santiago Battaglino.
 */
class ApiResponse<T> {

    @SerializedName("data")
    @Expose
    var data: List<T> = emptyList()
}
