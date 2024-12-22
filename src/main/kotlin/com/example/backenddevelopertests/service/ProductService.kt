package com.example.backenddevelopertests.service

import com.example.backenddevelopertests.model.ProductModel

interface ProductService {
    fun getAllProducts(): List<ProductModel>

    fun saveProduct(title: String, handle: String?, publishedAt: String?, vendor: String?, productType: String?)
}
