package com.battaglino.santiago.globallogic.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
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

        @PrimaryKey(autoGenerate = true)
        @NotNull
        @Expose
        var id: Int,

        @Expose
        @SerializedName("title")
        var title: String?,


        @Expose
        @SerializedName("description")
        var description: String?,


        @Expose
        @SerializedName("image")
        var image: String?
) : Parcelable {
    constructor() : this(0, "", "", "")
}