package com.battaglino.santiago.globallogic.ui.main.mvvm.viewmodel

import android.app.Application
import com.battaglino.santiago.globallogic.base.mvvm.viewmodel.BaseViewModel
import com.battaglino.santiago.globallogic.db.entity.Data
import com.battaglino.santiago.globallogic.ui.main.repository.DataRepository
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class MainDetailViewModel @Inject
constructor(application: Application, repository: DataRepository) : BaseViewModel<Data, DataRepository>(application) {

    init {
        useCaseRepository = repository
    }
}