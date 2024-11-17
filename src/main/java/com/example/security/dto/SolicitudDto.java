package com.example.security.dto;



import java.util.List;

import lombok.Data;

@Data
public class SolicitudDto {
	 private EstudianteDto estudiante; // Cambia el idEstudiante por un objeto
	    private Long idEmpresa;
	    private Long idLineaCarrera;
	    private String nombreEmpresa;
	    private String rucEmpresa;
	    private String direccionEmpresa;
	    private String telefonoEmpresa;
	    private String correoEmpresa;
	    private String estado;

	    private List<EmpresaDTO> empresas;
	    private List<LineaCarreraDTO> lineasCarrera;

	    @Data
	    public static class EstudianteDto {
	        private String nombre;
	        private String codigo; // Clave única para buscar al estudiante
	        private String dni;
	        private String telefono;
	        private String correo;
	    }

	    @Data
	    public static class EmpresaDTO {
	        private Long id;
	        private String razonSocial;
	    }

	    @Data
	    public static class LineaCarreraDTO {
	        private Long id;
	        private String nombre;
	    }
	}