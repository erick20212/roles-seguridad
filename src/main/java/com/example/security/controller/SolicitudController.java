package com.example.security.controller;

import com.example.security.dto.SolicitudDto;
import com.example.security.entity.Solicitud;
import com.example.security.repository.SolicitudRepository;
import com.example.security.service.SolicitudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudController {

	private static final Logger logger = LoggerFactory.getLogger(SolicitudController.class);

	@Autowired
	private SolicitudService solicitudService;

	/**
	 * Obtener datos iniciales (estudiante, empresas, líneas de carrera) y lista de
	 * solicitudes.
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> obtenerDatosInicialesYSolicitudes() {
		logger.info("Solicitando datos iniciales y lista de solicitudes...");
		try {
			// Obtener datos iniciales
			SolicitudDto datosIniciales = solicitudService.obtenerDatosEstudianteConEmpresasYLineas();
			// Obtener lista de solicitudes
			List<SolicitudDto> solicitudes = solicitudService.listarSolicitudes();

			// Crear respuesta combinada
			Map<String, Object> respuesta = new HashMap<>();
			respuesta.put("datosIniciales", datosIniciales);
			respuesta.put("solicitudes", solicitudes);

			logger.info("Datos iniciales y solicitudes obtenidos exitosamente.");
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			logger.error("Error al obtener datos iniciales y solicitudes: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/mis-solicitudes")
	public ResponseEntity<List<SolicitudDto>> listarSolicitudesDelEstudianteAutenticado() {
		logger.info("Listando solicitudes del estudiante autenticado...");
		try {
			List<SolicitudDto> solicitudes = solicitudService.listarSolicitudesDelEstudianteAutenticado();
			return ResponseEntity.ok(solicitudes);
		} catch (Exception e) {
			logger.error("Error al listar solicitudes del estudiante autenticado: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Guardar una nueva solicitud.
	 */
	@PostMapping
	public ResponseEntity<Map<String, String>> guardarSolicitud(@Valid @RequestBody SolicitudDto solicitudDto) {
		logger.info("Guardando nueva solicitud...");
		try {
			// Validar que empresa y lineaCarrera no sean nulos
			if (solicitudDto.getEmpresa() == null || solicitudDto.getEmpresa().getId() == null) {
				throw new RuntimeException("Debe seleccionar una empresa válida.");
			}
			if (solicitudDto.getLineaCarrera() == null || solicitudDto.getLineaCarrera().getId() == null) {
				throw new RuntimeException("Debe seleccionar una línea de carrera válida.");
			}

			solicitudService.guardarSolicitud(solicitudDto);
			logger.info("Solicitud guardada exitosamente.");

			Map<String, String> response = new HashMap<>();
			response.put("message", "Solicitud guardada exitosamente.");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (RuntimeException e) {
			logger.error("Error al guardar solicitud: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		} catch (Exception e) {
			logger.error("Error inesperado al guardar solicitud: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error interno del servidor."));
		}
	}

	@GetMapping("/estudiante/{estudianteId}")
	public ResponseEntity<List<SolicitudDto>> listarSolicitudesPorEstudiante(@PathVariable Long estudianteId) {
		try {
			List<SolicitudDto> solicitudes = solicitudService.listarSolicitudesPorEstudiante(estudianteId);
			if (solicitudes.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(solicitudes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Listar todas las solicitudes (para coordinador).
	 */
	@GetMapping("/list")
	public ResponseEntity<List<SolicitudDto>> listarSolicitudes() {
		logger.info("Listando todas las solicitudes...");
		try {
			List<SolicitudDto> solicitudes = solicitudService.listarSolicitudes();
			return ResponseEntity.ok(solicitudes);
		} catch (Exception e) {
			logger.error("Error al listar solicitudes: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/{solicitudId}/estado")
	public ResponseEntity<Map<String, String>> cambiarEstadoSolicitud(@PathVariable Long solicitudId,
			@RequestParam String nuevoEstado) {
		logger.info("Actualizando estado de la solicitud con ID {} a {}", solicitudId, nuevoEstado);
		try {
			solicitudService.cambiarEstadoSolicitud(solicitudId, nuevoEstado);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Estado de la solicitud actualizado a: " + nuevoEstado);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			logger.error("Error de validación: {}", e.getMessage());
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		} catch (RuntimeException e) {
			logger.error("Error al actualizar estado de la solicitud: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		} catch (Exception e) {
			logger.error("Error inesperado: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Ocurrió un error interno al procesar la solicitud."));
		}
	}
}