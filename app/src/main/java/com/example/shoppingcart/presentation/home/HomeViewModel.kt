package com.example.shoppingcart.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.local.FavoriteProductEntity
import com.example.shoppingcart.data.local.ProductEntity
import com.example.shoppingcart.data.models.Category
import com.example.shoppingcart.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    var isProductInFavorite: Boolean = false
    fun addProductToCart(product: ProductEntity) {
        viewModelScope.launch {
            repository.insertProduct(product)
        }


    }

    fun removeProductFromCart(product: ProductEntity) {
        viewModelScope.launch {
            repository.removeProductFromCart(product)
        }


    }

    fun addFavoriteProduct(favoriteProduct: FavoriteProductEntity) {
        viewModelScope.launch {
            repository.insertFavoriteProduct(favoriteProduct)
        }

    }


   suspend fun isProductInFavorite(favoriteProduct: FavoriteProductEntity): Boolean {
           return repository.isProductInFavorites(favoriteProduct) > 0
    }

    fun removeFavoriteProduct(favoriteProduct: FavoriteProductEntity) {
        viewModelScope.launch {
            repository.removeFavoriteProduct(favoriteProduct)
        }
    }


    val favoriteProducts: StateFlow<List<FavoriteProductEntity>> =
        repository.getAllFavoriteProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val cartProducts: StateFlow<List<ProductEntity>> = repository.getAllCartProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())



    fun getCategories(): LiveData<List<Category>> {
        val data = MutableLiveData<List<Category>>()
        val productsResponse = repository.getProductsResponse()
        data.value = productsResponse?.categories ?: emptyList()
        return data
    }
}
