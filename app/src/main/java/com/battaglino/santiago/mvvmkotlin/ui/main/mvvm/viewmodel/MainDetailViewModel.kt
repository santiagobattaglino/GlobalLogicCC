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
class MainDetailViewModel @Inject
constructor(application: Application, repository: DataRepository) : BaseViewModel<Data, DataRepository>(application) {

    init {
        useCaseRepository = repository
    }

    fun getSingleData(id: String): LiveData<Data>? {
        return useCaseRepository?.getSingleData(id)
    }
}