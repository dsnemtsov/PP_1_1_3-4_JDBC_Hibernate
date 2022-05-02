package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        UserDaoHibernateImpl.getInstance().createUsersTable();
    }

    public void dropUsersTable() {
        UserDaoHibernateImpl.getInstance().dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        UserDaoHibernateImpl.getInstance().saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        UserDaoHibernateImpl.getInstance().removeUserById(id);
    }

    public List<User> getAllUsers() {
        return UserDaoHibernateImpl.getInstance().getAllUsers();
    }

    public void cleanUsersTable() {
        UserDaoHibernateImpl.getInstance().cleanUsersTable();
    }
}
