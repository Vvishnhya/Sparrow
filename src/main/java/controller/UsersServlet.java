package controller;

import dao.UserDAO;
import model.User;
import sun.rmi.log.LogInputStream;

import static controller.HomeServlet.LOGIN;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "userServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

   private UserDAO userDAO = new UserDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher("/users.jsp").forward(req, resp);
        //        Set<String> followedUsers = userDao.getFollowedUsers((String) request.getSession().getAttribute("username"));
//        Set<String> notFollowedUsers = userDao.getNotFollowedUsers((String) request.getSession().getAttribute("username"));
//        request.setAttribute("followedUsers", followedUsers);
//        request.setAttribute("notFollowedUsers", notFollowedUsers);
        String currentUserLogin = (String) req.getSession().getAttribute(LOGIN);

        List<User> followedUsers = userDAO.getFollowedUsers(currentUserLogin);
        List<User> notFollowedUsers = userDAO.getNotFollowedUsers(currentUserLogin);
        req.setAttribute("followedUsers", followedUsers);
        req.setAttribute("notFollowedUsers", notFollowedUsers);

        req.getRequestDispatcher("/users.jsp").forward(req, resp);

    }
}
