package com.battaglino.santiago.mvvmkotlin.ui.main.activity

import android.os.Bundle
import com.battaglino.santiago.mvvmkotlin.R
import com.battaglino.santiago.mvvmkotlin.base.activity.BaseActivity
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import com.battaglino.santiago.mvvmkotlin.global.Constants
import com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.view.MainDetailView
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class MainDetailActivity : BaseActivity() {

    @set:Inject
    internal var view: MainDetailView? = null

    private var mRepo: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main_detail)
        mRepo = intent.getParcelableExtra(Constants.INTENT_DATA)
        super.onCreate(savedInstanceState)
    }

    override fun injectThis() {
        AndroidInjection.inject(this)
    }

    fun getRepo(): Data? {
        return mRepo
    }
}