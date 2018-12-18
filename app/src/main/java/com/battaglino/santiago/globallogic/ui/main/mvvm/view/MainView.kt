package com.battaglino.santiago.globallogic.ui.main.mvvm.view

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.battaglino.santiago.globallogic.R
import com.battaglino.santiago.globallogic.base.mvvm.view.BaseView
import com.battaglino.santiago.globallogic.db.entity.Data
import com.battaglino.santiago.globallogic.global.Constants
import com.battaglino.santiago.globallogic.global.EndlessRecyclerViewScrollListener
import com.battaglino.santiago.globallogic.ui.main.activity.MainActivity
import com.battaglino.santiago.globallogic.ui.main.activity.MainDetailActivity
import com.battaglino.santiago.globallogic.ui.main.adapter.DataAdapter
import com.battaglino.santiago.globallogic.ui.main.mvvm.viewmodel.MainViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
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

    var listOfJobs: MutableList<Job>? = null
    private var queryTextChangedJob: Job? = null

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
        baseViewModel.getAllLocalImagesByQuery(getQueryString())
    }

    private fun setUpRecyclerView() {
        recyclerView?.layoutManager = layoutManager

        val mScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                baseViewModel.getRemoteImages(page, mQueryString, false, null, false)
            }
        }
        recyclerView?.addOnScrollListener(mScrollListener)

        recyclerView?.adapter = mAdapter
    }

    override fun subscribeUiToLiveData() {
        listOfJobs = mutableListOf()
        subscribeSuggestions()
        subscribeImages()
    }

    private fun subscribeSuggestions() {
        val suggestionsJob: Job? = GlobalScope.launch(Dispatchers.Main) {
            baseViewModel.observeSuggestions()?.observe(baseActivity.get()!!, Observer<List<String>> { suggestions ->
                if (suggestions != null) {
                    setSuggestions(suggestions)
                }
            })
        }
        if (suggestionsJob != null)
            listOfJobs?.add(suggestionsJob)
    }

    private fun subscribeImages() {
        val imagesJob: Job? = GlobalScope.launch(Dispatchers.Main) {
            baseViewModel.observeImages()?.observe(baseActivity.get()!!, Observer<List<Data>> { data ->
                if (data != null && !data.isEmpty()) {
                    mData = data
                    fillImagesAdapter(data)
                    searchView?.closeSearch()
                    searchView?.visibility = View.GONE
                } else {
                    searchView?.showSearch(false)
                    searchView?.visibility = View.VISIBLE
                }
            })
        }
        if (imagesJob != null)
            listOfJobs?.add(imagesJob)
    }

    private fun setSuggestions(suggestions: List<String>) {
        searchView?.setSuggestions(suggestions.toTypedArray())
    }

    private fun fillImagesAdapter(data: List<Data>) {
        mAdapter.mData = data
    }

    override fun onQueryTextChange(queryString: String): Boolean {
        if (!queryString.isEmpty()) {
            queryTextChangedJob?.cancel()
            queryTextChangedJob = GlobalScope.launch(Dispatchers.Main) {
                delay(500)
                setQueryString(queryString)
                doSearch()
            }
        }
        return false
    }

    override fun onQueryTextSubmit(queryString: String): Boolean {
        return false
    }

    private fun doSearch() {
        setTitle()
        baseViewModel.getRemoteImages(0, mQueryString, false, null, false)
    }

    private fun setTitle() {
        if (!mQueryString.isEmpty()) {
            mainTitle?.visibility = View.VISIBLE
            mainTitle?.text = String.format(Locale.getDefault(), baseActivity.get()?.getString(R.string.mainTitle)!!, mQueryString
            )
        }
    }

    private fun setQueryString(queryString: String) {
        mQueryString = queryString
        val preferences = PreferenceManager.getDefaultSharedPreferences(baseActivity.get())
        preferences.edit().putString(Constants.QUERY, queryString).apply()
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
        intent.putExtra(Constants.EXTRA_DATA, data)

        val transitionName = baseActivity.get()?.getString(R.string.dataTransition)

        val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(baseActivity.get(), view, transitionName)
        baseActivity.get()?.startActivity(intent, transitionActivityOptions.toBundle())
    }
}
