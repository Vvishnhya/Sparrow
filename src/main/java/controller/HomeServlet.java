package controller;

import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "homeServlet", urlPatterns = {"", "/login"})
public class HomeServlet extends HttpServlet {
    // finalne i statyczne - wygoda używania w tej klasie
    UserDAO userDAO;
    public static final String LOGIN = "login";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String REMEMBER = "remember"; // opcja remember z ciacha
    private static final String CHECKBOX_SELECTED = "on";  // opcja remember z ciacha
    private static final String COOKIE_LOGIN = "sparrow_login";
    private static final String COOKIE_PASSWORD = "sparrow_password";
    private static final int SECONDS_IN_DAY = 86400; // opcja remember z ciacha


    @Override // init patter execute - często używane do uporządkowania różnych watków, tasków...
    public void init() throws ServletException {
        userDAO = new UserDAO();
        super.init();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;

        if (req != null && req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(COOKIE_LOGIN)) {
                    login = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);
                } else if (cookie.getName().equals(COOKIE_PASSWORD)) {
                    password = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);

                }
            }
        }
        if (login != null && password != null) {
            req.setAttribute(LOGIN, login);
            req.setAttribute(PASSWORD, password);
            doPost(req, resp);
        } else {

            req.getRequestDispatcher("/login.jsp").forward(req, resp);
//
//        if (null != username  && password != null) {
//            req.setAttribute(USERNAME, username);lllllllll
//
//        }


        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String remember = req.getParameter(REMEMBER);

        // kluczowy fragment kodu jeśli chodzi o COOKIES -- >
        if (login == null && password == null) {
            login = (String)req.getAttribute(LOGIN);
            password = (String)req.getAttribute(PASSWORD);
        }

        //resp.getWriter().println("User = " + login + "Password = " + password);

        if (userDAO.isUserExist(login, password)) {
            // zanim pojedziemy na servlet "users" polecimy z ciastkiem na remember
            req.getSession().setAttribute(LOGIN, login);
            if (remember != null && remember.equals(CHECKBOX_SELECTED)) {
                Cookie loginCookie = new Cookie(COOKIE_LOGIN, login);
                Cookie passwordCookie = new Cookie(COOKIE_PASSWORD, password);
                loginCookie.setMaxAge(SECONDS_IN_DAY);
                passwordCookie.setMaxAge(SECONDS_IN_DAY);
                resp.addCookie(loginCookie);
                resp.addCookie(passwordCookie);
            }
            req.getRequestDispatcher("users").forward(req, resp);
        } else {
            req.setAttribute("hasError", "true");
            req.setAttribute("error", "Username or password incorrect");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }

    }
}

