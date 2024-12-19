package com.example.backenddevelopertests.mapper

import com.example.backenddevelopertests.entity.ImageEntity
import com.example.backenddevelopertests.entity.OptionEntity
import com.example.backenddevelopertests.entity.ProductEntity
import com.example.backenddevelopertests.entity.VariantEntity
import com.example.backenddevelopertests.model.ImageModel
import com.example.backenddevelopertests.model.OptionModel
import com.example.backenddevelopertests.model.ProductModel
import com.example.backenddevelopertests.model.VariantModel
import org.springframework.stereotype.Service

@Service
class ProductModelMapper {

    fun mapToProductModel(productEntity: ProductEntity): ProductModel {
        return ProductModel(
            id = productEntity.id,
            title = productEntity.title,
            handle = productEntity.handle,
            bodyHtml = productEntity.bodyHtml,
            publishedAt = productEntity.publishedAt?.toString(),
            vendor = productEntity.vendor,
            productType = productEntity.productType,
            tags = productEntity.tags,
            images = mapImages(productEntity.images),
            variants = mapVariants(productEntity.variants),
            options = mapOptions(productEntity.options)
        )
    }

    private fun mapImages(images: MutableList<ImageEntity>?): MutableList<ImageModel>? {
        return images?.map { image ->
            ImageModel(
                id = image.id,
                src = image.src,
                width = image.width,
                height = image.height
            )
        }?.toMutableList()
    }

    private fun mapVariants(variants: MutableList<VariantEntity>?): MutableList<VariantModel>? {
        return variants?.map { variant ->
            VariantModel(
                id = variant.id,
                title = variant.title,
                price = variant.price,
                sku = variant.sku
            )
        }?.toMutableList()
    }

    private fun mapOptions(options: MutableList<OptionEntity>?): MutableList<OptionModel>? {
        return options?.map { option ->
            OptionModel(
                name = option.name,
                position = option.position,
                values = option.values
            )
        }?.toMutableList()
    }
}
