package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String URL = "jdbc:mysql://localhost:3306/my_database";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "user_password";

    private static Connection connection;

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {



    }

    public void createUsersTable() {     //создать таблицу пользователей

        try {
            Statement statement = connection.createStatement();
            String SQL = "CREATE TABLE IF NOT EXISTS Users (id Int, name varchar(255),lastname varchar(255), age TINYINT)";

            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {       //   удалить таблицу пользователей

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DROP TABLE IF EXISTS Users");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    public void saveUser(String name, String lastName, byte age) {      //сохранить пользователя

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Users VALUES(1, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void removeUserById(long id) {          //удалить пользователя по идентификатору

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Users WHERE id=?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() {               //получить всех пользователей
        List<User> person = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                User user = new User();

                user.setId( resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                person.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;

    }

    public void cleanUsersTable() {          //очистить таблицу пользователей

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("TRUNCATE TABLE Users");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
