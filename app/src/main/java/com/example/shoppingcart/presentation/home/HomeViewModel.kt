package com.example.shoppingcart.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcart.data.models.Category
import com.example.shoppingcart.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    fun getCategories(): LiveData<List<Category>> {
        val data = MutableLiveData<List<Category>>()
        val productsResponse = repository.getProductsResponse()
        data.value = productsResponse?.categories ?: emptyList()
        return data
    }
}
