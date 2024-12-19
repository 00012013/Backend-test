package com.example.backenddevelopertests.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import lombok.Data
import java.util.*

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class VariantModel(
    val id: Long? = null,

    val title: String? = null,

    val option1: String? = null,

    val option2: String? = null,

    val option3: String? = null,

    val sku: String? = null,

    val requiresShipping: Boolean? = null,

    val taxable: Boolean? = null,

    val featuredImage: FeaturedImageModel? = null,

    val available: Boolean? = null,

    val price: Double? = null,

    val grams: Int? = null,

    val compareAtPrice: Double? = null,

    val position: Int? = null,

    val productId: Long? = null,

    val createdAt: Date? = null,

    val updatedAt: Date? = null,
)
