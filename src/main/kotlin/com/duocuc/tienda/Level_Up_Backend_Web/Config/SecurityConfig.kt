package com.duocuc.tienda.Level_Up_Backend_Web.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtFilter: JwtFilter) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) } // ¡Activa la configuración de abajo!
            .authorizeHttpRequests {
                // Rutas Públicas (SIN LOGIN)
                it.requestMatchers(
                    "/api/auth/**",
                    "/api/productos",
                    "/api/productos/**",
                    "/api/usuarios",
                    "/v3/api-docs/**",
                    "/swagger-ui/**"
                ).permitAll()

                // Rutas de ADMIN
                it.requestMatchers(HttpMethod.POST, "/api/productos").hasRole("ADMIN")
                it.requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")
                it.requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")

                // Rutas protegidas generales
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()
        // CAMBIO CLAVE: Usamos allowedOriginPatterns("*") para permitir TODO
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}