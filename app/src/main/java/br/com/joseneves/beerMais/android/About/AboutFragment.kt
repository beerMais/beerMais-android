package br.com.joseneves.beerMais.android.About

import android.support.v4.app.Fragment
import br.com.joseneves.beerMais.android.R
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Sobre"

        val desc = view.findViewById<WebView>(R.id.desc_webView)
        var text: String
        text = "<html><body><p align=\"justify\">"
        text += "A ideia desse app nasceu quando 3 amigos estavam fazendo as contas para decidir qual cerveja compensaria comprar para um churrasco. <br><br>Não tendo um foco apenas em cerveja, o Beer Mais pode ser usado para calcular o melhor custo-beneficio entre quaisquer bebidas. <br><br>A área em verde destaca a bebida de maior custo-beneficio e ordena a lista utilizando do mesmo critério. <br><br>Ícones: <br><a href=\"https://icons8.com\" style=\"color: #FFB300;\">https://icons8.com</a> <br><a href=\"https://www.flaticon.com\" style=\"color: #FFB300;\">https://www.flaticon.com</a>"
        text += "</p></body></html>"
        desc.loadData(text, "text/html", "utf-8")
    }

}

