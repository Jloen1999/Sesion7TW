package es.unex.cum.tw.repositories;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.models.Regalo;
import es.unex.cum.tw.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegaloRepositoryJDBCImpl implements RegaloRepository {

    private Connection conn;

    public RegaloRepositoryJDBCImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Optional<Regalo> findRegaloById(int idUsuario) throws SQLException {
        String query = "SELECT * FROM regalos WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(new Regalo(rs.getInt("id"), rs.getString("nombre_regalo"), rs.getInt("id_carta")));
        }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Regalo> findRegaloByName(String nameRegalo) throws SQLException {
        String query = "SELECT * FROM regalos WHERE nombre_regalo = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nameRegalo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Regalo(rs.getInt("id"), rs.getString("nombre_regalo"), rs.getInt("id_carta")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public boolean updateCantidad(Regalo regalo, int cantidad) throws SQLException {
        String query = "UPDATE regalos SET cantidad = ? WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, regalo.getIdRegalo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Regalo> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Regalo> findAll() throws SQLException {
        String query = "SELECT * FROM regalos";
        List<Regalo> regalos = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                regalos.add(new Regalo(rs.getInt("id"), rs.getString("nombre_regalo"), rs.getInt("id_carta")));

            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return regalos;
    }

    @Override
    public boolean save(Regalo regalo) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Regalo regalo) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Regalo regalo) throws SQLException {
        return false;
    }
}
