package com.battaglino.santiago.globallogic.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.battaglino.santiago.globallogic.App
import com.battaglino.santiago.globallogic.db.AppDatabase
import com.battaglino.santiago.globallogic.db.dao.DataDao
import com.battaglino.santiago.globallogic.global.Constants
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

    /*@Provides
    @Singleton
    internal fun provideAppDatabase(application: App): AppDatabase {
        return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                Constants.APP_DB_NAME
        ).fallbackToDestructiveMigration().build()
    }*/

    @Singleton
    @Provides
    internal fun provideDataDao(appDatabase: AppDatabase): DataDao {
        return appDatabase.dataModel()
    }
}
