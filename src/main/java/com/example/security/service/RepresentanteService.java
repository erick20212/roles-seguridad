package com.example.security.service;

import java.util.List;
import java.util.Optional;

import com.example.security.entity.Representante;

public interface RepresentanteService {
	Representante create(Representante a);
	Representante update(Representante a);
    void delete(Long id);
    Optional<Representante> read(Long id);
    List<Representante> readAll();
}
