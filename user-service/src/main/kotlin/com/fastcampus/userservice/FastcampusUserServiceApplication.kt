package com.fastcampus.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan // Configuration 클래스 스캔하도록 하기 위함.
class FastcampusUserServiceApplication

fun main(args: Array<String>) {
    runApplication<FastcampusUserServiceApplication>(*args)
}
