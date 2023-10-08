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

    private val _cartProductsFlow: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())
    val cartProductsFlow: MutableStateFlow<List<Product>> = _cartProductsFlow

    init {
        getCartProducts()
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            if (product.quantity == 0) {
                repository.removeFromCart(product.toCartProductEntity())
            } else {
                repository.updateCartProduct(product.toCartProductEntity())
            }
        }
    }


    private fun getCartProducts() {
        viewModelScope.launch {
            repository.getAllCartProducts().collect {
                _cartProductsFlow.emit(it.map { it.toProduct() })
            }

        }
    }
}