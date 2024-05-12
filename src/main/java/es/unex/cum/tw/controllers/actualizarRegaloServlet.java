package es.unex.cum.tw.controllers;

import es.unex.cum.tw.models.Carta;
import es.unex.cum.tw.services.CartaService;
import es.unex.cum.tw.services.CartaServiceJDBCImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


/**
 * Servlet que se encarga de eliminar regalos de la carta de un usuario.
 * También se encarga de actualizar las cantidades de los regalos.
 * @author Jose Luis Obiang Ela Nanguang
 * @version 1.0 12-05-2024, Sun, 22:20
 */
@WebServlet(
        name = "eliminarRegalo",
        value = "/carta/actualizar"
)
public class actualizarRegaloServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        HttpSession session = req.getSession();
        CartaService cartaService = new CartaServiceJDBCImpl((Connection) req.getAttribute("con"));

        try (PrintWriter print = resp.getWriter()) {

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
            print.println("<body>");

            Carta carta = (Carta) session.getAttribute("carta"); // Obtenemos la carta del usuario de la sesión

            if (carta != null) { // Si la carta no es nula, eliminamos los regalos y actualizamos las cantidades de los regalos en caso de que se haya modificado
                eliminarRegalo(req, cartaService);
                updateCantidades(req, cartaService);

                resp.sendRedirect(req.getContextPath() + "/carta/listar?userId=" + carta.getIdUsuario());
            }else{
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No se ha encontrado la carta");
            }

            print.println("</body>");
            print.println("</html>");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que elimina los regalos de la carta de un usuario.
     * @param request Petición HTTP
     * @param cartaService Servicio de la carta
     */
    private static void eliminarRegalo(HttpServletRequest request, CartaService cartaService) {
        String[] deleteIds = request.getParameterValues("regalosAEliminar");

        if (deleteIds != null && deleteIds.length > 0) {
            List<String> regaloIds = Arrays.asList(deleteIds);
            try {
                cartaService.deleteRegalosFromCarta(regaloIds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método que actualiza las cantidades de los regalos de la carta de un usuario.
     * @param request Petición HTTP
     * @param cartaService Servicio de la carta
     */
    private void updateCantidades(HttpServletRequest request, CartaService cartaService) {

        Enumeration<String> enumer = request.getParameterNames();

        // Iteramos a traves de los parámetros y buscamos los que empiezan con
        // "cant_". El campo cant en la vista fueron nombrados "cant_" + productoId.
        // Obtenemos el id de cada producto y su correspondiente cantidad ;-).
        while (enumer.hasMoreElements()) {
            String paramName = enumer.nextElement();
            if (paramName.startsWith("cant_")) {
                String id = paramName.substring(5);
                String cantidad = request.getParameter(paramName);
                if (cantidad != null) {
                    try {
                        cartaService.addCantidadToRegalo(Integer.parseInt(id), Integer.parseInt(cantidad));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
