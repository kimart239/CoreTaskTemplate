package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS user (id BIGINT NOT NULL " +
            "PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(45)," +
            "lastName VARCHAR (80), age INT) ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS user";


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(CREATE_TABLE, User.class);
            query.executeUpdate();

            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(DROP_TABLE, User.class);
            query.executeUpdate();

            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            // save student object
            session.save(new User (name, lastName, age));

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        // auto close session object
        try (Session session = Util.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("Select i from User i", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {


    }
}
