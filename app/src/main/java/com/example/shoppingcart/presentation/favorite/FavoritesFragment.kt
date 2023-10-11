package com.example.shoppingcart.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.shoppingcart.R
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.FragmentFavoritesBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(), FavoriteAdapterCallback {
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoriteProductsAdapter: FavoriteProductsAdapter
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteProductsAdapter = FavoriteProductsAdapter(this)
        binding.rvFavorites.adapter = favoriteProductsAdapter
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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteProductsFlow.collect {
                    if (it.isNotEmpty()) {
                        binding.tvFavoritesEmpty.gone()
                        favoriteProductsAdapter.submitList(it)
                    } else {
                        binding.tvFavoritesEmpty.visible()
                    }
                }
            }
        }

    }

    override fun removeFromFavorite(product: Product) {
        viewModel.updateProduct(product = product)
    }

    override fun addProductToCart(product: Product) {
       viewModel.addProductToCart(product)
        Toast.makeText(
            requireContext(),
            getString(R.string.added_to_cart_successfully),
            Toast.LENGTH_SHORT
        ).show()
    }
}