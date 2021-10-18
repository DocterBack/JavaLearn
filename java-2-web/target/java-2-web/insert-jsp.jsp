<%@ page import="java.sql.*" %>

<html>
<body>
<form action="insert-jsp.jsp" method="POST">
  <label for="first_name">First Name:</label><br>
  <input type="text" name="first_name"><br><br>
  <label for="last_name">Last Name:</label><br>
  <input type="text" name="last_name"><br><br>
  <input type="submit" value="Submit">
</form>
</br>
<%
    final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    final String LOGIN = "postgres";
    final String PASSWORD = "postgres";

    String name = request.getParameter("first_name");
    String lastName = request.getParameter("last_name");

    if (name == null || lastName == null)
      return;

    if (name.isEmpty() || lastName.isEmpty()){
      out.print("Wrong data");
      return;
    }

    Class.forName("org.postgresql.Driver");
    try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
        PreparedStatement statement = c.prepareStatement(
                "insert into authors (author_name, last_name) values (?, ?)");
        statement.setString(1, name);
        statement.setString(2, lastName);
        statement.executeUpdate();
    }
    out.print("Author created");
%>
</body>
</html>
