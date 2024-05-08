package es.unex.cum.tw.services;

import es.unex.cum.tw.models.User;
import es.unex.cum.tw.repositories.UserRepository;
import es.unex.cum.tw.repositories.UserRepositoryJDBCImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserServiceJDBCImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceJDBCImpl(Connection connection){
        this.userRepository = new UserRepositoryJDBCImpl(connection);
    }

    @Override
    public Optional<User> login(String username, String password) {
        try {
            return userRepository.findByUsernameAndPassword(username, password).filter(user -> user.getPassword().equals(password));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try {
            return userRepository.findById(id);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean save(User user) {
        try {
            return userRepository.save(user);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean update(User user) {
        try {
            return userRepository.update(user);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            return userRepository.deleteById(id);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean delete(User user) {
        try {
            return userRepository.delete(user);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}
