package fastcampus.issueservice.model

import com.fasterxml.jackson.annotation.JsonFormat
import fastcampus.issueservice.domain.IssueEntity
import fastcampus.issueservice.domain.enums.IssuePriority
import fastcampus.issueservice.domain.enums.IssueStatus
import fastcampus.issueservice.domain.enums.IssueType
import java.time.LocalDateTime

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)

data class IssueResponse(
    val id: Long,
    val summary: String,
    val description: String,
    val userId: Long,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 이렇게 하든, 공통 설정을 수정을 하든..
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 지금은 가벼운 서비스이고, createdAt, updatedAt만 사용하므로 이거 씀.
    val updatedAt: LocalDateTime?,
) {

    companion object {

        operator fun invoke(issueEntity: IssueEntity) =
            with(issueEntity) {
                IssueResponse(
                    id = id!!,
                    userId = userId,
                    summary = summary,
                    description = description,
                    type = type,
                    priority = priority,
                    status = status,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
    }
}
