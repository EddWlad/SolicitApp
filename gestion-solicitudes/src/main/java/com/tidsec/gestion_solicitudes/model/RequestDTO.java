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
public class RequestDTO {
	private Long idRequest;
	private String nameCompany;
	private String nameProject;
	private String nameRequester;
	private Date date;
	private int status;
	private int statusLogicalDelete;
}
