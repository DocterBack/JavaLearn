<%@ page import="java.sql.*" %>
<%@ page import="ru.specialist.java.web.InsertAuthor" %>

<html>
<body>
<form action="insert-java.jsp" method="POST">
  <label for="first_name">First Name:</label><br>
  <input type="text" name="first_name"><br><br>
  <label for="last_name">Last Name:</label><br>
  <input type="text" name="last_name"><br><br>
  <input type="submit" value="Submit">
</form>
</br>
<%
    String name = request.getParameter("first_name");
    String lastName = request.getParameter("last_name");

    if (name == null || lastName == null)
      return;

    if (InsertAuthor.insertAuthor(name, lastName))
        out.print("Author created");
    else
        out.print("Wrong data");
%>
</body>
</html>
