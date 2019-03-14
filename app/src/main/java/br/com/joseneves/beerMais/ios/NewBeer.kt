package br.com.joseneves.beerMais.ios

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.DialogFragment
import br.com.joseneves.beerMais.ios.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.ios.Database.Database
import br.com.joseneves.beerMais.ios.Model.Beer
import kotlinx.android.synthetic.main.new_beer_modal.*

class NewBeer: DialogFragment() {
    private lateinit var newBeerDialog: Dialog
    private lateinit var beerDAO: BeerDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        newBeerDialog = Dialog(this.context!!)
        newBeerDialog.setContentView(R.layout.new_beer_modal)
        newBeerDialog.window.setBackgroundDrawableResource(android.R.color.transparent)

        val database = Database.instance(this.context!!)
        beerDAO = database.beerDAO()

        newBeerDialog.add_button.setOnClickListener {
            SaveBeer().execute()
            dismiss()
        }

        return newBeerDialog
    }

    private fun createBeer(): Beer {
        val brand = newBeerDialog.textInputLayoutBrand.editText?.text.toString()
        val value = newBeerDialog.textInputValue.editText?.text.toString().toFloat()
        val amount = newBeerDialog.textInputAmount.editText?.text.toString().toInt()

        return Beer(amount = amount, brand = brand, type = 1, value = value)
    }

    inner class SaveBeer: AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            beerDAO.add(createBeer())
            return null
        }
    }
}