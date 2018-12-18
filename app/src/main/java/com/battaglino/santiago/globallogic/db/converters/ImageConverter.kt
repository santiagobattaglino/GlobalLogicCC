package com.battaglino.santiago.globallogic.db.converters

import android.arch.persistence.room.TypeConverter
import com.battaglino.santiago.globallogic.db.entity.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


/**
 * Created by Santiago Battaglino.
 */
class ImageConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToImageList(data: String?): List<Image> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Image>>() {

        }.type

        return gson.fromJson<List<Image>>(data, listType)
    }

    @TypeConverter
    fun ImageListToString(images: List<Image>): String {
        return gson.toJson(images)
    }
}