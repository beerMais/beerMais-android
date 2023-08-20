package br.com.joseneves.beerMais.android.About

import android.content.Context
import androidx.fragment.app.Fragment
import br.com.joseneves.beerMais.android.R
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import br.com.joseneves.beerMais.android.databinding.FragmentAboutBinding
import com.google.android.gms.ads.AdRequest
import com.google.firebase.analytics.FirebaseAnalytics


class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    companion object {

        fun newInstance(): AboutFragment {
            return AboutFragment()
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
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adView.loadAd(AdRequest.Builder().build())

        activity?.title = "Sobre"

        val desc = view.findViewById<WebView>(R.id.desc_webView)
        var text: String
        text = "<html><body><p align=\"justify\">"
        text += "A ideia desse app nasceu quando 3 amigos estavam fazendo as contas para decidir qual cerveja compensaria comprar para um churrasco. <br><br>Não tendo um foco apenas em cerveja, o Beer Mais pode ser usado para calcular o melhor custo-beneficio entre quaisquer bebidas. <br><br>A área em verde destaca a bebida de maior custo-beneficio e ordena a lista utilizando do mesmo critério. <br><br>Ícones: <br><a href=\"https://icons8.com\" style=\"color: #FFB300;\">https://icons8.com</a> <br><a href=\"https://www.flaticon.com\" style=\"color: #FFB300;\">https://www.flaticon.com</a>"
        text += "</p></body></html>"
        desc.loadData(text, "text/html", "utf-8")
    }

}

