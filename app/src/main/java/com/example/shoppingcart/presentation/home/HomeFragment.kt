package com.example.shoppingcart.presentation.home

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
            viewModel.getProductsByCategory(category.id)
        })
        val fadeOutAnimation: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.heart_anim)
        categoryAdapter = CategoryAdapter(this, fadeOutAnimation)
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = categoryAdapter
        initClickListeners()
        initObservers()
    }

    private fun initObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productsStateFlow.collectLatest {
                categoryAdapter.submitList(it?.categories ?: emptyList())
            }
        }
    }

    private fun initClickListeners() {
        binding.btnCategories.setSafeOnClickListener {
            showCustomDialog()
            binding.btnCategories.gone()
            binding.btnCloseDialog.visible()
        }
        binding.btnCloseDialog.setSafeOnClickListener {
            binding.btnCloseDialog.gone()
            binding.btnCategories.visible()
            dialog.dismiss()
        }

        binding.ivFavorite.setSafeOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoritesFragment)
        }
    }

    private fun showCustomDialog() {

        val layoutManager = LinearLayoutManager(requireContext())
        dialogBinding.rvCategories.layoutManager = layoutManager
        dialogBinding.rvCategories.adapter = categoryFilterAdapter

//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
//        dialog.setCancelable(false)
        val layoutParams = dialog.window?.attributes
        layoutParams?.y = 200
        dialog.window?.attributes = layoutParams
        dialog.window?.setGravity(Gravity.BOTTOM)

        viewModel.getCategories().observe(viewLifecycleOwner) { categories ->
            categoryFilterAdapter.submitList(categories)
        }
    }

    override fun favoriteProduct(product: Product) {
        viewModel.updateProduct(product)
    }

    override fun unFavoriteProduct(product: Product) {
        viewModel.updateProduct(product)
    }

    override fun onResume() {
        super.onResume()
    }
}

