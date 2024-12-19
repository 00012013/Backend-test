package com.example.backenddevelopertests.entity

import jakarta.persistence.*

@Entity
@Table(name = "image")
class ImageEntity(
    @Id
    var id: Long? = null,

    var createdAt: String? = null,

    var position: Int? = null,

    var updatedAt: String? = null,

    var alt: String? = null,

    var src: String? = null,

    var width: Int? = null,

    var height: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    var variant: VariantEntity? = null,
)
