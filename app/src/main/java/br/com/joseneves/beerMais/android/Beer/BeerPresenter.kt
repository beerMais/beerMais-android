package br.com.joseneves.beerMais.android.Beer

import br.com.joseneves.beerMais.android.Model.Beer

class BeerPresenter(view: BeerContract.View) : BeerContract.Presenter {
    private var view: BeerContract.View? = view

    override fun onDestroy() {
        this.view = null
    }

    override fun onViewCreated() {
    }

    override fun calcRank(beers: List<Beer>) {
        var beer = Beer(0, 350, "Marca", 0, 0.0f)
        var economy = "R$ 0,00 /L"

        if (beers.count() > 1) {
            beer = beers.first()

            var economyString = String.format("%.2f", getEconomy(beers[0], beers[1]))
            economyString = economyString.replace(".", ",")

            economy = "R$ $economyString /L"
        }
        view?.setRank(beer, economy)
    }

    private fun getEconomy(beer1: Beer, beer2: Beer): Float {
        val value1 = getValuePerML(beer1.value, beer1.amount)
        val value2 = getValuePerML(beer2.value, beer2.amount)

        return (value2 - value1) * 1000
    }

    private fun getValuePerML(value: Float, amount: Int): Float {
        return value / amount.toFloat()
    }
}