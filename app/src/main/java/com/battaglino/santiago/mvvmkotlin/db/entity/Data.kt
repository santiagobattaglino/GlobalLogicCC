package com.battaglino.santiago.mvvmkotlin.db.entity

import android.arch.persistence.room.*
import android.os.Parcelable
import com.battaglino.santiago.mvvmkotlin.db.converters.ImageConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import kotlinx.android.parcel.Parcelize

/**
 * Created by Santiago Battaglino.
 */
@Parcelize
@Entity(tableName = "data", indices = [Index(value = arrayOf("id"))])
data class Data @Ignore constructor(

        @PrimaryKey
        @NonNull
        @Expose
        var id: String,

        @Expose
        @SerializedName("title")
        var title: String?,

        @Expose
        @SerializedName("images")
        @TypeConverters(ImageConverter::class)
        var images: List<Image>
) : Parcelable {
    constructor() : this("", "", emptyList())
}