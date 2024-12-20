package com.example.security.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.security.repository.Usuario_Rol_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.Usuario_Rol;
import com.example.security.service.UsuarioRolService;

@RestController
@RequestMapping("/api/usuario_role")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioRolController {
	 @Autowired
	    private UsuarioRolService usuarioRolService;
	 @Autowired
	 private Usuario_Rol_Repository usuarioRolRepository;

	    @GetMapping
	    public ResponseEntity<List<Usuario_Rol>> readAll() {
	        List<Usuario_Rol> lista = usuarioRolService.readAll();
	        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	    }

	    @PostMapping
	    public ResponseEntity<Usuario_Rol> create(@RequestBody Usuario_Rol usuarioRol) {
	        return new ResponseEntity<>(usuarioRolService.create(usuarioRol), HttpStatus.CREATED);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Usuario_Rol> read(@PathVariable Long id) {
	        Optional<Usuario_Rol> usuarioRol = usuarioRolService.read(id);
	        return usuarioRol.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Usuario_Rol> update(@PathVariable Long id, @RequestBody Usuario_Rol usuarioRol) {
	        Optional<Usuario_Rol> existingUsuarioRol = usuarioRolService.read(id);
	        if (existingUsuarioRol.isPresent()) {
	            return new ResponseEntity<>(usuarioRolService.update(usuarioRol), HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        usuarioRolService.delete(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	@GetMapping("/roles")
	public Map<String, Long> getRolesEstadisticas() {
		// Usamos el repositorio de JPA para obtener los datos
		List<Object[]> results = usuarioRolRepository.countRolesForPersons();

		// Transformamos los resultados en un mapa donde la clave es el nombre del rol
		// y el valor es la cantidad de personas en ese rol
		return results.stream()
				.collect(Collectors.toMap(
						result -> getRoleNameById((Long) result[0]),  // Obtener el nombre del rol
						result -> (Long) result[1]                    // Obtener la cantidad de personas
				));
	}

	// Método para convertir el id del rol a su nombre
	private String getRoleNameById(Long roleId) {
		switch (roleId.intValue()) {
			case 1: return "Administrador";
			case 2: return "Supervisor";
			case 3: return "Estudiante";
			case 4: return "Coordinador";
			case 21: return "Admin";
			default: return "Sin identificar";
		}
	}

}