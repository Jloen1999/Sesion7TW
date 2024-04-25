package es.unex.cum.tw.services;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.models.Regalo;
import es.unex.cum.tw.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CartaService {
    Optional<Carta> findCartaByUser(User user) throws SQLException;
    List<Regalo> findRegalosByCarta(Carta carta) throws SQLException;
    boolean addRegaloToCarta(Carta carta, Regalo regalo) throws SQLException;
    boolean existsCartaByUser(User user) throws SQLException;
    boolean existsRegaloByCarta(Carta carta) throws SQLException;

}
