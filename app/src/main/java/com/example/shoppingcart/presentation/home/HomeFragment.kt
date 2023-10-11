package com.example.shoppingcart.presentation.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.R
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.DialogCatogoriesBinding
import com.example.shoppingcart.databinding.FragmentHomeBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.setSafeOnClickListener
import com.example.shoppingcart.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ProductsCallback {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var dialogBinding: DialogCatogoriesBinding
    private lateinit var dialog: Dialog
    private lateinit var categoryFilterAdapter: CategoryFilterAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    var currentClickedProduct: Product? = null

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogBinding = DialogCatogoriesBinding.inflate(layoutInflater)
        dialog = Dialog(requireContext())

        dialog.setContentView(dialogBinding.root)
        categoryFilterAdapter = CategoryFilterAdapter(categoryClicked = { category ->
            dialog.dismiss()
            if (category.id == -1) {
                viewModel.getAllProducts()
            } else {
                viewModel.getProductsByCategory(category.id)
            }

        })
        val fadeOutAnimation: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.scale_anim)
        categoryAdapter = CategoryAdapter(this, fadeOutAnimation)
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = categoryAdapter
        initClickListeners()
        initObservers()
    }

    private fun initObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsStateFlow.collectLatest {
                    categoryAdapter.submitList(it?.categories ?: emptyList())
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartCountFlow.collectLatest { count ->
                    if (count > 0) {
                        binding.tvCartCount.visible()
                        binding.tvCartCount.text = count.toString()
                    } else {
                        binding.tvCartCount.gone()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoriesFlow.collectLatest { categories ->
                    if (categories.isNotEmpty()) {
                        categoryFilterAdapter.submitList(categories)
                        showCustomDialog()
                    }

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchedCartProduct.collectLatest { cartProduct ->
                    Log.d("HomeFragm", "$cartProduct")
                    if (cartProduct == null) {
                        currentClickedProduct?.let {
                            viewModel.addProductToCart(it)

                            Toast.makeText(
                                requireContext(),
                                getString(R.string.added_to_cart_successfully),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val existingQuantity = cartProduct.quantity
                        currentClickedProduct?.copy(quantity = existingQuantity + 1)?.let {
                            viewModel.updateCarteProduct(it)
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.added_to_cart_successfully),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }


    }

    private fun initClickListeners() {
        binding.btnCategories.setSafeOnClickListener {
            viewModel.getCategories()
            binding.btnCategories.gone()
            binding.btnCloseDialog.visible()
        }
        binding.btnCloseDialog.setSafeOnClickListener {
            dialog.dismiss()
        }

        binding.ivFavorite.setSafeOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoritesFragment)
        }
        binding.ivCart.setSafeOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }
    }

    private fun showCustomDialog() {
        val layoutManager = LinearLayoutManager(requireContext())
        dialogBinding.rvCategories.layoutManager = layoutManager
        dialogBinding.rvCategories.adapter = categoryFilterAdapter

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val layoutParams = dialog.window?.attributes
        layoutParams?.y = 250
        dialog.window?.attributes = layoutParams
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.attributes = layoutParams

        dialog.show()

        dialog.setOnDismissListener {
            binding.btnCloseDialog.gone()
            binding.btnCategories.visible()
        }
    }

    override fun favoriteProduct(product: Product) {
        viewModel.updateProduct(product)
    }

    override fun unFavoriteProduct(product: Product) {
        viewModel.updateProduct(product)
    }

    override fun addProductToCart(product: Product) {
        currentClickedProduct=product
        viewModel.getCartProductById(product.id)
    }
}

