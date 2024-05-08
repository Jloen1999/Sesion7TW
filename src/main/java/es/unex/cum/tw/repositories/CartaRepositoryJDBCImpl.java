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

public class CartaRepositoryJDBCImpl implements CartaRepository {

    private Connection conn;

    public CartaRepositoryJDBCImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Optional<Carta> findCartaByUser(User user) throws SQLException {
        String query = "SELECT * FROM cartas WHERE id_usuario = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, user.getId());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(new Carta(rs.getInt("id"), rs.getInt("id_usuario"), rs.getString("titulo_carta")));
        }
        return Optional.empty();
    }

    @Override
    public List<Regalo> findRegalosByCarta(Carta carta) throws SQLException {
        String query = "SELECT * FROM regalos WHERE id_carta = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, carta.getIdCarta());
        ResultSet rs = ps.executeQuery();
        List<Regalo> regalos = new ArrayList<>();
        while (rs.next()) {
            regalos.add(new Regalo(rs.getInt("id"), rs.getString("nombre_regalo"), rs.getInt("id_carta"), rs.getInt("cantidad")));
        }
        return regalos;
    }

    @Override
    public boolean addRegaloToCarta(Carta carta, Regalo regalo) throws SQLException {
        String query = "SELECT * FROM regalos WHERE id_carta = ? AND nombre_regalo = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, carta.getIdCarta());
        ps.setString(2, regalo.getNombre());
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            // El regalo ya existe para esta carta, incrementar la cantidad
            int regaloId = rs.getInt("id");
            String updateQuery = "UPDATE regalos SET cantidad = cantidad + 1 WHERE id = ?";
            PreparedStatement updatePs = conn.prepareStatement(updateQuery);
            updatePs.setInt(1, regaloId);
            return updatePs.executeUpdate() > 0;
        } else {
            // El regalo no existe para esta carta, insertarlo
            String insertQuery = "INSERT INTO regalos (id_carta, nombre_regalo, cantidad) VALUES (?, ?, 1)";
            PreparedStatement insertPs = conn.prepareStatement(insertQuery);
            insertPs.setInt(1, carta.getIdCarta());
            insertPs.setString(2, regalo.getNombre());
            return insertPs.executeUpdate() > 0;
        }
    }

    @Override
    public boolean existsCartaByUser(User user) throws SQLException {
        String query = "SELECT * FROM cartas WHERE id_usuario = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, user.getId());
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public void deleteRegalosFromCarta(List<String> idRegalos) throws SQLException {
        String query = "DELETE FROM regalos WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        for (String idRegalo : idRegalos) {
            ps.setInt(1, Integer.parseInt(idRegalo));
            ps.executeUpdate();
        }
    }

    @Override
    public boolean addCantidadToRegalo(int idRegalo, int cantidad) throws SQLException {
        String query = "UPDATE regalos SET cantidad = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cantidad);
        ps.setInt(2, idRegalo);
        return ps.executeUpdate() > 0;
    }

    @Override
    public Optional<Carta> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Carta> findAll() throws SQLException {
        String query = "SELECT * FROM cartas";
        List<Carta> cartas = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartas.add(new Carta(rs.getInt("id"), rs.getInt("id_usuario"), rs.getString("titulo_carta")));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return cartas;
    }

    @Override
    public boolean save(Carta carta) throws SQLException {
        String query = "INSERT INTO cartas (id_usuario, titulo_carta) VALUES (?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, carta.getIdUsuario());
            ps.setString(2, carta.getTitulo());
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Carta carta) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Carta carta) throws SQLException {
        return false;
    }
}
