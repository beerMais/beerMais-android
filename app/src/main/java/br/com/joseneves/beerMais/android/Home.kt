package br.com.joseneves.beerMais.android

import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.arch.lifecycle.Observer
import br.com.joseneves.beerMais.android.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.android.Database.Database
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var beerRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null

    private lateinit var beerDAO: BeerDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val database = Database.instance(this)
        beerDAO = database.beerDAO()
        val productsLiveData = beerDAO.all()
        productsLiveData.observe(this, Observer { beers ->
            beers?.let {
                mAdapter = BeerAdapter(beers)
                beerRecyclerView!!.adapter = mAdapter
            }
        })

        beerRecyclerView = findViewById(R.id.beer_recyclerView)
        beerRecyclerView!!.layoutManager = GridLayoutManager(this, 2)
        beerRecyclerView!!.addItemDecoration(RecyclerViewMargin())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                this.createBeer()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_about -> {
                // Handle the camera action
            }
            R.id.nav_help -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun createBeer() {
        NewBeer().show(supportFragmentManager,"new_beer_modal")
    }

    inner class RecyclerViewMargin: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            val totalWidth = parent.width
            val cardWidth = 150

            var sidePadding = (totalWidth - cardWidth) / 14
            sidePadding = Math.max(0, sidePadding)
            outRect.set(sidePadding, 0, 0, 0)
        }
    }
}
