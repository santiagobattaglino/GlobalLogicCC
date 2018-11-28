package com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import com.battaglino.santiago.mvvmkotlin.base.mvvm.viewmodel.BaseViewModel
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import com.battaglino.santiago.mvvmkotlin.ui.main.repository.DataRepository
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class MainViewModel @Inject
constructor(application: Application, datasitory: DataRepository) : BaseViewModel<Data, DataRepository>(application) {

    init {
        useCaseRepository = datasitory
    }

    fun getSuggestions(): LiveData<List<Data>>? {
        return useCaseRepository?.getSuggestions()
    }

    fun getData(): LiveData<List<Data>>? {
        return useCaseRepository?.getDataList()
    }

    fun getImagesByQuery(): LiveData<List<Data>>? {
        return useCaseRepository?.getDataByQuery()
    }

    fun findDataByQueryFromServer(page: Int, q: String, mature: Boolean) {
        useCaseRepository?.findDataByQueryFromServer(page, q, mature)
    }

    fun getDataCached(queryString: String) {
        useCaseRepository?.getDataCached(queryString)
    }
}