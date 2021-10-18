package ru.specialist.web;

import static java.lang.System.out;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CourseListServlet
 */
@WebServlet("/CourseList")
public class CourseListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public String connString;
	public String sqlDriver;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public CourseListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
    	super.init(config);
    	sqlDriver = config.getServletContext().getInitParameter("sqlDriver");
    	connString = config.getServletContext().getInitParameter("connectionString");
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException
    {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String search = request.getParameter("search");
		search = (search == null)? "":search;
		search = "%"+search.trim()+"%";
		
		try
		{
			Class.forName(sqlDriver);
			
			Connection conn = 
				DriverManager.getConnection(connString);
			
			String sql = 
					"SELECT title, length,description FROM Courses WHERE title LIKE ? ORDER BY title";
			PreparedStatement cmd =  conn.prepareStatement(sql);
			cmd.setString(1,search);
			ResultSet result = cmd.executeQuery();

			showCourses(out, result);
			
			conn.close();
		}
		catch(Exception ex)
		{
			throw new ServletException(ex);
			
		}    
	}


	private void showCourses(PrintWriter out, ResultSet result)
			throws SQLException
	{
		out.println("<h1>Курсы</h1>");
		//out.println(sqlDriver);
		
		out.println("<table border=1>");
		while(result.next())
		{
			out.println("<tr>");
			String title = result.getString("title");
			int length = result.getInt("length");
			String description = result.getString("description");
			out.printf("<td>%s</td><td>%d</td><td>%s</td>",
					title, length, description);
			
			out.println("</tr>");
			
		}
		out.println("</table>");
		
		result.close();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		try
		{
			Class.forName(sqlDriver);
			
			Connection conn = 
				DriverManager.getConnection(connString);
			
			Statement cmd =  conn.createStatement();
			String sql = 
				"SELECT title, length,description FROM Courses ORDER BY title";
			ResultSet result = cmd.executeQuery(sql);
			showCourses(out, result);
			conn.close();
		}
		catch(Exception ex)
		{
			throw new ServletException(ex);
			
		}

	}

}
