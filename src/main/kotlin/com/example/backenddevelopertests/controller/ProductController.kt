package com.example.backenddevelopertests.controller

import com.example.backenddevelopertests.model.ProductModel
import com.example.backenddevelopertests.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ProductController(@Autowired private val productService: ProductService) {

    @GetMapping("/test")
    fun getProducts(model: Model): String {
        model.addAttribute("products", productService.getAllProducts())
        return "products"
    }

    @PostMapping("/products")
    fun saveProduct(@ModelAttribute product: ProductModel, model: Model): String {
        productService.saveProduct(product)
        model.addAttribute("products", productService.getAllProducts())
        return "productTable :: tableBody"
    }

}