package com.example.security.entity;



import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "rol_acceso")
public class Rol_Acceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol_acceso")
    private Long id;

    @Column(name = "estado_rol_acceso")
    private String estadoRolAcceso;

    @ManyToOne
    @JoinColumn(name = "id_acceso", nullable = false)
    private Accesos accesos;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}