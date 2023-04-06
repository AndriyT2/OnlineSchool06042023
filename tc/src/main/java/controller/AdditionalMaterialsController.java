package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AmySQLForServlet;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Map;
@ComponentScan("model")
@WebServlet("/additional_materials")
public class AdditionalMaterialsController extends HttpServlet {
    AmySQLForServlet am = new AmySQLForServlet();
//    AmySQLForServlet am = ComponentScan;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Integer> type = am.amType();
        req.setAttribute("am", type);
        req.getRequestDispatcher("am.jsp").forward(req, resp);
    }
}
