package com.example.shoppingcart.util

import com.example.shoppingcart.data.local.entities.CartProductEntity
import com.example.shoppingcart.data.local.entities.ProductEntity
import com.example.shoppingcart.data.models.Category
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.data.models.ProductsResponse


fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        icon = this.icon,
        name = this.name,
        price = this.price,
        categoryName = this.name,
        categoryId = this.categoryId,
        isFavorite = this.isFavorite
    )
}

fun Product.toCartProductEntity(): CartProductEntity {
    return CartProductEntity(
        id = this.id,
        icon = this.icon,
        name = this.name,
        price = this.price,
        quantity = this.quantity
    )
}

fun Product.toProductEntity(categoryId: Int, categoryName: String): ProductEntity {
    return ProductEntity(
        id = this.id,
        icon = this.icon,
        name = this.name,
        price = this.price,
        categoryName = categoryName,
        categoryId = categoryId,
        isFavorite = this.isFavorite,
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = this.id,
        icon = this.icon,
        name = this.name,
        price = this.price,
        categoryId = this.categoryId,
        isFavorite = this.isFavorite,
        categoryName = this.categoryName
    )
}

fun CartProductEntity.toProduct(): Product {
    return Product(
        id = this.id,
        icon = this.icon,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        categoryId = this.id,
        categoryName = this.name
    )
}

fun List<ProductEntity>.toProductsResponse(): ProductsResponse {
    // Group ProductEntity objects by categoryId
    val groupedProducts = this.groupBy { it.categoryId }

    // Create Category and Product objects
    val categories = groupedProducts.map { (categoryId, products) ->
        Category(
            id = categoryId,
            name = products.first().categoryName,
            items = products.map {
                Product(
                    icon = it.icon,
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    categoryId = it.categoryId,
                    isFavorite = it.isFavorite,
                    categoryName = it.categoryName
                )
            }
        )
    }

    return ProductsResponse(categories = categories)
}
