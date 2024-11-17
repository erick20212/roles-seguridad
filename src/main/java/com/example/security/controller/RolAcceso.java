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

import com.example.security.entity.Rol_Acceso;
import com.example.security.service.RolAccesoService;

@RestController
@RequestMapping("/api/rol_acceso")
@CrossOrigin(origins = "http://localhost:4200")
public class RolAcceso {

    @Autowired
    private RolAccesoService rolAccesoService;

    @GetMapping
    public ResponseEntity<List<Rol_Acceso>> readAll() {
        List<Rol_Acceso> lista = rolAccesoService.readAll();
        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rol_Acceso> create(@RequestBody Rol_Acceso rolAcceso) {
        return new ResponseEntity<>(rolAccesoService.create(rolAcceso), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol_Acceso> read(@PathVariable Long id) {
        Optional<Rol_Acceso> rolAcceso = rolAccesoService.read(id);
        return rolAcceso.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol_Acceso> update(@PathVariable Long id, @RequestBody Rol_Acceso rolAcceso) {
        Optional<Rol_Acceso> existingRolAcceso = rolAccesoService.read(id);
        if (existingRolAcceso.isPresent()) {
            return new ResponseEntity<>(rolAccesoService.update(rolAcceso), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rolAccesoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}