package com.example.backenddevelopertests.controller

import com.example.backenddevelopertests.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ProductController(@Autowired private val productService: ProductService) {
    @GetMapping("/test")
    fun getList(model: Model): String {
        model.addAttribute("products", productService.getAllProducts())
        return "product"
    }

    @GetMapping("/load")
    fun reload(model: Model): String {
        model.addAttribute("products", productService.getAllProducts())
        return "product :: product-row"
    }

    @PostMapping("/test")
    fun addProduct(
        model: Model,
        @RequestParam("title") title: String,
        @RequestParam("handle") handle: String,
        @RequestParam("publishedAt") publishedAt: String,
        @RequestParam("vendor") vendor: String,
        @RequestParam("productType") productType: String,
    ): String {
        productService.saveProduct(
            title,
            handle,
            publishedAt,
            vendor,
            productType
        )
        model.addAttribute("products", productService.getAllProducts())
        return "product :: product-row"
    }
}