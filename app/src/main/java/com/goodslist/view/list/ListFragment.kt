package com.goodslist.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodslist.R
import com.goodslist.databinding.FragmentListBinding
import com.goodslist.utils.AppState
import com.goodslist.utils.hide
import com.goodslist.utils.show
import com.goodslist.utils.showSnackBar
import com.goodslist.view.details.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class ListFragment : Fragment(), KoinComponent {
    companion object {
        const val ERROR = "Ошибка загрузки"
        const val RELOAD = "Повторить"
        fun newInstance() = ListFragment()
    }

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = ListAdapter()
    private val viewModel: ListViewModel by viewModel<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listView.adapter = adapter
        binding.listView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.setOnItemClickListener {
            activity?.supportFragmentManager?.apply {
                beginTransaction().apply {
                    add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(DetailsFragment.BUNDLE_PRODUCT, it)
                    }))
                    addToBackStack("")
                    commitAllowingStateLoss()
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner) { showData(it) }
        viewModel.getProductsList()
    }

    private fun showData(state: AppState) {
        when (state) {
            is AppState.Success -> {
                binding.loadingLayout.hide()
                adapter.setData(state.products)
            }
            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.main.show()
                binding.loadingLayout.hide()
                binding.main.showSnackBar(ERROR, RELOAD) {
                    viewModel.getProductsList()
                }
            }

        }
    }
}