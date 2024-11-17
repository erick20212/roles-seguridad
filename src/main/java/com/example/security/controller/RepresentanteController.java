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

import com.example.security.entity.Representante;
import com.example.security.service.RepresentanteService;

@RestController
@RequestMapping("/api/representante")
@CrossOrigin(origins = "http://localhost:4200")
public class RepresentanteController {
	@Autowired
    private RepresentanteService representanteService;

    @GetMapping
    public ResponseEntity<List<Representante>> readAll() {
        List<Representante> lista = representanteService.readAll();
        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Representante> create(@RequestBody Representante representante) {
        return new ResponseEntity<>(representanteService.create(representante), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Representante> read(@PathVariable Long id) {
        Optional<Representante> representante = representanteService.read(id);
        return representante.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Representante> update(@PathVariable Long id, @RequestBody Representante representante) {
        Optional<Representante> existingRepresentante= representanteService.read(id);
        if (existingRepresentante.isPresent()) {
            return new ResponseEntity<>(representanteService.update(representante), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	representanteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}