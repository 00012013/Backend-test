package com.example.backenddevelopertests.service.impl

import com.example.backenddevelopertests.entity.ImageEntity
import com.example.backenddevelopertests.entity.OptionEntity
import com.example.backenddevelopertests.entity.ProductEntity
import com.example.backenddevelopertests.entity.VariantEntity
import com.example.backenddevelopertests.mapper.ProductModelMapper
import com.example.backenddevelopertests.model.ProductModel
import com.example.backenddevelopertests.model.ProductResponse
import com.example.backenddevelopertests.repository.ImageRepository
import com.example.backenddevelopertests.repository.OptionRepository
import com.example.backenddevelopertests.repository.ProductRepository
import com.example.backenddevelopertests.repository.VariantRepository
import com.example.backenddevelopertests.service.ProductService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class ProductServiceImpl(
    @Autowired val productRepository: ProductRepository,
    @Autowired val variantRepository: VariantRepository,
    @Autowired val imageRepository: ImageRepository,
    @Autowired val optionRepository: OptionRepository,
    @Autowired val productModelMapper: ProductModelMapper
) : ProductService {
    private final var URL = "https://famme.no/products.json";

    @PostConstruct
    fun fetchAndSaveProducts() {
        if (productRepository.findAll().isNotEmpty()) {
            return
        }

        val restTemplate = RestTemplate()

        val responseEntity = restTemplate.exchange(
            URL,
            HttpMethod.GET,
            null,
            ProductResponse::class.java
        )

        val products = responseEntity.body?.products
        saveProducts(products)
    }

    override fun getAllProducts(): List<ProductModel> {
        return productRepository.findAll().map { product -> productModelMapper.mapToProductModel(product) }.toList();
    }

    @Transactional
    fun saveProducts(products: List<ProductModel>?) {
        val nonNullProducts = products ?: emptyList()

        nonNullProducts.take(10).map { product ->
            val productEntity = ProductEntity(
                id = product.id,
                title = product.title,
                handle = product.handle,
                bodyHtml = product.bodyHtml,
                publishedAt = product.publishedAt,
                createdAt = product.createdAt,
                updatedAt = product.updatedAt,
                vendor = product.vendor,
                productType = product.productType,
                tags = product.tags ?: emptyList(),
                images = mutableListOf(),
                variants = mutableListOf(),
                options = mutableListOf()
            )

            productRepository.save(productEntity)

            val imageEntities = product.images?.map { image ->
                ImageEntity(
                    id = image.id,
                    createdAt = image.createdAt,
                    updatedAt = image.createdAt,
                    position = image.position,
                    src = image.src,
                    width = image.width,
                    height = image.height,
                    product = productEntity
                )
            } ?: emptyList()

            imageRepository.saveAll(imageEntities)

            val variantEntities = product.variants?.map { variant ->

                val featuredImage: ImageEntity? = variant.featuredImage?.let { image ->
                    val existingImageOpt = imageRepository.findById(image.id ?: return@let null)

                    if (existingImageOpt.isPresent) {
                        val existingImage = existingImageOpt.get()
                        existingImage.alt = image.alt
                        imageRepository.save(existingImage)
                        existingImage
                    } else {
                        null
                    }
                }

                VariantEntity(
                    id = variant.id,
                    title = variant.title,
                    option1 = variant.option1,
                    option2 = variant.option2,
                    option3 = variant.option3,
                    sku = variant.sku,
                    requiresShipping = variant.requiresShipping,
                    taxable = variant.taxable,
                    available = variant.available,
                    price = variant.price,
                    grams = variant.grams,
                    compareAtPrice = variant.compareAtPrice,
                    position = variant.position,
                    createdAt = variant.createdAt,
                    updatedAt = variant.updatedAt,
                    featuredImage = featuredImage,
                    product = productEntity
                )
            } ?: emptyList()

            variantRepository.saveAll(variantEntities)

            val optionEntities = product.options?.map { option ->
                OptionEntity(
                    name = option.name,
                    values = option.values,
                    position = option.position,
                    product = productEntity,
                )
            } ?: emptyList()
            optionRepository.saveAll(optionEntities)

            productEntity.images = imageEntities.toMutableList()
            productEntity.variants = variantEntities.toMutableList()
            productEntity.options = optionEntities.toMutableList()
            productRepository.save(productEntity)
        }
    }
}