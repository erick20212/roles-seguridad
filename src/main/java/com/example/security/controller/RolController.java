package com.example.security.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.security.entity.Rol;
import com.example.security.service.RolService;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "http://localhost:4200")
public class RolController {
	 @Autowired
	    private RolService rolService;

	    @GetMapping
	    public ResponseEntity<List<Rol>> readAll() {
	        List<Rol> lista = rolService.readAll();
	        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	    }

	    @PostMapping
	    public ResponseEntity<Rol> create(@RequestBody Rol rol) {
	        return new ResponseEntity<>(rolService.create(rol), HttpStatus.CREATED);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Rol> read(@PathVariable Long id) {
	        Optional<Rol> rol = rolService.read(id);
	        return rol.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Rol> update(@PathVariable Long id, @RequestBody Rol rol) {
	        Optional<Rol> existingRol = rolService.read(id);
	        if (existingRol.isPresent()) {
	            return new ResponseEntity<>(rolService.update(rol), HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        rolService.delete(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	}