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

import com.example.security.entity.Persona;
import com.example.security.service.PersonaService;

@RestController
@RequestMapping("/api/persona")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaController {
	@Autowired
    private PersonaService personaService;

    @GetMapping
    public ResponseEntity<List<Persona>> readAll() {
        List<Persona> lista = personaService.readAll();
        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Persona> create(@RequestBody Persona persona) {
        return new ResponseEntity<>(personaService.create(persona), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> read(@PathVariable Long id) {
        Optional<Persona> persona = personaService.read(id);
        return persona.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> update(@PathVariable Long id, @RequestBody Persona persona) {
        Optional<Persona> existingPersona = personaService.read(id);
        if (existingPersona.isPresent()) {
            return new ResponseEntity<>(personaService.update(persona), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}