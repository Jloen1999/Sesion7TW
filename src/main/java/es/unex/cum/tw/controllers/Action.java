package es.unex.cum.tw.controllers;

import es.unex.cum.tw.models.User;
import es.unex.cum.tw.services.LoginService;
import es.unex.cum.tw.services.LoginServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet que gestiona las acciones de la carta
 * <ul>
 *     <li>anadir: redirige a la vista de añadir regalo</li>
 *     <li>listar: redirige a la vista de listar regalos</li>
 * </ul>
 * @author Jose Luis Obiang Ela Nanguang
 * @version 1.0 12-05-2024, Sun, 21:20
 */
@WebServlet(
        name = "Action",
        value = "/carta/action"
)
public class Action extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        LoginService loginService = new LoginServiceImpl();
        Optional<User> userOptional = loginService.authenticate(request); // Obtener usuario de la sesión

        String userId = request.getParameter("userId"); // Obtener el ID del usuario de la petición

        String action = request.getParameter("accion"); // Obtener la acción de la petición

        if (action != null && !action.isBlank()) { // Si existe la acción
            RequestDispatcher requestDispatcher = null;
            switch (action) {
                case "anadir": // Si la acción es añadir
                    requestDispatcher = getServletContext().getRequestDispatcher("/carta/anadir"); // Redirigir a la vista de añadir regalo a la carta del usuario de la sesión
                    try {
                        requestDispatcher.forward(request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "listar": // Si la acción es listar
                    if (userId != null && !userId.isBlank()) {
                        requestDispatcher = getServletContext().getRequestDispatcher("/carta/listar?userId="+userId); // Redirigir a la vista de listar regalos del usuario en caso de que se haya pasado el ID del usuario
                        try {
                            requestDispatcher.forward(request, response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Redirigir a la vista de listar regalos del usuario de la sesión en caso de que no se haya pasado el ID del usuario
                    requestDispatcher = getServletContext().getRequestDispatcher("/carta/listar?userId="+userOptional.get().getId());
                    try {
                        requestDispatcher.forward(request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    response.getWriter().println("<h1 style=\"red\">Acción no válida</h1>");
            }
        } else {
            response.getWriter().println("<h1 style='red'>Debes introducir una acción</h1>");
        }

    }
}
