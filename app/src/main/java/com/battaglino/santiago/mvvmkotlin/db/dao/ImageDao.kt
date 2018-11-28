package com.battaglino.santiago.mvvmkotlin.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.battaglino.santiago.mvvmkotlin.db.entity.Image

/**
 * Created by Santiago Battaglino.
 */
@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(image: Image)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(images: List<Image>)

    @Query("select * from images")
    fun loadList(): LiveData<List<Image>>

    @Query("select * from images where id = :id")
    fun load(id: String?): LiveData<Image>

    @Query("select * from images where title like '%' || :query  || '%'")
    fun loadByQuery(query: String?): List<Image>

    @Query("select * from images")
    fun loadSuggestions(): LiveData<List<Image>>
}