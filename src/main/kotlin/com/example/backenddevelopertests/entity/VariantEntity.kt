package com.example.backenddevelopertests.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "variant")
class VariantEntity(
    @Id
    var id: Long? = null,

    var title: String? = null,

    var option1: String? = null,

    var option2: String? = null,

    var option3: String? = null,

    var sku: String? = null,

    var requiresShipping: Boolean? = null,

    var taxable: Boolean? = null,

    var available: Boolean? = null,

    var price: Double? = null,

    var grams: Int? = null,

    var compareAtPrice: Double? = null,

    var position: Int? = null,

    var createdAt: Date? = null,

    var updatedAt: Date? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "featured_image_id")
    var featuredImage: ImageEntity? = null
)