package com.example.shoppingcart.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.local.CartProductEntity
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _favoriteProductsFlow: StateFlow<List<Product>> = MutableStateFlow(emptyList())
    val favoriteProductsFlow: StateFlow<List<Product>> = _favoriteProductsFlow

    var isProductInFavorite: Boolean = false

    fun addFavoriteProduct(favoriteProduct: CartProductEntity) {
        viewModelScope.launch {
            repository.insertFavoriteProduct(favoriteProduct)
        }

    }


    fun isProductInFavorite(favoriteProduct: CartProductEntity) {
        viewModelScope.launch {
            isProductInFavorite = repository.isProductInFavorites(favoriteProduct) > 0
        }

    }

    fun removeFavoriteProduct(favoriteProduct: CartProductEntity) {
        viewModelScope.launch {
            repository.removeFavoriteProduct(favoriteProduct)
        }
    }


    val favoriteProducts: StateFlow<List<CartProductEntity>> =
        repository.getAllFavoriteProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}