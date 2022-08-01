package fastcampus.issueservice.service

import fastcampus.issueservice.domain.IssueEntity
import fastcampus.issueservice.domain.IssueEntityRepository
import fastcampus.issueservice.model.IssueRequest
import fastcampus.issueservice.model.IssueResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class IssueService(
    private val issueEntityRepository: IssueEntityRepository,
) {

    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {

        val issueEntity = IssueEntity(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse(issueEntityRepository.save(issueEntity))
    }
}
