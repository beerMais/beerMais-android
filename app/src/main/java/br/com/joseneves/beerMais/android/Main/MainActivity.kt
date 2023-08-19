package br.com.joseneves.beerMais.android.Main

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import br.com.joseneves.beerMais.android.About.AboutFragment
import br.com.joseneves.beerMais.android.Beer.BeerFragment
import br.com.joseneves.beerMais.android.NewBeer.NewBeerFragment
import br.com.joseneves.beerMais.android.R
import br.com.joseneves.beerMais.android.databinding.ActivityHomeBinding
import br.com.joseneves.beerMais.android.databinding.AppBarHomeBinding
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var bindingAppBar: AppBarHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        bindingAppBar = AppBarHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(bindingAppBar.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)

        this.changeFragment(BeerFragment())

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, bindingAppBar.toolbar, R.string.empty, R.string.empty
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        FirebaseAnalytics.getInstance(this.baseContext)
            .setCurrentScreen(this, javaClass.simpleName, javaClass.simpleName)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.nav_home)?.isVisible = true

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
        when (item.itemId) {
            R.id.nav_home -> {
                this.changeFragment(BeerFragment())
            }

            R.id.nav_about -> {
                this.changeFragment(AboutFragment())
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun createBeer() {
        val newBeer = NewBeerFragment()
        newBeer.show(supportFragmentManager, "new_beer_modal")

        supportFragmentManager.executePendingTransactions()

        newBeer.dialog?.setOnDismissListener {
            getVisibleFragment().let {
                val fragmentName = it!!.javaClass.simpleName
                FirebaseAnalytics.getInstance(this.baseContext).setCurrentScreen(this, fragmentName, fragmentName)
            }
        }
    }

    private fun getVisibleFragment(): Fragment? {
        supportFragmentManager.fragments.forEach {
            if (it != null && it.isVisible) {
                return it
            }
        }
        return null
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment, fragment)
        transaction.disallowAddToBackStack()

        transaction.commit()
    }

}
