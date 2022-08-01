package fastcampus.issueservice.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

// 엔티티들의 공통 속성을 정의하여 하위 엔티티에서 상속하여 사용함.
@MappedSuperclass
// 엔티티에 특정 이벤트 발생 시 콜백을 처리하도록 하는 애노테이션
@EntityListeners(AuditingEntityListener::class) // 이것이 적용되도록 하려면 @EnableJpaAuditing 을 붙여야 함.
abstract class BaseEntity (

    // 엔티티 생성 시 자동으로 값을 넣음.
    @CreatedDate
    var createdAt: LocalDateTime? = null,

    // 수정 될 때 마다 값을 넣음.
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
)
