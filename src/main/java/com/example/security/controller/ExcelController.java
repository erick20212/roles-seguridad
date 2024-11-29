package com.example.security.controller;

import com.example.security.dto.ExcelDTO;
import com.example.security.service.ExcelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            // Llamar al servicio para procesar el archivo
            excelService.procesarExcel(file);
            return new ResponseEntity<>("Archivo procesado con éxito", HttpStatus.OK);
        } catch (IOException e) {
            // Manejo de errores si hay un problema al leer el archivo
            return new ResponseEntity<>("Error al procesar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            // Si los encabezados no son correctos o algún otro error de validación
            return new ResponseEntity<>("Error en el formato del archivo: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
