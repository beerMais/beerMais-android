package br.com.joseneves.beerMais.ios

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import kotlinx.android.synthetic.main.new_beer_modal.*

class NewBeer: DialogFragment() {
    private var beer: Beer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = Dialog(this.context!!)
        dialog.setContentView(R.layout.new_beer_modal)

        return dialog
    }
}