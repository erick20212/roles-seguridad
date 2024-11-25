package com.example.security.service;

import java.util.List;

import com.example.security.dto.SolicitudDto;

public interface SolicitudService {
	 SolicitudDto obtenerDatosEstudianteConEmpresasYLineas();
	    void guardarSolicitud(SolicitudDto solicitudDTO);
	    List<SolicitudDto> listarSolicitudes();
	    List<SolicitudDto> listarSolicitudesPorEstudiante(Long estudianteId); // Nueva funcionalidad
}
    
