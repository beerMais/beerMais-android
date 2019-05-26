package br.com.joseneves.beerMais.android.Beer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.joseneves.beerMais.android.Model.Beer
import br.com.joseneves.beerMais.android.R

class BeerAdapter(private val mDataList: List<Beer>) : RecyclerView.Adapter<BeerAdapter.MyViewHolder>() {
    lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_beer, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val amount = mDataList[position].amount
        val beerPresenter = BeerPresenter(null)

        holder.amount_textView.text = beerPresenter.getAmountText(amount)
        holder.brand_textView.text = mDataList[position].brand
        holder.value_textView.text = beerPresenter.getValueText(mDataList[position].value)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var amount_textView: TextView
        internal var brand_textView: TextView
        internal var value_textView: TextView

        init {
            amount_textView = itemView.findViewById<View>(R.id.amount_textView) as TextView
            brand_textView = itemView.findViewById<View>(R.id.brand_textView) as TextView
            value_textView = itemView.findViewById<View>(R.id.value_textView) as TextView

            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
        }
    }
}