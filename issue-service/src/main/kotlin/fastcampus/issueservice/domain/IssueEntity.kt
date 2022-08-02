package fastcampus.issueservice.domain

import fastcampus.issueservice.domain.enums.IssuePriority
import fastcampus.issueservice.domain.enums.IssueStatus
import fastcampus.issueservice.domain.enums.IssueType
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.EAGER
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "issues")
class IssueEntity(
    @Id @GeneratedValue(strategy = IDENTITY) val id: Long? = null,

    @Column var userId: Long,

    @OneToMany(fetch = EAGER)
    val commentEntityList: MutableList<CommentEntity> = mutableListOf(),

    @Column var summary: String,
    @Column var description: String,
    @Column @Enumerated(STRING) var type: IssueType,
    @Column @Enumerated(STRING) var priority: IssuePriority,
    @Column @Enumerated(STRING) var status: IssueStatus,
) : BaseEntity()
