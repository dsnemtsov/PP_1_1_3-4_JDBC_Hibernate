package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

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
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(SQL_TO_CREATE_USER_TABLE).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(SQL_TO_DROP_TABLE).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User();
        user.setId(id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> cq = cb.createQuery(User.class);
//        Root<User> rootEntry = cq.from(User.class);
//        CriteriaQuery<User> all = cq.select(rootEntry);

        List<User> result = session.createQuery(JPQL_GET_ALL_USERS, User.class).getResultList();

        session.close();

        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery(JPQL_DELETE_ALL_FROM_USER_TABLE).executeUpdate();

        session.close();
    }
}
