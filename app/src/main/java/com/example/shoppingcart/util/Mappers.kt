package com.example.shoppingcart.util

import com.example.shoppingcart.data.local.FavoriteProductEntity
import com.example.shoppingcart.data.models.Product


fun FavoriteProductEntity.toProduct(): Product {
    return Product(icon = this.icon, id = this.id, name = this.name, price = this.price)
}
fun Product.toFavoriteProductEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(id = this.id, icon = this.icon, name = this.name, price = this.price)
}
