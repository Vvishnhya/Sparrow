package controller;

import model.User;
import services.UserService;

import static controller.HomeServlet.LOGIN;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "registerServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    // 17. dodajemy zmienne do metody doPOST
   // private static final String LOGIN = "login";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_REPEATED = "passwordRepeated";
    private static final String REMEMBER = "remember";
    private static final String EMAIL = "email";
    private static final String LAST_NAME = "lastName";

    UserService userService = new UserService();

    // ctrl+o i wybieramy  "doGet" i zaciągamy dispatcherem ścieżkę do register.jsp
    // i metodą forward przekazujemy req = request i resp = response
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String login = req.getParameter(LOGIN);
//        String username = req.getParameter(USERNAME);
//        String lastname = req.getParameter(LAST_NAME);
//        String password = req.getParameter(PASSWORD);
//        String passwordRepeated = req.getParameter(PASSWORD_REPEATED);
//        String email = req.getParameter(EMAIL);
//
//
////        // walidujemy poprawność ddwóch haseł
//       if(!password.equals(passwordRepeated)) {
//           req.setAttribute("hasError", "true");
//           req.setAttribute("error", "Passwords does no match!");
//          req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
//       }else{
//           if(!userService.registerUser(user).equals(UserService.SUCCESS))
//       }
//            User user = new User();
//            user.setLogin(login);
//            user.setName(username);
//            user.setLastname(lastname);
//            user.setPassword(password);
//            user.setEmail(email);
//            user.setDateOfregistration(new Date());
//            // wykorzystano obiekt Date z java.util
//
//            // sprawdzamy czy istnieje dany login
//            if(userService.registerUser(user)) {
//                req.getRequestDispatcher("login.jsp").forward(req,resp);
//                // forwardem wstrzykuje do bazy
//                // chyba że jest źle to wali errorem i wycofuje jeszcze raz do rejestracji
//            }else{
//                req.setAttribute("hasError", "true");
//                req.setAttribute("error", "Typed login is already used in our database");
//                req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
//
//            }
//

            // poniżej sprawdzamy czy użytkownik jest unikalny metodą z UserService

 //       }
        String login = req.getParameter(LOGIN);
        String username = req.getParameter(USERNAME);
        String lastName = req.getParameter(LAST_NAME);
        String password = req.getParameter(PASSWORD);
        String passwordRepeated = req.getParameter(PASSWORD_REPEATED);
        String email = req.getParameter(EMAIL);

        if (!password.equals(passwordRepeated)) {
            req.setAttribute("hasError", "true");
            req.setAttribute("error", "Passwords does no match!");
            req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
        } else {
            User user = new User();
            user.setLogin(login);
            user.setName(username);
            user.setLastname(lastName);
            user.setPassword(password);
            user.setEmail(email);
            user.setDateOfRegistration(new Date());

            String registerStatus = userService.registerUser(user);


            if (!registerStatus.equals(UserService.SUCCESS)) {
                if (registerStatus.equals(UserService.EMAIL_AND_LOGIN_ERROR)) {
                    req.setAttribute("hasError", "true");
                    req.setAttribute("error", "Email  and Login are already used in our database!");
                    req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
                } else if (registerStatus.equals(UserService.EMAIL_ERROR)) {
                    req.setAttribute("hasError", "true");
                    req.setAttribute("error", "Email is already used in our database!");
                    req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
                } else if (registerStatus.equals(UserService.LOGIN_ERROR)) {
                    req.setAttribute("hasError", "true");
                    req.setAttribute("error", "Login is already used in our database!");
                    req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
                }
            }
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
