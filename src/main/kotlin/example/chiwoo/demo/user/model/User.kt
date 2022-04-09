package example.chiwoo.demo.user.model

import com.fasterxml.jackson.annotation.JsonRootName
import example.chiwoo.demo.NoArg
import java.time.LocalDateTime

@NoArg
@JsonRootName("user")
data class User (
    val userName: String?,
    val userId: Int?,
    val email: String?,
    val creatdDtm: LocalDateTime? = null,
)