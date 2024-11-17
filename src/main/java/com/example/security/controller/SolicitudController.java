package com.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.dto.SolicitudDto;
import com.example.security.service.SolicitudService;

import java.util.List;

@RestController
@RequestMapping("/api/solicitud")
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping("/inicial")
    public ResponseEntity<SolicitudDto> getDatosIniciales() {
        SolicitudDto datosIniciales = solicitudService.getDatosIniciales();
        return ResponseEntity.ok(datosIniciales);
    }

    @PostMapping
    public ResponseEntity<Void> saveSolicitud(@RequestBody SolicitudDto solicitudDto) {
        solicitudService.saveSolicitud(solicitudDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDto>> listarSolicitudes() {
        List<SolicitudDto> solicitudes = solicitudService.listarSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }
}
