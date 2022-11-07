package ie.wit.onlineshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.onlineshop.databinding.CardProductBinding
import ie.wit.onlineshop.models.OnlineshopModel

class OnlineshopAdapter constructor(private var products: List<OnlineshopModel>) :
    RecyclerView.Adapter<OnlineshopAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val product = products[holder.adapterPosition]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    class MainHolder(private val binding : CardProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: OnlineshopModel) {
            binding.productName.text = product.name
//            binding.productId.text.toString().toInt() = product.id
            binding.productId.text = product.id.toString()
//            product.name = binding.productName.text.toString()
//            product.id = binding.productId.text.toString().toInt()
        }
    }
}