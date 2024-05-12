package es.unex.cum.tw.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase que se encarga de gestionar la conexión a la base de datos mediante un DataSource.
 * @author Jose Luis Obiang Ela Nanguang
 * @version 1.0 12-05-2024, Sun, 22:37
 */
public class ConexionBD_DSPool {

    public static Connection getConexionBD() throws SQLException, NamingException {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/MysqlDB");
        return ds.getConnection();
    }
}
