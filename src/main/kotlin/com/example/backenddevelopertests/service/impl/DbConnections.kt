package com.example.backenddevelopertests.service.impl

import com.example.backenddevelopertests.model.ImageModel
import com.example.backenddevelopertests.model.OptionModel
import com.example.backenddevelopertests.model.ProductModel
import com.example.backenddevelopertests.model.VariantModel
import org.springframework.stereotype.Service
import java.sql.*
import kotlin.math.absoluteValue
import kotlin.random.Random

@Service
class DbConnections {
    private final val url = "jdbc:postgresql://localhost:5432/postgres"
    private final val username = "postgres"
    private final val password = "1234"

    fun getProductCount(): Long {
        val sql = "SELECT COUNT(*) FROM product"

        val connection: Connection = DriverManager.getConnection(url, username, password)

        val statement: Statement = connection.createStatement()

        val resultSet: ResultSet = statement.executeQuery(sql)

        val count: Long = if (resultSet.next()) {
            resultSet.getLong(1)
        } else {
            0L
        }

        resultSet.close()
        statement.close()
        connection.close()

        return count
    }

    fun getAllProducts(): List<ProductModel> {
        val sql = """
        SELECT p.id, p.title, p.handle, p.body_html, p.published_at, p.created_at, p.updated_at, 
               p.vendor, p.product_type, t.tag 
        FROM product p
        LEFT JOIN product_tags t ON p.id = t.product_id
    """

        val connection: Connection = DriverManager.getConnection(url, username, password)

        val statement = connection.prepareStatement(sql)

        val resultSet: ResultSet = statement.executeQuery()

        val products = mutableListOf<ProductModel>()
        var currentProduct: ProductModel? = null

        while (resultSet.next()) {
            val productId = resultSet.getLong("id")

            if (currentProduct == null || currentProduct.id != productId) {
                currentProduct = mapRowToProduct(resultSet)
                products.add(currentProduct)
            }

            val tagName = resultSet.getString("tag")
            if (tagName != null) {
                currentProduct.tags?.add(tagName)
            }
        }

        resultSet.close()
        statement.close()
        connection.close()

        return products
    }


    fun saveProduct(productModel: ProductModel): Long {
        val sql = """
            INSERT INTO product (id, title, handle, body_html, published_at, created_at, updated_at, vendor, product_type)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """

        val connection: Connection = DriverManager.getConnection(url, username, password)
        val productPreparedStatement: PreparedStatement = connection.prepareStatement(sql)

        try {
            productPreparedStatement.setObject(1, productModel.id)
            productPreparedStatement.setString(2, productModel.title)
            productPreparedStatement.setString(3, productModel.handle)
            productPreparedStatement.setString(4, productModel.bodyHtml)
            productPreparedStatement.setString(5, productModel.publishedAt)
            productPreparedStatement.setString(6, productModel.createdAt)
            productPreparedStatement.setString(7, productModel.updatedAt)
            productPreparedStatement.setString(8, productModel.vendor)
            productPreparedStatement.setString(9, productModel.productType)

            val resultSet: ResultSet = productPreparedStatement.executeQuery()

            val productId: Long = if (resultSet.next()) {
                resultSet.getLong("id")
            } else {
                throw SQLException("Failed to retrieve the 'id' from the result set")
            }

            productPreparedStatement.close()

            if (!productModel.tags.isNullOrEmpty()) {
                val insertTagsSql = """
            INSERT INTO product_tags (product_id, tag)
            VALUES (?, ?)
        """

                val tagPreparedStatement: PreparedStatement = connection.prepareStatement(insertTagsSql)
                for (tag in productModel.tags) {
                    tagPreparedStatement.setLong(1, productId)
                    tagPreparedStatement.setString(2, tag)
                    tagPreparedStatement.addBatch()
                }

                tagPreparedStatement.executeBatch()
                tagPreparedStatement.close()
            }

            resultSet.close()
            productPreparedStatement.close()
            connection.close()
            return productId
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    fun saveImage(image: ImageModel, productId: Long) {
        val sql = """
        INSERT INTO image (id, created_at, updated_at, position, src, width, height, product_id)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """
        val connection: Connection = DriverManager.getConnection(url, username, password)

        try {
            val statement: PreparedStatement = connection.prepareStatement(sql)
            statement.setLong(1, image.id!!)
            statement.setString(2, image.createdAt)
            statement.setString(3, image.updatedAt)
            statement.setInt(4, image.position ?: 0)
            statement.setString(5, image.src)
            statement.setInt(6, image.width ?: 0)
            statement.setInt(7, image.height ?: 0)
            statement.setLong(8, productId)

            statement.executeUpdate()
            statement.close()
            connection.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun saveVariant(variant: VariantModel) {
        val sql = """
        INSERT INTO variant (id, title, option1, option2, option3, sku, requires_shipping, taxable, available, price, grams, compare_at_price, position, created_at, updated_at, featured_image_id, product_id)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """
        val connection: Connection = DriverManager.getConnection(url, username, password)
        try {
            val statement: PreparedStatement = connection.prepareStatement(sql)

            statement.setLong(1, variant.id!!)
            statement.setString(2, variant.title)
            statement.setString(3, variant.option1)
            statement.setString(4, variant.option2)
            statement.setString(5, variant.option3)
            statement.setString(6, variant.sku)
            statement.setBoolean(7, variant.requiresShipping!!)
            statement.setBoolean(8, variant.taxable!!)
            statement.setBoolean(9, variant.available!!)
            statement.setDouble(10, variant.price!!)
            statement.setInt(11, variant.grams!!)
            statement.setObject(12, variant.compareAtPrice)
            statement.setInt(13, variant.position!!)
            statement.setObject(14, variant.createdAt)
            statement.setString(15, variant.updatedAt)
            statement.setObject(16, variant.featuredImage?.id)
            statement.setLong(17, variant.productId!!)

            statement.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }
    }

    fun saveOption(option: OptionModel, productId: Long) {
        val connection: Connection = DriverManager.getConnection(url, username, password)

        val insertOptionSql = """
        INSERT INTO option (id, name, position, product_id)
        VALUES (?, ?, ?, ?)
        RETURNING id
    """

        var generatedId: Long? = null

        try {
            val optionStatement: PreparedStatement = connection.prepareStatement(insertOptionSql)

            optionStatement.setLong(1, Random.nextLong().absoluteValue)
            optionStatement.setString(2, option.name)
            optionStatement.setInt(3, option.position!!)
            optionStatement.setLong(4, productId)

            val resultSet: ResultSet = optionStatement.executeQuery()

            if (resultSet.next()) {
                generatedId = resultSet.getLong("id")
            }

            if (generatedId != null) {
                val insertEntityValueSql = """
                INSERT INTO option_entity_values (option_entity_id, values)
                VALUES (?, ?)
            """
                val entityValueStatement: PreparedStatement = connection.prepareStatement(insertEntityValueSql)

                option.values?.forEach { value ->
                    entityValueStatement.setLong(1, generatedId)
                    entityValueStatement.setString(2, value)

                    entityValueStatement.executeUpdate()
                }
            }

        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }
    }

    private fun mapRowToProduct(resultSet: ResultSet): ProductModel {
        val id = resultSet.getLong("id")
        val title = resultSet.getString("title")
        val handle = resultSet.getString("handle")
        val bodyHtml = resultSet.getString("body_html")
        val publishedAt = resultSet.getString("published_at")
        val createdAt = resultSet.getString("created_at")
        val updatedAt = resultSet.getString("updated_at")
        val vendor = resultSet.getString("vendor")
        val productType = resultSet.getString("product_type")

        val tags = mutableListOf<String>()

        return ProductModel(
            id = id,
            title = title,
            handle = handle,
            bodyHtml = bodyHtml,
            publishedAt = publishedAt,
            createdAt = createdAt,
            updatedAt = updatedAt,
            vendor = vendor,
            productType = productType,
            tags = tags
        )
    }
}
