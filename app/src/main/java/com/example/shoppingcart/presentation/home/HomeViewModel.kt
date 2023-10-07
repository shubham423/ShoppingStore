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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    suspend fun addProductToCart(product: ProductEntity) {
        repository.insertProduct(product)
    }

    suspend fun removeProductFromCart(product: ProductEntity) {
        repository.removeProductFromCart(product)
    }

    suspend fun addFavoriteProduct(favoriteProduct: FavoriteProductEntity) {
        repository.insertFavoriteProduct(favoriteProduct)
    }

    suspend fun removeFavoriteProduct(favoriteProduct: FavoriteProductEntity) {
        repository.removeFavoriteProduct(favoriteProduct)
    }

    val cartProducts: StateFlow<List<ProductEntity>> = repository.getAllCartProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val favoriteProducts: StateFlow<List<FavoriteProductEntity>> =
        repository.getAllFavoriteProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun getCategories(): LiveData<List<Category>> {
        val data = MutableLiveData<List<Category>>()
        val productsResponse = repository.getProductsResponse()
        data.value = productsResponse?.categories ?: emptyList()
        return data
    }
}
