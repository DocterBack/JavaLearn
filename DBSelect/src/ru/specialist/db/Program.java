package ru.specialist.db;

import java.sql.*;
import java.util.Scanner;

import static java.lang.System.out;

public class Program
{
	
	public static final String connString = 
		"jdbc:mysql://localhost:3306/web?user=root&password=demo&characterEncoding=utf-8";
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) 
			throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection conn = 
			DriverManager.getConnection(connString);
		
		System.out.print("Поиск: ");
		Scanner sc = new Scanner(System.in);
		String search = sc.nextLine();
		
		//System.out.println(search);
		
		/*// BAD
		Statement cmd =  conn.createStatement();
		String sql = 
			"SELECT title, length FROM Courses WHERE title LIKE '%"+
					search.trim()+"%' ORDER BY title";
		ResultSet result = cmd.executeQuery(sql);
		*/
		
		String sql = 
		"SELECT title, length FROM Courses WHERE title LIKE ? ORDER BY title";
		PreparedStatement cmd=conn.prepareStatement(sql);
		cmd.setString(1, "%"+search.trim()+"%");
		
		ResultSet result = cmd.executeQuery();
		//cmd.executeUpdate()
		//cmd.exe
		
		while(result.next())
		{
			String title = result.getString("title");
			
			
			int length = result.getInt("length");
			if (result.wasNull())
				out.println(title);
			else
				out.printf("%-50s %d\n", title, length);
		}
		
		result.close();
		
		
		conn.close();

	}

}
