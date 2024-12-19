package com.example.backenddevelopertests.service.impl

import com.example.backenddevelopertests.mapper.ProductModelMapper
import com.example.backenddevelopertests.model.*
import com.example.backenddevelopertests.service.ProductService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import java.sql.SQLException
import kotlin.math.absoluteValue
import kotlin.random.Random

@Service
class ProductServiceImpl(
    private val jdbcTemplate: JdbcTemplate,
    @Autowired val productModelMapper: ProductModelMapper
) : ProductService {
    private final var URL = "https://famme.no/products.json";

    @PostConstruct
    fun fetchAndSaveProducts() {
        val sql = "SELECT COUNT(*) FROM product"
        val count = jdbcTemplate.queryForObject(sql, Int::class.java) ?: 0

        if (count > 0) {
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
        val sql = "SELECT * FROM product"

        return jdbcTemplate.query(sql) { rs, _ ->
            productModelMapper.mapToProductModel(rs)
        }
    }

    override fun saveProduct(product: ProductModel) {
        val sql = """
            INSERT INTO product (id, title,  handle, product_type, vendor)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id
        """
        jdbcTemplate.queryForObject(
            sql,
            Long::class.java,
            Random.nextLong().absoluteValue, product.title, product.handle, product.productType, product.vendor
        ) ?: throw SQLException("Failed to insert product")
    }

    @Transactional
    fun saveProducts(products: List<ProductModel>?) {
        val nonNullProducts = products ?: emptyList()

        nonNullProducts.take(10).forEach { product ->

            val productId = insertProduct(product)

            insertImages(product.images, productId)

            insertVariants(product.variants, productId)

            insertOptions(product.options, productId)
        }
    }

    @Transactional
    fun insertProduct(product: ProductModel): Long {
        val sql = """
            INSERT INTO product (id, title, handle, body_html, published_at, created_at, updated_at, vendor, product_type)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """
        val productId = jdbcTemplate.queryForObject(
            sql,
            Long::class.java,
            product.id, product.title, product.handle, product.bodyHtml,
            product.publishedAt, product.createdAt, product.updatedAt,
            product.vendor, product.productType
        ) ?: throw SQLException("Failed to insert product")

        product.tags?.forEach { tag ->
            val insertTagSql = """
                INSERT INTO product_tags(product_id, tag) 
                VALUES (?, ?) 
            """
            jdbcTemplate.update(insertTagSql, productId, tag)
        }

        return productId
    }

    fun insertImages(images: List<ImageModel>?, productId: Long) {
        val sql = """
        INSERT INTO image (id, created_at, updated_at, position, src, width, height, product_id)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """

        images?.forEach { image ->
            jdbcTemplate.update(
                sql,
                image.id,
                image.createdAt,
                image.updatedAt,
                image.position,
                image.src,
                image.width,
                image.height,
                productId
            )
        }
    }

    fun insertVariants(variants: List<VariantModel>?, productId: Long) {
        val sql = """
        INSERT INTO variant (id, title, option1, option2, option3, sku, requires_shipping, taxable, available, price, grams, compare_at_price, position, created_at, updated_at, featured_image_id, product_id)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """

        variants?.forEach { variant ->
            val featuredImageId = variant.featuredImage?.id
            jdbcTemplate.update(
                sql,
                variant.id, variant.title, variant.option1, variant.option2, variant.option3,
                variant.sku, variant.requiresShipping, variant.taxable, variant.available, variant.price,
                variant.grams, variant.compareAtPrice, variant.position, variant.createdAt, variant.updatedAt,
                featuredImageId, productId
            )
        }
    }

    fun insertOptions(options: List<OptionModel>?, productId: Long) {
        val sql = """
    INSERT INTO option (name, position, product_id)
    VALUES (?, ?, ?)
    RETURNING id
    """

        options?.forEach { option ->
            val optionId = jdbcTemplate.queryForObject(sql, Long::class.java, option.name, option.position, productId)

            option.values?.forEach { value ->
                jdbcTemplate.update(
                    """
                INSERT INTO option_entity_values (option_entity_id, values)
                VALUES (?, ?)
                """.trimIndent(),
                    optionId,
                    value
                )
            }
        }
    }
}