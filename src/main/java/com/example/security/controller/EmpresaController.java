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

import com.example.security.entity.Empresa;
import com.example.security.service.EmpresaService;


@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpresaController {
	
	@Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<Empresa>> readAll() {
        List<Empresa> lista = empresaService.readAll();
        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Empresa> create(@RequestBody Empresa empresa) {
        return new ResponseEntity<>(empresaService.create(empresa), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> read(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.read(id);
        return empresa.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(@PathVariable Long id, @RequestBody Empresa empresa) {
        Optional<Empresa> existingEmpresa= empresaService.read(id);
        if (existingEmpresa.isPresent()) {
            return new ResponseEntity<>(empresaService.update(empresa), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	empresaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}