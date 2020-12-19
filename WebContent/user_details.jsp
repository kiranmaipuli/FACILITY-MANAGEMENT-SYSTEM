<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Details</title>
</head>
    
<body>

<h1>User Details</h1>
<input name="message" value="<c:out value='${success_message}'/>" type="text" style ="background-color: white; color: blue; border: none; width: 800px; size: 30"  disabled="disabled" maxlength="30"> 
<table>
  <tr>
   <td>
         <table border="1" class="myTable"> 
    <tr>
    <td> username </td>
    <td> <c:out value="${USERS.username}" /> </td>
    </tr>

    <tr>
    <td> Role: </td>
    <td> <c:out value="${USERS.role}"/> </td>
    </tr>

    <tr>
    <td> Phone: </td>
    <td> <c:out value="${USERS.phone}" /> </td>
    </tr>

    <tr>
    <td> Email: </td>
    <td> <c:out value="${USERS.email}" /> </td>
    </tr>

	<tr>
    <td> UTAID </td>
    <td> <c:out value="${USERS.utaId}" /> </td>
    </tr>

    <tr>
    </tr>
    </table>
</td>
</tr>
</table>
</body>
</html>