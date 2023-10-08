package com.example.shoppingcart.presentation.home

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.DialogCatogoriesBinding
import com.example.shoppingcart.databinding.FragmentHomeBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.presentation.CategoryAdapter
import com.example.shoppingcart.presentation.CategoryFilterAdapter
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.setSafeOnClickListener
import com.example.shoppingcart.util.toFavoriteProductEntity
import com.example.shoppingcart.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var dialogBinding:DialogCatogoriesBinding
    private lateinit var dialog: Dialog
    private lateinit var categoryFilterAdapter:CategoryFilterAdapter
    private lateinit var categoryAdapter:CategoryAdapter
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogBinding= DialogCatogoriesBinding.inflate(layoutInflater)
        dialog= Dialog(requireContext())

        dialog.setContentView(dialogBinding.root)
        categoryFilterAdapter = CategoryFilterAdapter(caterClicked = {category->
            dialog.dismiss()
            viewModel.getCategories().observe(viewLifecycleOwner) { categories ->
                val filteredList=categories.filter {
                    it.name==category.name
                }
                categoryAdapter.submitList(filteredList)
            }
        })
        categoryAdapter = CategoryAdapter(onFavoriteClicked = {product ->
            lifecycleScope.launch {
                if (viewModel.isProductInFavorite(product.toFavoriteProductEntity())){
                    viewModel.removeFavoriteProduct(product.toFavoriteProductEntity())
                }else{
                    viewModel.addFavoriteProduct(product.toFavoriteProductEntity())
                }
            }
        })
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = categoryAdapter
        viewModel.getCategories().observe(viewLifecycleOwner) { categories ->
           categoryAdapter.submitList(categories)
        }

        initClickListeners()
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
}

