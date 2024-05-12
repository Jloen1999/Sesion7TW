package es.unex.cum.tw.controllers;


import es.unex.cum.tw.services.UserService;
import es.unex.cum.tw.services.UserServiceJDBCImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

/**
 * Servlet que elimina un usuario de la base de datos.
 * @author Jose Luis Obiang Ela Nanguang
 * @version 1.0 12-05-2024, Sun, 22:28
 */
@WebServlet
        (name = "EliminarUsuarioServlet", value = "/usuario/eliminar")
public class EliminarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        String[] idUsuarios = req.getParameterValues("eliminarUsuarios"); // Obtenemos los ID de los usuarios a eliminar

        if(idUsuarios != null && idUsuarios.length > 0){ // Si se ha seleccionado algún usuario
            Connection connection = (Connection) req.getAttribute("con");
            UserService userService = new UserServiceJDBCImpl(connection);

            for(String idUsuario : idUsuarios){
                userService.deleteById(Integer.parseInt(idUsuario)); // Eliminamos todos los usuarios seleccionados
            }

            resp.sendRedirect(req.getContextPath() + "/login");

        }
    }
}
