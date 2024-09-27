package com.tidsec.gestion_solicitudes.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

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

   /* public List<EtiquetaDTO> findByDescription() {
        String sql = "SELECT e.id_etiqueta AS id_etiqueta, m.nombre AS nombre_empresa, e.numero_transferencia AS transferencia, " +
                     "CONCAT (d.nombre,'  ',d.apellido) AS nombre_destinatario_1, l.ciudad AS ciudad, e.estado AS estado " +
                     "FROM etiqueta e " +
                     "INNER JOIN empresa m ON e.id_empresa = m.id_empresa " +
                     "INNER JOIN lugar_destino l ON e.id_lugar_destino = l.id_lugar_destino " +
                     "INNER JOIN etiqueta_destinatario ed ON e.id_etiqueta = ed.id_etiqueta " +
                     "INNER JOIN destinatario d ON ed.id_destinatario = d.id_destinatario " +
                     "WHERE e.estado = 1 OR e.estado = 2";

        Map<Integer, EtiquetaDTO> etiquetasMap = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            try {
                int id = rs.getInt("id_etiqueta");
                EtiquetaDTO etiqueta = etiquetasMap.computeIfAbsent(id, k -> {
					try {
						return new EtiquetaDTO(
						    id,
						    rs.getString("nombre_empresa"),
						    rs.getString("transferencia"),
						    new ArrayList<>(),
						    rs.getString("ciudad"),
						    rs.getInt("estado")
						);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				});
                etiqueta.getNombres_destinatarios().add(rs.getString("nombre_destinatario_1"));
            } catch (SQLException e) {
                throw new DataAccessException("Error accessing data", e) {

					private static final long serialVersionUID = 1L;};
            }
        });

        return new ArrayList<>(etiquetasMap.values());
    }*/
    /*public List<RequestModalDTO> findDescriptionById(Long idRequest) {
        String sql = "SELECT e.id_etiqueta AS id_etiqueta,e.observaciones AS observaciones, e.numero_transferencia, e.fecha, m.id_empresa AS id_empresa, e.numero_transferencia AS transferencia, " +
                     "d.id_destinatario AS id_destinatario_1, l.id_lugar_destino AS id_lugar_destino, e.estado AS estado, e.fecha " +
                     "FROM etiqueta e " +
                     "INNER JOIN empresa m ON e.id_empresa = m.id_empresa " +
                     "INNER JOIN lugar_destino l ON e.id_lugar_destino = l.id_lugar_destino " +
                     "INNER JOIN etiqueta_destinatario ed ON e.id_etiqueta = ed.id_etiqueta " +
                     "INNER JOIN destinatario d ON ed.id_destinatario = d.id_destinatario " +
                     "WHERE e.id_etiqueta ="+idRequest;

        Map<Long, RequestModalDTO> etiquetasMap = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            try {
                int id = rs.getInt("id_etiqueta");
                RequestModalDTO etiqueta = etiquetasMap.computeIfAbsent(id, k -> {
					try {
						return new EtiquetaModalDTO(
						    id,
						    rs.getInt("id_empresa"),
						    rs.getString("observaciones"),
						    rs.getString("transferencia"),
						    new ArrayList<>(),
						    rs.getInt("id_lugar_destino"),
						    rs.getInt("estado"),
						    rs.getDate("fecha")
						);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				});
                etiqueta.getId_destinatario_1().add(rs.getInt("id_destinatario_1"));
            } catch (SQLException e) {
                throw new DataAccessException("Error accessing data", e) {

					private static final long serialVersionUID = 1L;};
            }
        });

        return new ArrayList<>(etiquetasMap.values());
    }*/
}