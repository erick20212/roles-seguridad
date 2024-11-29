package com.example.security.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.security.dto.SolicitudDto;

public interface SolicitudService {
    SolicitudDto obtenerDatosEstudianteConEmpresasYLineas();
    void guardarSolicitud(SolicitudDto solicitudDTO);
    List<SolicitudDto> listarSolicitudes();
    List<SolicitudDto> listarSolicitudesPorEstudiante(Long estudianteId);
    List<SolicitudDto> listarSolicitudesDelEstudianteAutenticado(); // Nueva funcionalidad
    void cambiarEstadoSolicitud(Long solicitudId, String nuevoEstado);
    
    List<SolicitudDto> listarSolicitudesAprobadas();
    
    

 //
}
