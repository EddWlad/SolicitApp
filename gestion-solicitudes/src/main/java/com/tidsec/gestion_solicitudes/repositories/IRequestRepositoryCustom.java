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

   public List<RequestDTO> findByDescription() {
        String sql = "SELECT " +
                "r.id AS idRequest, " +
                "c.name AS nameCompany, " +
                "p.project_name AS nameProject, " +
                "u.name AS nameRequester, " +
                "r.date AS date, " +
                "r.status AS status, " +
                "r.status_logical_delete AS statusLogicalDelete " +
                "FROM request r " +
                "INNER JOIN company c ON r.company_id = c.id " +
                "INNER JOIN project p ON r.project_id = p.id " +
                "INNER JOIN \"user\" u ON r.user_id = u.id " +
                "WHERE r.status_logical_delete IN (1, 2)";

        Map<Long, RequestDTO> requestMap = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            try {
                Long id = rs.getLong("idRequest");
                RequestDTO request = requestMap.computeIfAbsent(id, k -> {
					try {
						return new RequestDTO(
						    id,
						    rs.getString("nameCompany"),
						    rs.getString("nameProject"),
						    rs.getString("nameRequester"),
						    rs.getDate("date"),
						    rs.getInt("status"),
						    rs.getInt("statusLogicalDelete")
						);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				});
            } catch (SQLException e) {
                throw new DataAccessException("Error accessing data", e) {

					private static final long serialVersionUID = 1L;};
            }
        });

        return new ArrayList<>(requestMap.values());
    }
    public List<RequestModalDTO> findDescriptionById(Long idRequest) {
        String sql = "SELECT " +
                "r.id AS idRequest, " +
                "r.date AS date, " +
                "c.id AS idCompany, " +
                "m.id AS idMaterial, " +
                "i.id AS idInventory, " +
                "p.id AS idProject, " +
                "u.id AS idUser, " +
                "r.status AS status, " +
                "r.status_logical_delete AS statusLogicalDelete " +
                "FROM request r " +
                "INNER JOIN company c ON r.company_id = c.id " +
                "INNER JOIN inventory i ON r.id = i.request_id " +
                "INNER JOIN material m ON i.id = m.inventory_id " +
                "INNER JOIN project p ON r.project_id = p.id " +
                "INNER JOIN \"user\" u ON r.user_id = u.id " +
                "WHERE r.status_logical_delete IN (1, 2)";

        Map<Long, RequestModalDTO> requestMap = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            try {
                Long id = rs.getLong("idRequest");
                RequestModalDTO request = requestMap.computeIfAbsent(id, k -> {
					try {
						return new RequestModalDTO(
						    id,
						    rs.getDate("date"),
						    rs.getLong("idCompany"),
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
                request.getIdMaterial().add(rs.getLong("idMaterial"));
            } catch (SQLException e) {
                throw new DataAccessException("Error accessing data", e) {

					private static final long serialVersionUID = 1L;};
            }
        });

        return new ArrayList<>(requestMap.values());
    }
}