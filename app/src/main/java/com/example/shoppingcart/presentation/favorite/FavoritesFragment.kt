package com.example.shoppingcart.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shoppingcart.databinding.FragmentFavoritesBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {
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
        favoriteProductsAdapter = FavoriteProductsAdapter(removeFromFavorite = { product ->
            viewModel.updateProduct(product = product)
        })
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