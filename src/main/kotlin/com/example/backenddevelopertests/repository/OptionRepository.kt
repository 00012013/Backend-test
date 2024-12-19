package com.example.backenddevelopertests.repository

import com.example.backenddevelopertests.entity.OptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OptionRepository : JpaRepository<OptionEntity, Long>
