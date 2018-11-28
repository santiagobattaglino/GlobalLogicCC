package com.battaglino.santiago.mvvmkotlin.base.mvvm.view

import android.arch.lifecycle.AndroidViewModel
import android.support.v7.app.AppCompatActivity

import java.lang.ref.WeakReference

/**
 * Created by Santiago Battaglino.
 */
abstract class BaseView<A : AppCompatActivity, V : AndroidViewModel>(baseActivity: A, protected var baseViewModel: V) {

    protected var baseActivity: WeakReference<A> = WeakReference(baseActivity)

    init {
        this.subscribeUiToLiveData()
    }

    protected abstract fun subscribeUiToLiveData()
}
