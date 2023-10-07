package com.example.shoppingcart.presentation.home

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.databinding.DialogCatogoriesBinding
import com.example.shoppingcart.databinding.FragmentHomeBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.presentation.CategoryAdapter
import com.example.shoppingcart.presentation.CategoryFilterAdapter
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.setSafeOnClickListener
import com.example.shoppingcart.util.visible
import dagger.hilt.android.AndroidEntryPoint

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
        categoryAdapter = CategoryAdapter()
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

