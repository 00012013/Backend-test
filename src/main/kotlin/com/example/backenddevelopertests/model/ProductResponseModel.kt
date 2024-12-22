package com.example.backenddevelopertests.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import lombok.Data
import lombok.Getter
import lombok.Setter

@Data
class ProductResponse {
    val products: List<ProductModel>? = null
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ProductModel(
    val id: Long? = null,

    val title: String? = null,

    val handle: String? = null,

    val bodyHtml: String? = null,

    val publishedAt: String? = null,

    val createdAt: String? = null,

    val updatedAt: String? = null,

    val vendor: String? = null,

    val productType: String? = null,

    val tags: MutableList<String>? = null,

    val variants: MutableList<VariantModel>? = null,

    val images: MutableList<ImageModel>? = null,

    val options: MutableList<OptionModel>? = null
)


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

    val createdAt: String? = null,

    val updatedAt: String? = null,
)

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

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class FeaturedImageModel : ImageModel() {
    val alt: String? = null
}

class OptionModel(
    val name: String? = null,

    val position: Int? = null,

    val values: List<String>? = null
)
