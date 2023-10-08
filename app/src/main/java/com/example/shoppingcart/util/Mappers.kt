package com.example.shoppingcart.util

import com.example.shoppingcart.data.local.CartProductEntity
import com.example.shoppingcart.data.models.Product


fun CartProductEntity.toProduct(): Product {
    return Product(icon = this.icon, id = this.id, name = this.name, price = this.price)
}
fun Product.toFavoriteProductEntity(): CartProductEntity {
    return CartProductEntity(id = this.id, icon = this.icon, name = this.name, price = this.price)
}
