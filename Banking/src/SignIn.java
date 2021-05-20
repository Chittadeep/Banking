import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
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
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		Long number = Long.parseLong(request.getParameter("number"));
		
		Database database = new Database();
		RequestDispatcher rd = null;
		
			ResultSet rs =database.signIn(name, phone, password, number);
			ResultSet rsDeposits = database.getDeposits(number);
			ResultSet rsWithdrawls = database.getWithdrawls(number);
			ResultSet rsLoans = database.getLoans(Long.toString(number));
			//System.out.print("method called");

			try {
				if(rs.first()==false)
				{
					rd = request.getRequestDispatcher("falseSearch.html");
				}
				else {
					
					rd = request.getRequestDispatcher("Account.jsp");
					
				request.setAttribute("Name", rs.getString("Name"));
				request.setAttribute("Serial", rs.getInt("Serial"));
				request.setAttribute("ID", rs.getString("Id"));
				request.setAttribute("Date", rs.getString("Date"));
				request.setAttribute("balance", rs.getInt("balance"));
				request.setAttribute("phone", rs.getString("Phone"));
				request.setAttribute("password", rs.getString("Password"));
				//request.setAttribute("email", rs.getString("Email"));
				//request.setAttribute("birthdate", rs.getString("Birthdate"));
				//request.setAttribute("gender", rs.getString("Gender"));
				request.setAttribute("Deposits", rsDeposits);
				request.setAttribute("Withdrawls", rsWithdrawls);
				request.setAttribute("Loans", rsLoans);
				}
				
				/*while(rsDeposits.next())
				 {
					 System.out.println(rsDeposits.getString(7));
				 }
				 
				 while(rsWithdrawls.next())
					{
						System.out.println(rsWithdrawls.getString(7));
					}
				 
			
			
			//if(rs.first()!=false)*/
			
			}
			catch(Exception e)
			{
				e.getLocalizedMessage();
			}
			rd.forward(request, response);
	}

}
