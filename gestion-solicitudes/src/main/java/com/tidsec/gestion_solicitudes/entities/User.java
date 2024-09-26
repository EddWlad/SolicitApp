package com.tidsec.gestion_solicitudes.entities;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name= "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Temporal(TemporalType.DATE)
	private Date dateCreate = new Date();
    
	@NotBlank(message = "La identificación es requerida")
	@Size(min = 10, max = 13, message = "La identificación debe tener entre 10 y 13 dígitos")
	@Column(unique= true)
	private String identification;
    
	@NotBlank
	@NotNull
	@Size(min = 3, max = 50)
	private String name;
	
	@NotBlank
	@NotNull
	@Size(min = 3, max = 50)
	private String lastName;

    @Column(nullable = false, unique = true)
    private String username;
    
	@NotBlank(message = "El correo electrónico no debe estar vacío")
	@Email(message = "Correo electrónico no válido")
	@Column(unique= true)
	private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "role_id", nullable = false)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Role role;

    @OneToMany(mappedBy = "requester")
    private List<Request> requests;
    
    @OneToMany(mappedBy = "engineer")
    private List<Project> projects; 

    @Column(nullable = false)
    private Integer status; 
}
