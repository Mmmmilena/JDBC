package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {



    private final static String CREATE_USERS_TABLE_REQUEST = """
            CREATE TABLE IF NOT EXISTS Users (
                              id SERIAL PRIMARY KEY,
                              name varchar(128),
                              lastname varchar(128),
                              age INT );
            """;
    private final static String SAVE_USER_REQUEST = """
            INSERT INTO  Users (name, lastname,age) VALUES (?, ?, ?);
            """;
    private final static String GET_USERS_REQUEST = """
            SELECT id ,name , lastname, age FROM Users;
            """;
    private final static String DROP_USERS_TABLE_REQUEST = """
            DROP TABLE IF EXISTS User;
            """;
    private static final String DELETE_USER_REQUEST = """
            DELETE FROM User WHERE id = ?;
            """;
    private static final String SQL = """
            TRUNCATE TABLE Users;
    """;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_USERS_TABLE_REQUEST);
            System.out.println("Таблица User создана");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при создании таблицы Users :" + e.getMessage());

        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {;
            statement.execute(DROP_USERS_TABLE_REQUEST); //&&&
            System.out.println("Таблица User удалена");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при удалении таблицы Users :" + e.getMessage());

        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_REQUEST)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");


        } catch (SQLException e) {
            System.out.println("Произошла ошибка при создании таблицы users :" + e.getMessage());

        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_REQUEST);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("User с индификатором " + id + "удален из таблицы");

        } catch (SQLException e) {
            System.out.println("Произошла ошибка при удалении пользователя:" + e.getMessage());

        }

    }

    public List<User> getAllUsers() {

        try (Connection connection = Util.getConnection()) {
            ResultSet resultSet = connection.prepareStatement(GET_USERS_REQUEST).executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                userList.add(new User(name, lastname, age));
            }
            return userList;


        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении всех данных из таблицы users :" + e.getMessage());
            return null;

        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {;
            statement.execute(SQL);
            System.out.println("Таблица User очищена");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при очистке таблицы Users :" + e.getMessage());

        }

    }
}
