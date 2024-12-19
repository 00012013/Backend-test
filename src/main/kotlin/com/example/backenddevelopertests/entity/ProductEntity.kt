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
@Table(name = "product")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    private val title: String? = null

    private val handle: String? = null

    private val bodyHtml: String? = null

    private val publishedAt: Date? = null

    private val createdAt: Date? = null

    private val updatedAt: Date? = null

    private val vendor: String? = null

    private val productType: String? = null

    @ElementCollection
    private val tags: List<String>? = null

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val variants: List<VariantEntity>? = null

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val images: List<ImageEntity>? = null

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val options: List<OptionEntity>? = null
}

