<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>MAR details</title>

<h2>MAR details</h2>

<table>

<tr>
<th>ID</th>
<td><c:out value="${mar.id}"></c:out></td>
</tr>

<tr>
<th>Facility Name</th>
<td><c:out value="${mar.facilityName}"></c:out></td>
</tr>

<tr>
<th>Description</th>
<td><c:out value="${mar.description}"></c:out></td>
</tr>

<tr>
<th>Date of creation</th>
<td><c:out value="${mar.date}"></c:out></td>
</tr>

<tr>
<th>Urgency</th>
<td><c:out value="${assign.urgency}"></c:out></td>
</tr>

<tr>
<th>Assigned to</th>
<td><c:out value="${assign.assignedTo}"></c:out></td>
</tr>

</table>

<input name="assign_success"  value="<c:out value='${success_message}'/>" type="text"  style ="background-color: white; color: blue; border: none; width: 800px" disabled="disabled">
