package com.example.backenddevelopertests.mapper

import com.example.backenddevelopertests.model.ProductModel
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class ProductModelMapper {

    fun mapToProductModel(rs: ResultSet): ProductModel {
        return ProductModel(
            id = rs.getLong("id"),
            title = rs.getString("title"),
            handle = rs.getString("handle"),
            bodyHtml = rs.getString("body_html"),
            publishedAt = rs.getString("published_at"),
            vendor = rs.getString("vendor"),
            productType = rs.getString("product_type"),
        )
    }
}
