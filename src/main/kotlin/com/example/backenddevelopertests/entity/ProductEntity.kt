package com.example.backenddevelopertests.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
class ProductEntity(
    @Id
    var id: Long? = null,

    var title: String? = null,

    var handle: String? = null,

    var bodyHtml: String? = null,

    var publishedAt: String? = null,

    var createdAt: String? = null,

    var updatedAt: String? = null,

    var vendor: String? = null,

    var productType: String? = null,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_tags", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "tag")
    var tags: List<String>? = null,

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    var variants: MutableList<VariantEntity> = mutableListOf(),

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    var images: MutableList<ImageEntity> = mutableListOf(),

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    var options: MutableList<OptionEntity> = mutableListOf()
)
