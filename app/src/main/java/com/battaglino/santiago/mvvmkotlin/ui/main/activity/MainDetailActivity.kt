package com.battaglino.santiago.mvvmkotlin.ui.main.activity

import android.os.Bundle
import android.view.View
import com.battaglino.santiago.mvvmkotlin.R
import com.battaglino.santiago.mvvmkotlin.base.activity.BaseActivity
import com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.view.MainDetailView
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class MainDetailActivity : BaseActivity() {

    @set:Inject
    internal var view: MainDetailView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main_detail)
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    override fun injectThis() {
        AndroidInjection.inject(this)
    }
}