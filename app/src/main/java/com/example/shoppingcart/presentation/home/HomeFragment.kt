package com.example.shoppingcart.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.data.models.ProductsResponse
import com.example.shoppingcart.databinding.FragmentHomeBinding
import com.example.shoppingcart.presentation.BaseFragment
import com.example.shoppingcart.presentation.CategoryAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = musicList(requireContext()).categories

        val categoryAdapter = CategoryAdapter(categories)
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = categoryAdapter
    }

    fun getJsonDataFromAsset(
        context: Context,
        fileName: String
    ): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (exp: IOException) {
            exp.printStackTrace()
            return null
        }

        return jsonString
    }

    fun musicList(context: Context): ProductsResponse {
        val jsonFileString = getJsonDataFromAsset(context = context, "shopping.json")
        val type = object : TypeToken<ProductsResponse>() {}.type
        return Gson().fromJson(jsonFileString, type)
    }
}

