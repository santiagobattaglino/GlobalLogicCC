package com.battaglino.santiago.globallogic.ui.main.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import com.battaglino.santiago.globallogic.base.mvvm.viewmodel.BaseViewModel
import com.battaglino.santiago.globallogic.db.entity.Data
import com.battaglino.santiago.globallogic.ui.main.repository.DataRepository
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class MainViewModel @Inject
constructor(application: Application, datasitory: DataRepository) : BaseViewModel<Data, DataRepository>(application) {

    init {
        useCaseRepository = datasitory
    }

    suspend fun observeSuggestions(): LiveData<List<String>>? {
        return useCaseRepository?.observeSuggestions()
    }

    suspend fun observeImages(): LiveData<List<Data>>? {
        return useCaseRepository?.observeImages()
    }

    fun getAllLocalImagesByQuery(queryString: String) {
        useCaseRepository?.getAllLocalImagesByQuery(queryString)
    }

    fun getRemoteImages(page: Int, queryString: String, mature: Boolean, qType: String?, dispose: Boolean) {
        useCaseRepository?.getRemoteImages(page, queryString, mature, qType, dispose)
    }
}