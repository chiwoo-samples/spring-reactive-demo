package example.chiwoo.demo.config

import example.chiwoo.demo.model.Builder
import example.chiwoo.demo.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDateTime

@Suppress("JAVA_CLASS_ON_COMPANION")
@Configuration
class UserRouterFunctionConfig {

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

    val users = buildUsers()

    private fun exists(userId: Int): Int {
        return this.users.indexOfFirst { it.userId == userId }
    }

    private fun addUser(user: User): Mono<Int?> {
        this.users.add(user)
        return Mono.just(user.userId!!)
    }

    private fun updateUser(user: User): Mono<User?> {
        val index = this.users.indexOfFirst { it.userId == user.userId }
        this.users[index] = user
        return Mono.just(user)
    }

    private fun deleteUser(index: Int) {
        this.users.removeAt(index)
    }

    @Bean
    fun userHandler(): RouterFunction<ServerResponse?> = route().GET("/api/users") { req ->
        log.info("getUsers: {}", req)
        ok().contentType(MediaType.APPLICATION_JSON).bodyValue(this.users)
    }.GET("/api/users/{id}") { req ->
        val userId = req.pathVariable("id").toInt()
        log.info("getUsers: {}, userId: {}", req, userId)
        val index = exists(userId)
        if (index > -1) {
            ok().contentType(MediaType.APPLICATION_JSON).bodyValue(users[index])
        } else {
            log.info("{} not found!", userId)
            notFound().build()
        }
    }.POST("/api/users") { req ->
        log.info("req.requestPath(): {}", req.requestPath())
        val newId = users[users.lastIndex].userId!! + 1
        val location = String.format("%s/%s", req.requestPath(), newId)
        req.bodyToMono<User>().map { user -> User(user.userName, newId, user.email, LocalDateTime.now()) }
            .flatMap { user -> addUser(user) }.flatMap { id -> created(URI.create(location)).build() }
    }.PUT("/api/users/{id}") { req ->
        val userId = req.pathVariable("id").toInt()
        log.info("PUT-userId: {}", userId)
        val index = exists(userId)
        if (index > -1) {
            val user1 = users[index]
            req.bodyToMono<User>().map { user -> User(user.userName, user1.userId, user.email, user1.creatdDtm) }
                .flatMap { user ->
                    log.info("PUT-user: {}", user)
                    updateUser(user)
                }.flatMap { user ->
                    ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user!!)
                }
        } else {
            log.info("{} not found!", userId)
            notFound().build()
        }
    }.DELETE("/api/users/{id}") { req ->
        val userId = req.pathVariable("id").toInt()
        log.info("DELETE: {}, userId: {}", req, userId)
        val index = exists(userId)
        if (index > -1) {
            deleteUser(index)
            noContent().build()
        } else {
            log.info("{} not found!", userId)
            notFound().build()
        }
    }.build()


}