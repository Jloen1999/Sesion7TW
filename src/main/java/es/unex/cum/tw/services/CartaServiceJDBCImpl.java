package es.unex.cum.tw.services;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.models.Regalo;
import es.unex.cum.tw.models.User;
import es.unex.cum.tw.repositories.CartaRepository;
import es.unex.cum.tw.repositories.CartaRepositoryJDBCImpl;
import es.unex.cum.tw.repositories.UserRepositoryJDBCImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CartaServiceJDBCImpl implements CartaService{

    private CartaRepository cartaRepository;

    public CartaServiceJDBCImpl(Connection connection){
        this.cartaRepository = new CartaRepositoryJDBCImpl(connection);
    }


    @Override
    public Optional<Carta> findCartaByUser(User user) throws SQLException {
        try{
            return cartaRepository.findCartaByUser(user);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Regalo> findRegalosByCarta(Carta carta) throws SQLException {
        try{
            return cartaRepository.findRegalosByCarta(carta);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean addRegaloToCarta(Carta carta, Regalo regalo) throws SQLException {
        try{
            return cartaRepository.addRegaloToCarta(carta, regalo);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }


    @Override
    public void deleteRegalosFromCarta(List<String> idRegalos) throws SQLException {
        try{
            cartaRepository.deleteRegalosFromCarta(idRegalos);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean addCantidadToRegalo(int idRegalo, int cantidad) throws SQLException {
        try{
            return cartaRepository.addCantidadToRegalo(idRegalo, cantidad);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }


}
