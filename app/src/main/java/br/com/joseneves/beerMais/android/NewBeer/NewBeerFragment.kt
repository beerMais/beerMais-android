package br.com.joseneves.beerMais.android.NewBeer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import android.view.View
import br.com.joseneves.beerMais.android.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.android.Database.Database
import br.com.joseneves.beerMais.android.Model.Beer
import com.google.android.gms.ads.AdRequest
import android.view.ViewGroup
import br.com.joseneves.beerMais.android.R
import br.com.joseneves.beerMais.android.databinding.NewBeerModalBinding
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewBeerFragment : DialogFragment() {

    private var _binding: NewBeerModalBinding? = null
    private val binding get() = _binding!!

    private lateinit var newBeerDialog: Dialog
    private lateinit var beerDAO: BeerDAO
    private lateinit var beer: Beer

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, javaClass.simpleName)
        FirebaseAnalytics.getInstance(context).logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = NewBeerModalBinding.inflate(layoutInflater)

        beerDAO = Database.instance(binding.root.context).beerDAO()
        newBeerDialog = Dialog(binding.root.context)
        newBeerDialog.setContentView(R.layout.new_beer_modal)
        newBeerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        newBeerDialog.window?.setLayout(width, height)

        binding.adView.loadAd(AdRequest.Builder().build())

        binding.textInputAmount.helperText = getString(R.string.amountHelp)

        populateBeer()
        setButtonsListeners()
        setDialogListeners()

        return newBeerDialog
    }

    fun setBeer(beer: Beer) {
        this.beer = beer
    }

    private fun getBeer(beer: Beer?): Beer? {
        val brand = binding.textInputLayoutBrand.editText?.text.toString()

        val valueString = binding.textInputValue.editText?.text.toString()
        var value = 0.0f
        if (valueString.isNotEmpty()) {
            value = valueString.toFloat()
        }

        val amountString = binding.textInputAmount.editText?.text.toString()
        var amount = 0
        if (amountString.isNotEmpty()) {
            amount = amountString.toInt()
        }

        if (isValidBeer(brand, value, amount)) {
            if (beer == null) {
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

        if (isNotValidBrand) {
            binding.textInputLayoutBrand.error = getString(R.string.brandError)
        } else {
            binding.textInputLayoutBrand.error = null
        }

        if (isNotValidValue) {
            binding.textInputValue.error = getString(R.string.valueError)
        } else {
            binding.textInputValue.error = null
        }

        if (isNotValidAmount) {
            binding.textInputAmount.error = getString(R.string.amountError)
        } else {
            binding.textInputAmount.error = null
        }

        return !isNotValidBrand && !isNotValidValue && !isNotValidAmount
    }

    private fun populateBeer() {
        if (!::beer.isInitialized) {
            return
        }

        binding.textInputLayoutBrand.editText?.setText(beer.brand)
        binding.textInputAmount.editText?.setText(beer.amount.toString())
        binding.textInputValue.editText?.setText(beer.value.toString())

        binding.addLinearLayout.visibility = View.GONE
        binding.editLinearLayout.visibility = View.VISIBLE
    }

    private fun setButtonsListeners() {
        binding.addButton.setOnClickListener {
            val beer = getBeer(null)
            if (beer != null) {
                this.beer = beer
                SaveBeer().execute() {
                    dismiss()
                }
            }
        }

        binding.deleteButton.setOnClickListener {
            DeleteBeer().execute() {
                dismiss()
            }
        }

        binding.saveButton.setOnClickListener {
            val beer = getBeer(this.beer)
            if (beer != null) {
                this.beer = beer
                UpdateBeer().execute() {
                    dismiss()
                }
            }
        }
    }

    private fun setDialogListeners() {
        val listener = { _: View -> dismiss() }

        binding.contentContainer.setOnClickListener(listener)
        binding.closeButton.setOnClickListener(listener)
    }

    inner class SaveBeer() {
        fun execute(completion: () -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                beerDAO.add(beer)
                withContext(Dispatchers.Main) {
                    completion()
                }
            }
        }
    }

    inner class DeleteBeer() {
        fun execute(completion: () -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                beerDAO.delete(beer)
                withContext(Dispatchers.Main) {
                    completion()
                }
            }
        }
    }

    inner class UpdateBeer() {
        fun execute(completion: () -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                beerDAO.update(beer)
                withContext(Dispatchers.Main) {
                    completion()
                }
            }
        }
    }
}