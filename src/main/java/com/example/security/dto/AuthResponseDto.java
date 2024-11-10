package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
	private String accessToken;
    private String welcomeMessage;
    private String username; // Nombre de usuario
    private String role; // Rol del usuario
}