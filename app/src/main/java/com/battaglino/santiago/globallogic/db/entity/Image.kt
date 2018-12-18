package com.battaglino.santiago.globallogic.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import kotlinx.android.parcel.Parcelize

/**
 * Created by Santiago Battaglino.
 */
@Parcelize
@Entity(tableName = "images", indices = [Index(value = arrayOf("id"))])
data class Image @Ignore constructor(

        @PrimaryKey
        @NonNull
        @Expose
        var id: String,

        @Expose
        @SerializedName("title")
        var title: String?,

        @Expose
        @SerializedName("link")
        var link: String?
) : Parcelable {
    constructor() : this("", "", "")
}