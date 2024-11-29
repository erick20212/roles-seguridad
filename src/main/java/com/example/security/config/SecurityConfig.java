package com.example.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si no es necesario
<<<<<<< HEAD
            .cors(Customizer.withDefaults()) // Habilitar soporte para CORS
            .authorizeHttpRequests(authorize -> {
                // Rutas públicas
                authorize.requestMatchers(
                        "/api/auth/**", 
                        "/api/persona/**", 
                        "/api/estudiante/**", 
                        "/api/empresa/**", 
                        "/api/usuario/**",
                        "/api/rol_acceso/**",
                        "/api/role/**", 
                        "/api/acceso/**",
                        "/api/usuario_role/**",
                        "/api/linea/**",
                        "/api/empresa-linea**",
                        "/api/representante/**",
                        "/api/supervisores/**",
                        "/api/solicitud/**",
                        "/api/**",
                        "/api/persona-usuario/**",
                        "/api/supervisores/**",
                        "/api/**"
                ).permitAll();

                // Rutas protegidas
                authorize.requestMatchers(
                        "/api/solicitudes/**",
                        "/api/solicitudes/list**",
                        "/api/solicitudes/aprobadas**",
                        "/api/solicitudes/paginadas"
                        // Proteger solicitudes
                ).authenticated();

                // Todas las demás rutas requieren autenticación
                authorize.anyRequest().authenticated();
            })
            .httpBasic(Customizer.withDefaults()); // Usar autenticación básica
=======
                .cors(Customizer.withDefaults()) // Habilitar soporte para CORS
                .authorizeHttpRequests(authorize -> {
                    // Permitir todas las rutas sin autenticación
                    authorize.requestMatchers("/**").permitAll(); // Todas las rutas y métodos HTTP son accesibles
                })
                .httpBasic(Customizer.withDefaults()); // Usar autenticación básica opcionalmente
>>>>>>> ec9a1a3036ba84fc0e0eb957a01c62e25c708b3d

        // Configuración de manejo de excepciones
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));

        // Agregar filtro JWT (este filtro será ignorado porque todas las rutas son públicas)
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
