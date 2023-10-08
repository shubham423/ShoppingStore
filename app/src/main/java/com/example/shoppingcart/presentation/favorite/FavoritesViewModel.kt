package com.example.shoppingcart.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.local.CartProductEntity
import com.example.shoppingcart.data.local.ProductEntity
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.data.repository.ProductRepository
import com.example.shoppingcart.util.toProduct
import com.example.shoppingcart.util.toProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _favoriteProductsFlow: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())
    val favoriteProductsFlow: MutableStateFlow<List<Product>> = _favoriteProductsFlow

    var isProductInFavorite: Boolean = false

    init {
        getFavoriteProducts()
    }

    fun isProductInFavorite(productEntity: ProductEntity) {
        viewModelScope.launch {
            isProductInFavorite = repository.isProductInFavorites(productEntity) > 0
        }

    }

    fun removeFavoriteProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.removeProduct(product)
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch {
            repository.getFavoriteProducts().collect {
                _favoriteProductsFlow.emit(it.map { it.toProduct() })
            }

        }
    }
}