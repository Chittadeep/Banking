import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;



public class Database {
	
	Connection con;
	
	Database()
	{
		try {
		Class.forName("com.mysql.jdbc.Driver");  
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Banking","root","");  
		
		}catch(Exception e)
		{ System.out.println(e);}
	}
	
	ResultSet signUp(String name, String password, Long number, String email, String gender, String birthdate) 
	{
		
		Random random = new Random();
		Long id = Math.abs(random.nextLong());
		LocalDateTime myObj = LocalDateTime.now();
				
		try
		{
			System.out.println("Inserting records into the table...");
			
			String sql = "INSERT INTO `Accounts`(`SERIAL`, `NAME`, `ID`, `PASSWORD`, `BALANCE`, `DATE`, `PHONE`, `BIRTHDATE`, `GENDER`, `EMAIL`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, name);
	        pstmt.setString(2, Long.toString(id));
	        pstmt.setString(3, password);
	        pstmt.setString(4, "0");
	        pstmt.setString(5, myObj.toString());
	        pstmt.setString(6, Long.toString(number));
	        pstmt.setString(7, birthdate);
	        pstmt.setString(8, gender);
	        pstmt.setString(9, email);
	        
	        pstmt.executeUpdate();
	        
	        ResultSet rs = signIn(name, Long.toString(number), password, id);
	        return rs;
	  
		}
		catch(Exception e)
		{
			System.out.print(e);
			return null;
		}
		
		
	}
		
	ResultSet signIn(String name, String phone, String password, Long number)
	{
		try {
		String sql = "SELECT * FROM `Accounts` WHERE NAME = ? AND PHONE = ? AND ID = ? AND PASSWORD= ?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, name);
        pstmt.setString(2, phone);
        pstmt.setString(3, Long.toString(number));
        pstmt.setString(4, password);

        ResultSet rs = pstmt.executeQuery();
        
        rs.first();
        
        return rs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	ResultSet getDeposits(Long number)
	{
		try
		{
			String sql ="SELECT * FROM `Deposits` where ID = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, Long.toString(number));	
			
			ResultSet rs = pstmt.executeQuery();
			
			return rs;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	ResultSet getWithdrawls(Long number)
	{
		try
		{
			String sql ="SELECT * FROM `Withdraw` where ID = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, Long.toString(number));	
			
			ResultSet rs = pstmt.executeQuery();
			
			
			return rs;
	        
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	
	void updatePassword(String value, String id)
	{
		try {
		String sql = "UPDATE Accounts "
                + "SET PASSWORD = ? "
                + "WHERE id = ?";
				
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, value);
        pstmt.setString(2, id);
        		
		pstmt.executeUpdate();
        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void updatePhone(String value, String id)
	{
		try {
		String sql = "UPDATE Accounts "
                + "SET PHONE = ? "
                + "WHERE id = ?";
				
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, value);
        pstmt.setString(2, id);
        		
		pstmt.executeUpdate();
        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void withdraw(String id, String balance, String amount, String to, String toID, String reason)
	{
		try {
		        
		String sql = "INSERT INTO `Withdraw` (`ID`, `AMOUNT`, `TO`, `TO ID`, `REASON`) VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt= con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, amount);
		pstmt.setString(3, to);
		if(toID=="") toID=null;
		pstmt.setString(4, toID);
		pstmt.setString(5, reason);
		pstmt.executeUpdate();
        
		updateBalance(id, balance);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void deposit(String id, String balance, String amount, String to, String toID, String reason)
	{
		try {
		        
		String sql = "INSERT INTO `Deposits` (`ID`, `AMOUNT`, `TO`, `TO ID`, `REASON`) VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt= con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, amount);
		pstmt.setString(3, to);
		if(toID=="") toID=null;
		pstmt.setString(4, toID);
		pstmt.setString(5, reason);
		pstmt.executeUpdate();
        
		updateBalance(id, balance);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void updateBalance(String id, String balance)
	{
		try {
		String sql = "UPDATE Accounts "
                + "SET BALANCE = ? "
                + "WHERE id = ?";

		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, balance);
        pstmt.setString(2, id);
        		
		pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void applyLoan(String id, String amount, String reason, String balance)
	{
		try {
			String sql = "INSERT INTO `Loan` (`ID`, `LOAN ID`, `AMOUNT`, `REASON`) VALUES (?, ?, ?, ?)";
			
			PreparedStatement pstmt= con.prepareStatement(sql);
			
			Random random = new Random();
			String loanId = Long.toString(Math.abs(random.nextLong()));
			
			pstmt.setString(1, id);
			pstmt.setString(2, loanId);
			pstmt.setString(3, amount);
			pstmt.setString(4, reason);
			pstmt.executeUpdate();
			
			deposit(id, balance, amount, "self", id, reason+" loan");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	ResultSet getLoans(String id)
	{			//System.out.println(id);
		try {
			
			String sql ="SELECT * FROM `Loan` WHERE ID = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			//System.out.println("get Loans");
			ResultSet rs = pstmt.executeQuery();
			
			return rs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}