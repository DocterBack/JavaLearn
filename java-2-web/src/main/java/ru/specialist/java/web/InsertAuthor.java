package ru.specialist.java.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertAuthor {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    public static boolean insertAuthor(String name, String lastName) throws SQLException, ClassNotFoundException {
        if (name.isEmpty() || lastName.isEmpty()){
            return false;
        }

        Class.forName("org.postgresql.Driver");
        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
            PreparedStatement statement = c.prepareStatement(
                    "insert into authors (author_name, last_name) values (?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.executeUpdate();
        }
        return true;
    }

}
