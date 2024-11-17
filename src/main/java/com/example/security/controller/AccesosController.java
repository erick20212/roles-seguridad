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

import com.example.security.entity.Accesos;
import com.example.security.service.AccesoService;

@RestController
@RequestMapping("/api/acceso")
@CrossOrigin(origins = "http://localhost:4200")
public class AccesosController {
	 @Autowired
	    private AccesoService accesoService;

	    @GetMapping
	    public ResponseEntity<List<Accesos>> readAll() {
	        List<Accesos> lista = accesoService.readAll();
	        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	    }

	    @PostMapping
	    public ResponseEntity<Accesos> create(@RequestBody Accesos accesos) {
	        return new ResponseEntity<>(accesoService.create(accesos), HttpStatus.CREATED);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Accesos> read(@PathVariable Long id) {
	        Optional<Accesos> accesos = accesoService.read(id);
	        return accesos.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Accesos> update(@PathVariable Long id, @RequestBody Accesos accesos) {
	        Optional<Accesos> existingAcceso = accesoService.read(id);
	        if (existingAcceso.isPresent()) {
	            return new ResponseEntity<>(accesoService.update(accesos), HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        accesoService.delete(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	}