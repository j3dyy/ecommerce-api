package io.commerce.ecommerceapi

import io.commerce.ecommerceapi.app.service.FileService
import io.commerce.ecommerceapi.app.service.StorageProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication(
    exclude = [SecurityAutoConfiguration::class]
)
@EnableConfigurationProperties(StorageProperties::class)
class EcommerceApiApplication{
    @Bean
    fun init(fileService: FileService): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            fileService.deleteAll()
            fileService.init()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<EcommerceApiApplication>(*args)
}
