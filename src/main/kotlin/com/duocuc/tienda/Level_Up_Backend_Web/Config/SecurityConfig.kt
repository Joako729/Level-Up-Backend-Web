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
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests {
                // 1. SWAGGER Y AUTH (Público siempre)
                it.requestMatchers("/api/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()

                // 2. PRODUCTOS: VER es Público, pero MODIFICAR es Solo Admin
                // Es vital poner los métodos específicos antes o separar claramente
                it.requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll() // Ver productos
                it.requestMatchers(HttpMethod.POST, "/api/productos").hasRole("ADMIN") // Crear
                it.requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN") // Editar
                it.requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN") // Borrar

                // 3. USUARIOS (Para que puedas ver la lista en Postman/Navegador)
                it.requestMatchers(HttpMethod.GET, "/api/usuarios").permitAll()

                // 4. Cualquier otra cosa requiere estar logueado
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}