package com.example.backenddevelopertests

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BackEndDeveloperTestsApplication

fun main(args: Array<String>) {
    runApplication<BackEndDeveloperTestsApplication>(*args)
}
