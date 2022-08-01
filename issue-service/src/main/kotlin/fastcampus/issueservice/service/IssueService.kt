package fastcampus.issueservice.service

import fastcampus.issueservice.domain.IssueEntity
import fastcampus.issueservice.domain.IssueEntityRepository
import fastcampus.issueservice.domain.enums.IssueStatus
import fastcampus.issueservice.exception.NotFoundException
import fastcampus.issueservice.model.IssueRequest
import fastcampus.issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
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

    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus) =
        issueEntityRepository.findAllByStatusOrderByCreatedAtDesc(status)
            .map { IssueResponse(it) }

    @Transactional(readOnly = true)
    fun get(id: Long): IssueResponse {
        val issueEntity = issueEntityRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")

        return IssueResponse(issueEntity)
    }

    @Transactional
    fun edit(userId: Long, id: Long, request: IssueRequest): IssueResponse {
        val issueEntity = issueEntityRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")

        return with(issueEntity) {
            summary = request.summary
            description = request.description
            this.userId = userId
            type = request.type
            priority = request.priority
            status = request.status

            // 더티체킹 하기 때문에 save 를 호출 하지 않아도 되지만, 명시적으로 사용함.
            IssueResponse(issueEntityRepository.save(this))
        }
    }

    @Transactional
    fun delete(id: Long) {
        issueEntityRepository.deleteById(id)
    }
}
