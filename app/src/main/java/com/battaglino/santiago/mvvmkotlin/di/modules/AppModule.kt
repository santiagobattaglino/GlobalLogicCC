package com.battaglino.santiago.mvvmkotlin.di.modules

import android.app.Application
import com.battaglino.santiago.mvvmkotlin.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Santiago Battaglino.
 * TODO add Room Db Module
 */
@Module(includes = [RetrofitModule::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: App): Application {
        return application
    }
}
