package com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.view

import com.battaglino.santiago.mvvmkotlin.base.mvvm.view.BaseView
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import com.battaglino.santiago.mvvmkotlin.ui.main.activity.MainDetailActivity
import com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.viewmodel.MainDetailViewModel
import kotlinx.android.synthetic.main.activity_main_detail.*
import kotlinx.android.synthetic.main.content_main_detail.*

/**
 * Created by Santiago Battaglino.
 */
class MainDetailView(activity: MainDetailActivity, viewModel: MainDetailViewModel) :
        BaseView<MainDetailActivity, MainDetailViewModel>(activity, viewModel) {

    private var mData: Data? = null

    private val toolbar = baseActivity.get()?.toolbar
    private val name = baseActivity.get()?.name

    init {
        mData = baseActivity.get()?.getRepo()
        setUpToolbar()
        setValues()
    }

    private fun setUpToolbar() {
        baseActivity.get()?.setSupportActionBar(toolbar)
        baseActivity.get()?.supportActionBar?.setHomeButtonEnabled(true)
        baseActivity.get()?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun subscribeUiToLiveData() {

    }

    private fun setValues() {
        name?.text = mData?.title
    }
}