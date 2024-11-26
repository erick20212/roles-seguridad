package com.example.security.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Iterator;
import com.example.security.dto.ExcelDTO;
import com.example.security.entity.*;
import com.example.security.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class ExcelService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Usuario_Rol_Repository usuarioRolRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private Plan_Carrera_Repository planCarreraRepository;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder encode;

    @Transactional
    public void procesarExcel(ExcelDTO excelDTO) {
        try {
            int ciclo = Integer.parseInt(excelDTO.getCiclo());

            // Si el ciclo es menor a 6, no procesamos la fila
            if (ciclo < 6) {
                System.out.println("Ciclo menor a 6, omitiendo: " + excelDTO.getCiclo());
                return;
            }

            // Verificamos si el DNI o el código de estudiante ya existe
            if (personaRepository.existsByDni(excelDTO.getDni())) {
                System.err.println("El DNI " + excelDTO.getDni() + " ya existe. Omitiendo fila.");
                return;
            }

            if (estudianteRepository.existsByCodigo(excelDTO.getCodigo())) {
                System.err.println("El código " + excelDTO.getCodigo() + " ya existe. Omitiendo fila.");
                return;
            }

            // Asignamos email (institucional o personal)
            String email = excelDTO.getEmailinstitucional();
            if (email == null || email.isEmpty()) {
                email = excelDTO.getEmail();
            }

            // Verificar si el email ya existe
            if (usuarioRepository.existsByEmail(email)) {
                System.err.println("El email " + email + " ya existe. Omitiendo fila.");
                return; // Omite la fila si el email ya existe
            }

            // Crear una nueva persona
            Persona persona = new Persona();
            persona.setDni(excelDTO.getDni());
            persona.setTelefono(excelDTO.getCelular());
            persona.setEmail(email);
            persona.setEstado("activo");
            separarNombreApellido(excelDTO.getEstudiante(), persona);
            persona = personaRepository.save(persona);

            // Crear un nuevo usuario
            Usuario usuario = new Usuario();

            // Ahora obtenemos el 'Usuario' de la columna L (índice 11)
            if (excelDTO.getUsuario() != null && !excelDTO.getUsuario().isEmpty()) {
                usuario.setUsername(excelDTO.getUsuario()); // Asignamos el 'Usuario' de la columna L
            } else {
                System.err.println("Usuario vacío para DNI " + excelDTO.getDni() + ". Omitiendo fila.");
                return;  // Omitimos la fila si el 'Usuario' está vacío
            }

            usuario.setLogin(persona.getNombre() + " " + persona.getApellido());
            usuario.setPassword(encryptPassword(excelDTO.getDni()));
            usuario.setEmail(email);
            usuario.setImg("text.png");
            usuario.setEstado("activo");
            usuario.setPersona(persona);
            usuario = usuarioRepository.save(usuario);

            // Asignar un rol al usuario
            Usuario_Rol usuarioRol = new Usuario_Rol();
            usuarioRol.setUsuario(usuario);
            Rol rol = rolRepository.findById(3L)
                    .orElseThrow(() -> new RuntimeException("Rol con ID 3 no encontrado"));
            usuarioRol.setRol(rol);
            usuarioRol.setEstadoUsuarioRol("activo");
            usuarioRolRepository.save(usuarioRol);

            // Crear un nuevo estudiante
            Estudiante estudiante = new Estudiante();
            estudiante.setPersona(persona);
            estudiante.setCodigo(excelDTO.getCodigo());
            estudiante.setEstado("activo");
            estudiante = estudianteRepository.save(estudiante);

            // Crear una nueva matrícula
            Matricula matricula = new Matricula();
            matricula.setEstudiante(estudiante);
            matricula.setFecha_matricula(Date.valueOf(LocalDate.now()));
            matricula.setEstado("Activo");
            int planCarreraId = getPlanCarreraId(ciclo);
            Plan_Carrera planCarrera = planCarreraRepository.findById((long) planCarreraId)
                    .orElseThrow(() -> new RuntimeException("Plan de carrera no encontrado con ID: " + planCarreraId));

            matricula.setPlan_carrera(planCarrera);
            matriculaRepository.save(matricula);

        } catch (Exception e) {
            System.err.println("Error al procesar ExcelDTO: " + excelDTO.getDni() + " - " + e.getMessage());
        }
    }

    // Método para separar nombre y apellido
    private void separarNombreApellido(String nombreCompleto, Persona persona) {
        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            System.err.println("Nombre completo vacío para persona con DNI: " + persona.getDni());
            return;
        }
        String[] nombrePartes = nombreCompleto.split(" ", 2);
        String nombre = nombrePartes[0];
        String apellido = (nombrePartes.length > 1) ? nombrePartes[1] : "";
        persona.setNombre(nombre);
        persona.setApellido(apellido);
    }

    // Obtener el ID del plan de carrera basado en el ciclo
    private int getPlanCarreraId(int ciclo) {
        if (ciclo == 6) {
            return 1;
        }
        switch (ciclo) {
            case 8: return 21;
            case 10: return 22;
            default: return 1;
        }
    }

    // Método para encriptar la contraseña (solo un ejemplo)
    private String encryptPassword(String dni) {
        return encode.encode(dni);
    }
    // Procesar el archivo Excel
    @Transactional
    public void procesarExcel(MultipartFile file) throws IOException {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            // Saltar la primera fila (encabezado)
            iterator.next();

            int rowIndex = 1;

            while (iterator.hasNext()) {
                Row row = iterator.next();
                ExcelDTO excelDTO = new ExcelDTO();

                // Procesar las columnas del archivo Excel
                try {
                    if (row.getCell(5) != null && !row.getCell(5).toString().isEmpty()) {
                        excelDTO.setCiclo(row.getCell(5).toString());
                    }

                    if (row.getCell(7) != null && !row.getCell(7).toString().isEmpty()) {
                        excelDTO.setCodigo(row.getCell(7).toString());
                    }

                    if (row.getCell(8) != null && !row.getCell(8).toString().isEmpty()) {
                        excelDTO.setEstudiante(row.getCell(8).toString());
                    }

                    if (row.getCell(9) != null && !row.getCell(9).toString().isEmpty()) {
                        excelDTO.setDni(row.getCell(9).toString());
                    }

                    if (row.getCell(10) != null && !row.getCell(10).toString().isEmpty()) {
                        excelDTO.setEmail(row.getCell(10).toString());
                    }

                    if (row.getCell(12) != null && !row.getCell(12).toString().isEmpty()) {
                        excelDTO.setEmailinstitucional(row.getCell(12).toString());
                    }

                    if (row.getCell(13) != null && !row.getCell(13).toString().isEmpty()) {
                        excelDTO.setCelular(row.getCell(13).toString());
                    }

                    // Agregar el valor del 'Usuario' desde la columna L
                    if (row.getCell(11) != null && !row.getCell(11).toString().isEmpty()) {
                        excelDTO.setUsuario(row.getCell(11).toString()); // Asignar el 'Usuario'
                    }

                    // Verificar si la fila tiene datos válidos
                    if (excelDTO.getCiclo() == null || excelDTO.getCiclo().isEmpty()) {
                        System.err.println("Fila " + rowIndex + " - Ciclo vacío. Omitiendo.");
                        continue;
                    }

                    // Convertir ciclo a entero y verificar si es mayor o igual a 6
                    int ciclo = Integer.parseInt(excelDTO.getCiclo());
                    if (ciclo < 6) {
                        System.err.println("Fila " + rowIndex + " - Ciclo menor a 6. Omitiendo.");
                        continue; // Omitir si el ciclo es menor a 6
                    }

                    // Procesar solo las filas con la información básica
                    // Procesar solo las filas con la información básica
                    if (excelDTO.getCiclo() != null && !excelDTO.getCiclo().isEmpty() &&
                            excelDTO.getCodigo() != null && !excelDTO.getCodigo().isEmpty() &&
                            excelDTO.getEstudiante() != null && !excelDTO.getEstudiante().isEmpty() &&
                            excelDTO.getDni() != null && !excelDTO.getDni().isEmpty()) {
                        // Si todos los campos esenciales tienen datos, procesamos la fila
                        procesarExcel(excelDTO); // Procesar la fila
                    } else {
                        // Si hay campos vacíos pero el DNI y código son válidos, permitir el procesamiento
                        if (excelDTO.getDni() != null && !excelDTO.getDni().isEmpty() &&
                                excelDTO.getCodigo() != null && !excelDTO.getCodigo().isEmpty()) {
                            System.err.println("Fila " + rowIndex + " tiene datos incompletos. Aún se procesará.");
                            procesarExcel(excelDTO); // Procesar la fila aunque haya campos vacíos
                        } else {
                            System.err.println("Fila " + rowIndex + " con datos incompletos. Omitiendo.");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar la fila " + rowIndex + ": " + e.getMessage());
                }

                rowIndex++;
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo Excel: " + e.getMessage());
            throw new IOException("Error al leer el archivo Excel", e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}
