package ie.wit.onlineshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.onlineshop.databinding.CardProductBinding
import ie.wit.onlineshop.main.MainApp
import ie.wit.onlineshop.models.OnlineshopModel
import java.util.*
import kotlin.collections.ArrayList

interface OnlineshopListener {
    fun onProductClick(product: OnlineshopModel, position : Int)
}

class OnlineshopAdapter constructor(private var products: List<OnlineshopModel>,
                                   private val listener: OnlineshopListener) :
    RecyclerView.Adapter<OnlineshopAdapter.MainHolder>(), Filterable {

    lateinit var app: MainApp
    var productFilterList:kotlin.collections.List<OnlineshopModel>

    init {
        productFilterList = products
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        //app = application as MainApp

        return MainHolder(binding)

    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val product = productFilterList[holder.adapterPosition]
        holder.bind(product, listener)
    }

    override fun getItemCount(): Int = productFilterList.size

    class MainHolder(private val binding : CardProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: OnlineshopModel, listener: OnlineshopListener) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price.toString()
            binding.productBrand.text = product.brand
            binding.productType.text = product.type
            Picasso.get().load(product.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onProductClick(product, adapterPosition) }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(name: CharSequence?): FilterResults {
                val charSearch = name.toString()
                if (charSearch.isEmpty()) {
                    productFilterList = products
                } else {
                    productFilterList = products.filter { p -> p.name.contains(charSearch) }
                }
                val filterResults = FilterResults()
                filterResults.values = productFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productFilterList = results?.values as List<OnlineshopModel>
                notifyDataSetChanged()
            }

        }
    }}