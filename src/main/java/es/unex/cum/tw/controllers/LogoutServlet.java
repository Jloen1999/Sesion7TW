package es.unex.cum.tw.controllers;

import es.unex.cum.tw.models.User;
import es.unex.cum.tw.services.LoginService;
import es.unex.cum.tw.services.LoginServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(
        name = "LogoutServlet",
        value = "/logout"
)
public class LogoutServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        LoginService auth = new LoginServiceImpl();
        Optional<User> userOptional = auth.authenticate(request);

        if (userOptional.isPresent()) {
            HttpSession session = request.getSession();
            session.invalidate();
        }

        response.sendRedirect(request.getContextPath() + "/Autenticar.html");

    }

}
