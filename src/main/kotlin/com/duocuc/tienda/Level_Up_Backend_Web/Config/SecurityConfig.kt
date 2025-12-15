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
                // 1. Público
                it.requestMatchers("/api/auth/**", "/api/productos", "/api/productos/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()

                // 2. Mis pedidos (Cualquier usuario logueado)
                it.requestMatchers("/api/pedidos/mis-pedidos").authenticated()

                // 3. GESTIÓN DE PRODUCTOS: ADMIN y VENDEDOR
                // Aquí permitimos que ambos roles puedan Crear, Editar y Borrar productos
                it.requestMatchers(HttpMethod.POST, "/api/productos").hasAnyRole("ADMIN", "VENDEDOR")
                it.requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAnyRole("ADMIN", "VENDEDOR")
                it.requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAnyRole("ADMIN", "VENDEDOR")

                // 4. Solo ADMIN (Gestión de usuarios y ver todos los pedidos)
                it.requestMatchers(HttpMethod.GET, "/api/pedidos").hasRole("ADMIN")
                it.requestMatchers("/api/usuarios").hasRole("ADMIN")
                it.requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // 5. Resto requiere autenticación
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