package es.unex.cum.tw.controllers;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.models.Regalo;
import es.unex.cum.tw.models.User;
import es.unex.cum.tw.services.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@WebServlet(name = "ListarServlet", value = "/carta/listar")
public class ListarServlet extends HttpServlet {
    private Properties props;
    private String USERNAMEADMIN;
    private String PASSWORDADMIN;

    @Override
    public void init() throws ServletException {
        super.init();
        props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            USERNAMEADMIN = props.getProperty("USERNAME");
            PASSWORDADMIN = props.getProperty("PASSWORD");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        UserService userService = new UserServiceJDBCImpl((Connection) request.getAttribute("con"));
        Optional<User> userOptional = userService.findById(Integer.parseInt(request.getParameter("userId")));

        LoginService loginService = new LoginServiceImpl();
        Optional<User> userLogin = loginService.authenticate(request);

        CartaService cartaService = new CartaServiceJDBCImpl((Connection) request.getAttribute("con"));

        HttpSession session = request.getSession();

        Carta carta = (Carta) session.getAttribute("carta");

        try (PrintWriter print = response.getWriter()) {

            print.println("<!DOCTYPE html>");
            print.println("<html lang=\"es\">");
            print.println(" <head>");
            print.println("     <title>Sesion 7</title>");
            print.println("     <meta charset=\"utf-8\">");
            print.println("     <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            print.println("     <meta name=\"description\" content=\"\">");
            print.println("     <meta name=\"author\" content=\"Jose Luis Obiang Ela Nanguang\">");
            print.println("     <!-- Bootstrap CSS -->");
            print.println("     <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">");
            print.println("     <!-- Bootstrap Fonts -->");
            print.println("     <link href=\"https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900&amp;display=swap\" rel=\"stylesheet\">");
            print.println(" </head>");
            print.println("<body class=\"bg-dark\">");
            if (userOptional.isPresent()) {

                print.println("<div class=\"col-md-3 d-flex justify-content-end ms-6\">");
                print.println("<img class=\"\" src=\"../img/profile.svg\" alt=\"imagen de perfil del admin\" wi>");
                // Mostrar nombre username
                print.println("<p class=\"text-success\">" + userLogin.get().getUsername() + "</p>");
                print.println("</div>");

                carta = cartaService.findCartaByUser(userOptional.get()).orElse(carta);
                session.setAttribute("carta", carta);

                print.println("<h1 class=\"text-warning text-center display-1\">Reyes Magos</h1>");

                print.println("<div class=\"row flex-nowrap justify-content-between align-items-center\">");
                print.println("<header class=\"col-5\">");
                print.println("<nav id=\"navbar-example3\" class=\"h-100 flex-column align-items-stretch pe-4 border-end\">");
                print.println("<nav class=\"nav nav-pills flex-column\">");
                if (userLogin.get().getUsername().equals(USERNAMEADMIN) && userLogin.get().getPassword().equals(PASSWORDADMIN)) {
                    print.println("<a class=\"nav-link\" href=\"/webapp-sesion7/login\" data-bs-toggle=\"tooltip\"");
                    print.println("data-bs-title=\"Página de administración\" data-bs-placement=\"left\">");
                    print.println("Admin");
                    print.println("</a>");
                }
                print.println("<a class=\"nav-link\" href=\"/webapp-sesion7/Introducir.html\" data-bs-toggle=\"tooltip\"");
                print.println("data-bs-title=\"Añadir regalos a la carta de los reyes magos\" data-bs-placement=\"left\">");
                print.println("Añadir regalos");
                print.println("</a>");
                print.println("<a class=\"nav-link\" href=\"/webapp-sesion7/logout\" data-bs-toggle=\"tooltip\"");
                print.println("data-bs-title=\"Cerrar sesión de usuario\" data-bs-placement=\"left\">");
                print.println("Cerrar sesión");
                print.println("</a>");
                print.println("</nav>");
                print.println("</nav>");
                print.println("</header>");
                print.println("<main class=\"col-7\">");
                print.println("<div class=\"p-4 p-md-5 mb-4 text-white rounded bg-dark container\">");
                print.println("<div class=\"row\">");
                print.println("<div class=\"col-md-6 px-0\">");
                print.println("<h1 class=\"display-4 fst-italic\">Añadir y Listar Regalos</h1>");
                print.println("<p class=\"lead my-3\">Aquí podrás ver los regalos y añadir nuevos regalos.</p>");
                print.println("</div>");
                print.println("<div class=\"col-md-6 img-thumbnail\">");
                print.println("<img src=\"../img/navidad-reyes-magos.webp\" alt=\"imagen de los tres reyes magos\">");
                print.println("</div>");

                List<Regalo> regalos = cartaService.findRegalosByCarta(carta);

                print.println("<h2 class=\"text-warning text-center mt-4\">Carta del usuario " + userOptional.get().getUsername() + "</h2>");
                /* add <form name="formcarro" action="${pageContext.request.contextPath}/carro/actualizar" method="post"> */
                print.println("<form name=\"formCarta\" action=\"/webapp-sesion7/carta/actualizar\" method=\"post\">");
                print.println("<table class=\"table table-hover table-striped\">");
                print.println("<tr>");
                print.println("<th>id</th>");
                print.println("<th>nombre</th>");
                print.println("<th>id_carta</th>");
                print.println("<th>cantidad</th>");
                print.println("<th>borrar</th>");
                print.println("</tr>");
                for (Regalo regalo : regalos) {
                    print.println("<tr>");
                    print.println("<td>" + regalo.getIdRegalo() + "</td>");
                    print.println("<td>" + regalo.getNombre() + "</td>");
                    print.println("<td>" + regalo.getIdCarta() + "</td>");
                    print.println("<td><input type=\"number\" min=\"1\" max=\"100\" style=\"width: 40px\" name=\"cant_" + regalo.getIdRegalo() + "\" value=\"" + regalo.getCantidad() + "\"/></td>");
                    print.println("<td><input type=\"checkbox\" value=\"" + regalo.getIdRegalo() + "\" name=\"regalosAEliminar\"/></td>");
                    print.println("</tr>");
                }
                print.println("</table>");
                print.println("<a class=\"btn btn-primary\" href=\"javascript:document.formCarta.submit();\">Actualizar</a>");
                print.println("</form>");
            } else {
                response.getWriter().println("<p style=\"red\">No puedes listar regalos. Debes iniciar sesión</p>");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.html");
                try {
                    dispatcher.include(request, response);
                } catch (IOException | ServletException e) {
                    throw new RuntimeException(e);
                }
            }

            print.println("</div>");
            print.println("</div>");
            print.println("</main>");
            print.println("</div>");
            print.println("<footer class=\"row blog-footer d-flex justify-content-center align-content-center\">");
            print.println("<figure class=\"col-md-6 text-white text-center\">");
            print.println("<blockquote class=\"blockquote\">");
            print.println("<p>Sesión 7.</p>");
            print.println("</blockquote>");
            print.println("<figcaption class=\"blockquote-footer\">");
            print.println("Lista <cite title=\"Source Title\">de Regalos</cite>");
            print.println("</figcaption>");
            print.println("</figure>");
            print.println("</footer>");


            print.println("     <!-- Bootstrap JS -->");
            //  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            //        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            //        crossorigin="anonymous"></script>
            print.println("     <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"" +
                    "integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\"" +
                    "crossorigin=\"anonymous\"></script>");
            print.println("</body>");
            print.println("</html>");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
