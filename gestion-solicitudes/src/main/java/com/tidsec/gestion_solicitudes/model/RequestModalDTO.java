package com.tidsec.gestion_solicitudes.model;

import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestModalDTO {
	private Long idRequest;
	private Date date;
	private Long idCompany;
	private List<Long> idMaterial;
	private Long idInventory;
	private Long idProject;
	private Long idUser;
	private int status;
	private int statusLogicalDelete;
}
