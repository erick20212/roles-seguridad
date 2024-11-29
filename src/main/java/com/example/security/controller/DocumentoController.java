package com.example.security.controller;

import com.example.security.entity.Detalle_Documento;
import com.example.security.repository.DetalleDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DetalleDocumentoRepository detalleDocumentoRepository;

    // Directorio donde se guardarán los archivos
    private static final String UPLOAD_DIR = "/ruta/a/tu/directorio"; // Ajusta la ruta según tu entorno

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Validar si el archivo está vacío
        if (file.isEmpty()) {
            return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
        }

        try {
            // Obtener el nombre del archivo y crear la ruta completa
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, fileName);

            // Crear el directorio si no existe
            Files.createDirectories(path.getParent());

            // Guardar el archivo en el sistema de archivos
            file.transferTo(path);

            // Crear el objeto Detalle_Documento y establecer sus propiedades
            Detalle_Documento documento = new Detalle_Documento();
            documento.setNombre_documento(fileName);
            documento.setFecha_documento(new Date()); // Establecer la fecha del archivo
            documento.setEstado("Activo"); // Establecer estado como "Activo"
            documento.setRuta_documento(path.toString());  // Guardar la ruta completa

            // Guardar el documento en la base de datos
            detalleDocumentoRepository.save(documento);

            return new ResponseEntity<>("Archivo subido y registrado correctamente", HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Error al guardar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
