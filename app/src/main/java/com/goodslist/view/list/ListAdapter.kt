package com.goodslist.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.goodslist.databinding.ItemListBinding
import com.goodslist.model.data.Product

class ListAdapter : RecyclerView.Adapter<ListAdapter.MainViewHolder>() {
    private var products: List<Product> = listOf()
    private var onItemClickListener : (Product) -> Unit = {}

    fun setOnItemClickListener(onItemClickListener : (Product) -> Unit){
        this.onItemClickListener = onItemClickListener
    }

    fun setData(data: List<Product>) {
        products = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class MainViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                code.text = product.code
                name.text = product.name
                remainder.text = product.remainder.toString()
                price.text = product.price.toString()
                root.setOnClickListener {
                    onItemClickListener(product)
                }
            }
        }
    }
}