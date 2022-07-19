package com.goodslist.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodslist.databinding.FragmentDetailsBinding
import com.goodslist.model.data.Product

class DetailsFragment : Fragment() {
    companion object {
        const val BUNDLE_PRODUCT = "product"
        fun newInstance(bundle: Bundle) =
            DetailsFragment().apply {
                arguments = bundle
            }
    }

    lateinit var product: Product
    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = arguments?.getParcelable<Product>(BUNDLE_PRODUCT) ?: Product()
        with(binding){
            code.text = product.code
            name.text = product.name
            line
            remainder.text = product.remainder
            price.text = product.price
            retail.text = product.retail
            type.text = product.type
            alcohol.text = product.alcohol
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}