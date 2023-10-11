package com.example.shoppingcart.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.local.entities.CartProductEntity
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
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

    private val _cartCountFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val cartCountFlow: MutableStateFlow<Int> = _cartCountFlow

    private val _categoriesFlow: MutableSharedFlow<List<Category>> = MutableSharedFlow()
    val categoriesFlow: MutableSharedFlow<List<Category>> = _categoriesFlow

    private val _searchedCartProduct: MutableSharedFlow<CartProductEntity?> = MutableSharedFlow()
    val searchedCartProduct: MutableSharedFlow<CartProductEntity?> = _searchedCartProduct

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
            getCartProductCount()
        }
    }

     fun getAllProducts() {
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

    fun updateCarteProduct(product: Product) {
        viewModelScope.launch {
            cartRepository.updateCartProduct(product.toCartProductEntity())
        }
    }

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product.toCartProductEntity())
        }
    }

    private fun getCartProductCount() {
        viewModelScope.launch {
            cartRepository.getCartProductsCount().collect {
                _cartCountFlow.emit(it)
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val productsResponse = repository.getProductsResponse()
            val newCategoriesList= arrayListOf<Category>()
            newCategoriesList.addAll(productsResponse?.categories ?: emptyList())
            newCategoriesList.add(Category(-1, emptyList(),"All"))
            _categoriesFlow.emit(newCategoriesList)
        }
    }

    fun getCartProductById(id:Int) {
        viewModelScope.launch {
           val cartProduct=cartRepository.getCartProductById(id)
            _searchedCartProduct.emit(cartProduct)
        }
    }
}
