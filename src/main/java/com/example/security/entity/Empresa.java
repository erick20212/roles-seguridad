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
@Table(name = "empresa")
public class Empresa {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long id;

	@Column(name = "razon_social", length = 100, nullable = false)
	private String razonSocial;

	@Column(name = "direccion", length = 100)
	private String direccion;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "estado", length = 20)
	private String estado;

	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PPP> ppps;
	
}
