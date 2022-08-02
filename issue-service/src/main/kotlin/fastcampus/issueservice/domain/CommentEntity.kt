package fastcampus.issueservice.domain

import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "comments")
class CommentEntity(
    @Id @GeneratedValue(strategy = IDENTITY) val id: Long? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "issue_id")
    val issueEntity: IssueEntity,

    @Column
    val userId: Long,

    @Column
    val username: String,

    @Column
    var body: String,

) : BaseEntity()
