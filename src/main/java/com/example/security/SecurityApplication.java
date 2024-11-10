package com.example.security;

import com.example.security.entity.Rol;
import com.example.security.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    // Método para inicializar los roles en la base de datos
    @Bean
    CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {
            if (!rolRepository.findByName("USER").isPresent()) {
                rolRepository.save(new Rol("USER"));
            }
            if (!rolRepository.findByName("ADMIN").isPresent()) {
                rolRepository.save(new Rol("ADMIN"));
            }
            if (!rolRepository.findByName("secretaria").isPresent()) { // Añadir el rol de MODERATOR
                rolRepository.save(new Rol("secretaria"));
            }
        };
    }
}

