package es.unex.cum.tw.controllers;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.models.Regalo;
import es.unex.cum.tw.models.User;
import es.unex.cum.tw.services.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Servlet que añade un regalo a la carta de un usuario.
 * @author Jose Luis Obiang Ela Nanguang
 * @version 1.0 12-05-2024, Sun, 22:26
 */
@WebServlet(
        name = "AnadirRegalo",
        value = "/carta/anadir"
)
public class AnadirRegaloServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Connection conn = (Connection) request.getAttribute("con");
        CartaService cartaService = new CartaServiceJDBCImpl(conn);
        UserService userService = new UserServiceJDBCImpl(conn);

        LoginService loginService = new LoginServiceImpl();
        Optional<User> userOptional = loginService.authenticate(request); // Obtener usuario de la sesión

        String nombreRegalo = request.getParameter("regalo"); // Obtener nombre del regalo
        if (nombreRegalo != null && !nombreRegalo.isEmpty()) { // Si el nombre del regalo no es nulo ni vacío
            HttpSession session = request.getSession();

            Carta carta = (Carta) session.getAttribute("carta");
            try {
                if (userOptional.isPresent()) {
                    carta = cartaService.findCartaByUser(userOptional.get()).orElse(carta); // Obtener carta del usuario
                    Regalo regalo = new Regalo(nombreRegalo, carta.getIdCarta());

                    if (cartaService.addRegaloToCarta(carta, regalo)) { // Añadir regalo a la carta
                        session.setAttribute("carta", carta);
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/IntroducirOK.html");
                        try {
                            dispatcher.include(request, response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/IntroducirError.html");
                        try {
                            dispatcher.include(request, response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new ServiceJdbcException(e.getMessage(), e.getCause());
            }

        } else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/IntroducirError.html");
            try {
                dispatcher.include(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }

}
