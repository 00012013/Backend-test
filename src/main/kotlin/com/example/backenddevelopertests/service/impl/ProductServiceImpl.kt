package com.example.backenddevelopertests.service.impl

import com.example.backenddevelopertests.model.ProductModel
import com.example.backenddevelopertests.model.ProductResponse
import com.example.backenddevelopertests.service.ProductService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.math.absoluteValue
import kotlin.random.Random

@Service
class ProductServiceImpl(
    @Autowired val repo: DbConnections,
) : ProductService {
    private final var url = "https://famme.no/products.json";

    @Scheduled(cron = "0 0 * * * *")
    fun fetchAndSaveProducts() {
        if (repo.getProductCount() > 0) {
            return
        }

        val restTemplate = RestTemplate()

        val responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            ProductResponse::class.java
        )

        val products = responseEntity.body?.products
        saveProducts(products)
    }

    override fun getAllProducts(): List<ProductModel> {
        return repo.getAllProducts();
    }

    override fun saveProduct(
        title: String,
        handle: String?,
        publishedAt: String?,
        vendor: String?,
        productType: String?
    ) {
        repo.saveProduct(
            ProductModel(
                id = Random.nextLong().absoluteValue,
                title = title,
                handle = handle,
                publishedAt = publishedAt,
                vendor = vendor,
                productType = productType
            )
        )
    }

    fun saveProduct(product: ProductModel): Long {
        return repo.saveProduct(product)
    }

    fun saveProducts(products: List<ProductModel>?) {
        val nonNullProducts = products ?: emptyList()

        nonNullProducts.take(10).forEach { product ->

            val productId = saveProduct(product)

            product.images?.forEach { image ->
                repo.saveImage(image, productId)
            }

            product.variants?.forEach { variant ->
                repo.saveVariant(variant)
            }

            product.options?.forEach { option ->
                repo.saveOption(option, productId)
            }
        }
    }
}