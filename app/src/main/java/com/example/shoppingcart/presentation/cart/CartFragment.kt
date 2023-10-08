package com.example.shoppingcart.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.shoppingcart.databinding.FragmentCartBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartProductsAdapter: CartProductsAdapter
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartProductsAdapter = CartProductsAdapter() {}
        binding.rvCart.adapter = cartProductsAdapter
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartProductsFlow.collect {
                if (it.isNotEmpty()) {
                    binding.tvCartEmpty.gone()
                    cartProductsAdapter.submitList(it)
                } else {
                    binding.tvCartEmpty.visible()
                }
            }
        }
    }
}