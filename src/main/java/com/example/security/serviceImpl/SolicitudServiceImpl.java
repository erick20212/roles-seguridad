package com.example.security.serviceImpl;

import com.example.security.dto.SolicitudDto;
import com.example.security.entity.*;
import com.example.security.repository.*;
import com.example.security.service.SolicitudService;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private LineaRepository lineaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public SolicitudDto obtenerDatosEstudianteConEmpresasYLineas() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Usuario autenticado: {}", username);

        // Buscar usuario por su nombre de usuario (username)
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        // Obtener la persona asociada al usuario
        Persona persona = usuario.getPersona();
        if (persona == null) {
            throw new RuntimeException("Persona no asociada al usuario: " + username);
        }

        // Buscar el estudiante asociado a la persona
        Estudiante estudiante = estudianteRepository.findByPersonaId(persona.getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado para persona: " + persona.getId()));

        // Mapear datos del estudiante a DTO
        SolicitudDto.EstudianteDTO estudianteDto = new SolicitudDto.EstudianteDTO();
        estudianteDto.setNombre(persona.getNombre());
        estudianteDto.setApellido(persona.getApellido());
        estudianteDto.setCodigo(estudiante.getCodigo());
        estudianteDto.setDni(persona.getDni());
        estudianteDto.setTelefono(persona.getTelefono());
        estudianteDto.setCorreo(persona.getEmail());

        // Crear DTO de Solicitud
        SolicitudDto solicitudDto = new SolicitudDto();
        solicitudDto.setEstudiante(estudianteDto);
        solicitudDto.setEstado("activo");

        // Asignar lista de empresas al DTO
        List<SolicitudDto.EmpresaDTO> empresas = empresaRepository.findAll().stream()
                .map(this::convertirEmpresaADto)
                .collect(Collectors.toList());
        solicitudDto.setEmpresas(empresas);

        // Asignar lista de líneas de carrera al DTO
        List<SolicitudDto.LineaCarreraDTO> lineasCarrera = lineaRepository.findAll().stream()
                .map(this::convertirLineaADto)
                .collect(Collectors.toList());
        solicitudDto.setLineasCarrera(lineasCarrera);

        return solicitudDto;
    }

    @Override
    public void guardarSolicitud(SolicitudDto solicitudDTO) {
    	logger.info("Guardando nueva solicitud...");

        // Crear entidad de Solicitud
        Solicitud solicitud = new Solicitud();

        // Validar y asignar estudiante
        Estudiante estudiante = estudianteRepository.findByCodigo(solicitudDTO.getEstudiante().getCodigo())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + solicitudDTO.getEstudiante().getCodigo()));
        solicitud.setEstudiante(estudiante);

        // Validar y asignar empresa
        if (solicitudDTO.getEmpresa() == null || solicitudDTO.getEmpresa().getId() == null) {
            throw new RuntimeException("Debe seleccionar una empresa válida.");
        }
        Empresa empresa = empresaRepository.findById(solicitudDTO.getEmpresa().getId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada: " + solicitudDTO.getEmpresa().getId()));
        solicitud.setEmpresa(empresa);

        // Validar y asignar línea de carrera
        if (solicitudDTO.getLineaCarrera() == null || solicitudDTO.getLineaCarrera().getId() == null) {
            throw new RuntimeException("Debe seleccionar una línea de carrera válida.");
        }
        Linea linea = lineaRepository.findById(solicitudDTO.getLineaCarrera().getId())
                .orElseThrow(() -> new RuntimeException("Línea de carrera no encontrada: " + solicitudDTO.getLineaCarrera().getId()));
        solicitud.setLineaCarrera(linea);

        // Establecer estado inicial y guardar la solicitud
        solicitud.setEstado("pendiente");
        solicitudRepository.save(solicitud);
        logger.info("Solicitud guardada con ID: {}", solicitud.getId());
    }

    @Override
    public List<SolicitudDto> listarSolicitudes() {
        // Obtener todas las solicitudes de la base de datos y convertirlas a DTO
        return solicitudRepository.findAll().stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SolicitudDto> listarSolicitudesPorEstudiante(Long estudianteId) {
        // Filtrar solicitudes por estudiante
        return solicitudRepository.findByEstudianteId(estudianteId).stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    private SolicitudDto convertirEntidadADto(Solicitud solicitud) {
        // Crear DTO de Solicitud
        SolicitudDto dto = new SolicitudDto();

        // Mapear datos del estudiante
        Persona persona = solicitud.getEstudiante().getPersona();
        SolicitudDto.EstudianteDTO estudianteDto = new SolicitudDto.EstudianteDTO();
        estudianteDto.setNombre(persona.getNombre());
        estudianteDto.setApellido(persona.getApellido());
        estudianteDto.setCodigo(solicitud.getEstudiante().getCodigo());
        estudianteDto.setDni(persona.getDni());
        estudianteDto.setTelefono(persona.getTelefono());
        estudianteDto.setCorreo(persona.getEmail());
        dto.setEstudiante(estudianteDto);

        // Mapear datos completos de la empresa
        if (solicitud.getEmpresa() != null) {
            dto.setEmpresa(convertirEmpresaADto(solicitud.getEmpresa()));
        }

        // Mapear datos completos de la línea de carrera
        if (solicitud.getLineaCarrera() != null) {
            dto.setLineaCarrera(convertirLineaADto(solicitud.getLineaCarrera()));
        }

        // Mapear otros datos de la solicitud
        dto.setEstado(solicitud.getEstado());
        dto.setId(solicitud.getId());
        dto.setFechaCreacion(solicitud.getFechaCreacion());

        return dto;
    }

    private SolicitudDto.EmpresaDTO convertirEmpresaADto(Empresa empresa) {
        SolicitudDto.EmpresaDTO empresaDto = new SolicitudDto.EmpresaDTO();
        empresaDto.setId(empresa.getId());
        empresaDto.setRazonSocial(empresa.getRazonSocial());
        empresaDto.setDireccion(empresa.getDireccion());
        empresaDto.setEmail(empresa.getEmail());
        empresaDto.setTelefono(empresa.getTelefono());
        return empresaDto;
    }

    private SolicitudDto.LineaCarreraDTO convertirLineaADto(Linea linea) {
        SolicitudDto.LineaCarreraDTO lineaDto = new SolicitudDto.LineaCarreraDTO();
        lineaDto.setId(linea.getId());
        lineaDto.setNombre(linea.getNombre());
        return lineaDto;
    }

    @Override
    public List<SolicitudDto> listarSolicitudesDelEstudianteAutenticado() {
        // Obtener el nombre de usuario del estudiante autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Obteniendo solicitudes para el usuario autenticado: {}", username);

        // Buscar el usuario por su nombre de usuario
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        // Obtener la persona asociada al usuario
        Persona persona = usuario.getPersona();
        if (persona == null) {
            throw new RuntimeException("No se encontró una persona asociada al usuario: " + username);
        }

        // Obtener el estudiante asociado a la persona
        Estudiante estudiante = estudianteRepository.findByPersonaId(persona.getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado para la persona: " + persona.getId()));

        // Filtrar las solicitudes por el estudiante autenticado
        return solicitudRepository.findByEstudianteId(estudiante.getId()).stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void cambiarEstadoSolicitud(Long solicitudId, String nuevoEstado) {
        logger.info("Intentando cambiar estado de solicitud con ID {} a {}", solicitudId, nuevoEstado);

        if (!nuevoEstado.equalsIgnoreCase("aceptado") && !nuevoEstado.equalsIgnoreCase("rechazado")) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }

        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + solicitudId));

        solicitud.setEstado(nuevoEstado);
        solicitudRepository.save(solicitud);

        logger.info("Estado actualizado correctamente para la solicitud con ID {}", solicitudId);
    }
}