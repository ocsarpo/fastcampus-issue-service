package fastcampus.issueservice.model

import fastcampus.issueservice.domain.CommentEntity

data class CommentRequest(
    val body: String,
)


data class CommentResponse(
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val username: String? = null,
    val body: String
)

// 이번엔 다른 방법으로 초기화 하는 방법 확장함수 사용
fun CommentEntity.toResponse() = CommentResponse(
    id = id!!,
    issueId = issueEntity.id!!,
    userId = userId,
    body = body,
    username = username,
)
