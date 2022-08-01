package fastcampus.issueservice.domain.enums

enum class IssueType {

    BUG, TASK;

    companion object {
        // fun of(type: String) = valueOf(type.uppercase()) --> IssueType.of("BUG") == IssueType.BUG

        // --> IssueType("BUG") == IssueType.BUG 혹은 IssueType.invoke("BUG")
        operator fun invoke(type: String) = valueOf(type.uppercase())
    }
}
