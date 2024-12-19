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
    @GetMapping("/")
    fun getProducts(model: Model): String {
        val products = productService.getAllProducts()
        model.addAttribute("products", products)
        return "productList :: table-and-form"
    }

    @PostMapping("/save")
    fun saveProduct(@ModelAttribute product: ProductModel): String {
        productService.saveProduct(product)
        return "productList :: table-and-form"
    }

    @GetMapping("/test")
    fun reloadProducts(model: Model): String {
        val products = productService.getAllProducts()
        model.addAttribute("products", products)
        return "productList"
    }

}