package br.com.joseneves.beerMais.android.Beer

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        holder.beer_imageView.setImageResource(beerPresenter.getBeerImage(mDataList[position].amount))
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
        internal var beer_imageView: ImageView

        init {
            amount_textView = itemView.findViewById(R.id.amount_textView)
            brand_textView = itemView.findViewById(R.id.brand_textView)
            value_textView = itemView.findViewById(R.id.value_textView)
            beer_imageView = itemView.findViewById(R.id.beer_imageView)

            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
        }
    }
}