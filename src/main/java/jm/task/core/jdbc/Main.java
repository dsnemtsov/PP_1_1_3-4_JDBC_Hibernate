package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();

        service.createUsersTable();

        service.saveUser("name1", "lastName1", (byte) 13);
        System.out.println("User с именем - name1 добавлен в базу данных");
        service.saveUser("name2", "lastName2", (byte) 23);
        System.out.println("User с именем - name2 добавлен в базу данных");
        service.saveUser("name3", "lastName3", (byte) 33);
        System.out.println("User с именем - name3 добавлен в базу данных");
        service.saveUser("name4", "lastName4", (byte) 43);
        System.out.println("User с именем - name4 добавлен в базу данных");

        service.getAllUsers().forEach(System.out::println);

        service.cleanUsersTable();

        service.dropUsersTable();
    }
}
