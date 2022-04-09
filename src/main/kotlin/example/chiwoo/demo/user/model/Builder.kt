package example.chiwoo.demo.user.model

import example.chiwoo.demo.utils.RandomUtils
import java.time.LocalDateTime

class Builder {
    fun buildUser(index: Int): User {
        val userName = RandomUtils.nextWord(10)
        val userId = index // RandomUtils.nextInt(1, limit)
        val email = RandomUtils.nextEmail(userName)
        val creatdDtm = LocalDateTime.now()
        return User(userName, userId, email, creatdDtm)
    }
}