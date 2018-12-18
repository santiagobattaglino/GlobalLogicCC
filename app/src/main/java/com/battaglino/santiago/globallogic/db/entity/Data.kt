package com.battaglino.santiago.globallogic.db.entity

import android.arch.persistence.room.*
import android.os.Parcelable
import com.battaglino.santiago.globallogic.db.converters.ImageConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

/**
 * Created by Santiago Battaglino.
 */
@Parcelize
@Entity(tableName = "data", indices = [Index(value = arrayOf("id"))])
data class Data @Ignore constructor(

        @PrimaryKey
        @NotNull
        @Expose
        var id: String,

        @Expose
        @SerializedName("title")
        var title: String?,

        @Expose
        @SerializedName("is_album")
        var isAlbum: Boolean,

        @Expose
        @SerializedName("link")
        var link: String,

        @Expose
        @SerializedName("images")
        @TypeConverters(ImageConverter::class)
        var images: List<Image>
) : Parcelable {
    constructor() : this("", "", false, "", emptyList())
}