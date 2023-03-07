package com.fastcampus.userservice.exception

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Configuration
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper,
) : ErrorWebExceptionHandler { // 리액티브에서 제공하는 ExceptionHandler.

    private val logger = KotlinLogging.logger { }
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> = mono {
        // 코루틴 코드를 Mono 로 반환

        logger.error { ex.message }

        val errorResponse = if (ex is ServerException) {
            ErrorResponse(code = ex.code, message = ex.message)
        } else {
            ErrorResponse(code = 500, message = "Internal Server Error")
        }

        with(exchange.response) {
            this.statusCode = HttpStatus.OK
            this.headers.contentType = MediaType.APPLICATION_JSON

            val dataBuffer = bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))

            this.writeWith(dataBuffer.toMono()).awaitSingle()
        }
    }
}