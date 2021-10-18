package ru.specialist.java.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/insertAuthor")
public class InsertAuthorServlet extends HttpServlet {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String name = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");

        if (isEmpty(name) || isEmpty(lastName)){
            out.print("Wrong data");
            return;
        }

        try {
            insertAuthor(name, lastName);
//            out.print("Author created");
            resp.sendRedirect(req.getContextPath() + "/insert-servlet.jsp?authorCreated");

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            out.print("Something went wrong");
        }

    }

    private void insertAuthor(String name, String lastName) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
            PreparedStatement statement = c.prepareStatement(
                    "insert into authors (author_name, last_name) values (?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.executeUpdate();
        }
    }

    private boolean isEmpty(String s){
        return s == null || s.isEmpty();
    }
}
