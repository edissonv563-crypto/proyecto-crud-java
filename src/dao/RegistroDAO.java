package dao;

import conexion.ConexionBD;
import modelo.RegistroModulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAO {

    // 1. CREAR (Inserción)
    public boolean insertarRegistro(RegistroModulo registro) {
        String sql = "INSERT INTO registros (nombre, descripcion) VALUES (?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, registro.getNombre());
            ps.setString(2, registro.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // 2. LEER (Consulta)
    public List<RegistroModulo> consultarRegistros() {
        List<RegistroModulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM registros";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RegistroModulo reg = new RegistroModulo();
                reg.setId(rs.getInt("id"));
                reg.setNombre(rs.getString("nombre"));
                reg.setDescripcion(rs.getString("descripcion"));
                lista.add(reg);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar: " + e.getMessage());
        }
        return lista;
    }

    // 3. ACTUALIZAR (Modificación)
    public boolean actualizarRegistro(RegistroModulo registro) {
        String sql = "UPDATE registros SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, registro.getNombre());
            ps.setString(2, registro.getDescripcion());
            ps.setInt(3, registro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR (Borrado)
    public boolean eliminarRegistro(int id) {
        String sql = "DELETE FROM registros WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}