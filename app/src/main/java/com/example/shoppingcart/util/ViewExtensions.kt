package com.example.shoppingcart.util

import android.view.View

fun View.visible() {
    if (visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE)
        this.visibility = View.GONE
}

fun View.setSafeOnClickListener(delay: Int = 1000, onSafeClick: ((View) -> Unit)?) {
    val safeClickListener = onSafeClick?.let {
        SafeClickListener(delay) {
            onSafeClick(it)
        }
    }
    setOnClickListener(safeClickListener)
}
