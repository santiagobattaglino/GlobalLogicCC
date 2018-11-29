package com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.battaglino.santiago.mvvmkotlin.R
import com.battaglino.santiago.mvvmkotlin.base.mvvm.view.BaseView
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import com.battaglino.santiago.mvvmkotlin.global.Constants
import com.battaglino.santiago.mvvmkotlin.ui.main.activity.MainActivity
import com.battaglino.santiago.mvvmkotlin.ui.main.activity.MainDetailActivity
import com.battaglino.santiago.mvvmkotlin.ui.main.adapter.DataAdapter
import com.battaglino.santiago.mvvmkotlin.ui.main.mvvm.viewmodel.MainViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

/**
 * Created by Santiago Battaglino.
 */
class MainView(activity: MainActivity, viewModel: MainViewModel) :
        BaseView<MainActivity, MainViewModel>(activity, viewModel),
        MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener,
        DataAdapter.OnViewHolderClick {

    private var mData: List<Data> = emptyList()
    private var mAdapter: DataAdapter = DataAdapter(baseActivity.get(), this)
    private var mQueryString: String = getQueryString()

    private val toolbar = baseActivity.get()?.toolbar
    private val searchView = baseActivity.get()?.searchView
    private val mainTitle = baseActivity.get()?.mainTitle
    private val recyclerView = baseActivity.get()?.recyclerView
    private val layoutManager = LinearLayoutManager(baseActivity.get(), LinearLayoutManager.VERTICAL, false)

    init {
        setUpToolbar()
        setUpSearchView()
        setTitle()
        setUpRecyclerView()
    }

    private fun setUpToolbar() {
        baseActivity.get()?.setSupportActionBar(toolbar)
    }

    private fun setUpSearchView() {
        searchView?.setEllipsize(true)
        searchView?.setOnQueryTextListener(this)
        baseViewModel.getDataCached(getQueryString())
    }

    private fun setUpRecyclerView() {
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = mAdapter
    }

    override fun subscribeUiToLiveData() {
        subscribeSuggestions()
        subscribeImagesByQuery()
    }

    private fun subscribeSuggestions() {
        baseViewModel.getSuggestions()?.observe(baseActivity.get()!!, Observer<List<Data>> { suggestions ->
            if (suggestions != null) {
                setSuggestions(suggestions)
            }
        })
    }

    private fun subscribeImagesByQuery() {
        baseViewModel.getImagesByQuery()?.observe(baseActivity.get()!!, Observer<List<Data>> { data ->
            if (data != null && !data.isEmpty()) {
                mData = data
                fillImagesAdapter(data)
            } else {
                searchView?.showSearch(false)
                searchView?.visibility = View.VISIBLE
            }
        })
    }

    private fun setSuggestions(data: List<Data>) {
        searchView?.setSuggestions(getSuggestions(data))
    }

    private fun getSuggestions(data: List<Data>): Array<String?> {
        var suggestions = arrayOfNulls<String>(0)
        data.forEach {
            suggestions += it.title
        }
        return suggestions
    }

    private fun fillImagesAdapter(data: List<Data>) {
        mAdapter.mData = data
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        setQueryString(query)
        doSearch()
        return false
    }

    private fun doSearch() {
        setTitle()
        baseViewModel.findDataByQueryFromServer(1, mQueryString, false, null)
    }

    private fun setTitle() {
        if (!mQueryString.isEmpty()) {
            mainTitle?.visibility = View.VISIBLE
            mainTitle?.text = String.format(Locale.getDefault(), "%s: %s",
                    baseActivity.get()?.getString(R.string.mainTitle), mQueryString
            )
        }
    }

    private fun setQueryString(query: String) {
        mQueryString = query
        val preferences = PreferenceManager.getDefaultSharedPreferences(baseActivity.get())
        preferences.edit().putString(Constants.QUERY, query).apply()
    }

    private fun getQueryString(): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(baseActivity.get())
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        mQueryString = preferences.getString(Constants.QUERY, "")
        return mQueryString
    }

    override fun onSearchViewShown() {

    }

    override fun onSearchViewClosed() {

    }

    override fun dataViewClickFromList(view: View, position: Int, data: Data) {
        val intent = Intent(baseActivity.get(), MainDetailActivity::class.java)
        intent.putExtra(Constants.INTENT_DATA, data)
        baseActivity.get()?.startActivity(intent)
    }
}
