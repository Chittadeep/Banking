
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("Firstname")+ " " +request.getParameter("lastName");
		String password = request.getParameter("password");
		Long phone = Long.parseLong(request.getParameter("phone"));
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String birthdate = request.getParameter("birthdate");
		
		Database database = new Database();
		
		ResultSet rs = database.signUp(name, password, phone, email, gender, birthdate);
				
		RequestDispatcher rd = request.getRequestDispatcher("Account.jsp");
		
		try {
			request.setAttribute("Name", rs.getString("Name"));
			request.setAttribute("Serial", rs.getInt("Serial"));
			request.setAttribute("ID", rs.getString("Id"));
			request.setAttribute("Date", rs.getString("Date"));
			request.setAttribute("balance", rs.getInt("balance"));
			request.setAttribute("phone", rs.getString("Phone"));
			request.setAttribute("password", rs.getString("Password"));
			request.setAttribute("email", rs.getString("email"));
			request.setAttribute("gender", rs.getString("gender"));
			request.setAttribute("birthdate", rs.getString("birthdate"));
			}
			catch(Exception e)
			{
				e.getLocalizedMessage();
			}
		
		rd.forward(request, response);
		
	}

}