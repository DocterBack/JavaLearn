<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html>
<body>
<%!
    String hello = "Hello World!";
    int a = 9;
%>
<h2><%= hello.toUpperCase() %></h2>

<%
	out.print("<h1>Hello</h1></br>");
	out.print("a = " + a + "</br>");
	out.print("Current time: " + LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")));

%>
</body>

</html>
