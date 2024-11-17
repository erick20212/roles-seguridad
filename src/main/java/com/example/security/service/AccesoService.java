package com.example.security.service;

import java.util.List;
import java.util.Optional;


import com.example.security.entity.Accesos;

public interface AccesoService {
	
	Accesos create(Accesos a);
	Accesos update(Accesos a);
    void delete(Long id);
    Optional<Accesos> read(Long id);
    List<Accesos> readAll();
}
