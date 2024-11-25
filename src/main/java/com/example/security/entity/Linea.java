package com.example.security.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "linea")
public class Linea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_linea")
	private Long id;

	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;
	
	@Column(name = "estado", length = 100, nullable = false)
	private String estado;


	@OneToMany(mappedBy = "linea", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PPP> ppps;
}
