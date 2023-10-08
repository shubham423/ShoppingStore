package com.example.shoppingcart.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.local.entities.ProductEntity
import com.example.shoppingcart.data.models.Category
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.data.models.ProductsResponse
import com.example.shoppingcart.data.repository.CartRepository
import com.example.shoppingcart.data.repository.ProductsRepository
import com.example.shoppingcart.data.repository.SyncDatastoreRepository
import com.example.shoppingcart.util.toCartProductEntity
import com.example.shoppingcart.util.toProductEntity
import com.example.shoppingcart.util.toProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val datastoreRepository: SyncDatastoreRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _productsStateFlow: MutableStateFlow<ProductsResponse?> = MutableStateFlow(null)
    val productsStateFlow: MutableStateFlow<ProductsResponse?> = _productsStateFlow

    init {
        viewModelScope.launch {
            if (!datastoreRepository.isProductSynced()) {
                val productsList = arrayListOf<ProductEntity>()
                repository.getProductsResponse()?.categories?.forEach { category ->
                    productsList.addAll(category.items.map {
                        it.toProductEntity(category.id, category.name)
                    })
                }
                repository.insertProducts(productsList)
                datastoreRepository.syncProduct(true)
            }

            getAllProducts()
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            repository.getAllProducts().collect {
                _productsStateFlow.emit(it.toProductsResponse())
            }
        }
    }

    fun getProductsByCategory(categoryId: Int) {
        viewModelScope.launch {
            repository.getProductsByCategoryId(categoryId).collect {
                _productsStateFlow.emit(it.toProductsResponse())
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product.toProductEntity())
        }
    }

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product.toCartProductEntity())
        }
    }

    fun getCategories(): LiveData<List<Category>> {
        val data = MutableLiveData<List<Category>>()
        val productsResponse = repository.getProductsResponse()
        data.value = productsResponse?.categories ?: emptyList()
        return data
    }
}
