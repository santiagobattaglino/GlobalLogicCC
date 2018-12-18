package com.battaglino.santiago.globallogic.di.modules

import com.battaglino.santiago.globallogic.ui.main.activity.MainActivity
import com.battaglino.santiago.globallogic.ui.main.activity.MainDetailActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Santiago Battaglino.
 *
 *
 * This is a given module to dagger. We map all our activities here.
 * And Dagger know our activities in compile time.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainDetailActivityModule::class])
    internal abstract fun bindMainDetailActivity(): MainDetailActivity
}
