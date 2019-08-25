package dao;

import model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends AbstractDAO {



    public List<User> list() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    public void createUser(User user) {
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
}