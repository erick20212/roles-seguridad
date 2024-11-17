package com.example.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.security.entity.Rol;

@Service
public interface RolService {

	Rol create(Rol a);
	Rol update(Rol a);
    void delete(Long id);
    Optional<Rol> read(Long id);
    List<Rol> readAll();
}
