package com.battaglino.santiago.mvvmkotlin.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.battaglino.santiago.mvvmkotlin.db.entity.Data

/**
 * Created by Santiago Battaglino.
 */
@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(data: List<Data>)

    @Query("select * from data")
    fun loadList(): LiveData<List<Data>>

    @Query("select * from data where id = :id")
    fun load(id: String?): LiveData<Data>

    @Query("select * from data where title like '%' || :query  || '%'")
    fun loadByQuery(query: String?): List<Data>

    @Query("select title from data")
    fun loadSuggestions(): LiveData<List<String>>
}
