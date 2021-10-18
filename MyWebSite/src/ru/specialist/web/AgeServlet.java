package ru.specialist.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AgeServlet
 */
@WebServlet("/Age")
public class AgeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		
		PrintWriter out = response.getWriter();
		
		
		try
		{
			String strDate = request.getParameter("birthDate");
			
			Locale rLocale = Locale.getDefault();
			
			String langs = request.getHeader("Accept-Language");
			if (langs != null)
			{
				String[] ls = langs.split(",");
				if (ls.length != 0)
				{
					
					//out.println(ls[0]);
					rLocale = Locale.forLanguageTag(ls[0]);
							
				}
				
						
			}
			
			Calendar now = 
				Calendar.getInstance(rLocale);
			
			Calendar birthCurrent = 
				Calendar.getInstance(rLocale);
			
			DateFormat f = DateFormat.getDateInstance(
					DateFormat.SHORT, rLocale);
			f.setCalendar(birthCurrent);
			
			try
			{
				f.parse(strDate);
			}
			catch(ParseException ex)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				birthCurrent.setTime(sdf.parse(strDate));
			}
			
			int birthYear = birthCurrent.get(Calendar.YEAR);
			
			birthCurrent.set(Calendar.YEAR, 
					now.get(Calendar.YEAR));
			
			
			int age = birthCurrent.get(Calendar.YEAR)-birthYear;
			
			if (birthCurrent.after(now))
				age--;
			
			out.printf("<h1>Возраст: %d</h1>", age);
		}
		catch(Exception ex)
		{
			throw new ServletException(ex);
			//response.sendRedirect("birthDate.html");
			
		}
		
		
		
	}

}
