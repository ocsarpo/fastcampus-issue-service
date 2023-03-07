package com.fastcampus.userservice.domain.repository

import com.fastcampus.userservice.domain.entity.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<UserEntity, Long>{

    // 코루틴 함수임을 표시 (suspend)
    suspend fun findByEmail(email: String): UserEntity?
}
