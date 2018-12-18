package com.battaglino.santiago.globallogic.ui.main.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.battaglino.santiago.globallogic.data.repository.UseCaseRepository
import com.battaglino.santiago.globallogic.data.service.ApiService
import com.battaglino.santiago.globallogic.db.AppDatabase
import com.battaglino.santiago.globallogic.db.entity.Data
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class DataRepository @Inject
constructor(context: Application, private val mClient: ApiService) : UseCaseRepository<Data>(context) {

    private lateinit var mDataBase: AppDatabase
    private val mDisposable: CompositeDisposable = CompositeDisposable()

    private var mFoundImages: MutableLiveData<List<Data>> = MutableLiveData()
    private var mFoundSuggestions: MutableLiveData<List<String>> = MutableLiveData()

    override fun initLocalData() {
        mDataBase = AppDatabase.getDatabaseBuilder(context)
        GlobalScope.launch(Dispatchers.IO) {
            setDataList(mDataBase.dataModel().loadList())
        }
    }

    override fun addData(data: Data) {
        GlobalScope.launch(Dispatchers.IO) {
            mDataBase.dataModel().insert(data)
            setData(mDataBase.dataModel().load(data.id))
        }
    }

    override fun addDataList(dataList: List<Data>) {
        GlobalScope.launch(Dispatchers.IO) {
            mDataBase.dataModel().insertAll(dataList)
            setDataList(mDataBase.dataModel().loadList())
        }
    }

    private fun addFoundImages(data: List<Data>) {
        GlobalScope.launch(Dispatchers.IO) {
            mDataBase.dataModel().insertAll(data)
            setImages(mDataBase.dataModel().loadImages())
        }
    }

    override fun requestDataToServer() {

    }

    fun getRemoteDataList(dispose: Boolean) {
        mClient.getDataList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Data>> {
                    override fun onSubscribe(d: Disposable) {
                        if (dispose)
                            mDisposable.add(d)
                    }

                    override fun onNext(dataListFromServer: List<Data>) {
                        addFoundImages(dataListFromServer)
                        if (dispose)
                            mDisposable.dispose()
                    }

                    override fun onError(e: Throwable) {
                        Log.e("DataRepository", e.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

    suspend fun observeSuggestions(): LiveData<List<String>> {
        GlobalScope.async(Dispatchers.IO) {
            setSuggestions(mDataBase.dataModel().loadSuggestions())
        }.await()
        return mFoundSuggestions
    }

    suspend fun observeImages(): LiveData<List<Data>> {
        GlobalScope.async(Dispatchers.IO) {
            setImages(mDataBase.dataModel().loadImages())
        }.await()
        return mFoundImages
    }

    fun getAllLocalImagesByQuery(queryString: String) {
        GlobalScope.launch(Dispatchers.IO) {
            setImages(mDataBase.dataModel().loadImagesByQuery(queryString))
        }
    }

    private fun setSuggestions(suggestions: List<String>) {
        mFoundSuggestions.postValue(suggestions)
    }

    private fun setImages(images: List<Data>) {
        mFoundImages.postValue(images)
    }
}
