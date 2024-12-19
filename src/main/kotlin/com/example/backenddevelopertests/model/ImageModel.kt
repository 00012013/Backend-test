package com.example.backenddevelopertests.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
open class ImageModel(
    val id: Long? = null,

    val createdAt: String? = null,

    val position: Int? = null,

    val updatedAt: String? = null,

    val productId: Long? = null,

    val variantIds: MutableList<Long>? = null,

    val src: String? = null,

    val width: Int? = null,

    val height: Int? = null
)
