package hibernate.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static HibernateUtil instance;

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static HibernateUtil getInstance() {
        if (null == instance) {                  // szybsze niż instance == null, a więc dobra praktyka
            instance = new HibernateUtil();
        }
        return instance;
    }

    public void saveByHibernateSession(Object t) { //saveByHibernateSession powstał w kontekście dużych plików, przy użyciu entitymenagera
        // otworzyć sesję, stworzyć transakcję, wywołanie commitu zmiany na koniec
        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        session.close(); // <- zawsze
    }

    public void save(Object t) { // save realizowany przez persistance
        entityManager.getTransaction().begin();
        if (!entityManager.contains(t)) {
            entityManager.persist(t);
            entityManager.flush();
        }
        entityManager.getTransaction().commit();
    }

    public void delete(Class clazz, Long objectId) {
        entityManager.getTransaction().begin();
        Object toRemove = entityManager.find(clazz, objectId);
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
