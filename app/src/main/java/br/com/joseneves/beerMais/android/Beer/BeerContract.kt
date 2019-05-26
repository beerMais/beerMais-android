package br.com.joseneves.beerMais.android.Beer

import br.com.joseneves.beerMais.android.BasePresenter
import br.com.joseneves.beerMais.android.BaseView
import br.com.joseneves.beerMais.android.Model.Beer

interface BeerContract {
    interface Presenter : BasePresenter {
        fun onViewCreated()
        fun calcRank(beers: List<Beer>)
        fun getAmountText(amount: Int): String
    }

    interface View : BaseView<Presenter> {
        fun setRank(beer: Beer, economy: String)
    }
}