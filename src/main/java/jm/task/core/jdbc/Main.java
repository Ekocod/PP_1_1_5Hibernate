package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        try {
            //Создание таблицы User(ов)
            userDao.createUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Добавление 4 User(ов) в таблицу с данными
        userDao.saveUser("Name1", "LastName1", (byte) 20);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);

        //Получение всех User из базы и вывод в консоль
        System.out.println(userDao.getAllUsers());

        //Очистка таблицы User(ов)
        userDao.cleanUsersTable();

        //Удаление таблицы
        userDao.dropUsersTable();
    }
}
