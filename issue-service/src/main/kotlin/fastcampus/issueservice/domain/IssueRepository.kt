package fastcampus.issueservice.domain

import fastcampus.issueservice.domain.enums.IssueStatus
import org.springframework.data.jpa.repository.JpaRepository

interface IssueRepository : JpaRepository<IssueEntity, Long> {

    fun findAllByStatusOrderByCreatedAtDesc(status: IssueStatus): List<IssueEntity>
}
