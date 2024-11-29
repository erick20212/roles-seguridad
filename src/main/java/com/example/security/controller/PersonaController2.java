package com.example.security.controller;

import com.example.security.dto.EstudianteDTO;
import com.example.security.dto.SupervisorDTO;
import com.example.security.entity.Estudiante;
import com.example.security.entity.Persona;
import com.example.security.entity.Usuario;
import com.example.security.entity.Usuario_Rol;
import com.example.security.repository.EstudianteRepository;
import com.example.security.repository.PersonaRepository;
import com.example.security.repository.UsuarioRepository;
import com.example.security.repository.Usuario_Rol_Repository;
import com.example.security.service.Personaservice2;
import com.example.security.service.UsuarioService;
import com.example.security.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estudiantess")
public class PersonaController2 {

    @Autowired
    private Personaservice2 personaService; // Servicio para gestionar Persona
    @Autowired
    private PasswordEncoder passwordEncoder; // Para encriptar el DNI
    @Autowired
    private UsuarioService usuarioService; // Servicio para gestionar Usuario
    @Autowired
    private EstudianteService estudianteService; // Servicio para gestionar Estudiante
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private Usuario_Rol_Repository usuarioRolRepository;  // Repositorio de Usuario_Rol
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encode;

    @PostMapping("/crear")
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody EstudianteDTO estudianteDto) {
        try {
            // Paso 1: Crear la entidad Persona
            Persona persona = new Persona();
            persona.setNombre(estudianteDto.getNombre());
            persona.setApellido(estudianteDto.getApellido());
            persona.setTelefono((estudianteDto.getTelefono()));
            persona.setEmail(estudianteDto.getEmail());
            persona.setDni(estudianteDto.getDni());
            persona.setEstado("Activo");

            // Guardar Persona
            persona = personaService.save(persona); // Llamada correcta al método save() del servicio

            // Paso 2: Crear el Usuario
            Usuario usuario = new Usuario();
            usuario.setUsername(persona.getNombre()); // Username = nombre de la persona
            usuario.setLogin(persona.getNombre() + persona.getApellido()); // Login = nombre + apellido
            usuario.setEmail(persona.getEmail()); // Email copiado de la persona
            usuario.setPassword(passwordEncoder.encode(persona.getDni())); // Clave = dni encriptado
            usuario.setEstado("activo"); // Estado activo
            usuario.setImg("txt.png"); // Imagen por defecto
            usuario.setPersona(persona); // Relacionar Usuario con Persona

            // Guardar Usuario
            usuarioService.create(usuario); // Guarda el usuario

            // Paso 3: Crear el Estudiante
            Estudiante estudiante = new Estudiante();
            estudiante.setCodigo(estudianteDto.getCodigo()); // Código pasado en el DTO
            estudiante.setEstado("activo"); // Estado activo
            estudiante.setPersona(persona); // Relacionar Estudiante con Persona

            // Guardar Estudiante
            estudiante = estudianteService.create(estudiante); // Guarda el estudiante

            // Respuesta exitosa
            return new ResponseEntity<>(estudiante, HttpStatus.CREATED);

        } catch (Exception e) {
            // Manejo de errores
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/listar")
    public List<EstudianteDTO> listarestudiantes() {
        // Obtener los roles de los supervisores (rolId = 3L)
        List<Usuario_Rol> usuarioRoles = usuarioRolRepository.findByRolId(3L);

        // Convertir los usuarioRoles a Estudiantes y mapearlos a EstudianteDTO
        List<EstudianteDTO> estudiantesDTO = usuarioRoles.stream()
                .map(usuarioRol -> {
                    // Obtener el usuario asociado al rol
                    Usuario usuario = usuarioRol.getUsuario();

                    // Obtener la persona asociada al usuario
                    Persona persona = usuario.getPersona();

                    // Buscar el Estudiante relacionado con esta Persona
                    Estudiante estudiante = estudianteRepository.findByPersona(persona);

                    // Si el Estudiante es encontrado, mapeamos a EstudianteDTO
                    if (estudiante != null) {
                        EstudianteDTO estudianteDTO = new EstudianteDTO();
                        estudianteDTO.setId(estudiante.getId());
                        estudianteDTO.setCodigo(estudiante.getCodigo());

                        // Mapeamos los datos de Persona a EstudianteDTO
                        if (persona != null) {
                            estudianteDTO.setNombre(persona.getNombre());
                            estudianteDTO.setApellido(persona.getApellido());
                            estudianteDTO.setEmail(persona.getEmail());
                            estudianteDTO.setTelefono(persona.getTelefono());
                            estudianteDTO.setDni(persona.getDni());
                        }

                        return estudianteDTO;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)  // Filtramos cualquier valor nulo (si no se encuentra Estudiante)
                .collect(Collectors.toList());

        // Retornar la lista de EstudiantesDTO
        return estudiantesDTO;
    }





    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarSupervisor(
            @PathVariable Long id,
            @RequestBody EstudianteDTO estudianteDTO
    ) {
        estudianteDTO.setId(id); // Asegura que el ID esté en el DTO
        try {
                // Verificar que el ID no sea nulo
                if (id == null) {
                    throw new RuntimeException("El ID del estudiante no puede ser nulo.");
                }

                // Buscar la relación Usuario_Rol asociada al estudiante (id_rol = 3)
                Usuario_Rol usuarioRol = usuarioRolRepository.findByUsuario_Persona_IdAndRol_Id(id, 3L)
                        .orElseThrow(() -> new RuntimeException("El usuario no es un estudiante o no existe"));

                // Obtener la Persona y el Usuario asociados
                Persona persona = usuarioRol.getUsuario().getPersona();
                Usuario usuario = usuarioRol.getUsuario();

                // Actualizar los datos de Persona si han cambiado
                if (!persona.getNombre().equals(estudianteDTO.getNombre())) {
                    persona.setNombre(estudianteDTO.getNombre());
                }
                if (!persona.getApellido().equals(estudianteDTO.getApellido())) {
                    persona.setApellido(estudianteDTO.getApellido());
                }
                if (!persona.getEmail().equals(estudianteDTO.getEmail())) {
                    if (personaRepository.existsByEmail(estudianteDTO.getEmail())) {
                        throw new RuntimeException("El email ya está registrado.");
                    }
                    persona.setEmail(estudianteDTO.getEmail());
                }
                if (!persona.getDni().equals(estudianteDTO.getDni())) {
                    if (personaRepository.existsByDni(estudianteDTO.getDni())) {
                        throw new RuntimeException("El DNI ya está registrado.");
                    }
                    persona.setDni(estudianteDTO.getDni());
                }
                personaRepository.save(persona); // Guardar cambios en Persona

                // Actualizar los datos de Usuario si han cambiado
                if (!usuario.getUsername().equals(persona.getNombre())) {
                    if (usuarioRepository.existsByUsername(persona.getNombre())) {
                        throw new RuntimeException("El username ya está registrado.");
                    }
                    usuario.setUsername(persona.getNombre());
                }
                if (!encode.matches(estudianteDTO.getDni(), usuario.getPassword())) {
                    usuario.setPassword(encode.encode(estudianteDTO.getDni())); // Encriptar contraseña
                }
                usuarioRepository.save(usuario); // Guardar cambios en Usuario

            return ResponseEntity.ok("Supervisor actualizado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error al actualizar supervisor: " + e.getMessage());
        }
    }
}
