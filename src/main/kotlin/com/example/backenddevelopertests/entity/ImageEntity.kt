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
@Table(name = "image")
class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    private val createdAt: Date? = null

    private val position: Int? = null

    private val updatedAt: Date? = null

    private val alt: String? = null

    private val src: String? = null

    private val width: Int? = null

    private val height: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private val product: ProductEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private val variant: VariantEntity? = null
}
