package br.com.joseneves.beerMais.android.About

import android.support.v4.app.Fragment
import br.com.joseneves.beerMais.android.R
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View


class AboutFragment : Fragment() {
    companion object {

        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

}

