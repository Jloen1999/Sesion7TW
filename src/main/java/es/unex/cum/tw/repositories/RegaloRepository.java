package es.unex.cum.tw.repositories;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.models.Regalo;
import es.unex.cum.tw.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RegaloRepository extends Repository<Regalo>{
    Optional<Regalo> findRegaloById(int id) throws SQLException;
    Optional<Regalo> findRegaloByName(String nameRegalo) throws SQLException;
    boolean updateCantidad(Regalo regalo, int cantidad) throws SQLException;

}
