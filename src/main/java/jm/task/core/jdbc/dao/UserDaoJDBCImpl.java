package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS `jdbc_db`.`users` (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NULL,
                      `age` INT(3) NOT NULL,
                      PRIMARY KEY (`id`))
                    ENGINE = InnoDB
                    DEFAULT CHARACTER SET = utf8""");
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            statement.execute("DROP TABLE `jdbc_db`.`users`");
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("INSERT INTO users (name, lastName, age) VALUES (? ,?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("SELECT * FROM users")) {
            ResultSet res =  preparedStatement.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastName"));
                user.setAge(res.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
    }
}
