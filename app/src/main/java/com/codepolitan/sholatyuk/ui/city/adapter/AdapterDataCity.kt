package com.codepolitan.sholatyuk.ui.city.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepolitan.sholatyuk.R
import com.codepolitan.sholatyuk.model.Data
import kotlinx.android.synthetic.main.item_data_city.view.*

/**
 * Created by yudisetiawan on 12/24/17.
 */
class AdapterDataCity(val listDataCity: List<Data>, val listenerAdapterDataCity: ListenerAdapterDataCity) : RecyclerView.Adapter<AdapterDataCity.ViewHolderItemDataCity>() {

    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolderItemDataCity =
            ViewHolderItemDataCity(
                    LayoutInflater.from(parent?.context).inflate(
                            R.layout.item_data_city, null
                    )
            )

    override fun onBindViewHolder(holder: ViewHolderItemDataCity?, position: Int) {
        holder?.itemView?.let {
            it.text_view_city_name_item_data_city.text = listDataCity[position].namaKota
        }
    }

    override fun getItemCount(): Int = listDataCity.size

    inner class ViewHolderItemDataCity(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.text_view_city_name_item_data_city.setOnClickListener(this)
            itemView.relative_layout_container_item_data_city.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            view?.id.let {
                when (it) {
                    R.id.relative_layout_container_item_data_city,
                    R.id.text_view_city_name_item_data_city -> {
                        listenerAdapterDataCity.clickItem(listDataCity[adapterPosition])
                    }
                    else -> {
                        /** nothing to do in here */
                    }
                }
            }
        }

    }

    interface ListenerAdapterDataCity {

        fun clickItem(data: Data)

    }

}

