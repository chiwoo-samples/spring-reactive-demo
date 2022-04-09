package example.chiwoo.demo.user.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class UserRouterFunction {

    @Bean
    fun userRouter(userHandler: UserHandler): RouterFunction<ServerResponse> {
        return router {
            GET("/api/users", userHandler::getUsers)
            GET("/api/users/{id}", userHandler::getUser)
            POST("/api/users", userHandler::addUser)
            PUT("/api/users/{id}", userHandler::updateUser)
            DELETE("/api/users/{id}", userHandler::deleteUser)
        }
    }

}
