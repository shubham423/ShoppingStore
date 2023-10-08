package com.example.shoppingcart.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.FragmentCartBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(),CartAdapterCallback {

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
        cartProductsAdapter = CartProductsAdapter(this)
        binding.rvCart.adapter = cartProductsAdapter
        initObservers()
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartProductsFlow.collect {
                if (it.isNotEmpty()) {
                    binding.tvCartEmpty.gone()
                    binding.viewGroupCheckout.visible()
                    var price = 0
                    it.forEach { price=price.plus(it.price * it.quantity).toInt() }
                    cartProductsAdapter.submitList(it)
                    binding.tvSubtotalValue.text = "₹${price}"
                    binding.tvTotalValue.text = "₹${price.minus(4)}"
                } else {
                    binding.viewGroupCheckout.gone()
                    binding.tvCartEmpty.visible()
                }
            }
        }
    }

    override fun decrementQuantity(product: Product) {
       viewModel.updateProduct(product)
    }

    override fun incrementQuantity(product: Product) {
        viewModel.updateProduct(product)
    }
}