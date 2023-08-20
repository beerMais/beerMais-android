package br.com.joseneves.beerMais.android.Beer

import androidx.lifecycle.Observer
import android.content.Context
import androidx.fragment.app.Fragment
import br.com.joseneves.beerMais.android.R
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import br.com.joseneves.beerMais.android.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.android.Database.Database
import br.com.joseneves.beerMais.android.Model.Beer
import br.com.joseneves.beerMais.android.NewBeer.NewBeerFragment
import br.com.joseneves.beerMais.android.databinding.FragmentBeerBinding
import com.google.firebase.analytics.FirebaseAnalytics


class BeerFragment : Fragment(), BeerContract.View {

    private var _binding: FragmentBeerBinding? = null
    private val binding get() = _binding!!

    private var beerRecyclerView: RecyclerView? = null
    private var mAdapter: BeerAdapter? = null

    private lateinit var beerDAO: BeerDAO
    private lateinit var presenter: BeerContract.Presenter

    companion object {

        fun newInstance(): BeerFragment {
            return BeerFragment()
        }
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
        _binding = FragmentBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.app_name)

        beerRecyclerView = view.findViewById(R.id.beer_recyclerView)
        setPresenter(BeerPresenter(this))

        val database = Database.instance(binding.root.context)
        beerDAO = database.beerDAO()

        beerDAO.all().observe(viewLifecycleOwner, Observer { beers ->
            beers?.let {
                controlHelpMessage(beers.count())
                presenter.calcRank(beers)

                mAdapter = BeerAdapter(beers)
                beerRecyclerView?.adapter = mAdapter

                mAdapter?.setOnItemClickListener(object : BeerAdapter.ClickListener {
                    override fun onClick(pos: Int, aView: View) {
                        val newBeer = NewBeerFragment()
                        newBeer.setBeer(beers[pos])
                        newBeer.show(activity?.supportFragmentManager!!, "new_beer_modal")
                    }
                })
            }
        })

        beerRecyclerView?.layoutManager =
            GridLayoutManager(binding.root.context, 2)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: BeerContract.Presenter) {
        this.presenter = presenter
    }

    override fun setRank(beer: Beer, economy: String) {
        binding.textViewBrand.text = beer.brand
        binding.textViewValue.text = presenter.getValueText(beer.value)
        binding.textViewAmount.text = presenter.getAmountText(beer.amount)
        binding.textViewEconomy.text = economy
        binding.imageViewBeer.setImageResource(presenter.getBeerImage(beer.amount))
    }

    private fun controlHelpMessage(beersCount: Int) {
        val beersVisibility = if (beersCount == 0)  View.GONE else View.VISIBLE
        val helperVisibility = if (beersCount == 0)  View.VISIBLE else View.GONE

        binding.beerRecyclerView.visibility = beersVisibility
        binding.helperText.visibility = helperVisibility
    }

}

