package com.battaglino.santiago.mvvmkotlin.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context

/**
 * Created by Santiago Battaglino.
 */
abstract class UseCaseRepository<T>(protected var context: Context) {

    var mContext: Context? = context

    private var mDataList: LiveData<List<T>> = MutableLiveData()

    private var mData: LiveData<T> = MutableLiveData()

    init {
        this.initLocalData()
    }

    abstract fun initLocalData()

    abstract fun addData(data: T)

    fun getData(): LiveData<T> {
        return mData
    }

    fun setData(data: LiveData<T>) {
        mData = data
    }

    abstract fun addDataList(dataList: List<T>)

    fun getDataList(): LiveData<List<T>> {
        return mDataList
    }

    fun setDataList(dataList: LiveData<List<T>>) {
        mDataList = dataList
    }

    abstract fun requestDataToServer()
}
