package services;

import dao.UserDAO;
import model.User;

import javax.persistence.NoResultException;

public class UserService {
    UserDAO userDAO;

    // dodajemy constructor bez ciała ze względu na RegisterServlet??
    public UserService() {
        userDAO = new UserDAO();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    // metoda ktora sprawdza czy dodawany uzytkownik już jest w bazie danych
    public boolean registerUser(User user){
        // trzeba przeprowadzić validację
        if(isUserAlreadyExist(user.getEmail())) {
            return false;
        }
        userDAO.createUser(user);
        return true;
    }

    private boolean isUserAlreadyExist(String email) {
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

}
