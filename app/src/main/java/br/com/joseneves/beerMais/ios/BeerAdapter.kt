package br.com.joseneves.beerMais.ios

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList

class BeerAdapter(private val mDataList: ArrayList<Beer>) : RecyclerView.Adapter<BeerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_beer, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.brand_textView.text = mDataList[position].name
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var brand_textView: TextView

        init {
            brand_textView = itemView.findViewById<View>(R.id.brand_textView) as TextView
        }
    }
}