package com.example.backenddevelopertests.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import lombok.Data

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ProductModel (
    val id: Long? = null,

    val title: String? = null,

    val handle: String? = null,

    val bodyHtml: String? = null,

    val publishedAt: String? = null,

    val createdAt: String? = null,

    val updatedAt: String? = null,

    val vendor: String? = null,

    val productType: String? = null,

    val tags: List<String>? = null,

    val variants: MutableList<VariantModel>? = null,

    val images: MutableList<ImageModel>? = null,

    val options: MutableList<OptionModel>? = null
)
