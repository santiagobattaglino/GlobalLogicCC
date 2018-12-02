package com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.view

import com.battaglino.santiago.mvvmkotlin.R
import com.battaglino.santiago.mvvmkotlin.base.mvvm.view.BaseView
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import com.battaglino.santiago.mvvmkotlin.global.Constants
import com.battaglino.santiago.mvvmkotlin.ui.main.activity.MainDetailActivity
import com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.viewmodel.MainDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_detail.*

/**
 * Created by Santiago Battaglino.
 */
class MainDetailView(activity: MainDetailActivity, viewModel: MainDetailViewModel) :
        BaseView<MainDetailActivity, MainDetailViewModel>(activity, viewModel) {

    private val toolbar = baseActivity.get()?.toolbar
    private val image = baseActivity.get()?.image

    init {
        setUpToolbar()
        setValues()
    }

    private fun setUpToolbar() {
        baseActivity.get()?.setSupportActionBar(toolbar)
        baseActivity.get()?.supportActionBar?.setHomeButtonEnabled(true)
        baseActivity.get()?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        baseActivity.get()?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun subscribeUiToLiveData() {

    }

    private fun setValues() {
        val data: Data? = baseActivity.get()?.intent?.getParcelableExtra(Constants.INTENT_DATA)

        if (data!!.images.isNotEmpty() && data.isAlbum) {
            Picasso.get()
                    .load(data.images[0].link)
                    .fit()
                    .placeholder(R.drawable.picasso_placeholder)
                    .error(R.drawable.picasso_error)
                    .centerCrop()
                    .into(image)
        } else {
            Picasso.get()
                    .load(data.link)
                    .fit()
                    .placeholder(R.drawable.picasso_placeholder)
                    .error(R.drawable.picasso_error)
                    .centerCrop()
                    .into(image)
        }
    }
}