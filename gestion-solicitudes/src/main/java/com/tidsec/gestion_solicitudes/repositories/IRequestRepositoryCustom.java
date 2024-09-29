package com.tidsec.gestion_solicitudes.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import com.tidsec.gestion_solicitudes.model.RequestDTO;
import com.tidsec.gestion_solicitudes.model.RequestModalDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IRequestRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   /*public List<RequestDTO> findByDescription() {
        String sql = "SELECT r.id AS idRequest, " +
                "c.name AS nameCompany, " +
                "p.project_name AS nameProject, " +
                "r.date AS date, " +
                "r.status AS status, " +
                "string_agg(m.name, ', ') AS Inventory " +
                "FROM request r " +
                "INNER JOIN company c ON r.company_id = c.id " +
                "INNER JOIN project p ON r.project_id = p.id " +
                "LEFT JOIN inventory i ON r.id = i.request_id " +
                "LEFT JOIN material m ON i.inventory_id = m.id " +
                "GROUP BY r.id, c.name, p.project_name, r.date, r.status"+
                "WHERE r.status_logical_delete = 1 OR r.status_logical_delete = 2";

        Map<Integer, RequestDTO> requestMap = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            try {
                int id = rs.getInt("idRequest");
                RequestDTO request = requestMap.computeIfAbsent(id, k -> {
					try {
						return new RequestDTO(
						    id,
						    rs.getString("nameCompany"),
						    rs.getString("nameProject"),
						    rs.getString("Inventory"),
						    new ArrayList<>(),
						    rs.getDate("date"),
						    rs.getInt("status")
						);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				});
                request.getInventory().add(rs.getString("nameInventory1"));
            } catch (SQLException e) {
                throw new DataAccessException("Error accessing data", e) {

					private static final long serialVersionUID = 1L;};
            }
        });

        return new ArrayList<>(requestMap.values());
    }*/
    /*public List<RequestModalDTO> findDescriptionById(Long idRequest) {
        String sql = "SELECT r.id AS idRequest, " +
                "r.date AS date, " +
                "r.company_id AS idCompany, " +
                "i.id AS idInventory, " +
                "r.project_id AS idProject, " +
                "r.user_id AS idUser, " +
                "r.status AS status, " +
                "r.status_logical_delete AS statusLogicalDelete, " +
                "array_agg(m.id) AS idMaterial " +
                "FROM request r " +
                "LEFT JOIN inventory i ON r.id = i.request_id " +
                "LEFT JOIN material m ON i.id = m.inventory_id " +
                "WHERE r.id = " + idRequest + " " +
                "GROUP BY r.id, r.date, r.company_id, i.id, r.project_id, r.user_id, r.status, r.status_logical_delete";

        Map<Integer, RequestModalDTO> requestMap = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            try {
                int id = rs.getInt("idRequest");
                RequestModalDTO request = requestMap.computeIfAbsent(id, k -> {
					try {
						return new RequestModalDTO(
						    id,
						    rs.getDate("date"),
						    rs.getLong("idCompany"),
						    rs.getLong("idMaterial"),
						    new ArrayList<>(),
						    rs.getLong("idInventory"),
						    rs.getLong("idProject"),
						    rs.getLong("idUser"),
						    rs.getInt("status"),
						    rs.getInt("statusLogicalDelete")
						);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				});
                request.getIdMaterial().add(rs.getLong("idMaterial1"));
            } catch (SQLException e) {
                throw new DataAccessException("Error accessing data", e) {

					private static final long serialVersionUID = 1L;};
            }
        });

        return new ArrayList<>(requestMap.values());
    }*/
}