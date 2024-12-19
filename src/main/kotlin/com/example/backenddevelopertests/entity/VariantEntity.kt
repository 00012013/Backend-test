package com.example.backenddevelopertests.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "variant")
public class VariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String option1;

    private String option2;

    private String option3;

    private String sku;

    private Boolean requiresShipping;

    private Boolean taxable;

    private Boolean available;

    private String price;

    private Integer grams;

    private String compareAtPrice;

    private Integer position;

    private Date createdAt;

    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "featured_image_id")
    private ImageEntity featuredImage;
}