package es.unex.cum.tw.repositories;

import es.unex.cum.tw.models.User;
import es.unex.cum.tw.models.UserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJDBCImpl implements UserRepository {

    private Connection conn;

    public UserRepositoryJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        User user = null;
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE username = ?")){
            statement.setString(1, username);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    user = new UserBuilder().setId(rs.getInt("id"))
                            .setNombre(rs.getString("nombre"))
                            .setApellidos(rs.getString("apellidos"))
                            .setEmail(rs.getString("email"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .build();

                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(user);

    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        User user = null;
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE email = ?")) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new UserBuilder().setId(rs.getInt("id"))
                            .setNombre(rs.getString("nombre"))
                            .setApellidos(rs.getString("apellidos"))
                            .setEmail(rs.getString("email"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .build();

                }
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) throws SQLException {
        User user = null;
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new UserBuilder().setId(rs.getInt("id"))
                            .setNombre(rs.getString("nombre"))
                            .setApellidos(rs.getString("apellidos"))
                            .setEmail(rs.getString("email"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .build();

                }
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        User user = null;
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new UserBuilder().setId(rs.getInt("id"))
                            .setNombre(rs.getString("nombre"))
                            .setApellidos(rs.getString("apellidos"))
                            .setEmail(rs.getString("email"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .build();

                }
            }
        }
        return Optional.ofNullable(user);
    }



    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios")) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = new UserBuilder().setId(rs.getInt("id"))
                            .setNombre(rs.getString("nombre"))
                            .setApellidos(rs.getString("apellidos"))
                            .setEmail(rs.getString("email"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .build();
                    users.add(user);
                }
            }
        }
        return users;
    }

    @Override
    public boolean save(User user) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO usuarios (nombre, apellidos, email, username, password) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getApellidos());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(User user) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "UPDATE usuarios SET nombre = ?, apellidos = ?, email = ?, username = ?, password = ? WHERE id = ?")) {
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getApellidos());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setLong(6, user.getId());
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        if(selectCartaAndDeleteInRegalo(id)){
            deleteUserInCarta(id);
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
                statement.setLong(1, id);
                return statement.executeUpdate() > 0;
            }
        }

        return false;
    }

    public void deleteUserInCarta(int idUser) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement("DELETE FROM cartas WHERE id_usuario = ?")){
            statement.setLong(1, idUser);
            statement.executeUpdate();
        }
    }

    public boolean selectCartaAndDeleteInRegalo(int idUser) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement("SELECT id from cartas where id_usuario = ?")){
            statement.setLong(1, idUser);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return deleteCartaInRegalo(rs.getInt("id"));
            }
        }

        return false;
    }

    public boolean deleteCartaInRegalo(int idCarta) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement("DELETE FROM regalos WHERE id_carta = ?")){
            statement.setLong(1, idCarta);
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(User user) throws SQLException {
        return deleteById(user.getId());
    }
}
