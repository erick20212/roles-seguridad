package com.example.security.serviceImpl;

import com.example.security.dto.SolicitudDto;
import com.example.security.entity.*;
import com.example.security.repository.*;
import com.example.security.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private LineaRepository lineaRepository;

    @Override
    public SolicitudDto getDatosIniciales() {
        SolicitudDto datosIniciales = new SolicitudDto();

        datosIniciales.setEmpresas(
                empresaRepository.findAll().stream().map(empresa -> {
                    SolicitudDto.EmpresaDTO dto = new SolicitudDto.EmpresaDTO();
                    dto.setId(empresa.getId());
                    dto.setRazonSocial(empresa.getRazonSocial());
                    return dto;
                }).collect(Collectors.toList())
        );

        datosIniciales.setLineasCarrera(
                lineaRepository.findAll().stream().map(linea -> {
                    SolicitudDto.LineaCarreraDTO dto = new SolicitudDto.LineaCarreraDTO();
                    dto.setId(linea.getId());
                    dto.setNombre(linea.getNombre());
                    return dto;
                }).collect(Collectors.toList())
        );

        return datosIniciales;
    }

    @Override
    public void saveSolicitud(SolicitudDto solicitudDTO) {
        Solicitud solicitud = new Solicitud();

        Estudiante estudiante = estudianteRepository.findByCodigo(solicitudDTO.getEstudiante().getCodigo())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con el código: " + solicitudDTO.getEstudiante().getCodigo()));
        solicitud.setEstudiante(estudiante);

        if (solicitudDTO.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(solicitudDTO.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            solicitud.setEmpresa(empresa);
        } else {
            solicitud.setNombreEmpresa(solicitudDTO.getNombreEmpresa());
            solicitud.setRucEmpresa(solicitudDTO.getRucEmpresa());
            solicitud.setDireccionEmpresa(solicitudDTO.getDireccionEmpresa());
            solicitud.setTelefonoEmpresa(solicitudDTO.getTelefonoEmpresa());
            solicitud.setCorreoEmpresa(solicitudDTO.getCorreoEmpresa());
        }

        if (solicitudDTO.getIdLineaCarrera() != null) {
            Linea linea = lineaRepository.findById(solicitudDTO.getIdLineaCarrera())
                    .orElseThrow(() -> new RuntimeException("Línea de carrera no encontrada"));
            solicitud.setLineaCarrera(linea);
        }

        solicitud.setFechaCreacion(LocalDateTime.now());
        solicitud.setEstado("pendiente");

        solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudDto> listarSolicitudes() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        return solicitudes.stream().map(solicitud -> {
            SolicitudDto dto = new SolicitudDto();

            // Configurar los datos del estudiante
            SolicitudDto.EstudianteDto estudianteDto = new SolicitudDto.EstudianteDto();
            estudianteDto.setNombre(solicitud.getEstudiante().getPersona().getNombre());
            estudianteDto.setCodigo(solicitud.getEstudiante().getCodigo());
            estudianteDto.setDni(solicitud.getEstudiante().getPersona().getDni());
            estudianteDto.setCorreo(solicitud.getEstudiante().getPersona().getEmail());
            // Puedes agregar el teléfono aquí desde otra fuente, si es necesario
            dto.setEstudiante(estudianteDto);

            // Configurar los datos de la empresa
            dto.setNombreEmpresa(
                solicitud.getNombreEmpresa() != null ?
                solicitud.getNombreEmpresa() :
                solicitud.getEmpresa().getRazonSocial()
            );
            dto.setRucEmpresa(solicitud.getRucEmpresa());

            // Configurar el estado de la solicitud
            dto.setEstado(solicitud.getEstado());

            return dto;
        }).collect(Collectors.toList());
    }
}