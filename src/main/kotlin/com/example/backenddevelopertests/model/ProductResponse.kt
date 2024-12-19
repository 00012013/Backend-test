package com.example.backenddevelopertests.model

import lombok.Data

@Data
class ProductResponse {
    val products: List<ProductModel>? = null
}
