package example.chiwoo.demo.user.router

import example.chiwoo.demo.user.model.User
import example.chiwoo.demo.user.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDateTime

@Component
class UserHandler(val userRepository: UserRepository) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    fun getUsers(req: ServerRequest): Mono<ServerResponse> {
        return ok().bodyValue(userRepository.getUsers())
    }

    fun getUser(req: ServerRequest): Mono<ServerResponse> {
        val userId = req.pathVariable("id").toInt()
        log.info("getUsers: {}, userId: {}", req, userId)
        return try {
            val user = userRepository.getUser(userId)
            ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
        } catch (e: Exception) {
            log.info("{} not found!", userId)
            ServerResponse.notFound().build()
        }
    }

    fun addUser(req: ServerRequest): Mono<ServerResponse> {
        log.info("req.requestPath(): {}", req.requestPath())
        return req.bodyToMono<User>()
            .flatMap { user ->
                val newId = userRepository.addUser(user)
                ServerResponse.created(
                    URI.create(String.format("%s/%s", req.requestPath(), newId))
                ).build()
            }
    }

    fun updateUser(req: ServerRequest): Mono<ServerResponse> {
        val userId = req.pathVariable("id").toInt()
        return req.bodyToMono<User>()
            .map {
                val createDtm = it.creatdDtm ?: LocalDateTime.now()
                User(it.userName, userId, it.email, createDtm)
            }.flatMap {
                if (userRepository.updateUser(it)) {
                    ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(it))
                } else {
                    status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                }
            }
    }

    fun deleteUser(req: ServerRequest): Mono<ServerResponse> {
        val userId = req.pathVariable("id").toInt()
        log.info("DELETE: {}, userId: {}", req, userId)
        return if (userRepository.deleteUser(userId)) {
            ServerResponse.noContent().build()
        } else {
            ServerResponse.notFound().build()
        }
    }

}