package com.example.security.serviceImpl;

import com.example.security.dto.PersonaUsuarioDTO;
import com.example.security.entity.Persona;
import com.example.security.entity.Rol;
import com.example.security.entity.Usuario;
import com.example.security.entity.Usuario_Rol;
import com.example.security.repository.PersonaRepository;
import com.example.security.repository.UsuarioRepository;
import com.example.security.repository.RolRepository;
import com.example.security.repository.Usuario_Rol_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class PersonaUsuarioService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private Usuario_Rol_Repository usuarioRolRepository;

    @Autowired
    private PasswordEncoder encode;

    /**
     * Método para crear una persona y un usuario asociado.
     *
     * @param personaUsuarioDTO DTO con los datos de la persona y el usuario.
     */
    @Transactional
    public void crearPersonaYUsuario(PersonaUsuarioDTO personaUsuarioDTO) {
        if (personaUsuarioDTO.getNombre() == null || personaUsuarioDTO.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        if (personaUsuarioDTO.getApellido() == null || personaUsuarioDTO.getApellido().isEmpty()) {
            throw new RuntimeException("El apellido es obligatorio");
        }
        if (personaRepository.existsByEmail(personaUsuarioDTO.getEmailPersona())) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }

        // Validar si el nombre de usuario ya existe en la tabla usuario
        if (usuarioRepository.existsByUsername(personaUsuarioDTO.getNombre())) {
            throw new RuntimeException("El nombre de usuario ya está registrado.");
        }

        if (!personaUsuarioDTO.getDni().matches("\\d{8}")) {
            throw new RuntimeException("El DNI debe contener 8 dígitos.");
        }


        // Crear la Persona
        Persona persona = new Persona();
        persona.setNombre(personaUsuarioDTO.getNombre());
        persona.setApellido(personaUsuarioDTO.getApellido());
        persona.setEmail(personaUsuarioDTO.getEmailPersona()); // Usar el email de la persona
        persona.setDni(personaUsuarioDTO.getDni());
        persona.setEstado("activo"); // Estado por defecto

        // Guardar Persona
        Persona personaGuardada = personaRepository.save(persona);

        // Crear el Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(personaUsuarioDTO.getNombre());  // Usar el nombre de la persona
        usuario.setLogin(personaUsuarioDTO.getNombre() + " " + personaUsuarioDTO.getApellido());  // Concatenar nombre y apellido
        String dniEncriptado = encode.encode(personaGuardada.getDni());  // Encriptar el DNI
        usuario.setPassword(dniEncriptado);  // Password es el DNI encriptado
        usuario.setEmail(personaGuardada.getEmail());  // Email de la persona
        usuario.setImg("text.png");  // Imagen predeterminada
        usuario.setEstado("activo");  // Estado por defecto
        usuario.setPersona(personaGuardada);  // Asociar Persona con Usuario

        // Guardar Usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Obtener el rol de supervisor (ID 2, puedes cambiarlo según tu base de datos)
        Rol rolSupervisor = rolRepository.findById(2L).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Crear la relación Usuario_Rol
        Usuario_Rol usuarioRol = new Usuario_Rol();
        usuarioRol.setUsuario(usuarioGuardado);  // Asociar el usuario guardado
        usuarioRol.setRol(rolSupervisor);  // Asociar el rol de supervisor
        usuarioRol.setEstadoUsuarioRol("activo");  // Estado activo por defecto

        // Guardar la relación en la tabla usuario_rol
        usuarioRolRepository.save(usuarioRol);
    }
}
