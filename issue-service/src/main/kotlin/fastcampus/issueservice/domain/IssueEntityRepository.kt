package fastcampus.issueservice.domain

import fastcampus.issueservice.domain.enums.IssueStatus
import org.springframework.data.jpa.repository.JpaRepository

interface IssueEntityRepository : JpaRepository<IssueEntity, Long> {

    fun findAllByStatusOrderByCreatedAtDesc(status: IssueStatus): List<IssueEntity>
}
