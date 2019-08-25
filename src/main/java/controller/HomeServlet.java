package controller;

import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "homeServlet", urlPatterns = {"", "/login"})
public class HomeServlet extends HttpServlet {
    // finalne i statyczne - wygoda używania w tej klasie
    UserDAO userDAO;
    private static final String LOGIN = "login";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String REMEMBER = "remember";

    @Override // init patter execute - często używane do uporządkowania różnych watków, tasków...
    public void init() throws ServletException {
        userDAO = new UserDAO();
        super.init();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
//
//        if (null != username  && password != null) {
//            req.setAttribute(USERNAME, username);
//
//        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String remember = req.getParameter(REMEMBER);

        resp.getWriter().println("User = " + login + "Password = " + password);

    }
}
