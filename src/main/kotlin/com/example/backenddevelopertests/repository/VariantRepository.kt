package com.example.backenddevelopertests.repository

import com.example.backenddevelopertests.entity.VariantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VariantRepository : JpaRepository<VariantEntity, Long>