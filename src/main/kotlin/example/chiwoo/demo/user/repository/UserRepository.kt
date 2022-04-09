package example.chiwoo.demo.user.repository

import example.chiwoo.demo.user.model.Builder
import example.chiwoo.demo.user.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
class UserRepository {

    companion object {
        val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    private fun buildUsers(): ArrayList<User> {
        val users: ArrayList<User> = ArrayList()
        val limit = 100
        for (i in 1..limit) {
            val user = Builder().buildUser(i)
            users.add(user)
        }
        return users
    }

    private val users: ArrayList<User> = buildUsers()

    private fun exists(userId: Int): Int {
        return this.users.indexOfFirst { it.userId == userId }
    }

    private fun newId(): Int {
        return this.users[this.users.lastIndex].userId!! + 1
    }

    fun getUsers(): List<User> {
        return this.users
    }

    fun getUser(userId: Int): User {
        val index = exists(userId)
        return this.users[index]
    }

    fun addUser(user: User): Int {
        val newId = newId()
        val newUser = User(user.userName, newId(), user.email, LocalDateTime.now())
        this.users.add(newUser)
        return newId
    }

    fun updateUser(user: User): Boolean {
        return try {
            val index = this.exists(user.userId!!)
            val oldUser = getUser(index)
            log.info("oldUser: {}", oldUser)
            if (oldUser != null) {
                this.users[index] = user
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteUser(userId: Int): Boolean {
        return try {
            val index = exists(userId)
            this.users.removeAt(index)
            true
        } catch (e: Exception) {
            false
        }
    }
}