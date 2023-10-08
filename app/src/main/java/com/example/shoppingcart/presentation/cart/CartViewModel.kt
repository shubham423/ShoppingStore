package com.example.shoppingcart.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.data.repository.CartRepository
import com.example.shoppingcart.util.toCartProductEntity
import com.example.shoppingcart.util.toProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) :
    ViewModel() {

    private val _favoriteProductsFlow: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())
    val favoriteProductsFlow: MutableStateFlow<List<Product>> = _favoriteProductsFlow

    init {
        getCartProducts()
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateCartProduct(product.toCartProductEntity())
        }
    }


    private fun getCartProducts() {
        viewModelScope.launch {
            repository.getAllCartProducts().collect {
                _favoriteProductsFlow.emit(it.map { it.toProduct() })
            }

        }
    }
}