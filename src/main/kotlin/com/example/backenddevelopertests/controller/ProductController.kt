package com.example.backenddevelopertests.controller

import com.example.backenddevelopertests.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProductController(@Autowired private val productService: ProductService) {

    @GetMapping("/test")
    fun getProducts(model: Model): String {
        model.addAttribute("products", productService.getAllProducts())
        return "products"
    }
}