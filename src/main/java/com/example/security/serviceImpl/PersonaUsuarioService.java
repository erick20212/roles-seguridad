package com.example.security.serviceImpl;

import com.example.security.dto.PersonaUsuarioDTO;
import com.example.security.entity.Persona;
import com.example.security.entity.Rol;
import com.example.security.entity.Usuario;
import com.example.security.entity.Usuario_Rol;
import com.example.security.repository.PersonaRepository;
import com.example.security.repository.UsuarioRepository;
import com.example.security.repository.RolRepository; // Asegúrate de tener este repositorio
import com.example.security.repository.Usuario_Rol_Repository; // Asegúrate de tener este repositorio
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonaUsuarioService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;  // Repositorio para obtener el rol

    @Autowired
    private Usuario_Rol_Repository usuarioRolRepository;  // Repositorio para guardar Usuario_Rol

    @Autowired
    private PasswordEncoder encode;  // Inyección del BCryptPasswordEncoder

    /**
     * Método para crear una persona y un usuario asociado.
     *
     * @param personaUsuarioDTO DTO con los datos de la persona y el usuario.
     */
    @Transactional
    public void crearPersonaYUsuario(PersonaUsuarioDTO personaUsuarioDTO) {
        // Validar si el email ya existe en la tabla persona
        if (personaRepository.existsByEmail(personaUsuarioDTO.getEmailPersona())) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }

        // Validar si el username ya existe en la tabla usuario
        if (usuarioRepository.existsByUsername(personaUsuarioDTO.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está registrado.");
        }

        // Crear la Persona
        Persona persona = new Persona();
        persona.setNombre(personaUsuarioDTO.getNombre());
        persona.setApellido(personaUsuarioDTO.getApellido());
        persona.setEmail(personaUsuarioDTO.getEmailPersona());
        persona.setDni(personaUsuarioDTO.getDni());
        persona.setEstado("activo"); // Estado por defecto

        // Guardar Persona
        Persona personaGuardada = personaRepository.save(persona);

        // Crear el Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(personaUsuarioDTO.getUsername());  // Usar el username del DTO
        usuario.setEmail(personaGuardada.getEmail());  // Email vinculado a la persona
        String dniEncriptado = encode.encode(personaGuardada.getDni());  // Encriptar el DNI
        usuario.setPassword(dniEncriptado);  // Password es el DNI encriptado
        usuario.setLogin(personaUsuarioDTO.getLogin());  // Login del DTO
        usuario.setImg("text.png");  // Imagen fija
        usuario.setEstado("activo");  // Estado por defecto
        usuario.setPersona(personaGuardada);  // Asociar Persona con Usuario

        // Guardar Usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Obtener el rol de supervisor (puedes cambiar el ID según tu base de datos)
        Rol rolSupervisor = rolRepository.findById(28L).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Crear la relación Usuario_Rol
        Usuario_Rol usuarioRol = new Usuario_Rol();
        usuarioRol.setUsuario(usuarioGuardado);  // Asociar el usuario guardado
        usuarioRol.setRol(rolSupervisor);  // Asociar el rol de supervisor
        usuarioRol.setEstadoUsuarioRol("activo");  // Estado activo por defecto

        // Guardar la relación en la tabla usuario_rol
        usuarioRolRepository.save(usuarioRol);
    }
}