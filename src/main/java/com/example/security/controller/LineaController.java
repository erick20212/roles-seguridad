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

import com.example.security.entity.Linea;
import com.example.security.service.LineaService;


@RestController
@RequestMapping("/api/linea")
@CrossOrigin(origins = "http://localhost:4200")
public class LineaController {
	@Autowired
    private LineaService lineaService;

    @GetMapping
    public ResponseEntity<List<Linea>> readAll() {
        List<Linea> lista = lineaService.readAll();
        return lista.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Linea> create(@RequestBody Linea linea) {
        return new ResponseEntity<>(lineaService.create(linea), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Linea> read(@PathVariable Long id) {
        Optional<Linea> linea = lineaService.read(id);
        return linea.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Linea> update(@PathVariable Long id, @RequestBody Linea linea) {
        Optional<Linea> existingLinea = lineaService.read(id);
        if (existingLinea.isPresent()) {
            return new ResponseEntity<>(lineaService.update(linea), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	lineaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}