package br.com.joseneves.beerMais.android

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import br.com.joseneves.beerMais.android.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.android.Database.Database
import br.com.joseneves.beerMais.android.Model.Beer
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.new_beer_modal.*
import android.view.ViewGroup

class NewBeer: DialogFragment() {
    private lateinit var newBeerDialog: Dialog
    private lateinit var beerDAO: BeerDAO
    private lateinit var beer: Beer

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        newBeerDialog = Dialog(this.context!!)
        newBeerDialog.setContentView(R.layout.new_beer_modal)
        newBeerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        newBeerDialog.window?.setLayout(width, height)

        val adRequest = AdRequest.Builder().build()

        newBeerDialog.adView.loadAd(adRequest)

        populateBeer()

        val database = Database.instance(this.context!!)
        beerDAO = database.beerDAO()

        newBeerDialog.add_button.setOnClickListener {
            val beer = getBeer(null)
            if(beer != null) {
                this.beer = beer
                SaveBeer().execute()
                dismiss()
            }
        }

        newBeerDialog.delete_button.setOnClickListener {
            DeleteBeer().execute()
            dismiss()
        }

        newBeerDialog.save_button.setOnClickListener {
            val beer = getBeer(this.beer)
            if(beer != null) {
                this.beer = beer
                UpdateBeer().execute()
                dismiss()
            }
        }

        newBeerDialog.containerConstraintLayout.setOnClickListener {
            dismiss()
        }

        newBeerDialog.close_button.setOnClickListener {
            dismiss()
        }

        return newBeerDialog
    }

    fun setBeer(beer: Beer) {
        this.beer = beer
    }

    private fun getBeer(beer: Beer?): Beer? {
        val brand = newBeerDialog.textInputLayoutBrand.editText?.text.toString()

        val valueString = newBeerDialog.textInputValue.editText?.text.toString()
        var value = 0.0f
        if (valueString.isNotEmpty()) {
            value = valueString.toFloat()
        }

        val amountString = newBeerDialog.textInputAmount.editText?.text.toString()
        var amount = 0
        if(amountString.isNotEmpty()) {
            amount = amountString.toInt()
        }

        if(isValidBeer(brand, value, amount)) {
            if (beer ==  null) {
                return Beer(amount = amount, brand = brand, type = 1, value = value)
            } else {
                beer.amount = amount
                beer.brand = brand
                beer.type = 1
                beer.value = value

                return beer
            }
        }

        return null
    }

    private fun isValidBeer(brand: String, value: Float, amount: Int): Boolean {
        val isNotValidBrand = brand.isEmpty()
        val isNotValidValue = value < 0.01f
        val isNotValidAmount = amount < 1

        if(isNotValidBrand) {
            newBeerDialog.textInputLayoutBrand.error = getString(R.string.brandError)
        } else {
            newBeerDialog.textInputLayoutBrand.error = null
        }

        if(isNotValidValue) {
            newBeerDialog.textInputValue.error = getString(R.string.valueError)
        } else {
            newBeerDialog.textInputValue.error = null
        }

        if(isNotValidAmount) {
            newBeerDialog.textInputAmount.error = getString(R.string.amountError)
        } else {
            newBeerDialog.textInputAmount.error = null
        }

        return !isNotValidBrand && !isNotValidValue && !isNotValidAmount
    }

    private fun populateBeer() {
        if (!::beer.isInitialized) {
            return
        }

        newBeerDialog.textInputLayoutBrand.editText?.setText(beer.brand)
        newBeerDialog.textInputAmount.editText?.setText(beer.amount.toString())
        newBeerDialog.textInputValue.editText?.setText(beer.value.toString())

        newBeerDialog.add_linearLayout.visibility = View.GONE
        newBeerDialog.edit_linearLayout.visibility = View.VISIBLE
    }

    inner class SaveBeer(): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            beerDAO.add(beer)

            return null
        }
    }

    inner class DeleteBeer(): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            beerDAO.delete(beer)

            return null
        }
    }

    inner class UpdateBeer(): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            beerDAO.update(beer)

            return null
        }
    }
}