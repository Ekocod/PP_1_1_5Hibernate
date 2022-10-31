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
        String sqlCreate = """
                    CREATE TABLE `new_db`.`users` (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NULL,
                      `age` INT(3) NOT NULL,
                      PRIMARY KEY (`id`))
                    ENGINE = InnoDB
                    DEFAULT CHARACTER SET = utf8""";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute(sqlCreate);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE `new_db`.`users`";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute(sqlDrop);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement;
        String sqlAdd = "INSERT INTO users (name, lastName, age) VALUES (? ,?, ?)";
        try {
            preparedStatement = Util.getConnection().prepareStatement(sqlAdd);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement;
        String sqlRemoveById = "DELETE FROM users WHERE id = ?";
        try {
            preparedStatement = Util.getConnection().prepareStatement(sqlRemoveById);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        PreparedStatement preparedStatement;
        String sqlGetAll = "SELECT * FROM users";
        try {
            preparedStatement = Util.getConnection().prepareStatement(sqlGetAll);
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
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE users";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute(sqlClean);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
