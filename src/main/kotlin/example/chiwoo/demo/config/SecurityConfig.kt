package example.chiwoo.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity

import org.springframework.security.web.server.SecurityWebFilterChain

/**
 * @see <a href="https://github.com/eugenp/tutorials/blob/master/spring-reactive/src/main/java/com/baeldung/reactive/webflux/functional/EmployeeFunctionalConfig.java">SecurityWebFilterChain</a>
 */
@Configuration
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http.csrf()
            .disable()
            .authorizeExchange()
            .anyExchange()
            .permitAll()
        return http.build()
    }
}