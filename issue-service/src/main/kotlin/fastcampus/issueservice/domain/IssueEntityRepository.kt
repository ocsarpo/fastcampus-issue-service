package fastcampus.issueservice.domain

import org.springframework.data.jpa.repository.JpaRepository

interface IssueEntityRepository : JpaRepository<IssueEntity, Long>
