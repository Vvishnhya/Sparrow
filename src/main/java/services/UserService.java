package services;

import dao.UserDAO;
import model.User;

import javax.persistence.NoResultException;

public class UserService {

    public static final String EMAIL_ERROR = "emailError";
    public static final String LOGIN_ERROR = "loginError";
    public static final String SUCCESS = "success";
    public static final String EMAIL_AND_LOGIN_ERROR = "emailAndLoginError";

    UserDAO userDAO;

    // dodajemy constructor bez ciała ze względu na RegisterServlet??
    public UserService() {
        userDAO = new UserDAO();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    // metoda ktora sprawdza czy dodawany uzytkownik już jest w bazie danych
    public String registerUser(User user){
        if(isUserEmailAlreadyExist(user.getEmail())&&isLoginAlreadyUsed(user.getLogin())){
            return EMAIL_AND_LOGIN_ERROR;
        }
        if(isUserEmailAlreadyExist(user.getEmail())) {
            return EMAIL_ERROR;
        } else if (isLoginAlreadyUsed(user.getLogin())){
            return LOGIN_ERROR;
        }
        userDAO.saveUser(user);
        return SUCCESS;
    }

    private boolean isUserEmailAlreadyExist(String email) {
        // private bo będzie używana tylko w tym servisie, w tym celu wykorzystamy wcześniej zrobioną metodę
        // getUserByEmail z UserDAO
        // jeśli metoda nie jest voidowa "nie może" zwracać nulla w tym celu łapie sie exceptiony...
        try {
            userDAO.getUserByEmail(email);
            return true;
        }catch(NoResultException e){
            return false;
        }
    }

    private boolean isLoginAlreadyUsed(String login) {
        // private bo będzie używana tylko w tym servisie, w tym celu wykorzystamy wcześniej zrobioną metodę
        // getUserByEmail z UserDAO
        // jeśli metoda nie jest voidowa "nie może" zwracać nulla w tym celu łapie sie exceptiony...
        try {
            userDAO.getUserByLogin(login);
            return true;
        }catch(NoResultException e){
            return false;
        }
    }

}
