package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Katya","Ivanova",(byte) 20);
        userService.saveUser("Sergey", "Sergeev",(byte) 25);
        userService.saveUser("Peter","Petrov",(byte) 23);
        userService.saveUser("Ivan","Ivanov",(byte) 22);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
