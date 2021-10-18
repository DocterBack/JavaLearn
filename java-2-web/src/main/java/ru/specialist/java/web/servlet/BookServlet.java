package ru.specialist.java.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class BookServlet extends HttpServlet {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String STYLE = "<style>\n" +
            "table {\n" +
            "  font-family: arial, sans-serif;\n" +
            "  border-collapse: collapse;\n" +
            "  width: 100%;\n" +
            "}\n" +
            "\n" +
            "td, th {\n" +
            "  border: 1px solid #dddddd;\n" +
            "  text-align: left;\n" +
            "  padding: 8px;\n" +
            "}\n" +
            "\n" +
            "tr:nth-child(even) {\n" +
            "  background-color: #dddddd;\n" +
            "}\n" +
            "</style>";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
            Statement statement = c.createStatement();
            ResultSet set = statement.executeQuery("select b.book_id, b.title, a.author_name, a.last_name " +
                    "from books b " +
                    "join authors a on a.author_id = b.author_id");
            out.print(STYLE);
            out.print("<table>");
            out.print("  <tr>\n" +
                    "    <th>ID</th>\n" +
                    "    <th>Title</th>\n" +
                    "    <th>Author</th>\n" +
                    "  </tr>");
            while (set.next()){
                out.printf("  <tr>\n" +
                        "    <th>%d</th>\n" +
                        "    <th>%s</th>\n" +
                        "    <th>%s</th>\n" +
                        "  </tr>", set.getInt(1),
                        set.getString(2),
                        set.getString(3) + " " + set.getString(4));
            }
            out.print("</table>");
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
