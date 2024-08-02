package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userr = new UserDaoJDBCImpl();
        userr.createUsersTable();
        userr.saveUser("Ivan", "Ogr", (byte) 33);
        List<User> list = userr.getAllUsers();
        System.out.println();
    }
}
