package dao;

import model.User;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends AbstractDAO {



    public List<User> list() {
        return entityManager.createQuery("select u from User u").getResultList();
    }
// originalnie było createUser, ale trzeba było zmienić na saveUser bo był konflikt
    public void saveUser(User user) {
        hibernateUtil.save(user);
    }

    public void deleteUser(long userId) {
        hibernateUtil.delete(User.class, userId);
    }

    // name, email,

    public List<User> getUserByName(String name) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT c FROM User c WHERE c.name = :name", User.class);
        return query.setParameter("name", name).getResultList();
    }

    public User getUserByEmail(String email) {
            TypedQuery<User> query = entityManager.createQuery(
                "SELECT c FROM User c WHERE c.email = :email", User.class);
        return query.setParameter("email", email).getSingleResult();
    }

    public User getUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT c FROM User c WHERE c.login = :login", User.class);
        return query.setParameter("login", login).getSingleResult();
    }

// metoda sprawdzająca czy user istnieje
    public boolean isUserExist(String login, String password) {
        // w tym celu query czy login i password ilość większa od zera, czyli istnieje!
        Query query = entityManager.createQuery("select count(*) as cnt from User u where u.login = :login and u.password = :password");
        // podać liczbę userów gdzie login równa się login
        query.setParameter("login", login);
        query.setParameter("password", password);
        Object singleResult = query.getSingleResult();
        return ((Long) singleResult > 0) ? true : false;
    }


    public List<User> getFollowedUsers(String followerLogin) {
        User user = getUserByLogin(followerLogin);
        Long userId = user.getId();
        Query query = entityManager.createQuery("select distinct follows from User u where u.id = :userId");
        return query.setParameter("userId", userId).getResultList();
    }

    public List<User> getNotFollowedUsers(String followerLogin) {
        Query query = entityManager.createQuery("select u from User u where u.login != :followerLogin");
        List users = query.setParameter("followerLogin", followerLogin).getResultList();
        List<User> followed = getFollowedUsers(followerLogin);
        User userByLogin = getUserByLogin(followerLogin);

        users.removeAll(followed);
        return users;
    }

    // metoda która dodaje śledzonego użytkownika do uzykowników śledzonych w zalogowanym użytkowniku

    public void follow (String userLogin, String userLoginToFollow){
        User currentUser = getUserByLogin(userLogin);
        User userToFollow = getUserByLogin(userLoginToFollow);
        currentUser.getFollows().add(userToFollow);
        saveUser(currentUser);
    }

    public void stopfollow (String userLogin, String userLoginToStopFollow){
        User currentUser = getUserByLogin(userLogin);
        User userToFollow = getUserByLogin(userLoginToStopFollow);
        currentUser.getFollows().remove(userToFollow);
        saveUser(currentUser);
    }



}