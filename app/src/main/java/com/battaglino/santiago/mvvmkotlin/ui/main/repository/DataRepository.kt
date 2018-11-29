package com.battaglino.santiago.mvvmkotlin.ui.main.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.battaglino.santiago.mvvmkotlin.data.model.ApiResponse
import com.battaglino.santiago.mvvmkotlin.data.repository.UseCaseRepository
import com.battaglino.santiago.mvvmkotlin.data.service.ApiService
import com.battaglino.santiago.mvvmkotlin.db.AppDatabase
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Santiago Battaglino.
 */
class DataRepository @Inject
constructor(context: Application, private val mClient: ApiService) : UseCaseRepository<Data>(context) {

    private var mDataBase: AppDatabase? = null
    private val mDisposable: CompositeDisposable = CompositeDisposable()

    private var mFoundData: MutableLiveData<List<Data>> = MutableLiveData()
    private var mFoundSuggestions: LiveData<List<Data>> = MutableLiveData()

    override fun initLocalData() {
        mDataBase = AppDatabase.getDatabaseBuilder(context)
        setDataList(mDataBase!!.dataModel().loadList())
    }

    override fun addData(data: Data) {
        mDataBase!!.dataModel().insert(data)
        setData(mDataBase!!.dataModel().load(data.id))
    }

    override fun addDataList(dataList: List<Data>) {
        mDataBase!!.dataModel().insertAll(dataList)
        setDataList(mDataBase!!.dataModel().loadList())
    }

    override fun requestDataToServer() {

    }

    fun findDataByQueryFromServer(page: Int, q: String, mature: Boolean, qType: String?) {
        mClient.getPagedImagesByQuery(page, q, mature, qType)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResponse<Data>> {
                    override fun onSubscribe(d: Disposable) {
                        //mDisposable.add(d)
                    }

                    override fun onNext(dataListFromServer: ApiResponse<Data>) {
                        addFoundDataList(dataListFromServer.data, q)
                        //mDisposable.dispose()
                    }

                    override fun onError(e: Throwable) {
                        Log.e("error", e.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

    fun getSuggestions(): LiveData<List<Data>> {
        mFoundSuggestions = mDataBase!!.dataModel().loadSuggestions()
        return mFoundSuggestions
    }

    fun getDataByQuery(): LiveData<List<Data>> {
        return mFoundData
    }

    private fun addFoundDataList(data: List<Data>, q: String) {
        mDataBase!!.dataModel().insertAll(data)
        setFoundDataList(mDataBase!!.dataModel().loadByQuery(q))
    }

    private fun setFoundDataList(foundData: List<Data>) {
        mFoundData.value = foundData
    }

    fun getDataCached(queryString: String) {
        setFoundDataList(mDataBase!!.dataModel().loadByQuery(queryString))
    }

    fun getSingleData(id: String): LiveData<Data>? {
        return mDataBase!!.dataModel().load(id)
    }
}
