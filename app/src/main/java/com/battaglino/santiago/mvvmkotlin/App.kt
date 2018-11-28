package com.battaglino.santiago.mvvmkotlin

import android.app.Activity
import android.app.Application
import com.battaglino.santiago.mvvmkotlin.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class App : Application(), HasActivityInjector {

    @set:Inject
    internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }
}
