package br.com.joseneves.beerMais.android

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.joseneves.beerMais.android.Model.Beer

class BeerAdapter(private val mDataList: List<Beer>) : RecyclerView.Adapter<BeerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_beer, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val amount = mDataList[position].amount

        holder.amount_textView.text = amount.toString() + getAmountLabel(amount)
        holder.brand_textView.text = mDataList[position].brand
        holder.value_textView.text = "R$ " + mDataList[position].value.toString().replace('.', ',')
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    private fun getAmountLabel(amount: Int): String {
        var amountLabel = "ml"

        if (amount >= 1000) {
            amountLabel = "L"
        }
        return amountLabel
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var amount_textView: TextView
        internal var brand_textView: TextView
        internal var value_textView: TextView

        init {
            amount_textView = itemView.findViewById<View>(R.id.amount_textView) as TextView
            brand_textView = itemView.findViewById<View>(R.id.brand_textView) as TextView
            value_textView = itemView.findViewById<View>(R.id.value_textView) as TextView
        }
    }
}