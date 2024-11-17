package com.example.security.service;

import java.util.List;

import com.example.security.dto.SolicitudDto;

public interface SolicitudService {
	SolicitudDto getDatosIniciales();
	void saveSolicitud(SolicitudDto solicitudDTO);
	List<SolicitudDto> listarSolicitudes();
}
    
