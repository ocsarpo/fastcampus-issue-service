package com.fastcampus.userservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebConfig : WebFluxConfigurer {

    // 웹 브라우저에서 cross-origin 호출 시
    // preflight 로 OPTIONS method 로 호출하여 아래 설정한 응답헤더를 받아 동작을 처리함.
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // 실제 호출하게 되는 오리진을 넣어줘야함..
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(3600)
    }
}
