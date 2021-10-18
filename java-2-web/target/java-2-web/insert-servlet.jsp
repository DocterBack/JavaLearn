<%@ page import="java.sql.*" %>
<%@ page import="ru.specialist.java.web.InsertAuthor" %>

<html>
<body>
<form action="insertAuthor" method="POST">
  <label for="first_name">First Name:</label><br>
  <input type="text" name="first_name"><br><br>
  <label for="last_name">Last Name:</label><br>
  <input type="text" name="last_name"><br><br>
  <input type="submit" value="Submit">
</form>
</br>
<%=request.getParameter("authorCreated") == null ? "" : "Author created"%>
</body>
</html>
