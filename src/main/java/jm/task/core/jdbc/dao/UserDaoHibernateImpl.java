package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {
    private static UserDaoHibernateImpl instance;

    private static final String SQL_TO_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user(" +
            "id BIGINT PRIMARY KEY  AUTO_INCREMENT, " +
            "name VARCHAR(50), " +
            "lastName VARCHAR(50), " +
            "age TINYINT);";
    private static final String SQL_TO_DROP_TABLE = "DROP TABLE IF EXISTS user";
    private static final String JPQL_GET_ALL_USERS = "SELECT a FROM User a";
    private static final String JPQL_DELETE_ALL_FROM_USER_TABLE = "DELETE FROM User";

    private UserDaoHibernateImpl() {
    }

    public static synchronized UserDaoHibernateImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoHibernateImpl();
        }
        return instance;
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createSQLQuery(SQL_TO_CREATE_USER_TABLE).executeUpdate();
                if (transaction != null) {
                    transaction.commit();
                }
            } catch (HibernateException ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createSQLQuery(SQL_TO_DROP_TABLE).executeUpdate();
                if (transaction != null) {
                    transaction.commit();
                }
            } catch (HibernateException ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(user);
                if (transaction != null) {
                    transaction.commit();
                }
            } catch (HibernateException ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.delete(user);
                if (transaction != null) {
                    transaction.commit();
                }
            } catch (HibernateException ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result;

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                result = session.createQuery(JPQL_GET_ALL_USERS, User.class).getResultList();
                if (transaction != null) {
                    transaction.commit();
                }
            } catch (HibernateException ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new IllegalStateException(ex);
            }
        }

        return result;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createQuery(JPQL_DELETE_ALL_FROM_USER_TABLE).executeUpdate();
                if (transaction != null) {
                    transaction.commit();
                }
            } catch (HibernateException ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new IllegalStateException(ex);
            }
        }
    }
}
