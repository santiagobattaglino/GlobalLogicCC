package com.battaglino.santiago.globallogic.ui.main.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.battaglino.santiago.globallogic.R
import com.battaglino.santiago.globallogic.base.activity.BaseActivity
import com.battaglino.santiago.globallogic.ui.main.mvvm.view.MainView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @set:Inject
    internal var view: MainView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        val iterate = view?.listOfJobs!!.listIterator()
        while (iterate.hasNext()) {
            val job: Job = iterate.next()
            job.cancel()
        }
    }

    override fun injectThis() {
        AndroidInjection.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            searchView.showSearch(true)
            searchView.visibility = View.VISIBLE
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (searchView.isSearchOpen) {
            searchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }
}
