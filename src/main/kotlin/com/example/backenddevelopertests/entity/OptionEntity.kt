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
@Table(name = "option")
@SequenceGenerator(name = "option_id_seq", sequenceName = "option_id_seq", allocationSize = 1)
class OptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "option_id_seq")
    var id: Long? = null,

    var name: String? = null,

    var position: Int? = null,

    @ElementCollection
    var values: List<String>? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductEntity? = null,
)