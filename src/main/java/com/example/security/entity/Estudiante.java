package com.example.security.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "estudiante")
public class Estudiante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_estudiante")
	private Long id;

	@Column(name="codigo",unique = true)
    private String codigo;
	
	@Column(name="estado")
    private String estado;
	
	@ManyToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
    private Persona persona;

	@OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Matricula> matriculas;
	
}
