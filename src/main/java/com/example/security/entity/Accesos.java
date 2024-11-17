package com.example.security.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "accesos")
public class Accesos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_acceso")
    private Long id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "estado_accesos")
    private String estado;

    @OneToMany(mappedBy = "accesos", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rol_Acceso> rolAcceso;
}
