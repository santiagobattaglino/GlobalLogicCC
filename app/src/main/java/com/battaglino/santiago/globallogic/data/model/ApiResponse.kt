package com.battaglino.santiago.globallogic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Santiago Battaglino.
 */
class ApiResponse<T> {

    @SerializedName("success")
    @Expose
    var success: Boolean = true

    @SerializedName("status")
    @Expose
    var status: Int = 200

    @SerializedName("data")
    @Expose
    var data: List<T> = emptyList()
}
