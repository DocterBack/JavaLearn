import static java.lang.System.out;

import java.sql.*;
import java.util.Scanner;


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

			System.out.println("Новый курс");
			Scanner sc = new Scanner(System.in);
			System.out.print("Название: ");
			String title = sc.nextLine();
			System.out.print("Длительность: ");
			int length = sc.nextInt();
			System.out.print("Описание: ");
			if (sc.hasNextLine()) sc.nextLine();
			String description = sc.nextLine();
			
			Connection conn = 
				DriverManager.getConnection(connString);
			try
			{
				
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				conn.setAutoCommit(false);
				
				
				Statement scmd = conn.createStatement();
				ResultSet r = 
						scmd.executeQuery("SELECT MAX(id) AS MAX_ID FROM Courses");
				
				int id = 1;
				if (r.next())
				{
					id = r.getInt("MAX_ID");
					id++;
				}
				
				//CallableStatement scmd = 
				//		conn.prepareCall("CALL GetCourseID(?)");
				
				//scmd.setInt(1, 0)
				//scmd.registerOutParameter(1, Types.INTEGER);
				
				/*
				ResultSet r = scmd.executeQuery();
				int id = 1;
				if (r.next())
				{
					id = r.getInt(1);
					id++;
				}*/
	
				//int id = scmd.getInt(1);
				//System.out.println(id);
				
				//if (scmd.wasNull())
				//	id = 1;
				//else
				//	id++;
				
				String sql = 
				"INSERT INTO Courses (title, length, description, id) VALUES (?,?,?,?)";
				PreparedStatement cmd=conn.prepareStatement(sql);
				cmd.setString(1, title);
				cmd.setInt(2, length);
				cmd.setString(3, description);
				cmd.setInt(4, id);
				
				if (cmd.executeUpdate() == 1)
				{
					conn.commit();
					System.out.println("Новый курс создан");
				}
				
				
			}
			catch(SQLException ex)
			{
				
				conn.rollback();
				System.out.println(ex.getMessage());
				
			}
			finally
			{
				conn.close();
			}
			

		}
}
