package com.example.shoppingcart.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.local.entities.ProductEntity
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.data.repository.ProductRepository
import com.example.shoppingcart.util.toProduct
import com.example.shoppingcart.util.toProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: ProductRepository) :
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