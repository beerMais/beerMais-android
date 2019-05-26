package br.com.joseneves.beerMais.android.Beer

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import br.com.joseneves.beerMais.android.R
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import br.com.joseneves.beerMais.android.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.android.Database.Database
import br.com.joseneves.beerMais.android.Model.Beer
import br.com.joseneves.beerMais.android.NewBeer
import kotlinx.android.synthetic.main.fragment_beer.*


class BeerFragment : Fragment(), BeerContract.View {
    private var beerRecyclerView: RecyclerView? = null
    private var mAdapter: BeerAdapter? = null

    private lateinit var beerDAO: BeerDAO
    private lateinit var presenter: BeerContract.Presenter

    companion object {

        fun newInstance(): BeerFragment {
            return BeerFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Beer Mais"

        beerRecyclerView = view.findViewById(R.id.beer_recyclerView)
        setPresenter(BeerPresenter(this))

        val database = Database.instance(this.context!!)
        beerDAO = database.beerDAO()

        beerDAO.all().observe(this, Observer { beers ->
            beers?.let {
                presenter.calcRank(beers)

                mAdapter = BeerAdapter(beers)
                beerRecyclerView?.adapter = mAdapter

                mAdapter?.setOnItemClickListener(object : BeerAdapter.ClickListener {
                    override fun onClick(pos: Int, aView: View) {
                        val newBeer = NewBeer()
                        newBeer.setBeer(beers[pos])
                        newBeer.show(activity?.supportFragmentManager, "new_beer_modal")
                    }
                })
            }
        })

        beerRecyclerView?.layoutManager = GridLayoutManager(this.context, 2)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: BeerContract.Presenter) {
        this.presenter = presenter
    }

    override fun setRank(beer: Beer, economy: String) {
        textViewBrand.text = beer.brand
        textViewValue.text = presenter.getValueText(beer.value)
        textViewAmount.text = presenter.getAmountText(beer.amount)
        textViewEconomy.text = economy
        imageViewBeer.setImageResource(presenter.getBeerImage(beer.amount))
    }


}

