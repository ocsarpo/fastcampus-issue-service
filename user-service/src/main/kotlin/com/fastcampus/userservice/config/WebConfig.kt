package com.fastcampus.userservice.config

import com.fastcampus.userservice.model.AuthToken
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Configuration
class WebConfig(
    private val authTokenResolver: AuthTokenResolver,
) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        super.configureArgumentResolvers(configurer)
        configurer.addCustomResolver(authTokenResolver)
    }

    // 웹 브라우저에서 cross-origin 호출 시
    // preflight 로 OPTIONS method 로 호출하여 아래 설정한 응답헤더를 받아 동작을 처리함.
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // 실제 호출하게 되는 오리진을 넣어줘야함..
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(3600)
    }
}

@Component
class AuthTokenResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        // 여기에 작성한 조건에 맞는 경우에만 resolveArgument 가 동작.
        return parameter.hasParameterAnnotation(AuthToken::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        val authHeader = exchange.request.headers["Authorization"]?.first()
        checkNotNull(authHeader)

        // Authorization : Bearer xxxyyydddffgdfgdsgf
        val token = authHeader.split(" ")[1]
        return token.toMono()
    }
}
