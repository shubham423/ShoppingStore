package com.example.shoppingcart.presentation.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var dialogBinding:DialogCatogoriesBinding
    private lateinit var dialog: Dialog
    private lateinit var categoryFilterAdapter:CategoryFilterAdapter
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
        val categoryAdapter = CategoryAdapter()
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = categoryAdapter
        viewModel.getCategories().observe(viewLifecycleOwner) { categories ->
           categoryAdapter.submitList(categories)
            categoryFilterAdapter.submitList(categories)
        }
    }

    private fun showCustomDialog() {
        val layoutManager = LinearLayoutManager(requireContext())
        dialogBinding.rvCategories.layoutManager = layoutManager

        categoryFilterAdapter = CategoryFilterAdapter( )
        dialogBinding.rvCategories.adapter = categoryFilterAdapter

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
}

