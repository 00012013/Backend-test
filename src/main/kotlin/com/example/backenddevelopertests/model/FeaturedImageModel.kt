package com.example.backenddevelopertests.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import lombok.Getter
import lombok.Setter

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class FeaturedImageModel : ImageModel() {
    val alt: String? = null
}
