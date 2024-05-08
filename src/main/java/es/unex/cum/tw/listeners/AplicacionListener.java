package es.unex.cum.tw.listeners;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.models.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class AplicacionListener implements ServletContextListener, ServletRequestListener, HttpSessionListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("Iniciando la aplicación");
        servletContext = sce.getServletContext();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        sre.getServletContext().log("Petición recibida desde la IP: " + sre.getServletRequest().getRemoteAddr());
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        servletContext.log("Sesión creada: " + se.getSession().getId());
        HttpSession session = se.getSession();

        /*User user = new User();
        session.setAttribute("user", user);*/
        Carta carta = new Carta();
        session.setAttribute("carta", carta);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("Destruyendo la aplicación!");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        sre.getServletContext().log("Petición finalizada");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        servletContext.log("Sesión destruida: " + se.getSession().getId());
    }
}
