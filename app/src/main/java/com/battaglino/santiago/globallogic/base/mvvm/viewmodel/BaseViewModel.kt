package com.battaglino.santiago.globallogic.base.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import com.battaglino.santiago.globallogic.data.repository.UseCaseRepository

/**
 * Created by Santiago Battaglino.
 */
abstract class BaseViewModel<T, R : UseCaseRepository<T>>(application: Application) : AndroidViewModel(application) {

    protected var useCaseRepository: R? = null
}
