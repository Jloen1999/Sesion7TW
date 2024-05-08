package es.unex.cum.tw.repositories;

import es.unex.cum.tw.models.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository extends Repository<User>{
    Optional<User> findByUsername(String username) throws SQLException;
    Optional<User> findByEmail(String email) throws SQLException;
    Optional<User> findByUsernameAndPassword(String username, String password) throws SQLException;

}
