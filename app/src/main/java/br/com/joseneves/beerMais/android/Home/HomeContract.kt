package br.com.joseneves.beerMais.android.Home

import br.com.joseneves.beerMais.android.BasePresenter
import br.com.joseneves.beerMais.android.BaseView
import br.com.joseneves.beerMais.android.Model.Beer

interface HomeContract {
    interface Presenter: BasePresenter {
        fun onViewCreated()
        fun calcRank(beers: List<Beer>)
    }

    interface View: BaseView<Presenter> {
        fun setRank(beer: Beer, economy: String)
    }
}