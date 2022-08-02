package fastcampus.issueservice.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<CommentEntity, Long> {

    fun findByIdAndUserId(id: Long, userId: Long): CommentEntity?
}
