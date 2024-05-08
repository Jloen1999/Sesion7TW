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

@WebServlet(
        name = "Action",
        value = "/carta/action"
)
public class Action extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        LoginService loginService = new LoginServiceImpl();
        Optional<User> userOptional = loginService.authenticate(request);

        String userId = request.getParameter("userId");

        String action = request.getParameter("accion");

        if (action != null && !action.isBlank()) {
            RequestDispatcher requestDispatcher = null;
            switch (action) {
                case "anadir":
                    requestDispatcher = getServletContext().getRequestDispatcher("/carta/anadir");
                    try {
                        requestDispatcher.forward(request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "listar":
                    if (userId != null && !userId.isBlank()) {
                        requestDispatcher = getServletContext().getRequestDispatcher("/carta/listar?userId="+userId);
                        try {
                            requestDispatcher.forward(request, response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

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
