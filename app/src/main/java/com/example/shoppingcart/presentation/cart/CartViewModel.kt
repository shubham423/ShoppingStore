package com.example.shoppingcart.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.data.repository.ProductRepositoryImpl
import com.example.shoppingcart.util.toProduct
import com.example.shoppingcart.util.toProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: ProductRepositoryImpl) :
    ViewModel() {

    private val _favoriteProductsFlow: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())
    val favoriteProductsFlow: MutableStateFlow<List<Product>> = _favoriteProductsFlow

    init {
        getFavoriteProducts()
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product.toProductEntity())
        }
    }


    private fun getFavoriteProducts() {
        viewModelScope.launch {
            repository.getFavoriteProducts().collect {
                _favoriteProductsFlow.emit(it.map { it.toProduct() })
            }

        }
    }
}