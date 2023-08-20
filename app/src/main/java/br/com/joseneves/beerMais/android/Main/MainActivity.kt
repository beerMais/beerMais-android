package br.com.joseneves.beerMais.android.Main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import br.com.joseneves.beerMais.android.About.AboutFragment
import br.com.joseneves.beerMais.android.Beer.BeerFragment
import br.com.joseneves.beerMais.android.NewBeer.NewBeerFragment
import br.com.joseneves.beerMais.android.R
import br.com.joseneves.beerMais.android.databinding.ActivityHomeBinding
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    companion object {
        lateinit var amplitude: Amplitude
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        amplitude = Amplitude(
            Configuration(
                apiKey = "39f15c55f5a446b6e1e5b6e9e424ec73",
                context = applicationContext
            )
        )

        setContentView(binding.root)

        this.changeFragment(BeerFragment())

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarHome.toolbar,
            R.string.empty,
            R.string.empty
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    this.changeFragment(BeerFragment())
                }

                R.id.nav_about -> {
                    this.changeFragment(AboutFragment())
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.appBarHome.toolbar.setOnMenuItemClickListener {
            createBeer()
            true
        }

        binding.appBarHome.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(
                GravityCompat.START,
                true
            )
        }

        supportActionBar?.setDisplayShowTitleEnabled(true)

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, javaClass.simpleName)
        FirebaseAnalytics.getInstance(binding.root.context).logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundle
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    private fun createBeer() {
        val newBeer = NewBeerFragment()
        newBeer.show(supportFragmentManager, "new_beer_modal")

        supportFragmentManager.executePendingTransactions()

        newBeer.dialog?.setOnDismissListener {
            getVisibleFragment().let {
                it?.javaClass?.simpleName.let { fragmentName ->
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, fragmentName)
                    FirebaseAnalytics.getInstance(binding.root.context).logEvent(
                        FirebaseAnalytics.Event.SCREEN_VIEW,
                        bundle
                    )
                }
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
