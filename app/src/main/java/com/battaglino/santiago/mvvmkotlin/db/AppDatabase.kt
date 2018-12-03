package com.battaglino.santiago.mvvmkotlin.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.battaglino.santiago.mvvmkotlin.db.converters.DateConverter
import com.battaglino.santiago.mvvmkotlin.db.converters.ImageConverter
import com.battaglino.santiago.mvvmkotlin.db.dao.DataDao
import com.battaglino.santiago.mvvmkotlin.db.dao.ImageDao
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import com.battaglino.santiago.mvvmkotlin.db.entity.Image
import com.battaglino.santiago.mvvmkotlin.global.Constants

/**
 * Created by Santiago Battaglino.
 * This class is used to create the database and get an instance of it.
 */
@Database(entities = [Data::class, Image::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class, ImageConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dataModel(): DataDao
    abstract fun imagesModel(): ImageDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabaseBuilder(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, Constants.APP_DB_NAME)
                        .build()
    }

    // If you need to update your database version, and add entities or new columns,
    // you gonna have to implement a Migration operation in order to avoid crashes or users losing data

    /*public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE product "
                    + " ADD COLUMN street TEXT, number TEXT, neighborhood TEXT");
        }
    };*/
}
