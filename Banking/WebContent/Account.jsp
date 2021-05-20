<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Account</title>
</head>
<body>

Serial no.<%=request.getAttribute("Serial") %>
<br>


<h2><%=request.getAttribute("Name") %></h2>

Account created on:<%=request.getAttribute("Date") %>
<br>

<script type="text/javascript">
function Withdraw()
{
	balance = parseInt(document.getElementById("balance").value);
	amount = parseInt(document.getElementById("amount").value);
	if(amount>balance){
		alert("Insufficient Balance");
		return false;
	}
	document.getElementById("balance").value=balance-amount;
}
function Deposit()
{
	balance = parseInt(document.getElementById("balance").value);
	amount = parseInt(document.getElementById("amount").value);
	
	document.getElementById("balance").value=balance+amount;
}
function show()
{
	var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    alert(selectedValue);
}
</script>

<form name="UpdateForm" method = "post" action="Account">

<label for="number">account number</label>
<input type = "text" id = number name = "number" readonly="readonly" value=<%=request.getAttribute("ID") %> required style="border:none;">

<br>
<label for="name">name</label>
<input type = "text" id = name name = "name" value=<%=request.getAttribute("Name") %> required>
<br>
<label for="password">password</label>
<input type = "text" id = password name = "password" value=<%=request.getAttribute("password") %> required>
<input type = "submit" id = updatePassword name = "action" value = "updatePassword" required>

<br>
<label for="phone">phone</label>
<input type = "tel" id = phone name="phone" value=<%=request.getAttribute("phone")%> required>
<input type = "submit" id = updatePhone name="action" value = "updatePhone" required>
<br>


<input type="submit" id=updateEmail name="action" value="updateEmail">

<br>
<label for="balance">balance</label>
<input type = "text" id = balance name="balance" readonly="readonly" value=<%=request.getAttribute("balance") %> required style="border:none;">
<br>
<input type = "submit" id = withdraw name = "action" value = "withdraw" onClick="return Withdraw()" required>
<label for="amount">amount</label>
<input type = "number" id = amount name ="amount" required>
<select id="to" name="to" onClick = "return show()">
  <option value="Self">Self</option>
  <option value="otherAccount" >Other Account</option>
</select>
<label for="toID">toID</label>
<input type="number" id = "toID" name = "toID"  >
<label for="reason">reason</label>
<input type="text" id ="reason" name="reason" required>
<input type = "submit" id = deposit name = "action" value = "deposit" onClick="return Deposit()">
<input type="submit" id = loan name = "action" value ="loan"> 
</form>
<br>

<%@page import="java.sql.*"%>

<table>

<%
try{
	ResultSet rsDeposits = (ResultSet)request.getAttribute("Deposits");
	if(rsDeposits.first()==true)
	{
		rsDeposits.beforeFirst();
		
		%><tr><th>Serial</th><th>Amount</th><th>To</th><th>To Id</th><th>Time</th><th>Reason</th></tr><%
		
	while(rsDeposits.next())
	{
		%>
		<tr><td><%=rsDeposits.getString(1)%></td><td><%=rsDeposits.getString(3)%></td><td><%=rsDeposits.getString(4)%></td><td><%=rsDeposits.getString(5)%></td>
		<td><%=rsDeposits.getString(6)%></td><td><%=rsDeposits.getString(7)%></td></tr>
		<%
	}
	}
}catch(Exception e){}
%>
</table>

<table>

<%
try{
	ResultSet rsWithdrawls = (ResultSet)request.getAttribute("Withdrawls");
	if(rsWithdrawls.first()==true)
	{
		rsWithdrawls.beforeFirst();
		
		%><tr><th>Serial</th><th>Amount</th><th>To</th><th>To Id</th><th>Time</th><th>Reason</th></tr><%
		
	while(rsWithdrawls.next())
	{
		%><tr><td><%=rsWithdrawls.getString(1)%></td><td><%=rsWithdrawls.getString(3)%></td><td><%=rsWithdrawls.getString(4)%></td><td><%=rsWithdrawls.getString(5)%></td>
		<td><%=rsWithdrawls.getString(6)%></td><td><%=rsWithdrawls.getString(7)%></td></tr>
		<%
	}
	}
}catch(Exception e){}
%>
</table>


<table>
<%
try{
	ResultSet rsLoans = (ResultSet)request.getAttribute("Loans");
	if(rsLoans.first()==true)
	{
		rsLoans.beforeFirst();
		%><tr><th>Serial</th><th>Loan Id</th><th>Amount</th><th>Reason</th><th>Time</th></tr><%
	}else System.out.print("No loan found");
	
	while(rsLoans.next())
	{
		%><tr><td><%=rsLoans.getString(1)%></td><td><%=rsLoans.getString(3)%></td><td><%=rsLoans.getString(4)%></td><td><%=rsLoans.getString(5)%></td>
		<td><%=rsLoans.getString(6)%></td></tr>
		<%
	}
	
}catch(Exception e){}
%>
</table>

<button onclick="location.href = 'signIn.html';" >Log Out</button>

</body>
</html>