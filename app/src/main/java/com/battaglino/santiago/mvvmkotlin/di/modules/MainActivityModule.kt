package com.battaglino.santiago.mvvmkotlin.di.modules

import com.battaglino.santiago.mvvmkotlin.ui.main.activity.MainActivity
import com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.view.MainView
import com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.viewmodel.MainViewModel

import dagger.Module
import dagger.Provides

/**
 * Created by Santiago Battaglino.
 */
@Module
class MainActivityModule {

    @Provides
    internal fun provideMainView(
            activity: MainActivity,
            viewModel: MainViewModel): MainView {
        return MainView(activity, viewModel)
    }
}