<%@page import="com.Bill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Service</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/Bill.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Bill Service</h1>

				<form id="formBill" name="formBill" method="post" action="Bill.jsp">


					Bill Name: <input id="bname" name="bname" type="text"
						class="form-control form-control-sm"> 
						
						<br> Description: <input id="bdes" name="bdes" type="text"
						class="form-control form-control-sm"> 
						
						<br>  Price: <input id="price" name="price" type="text"
						class="form-control form-control-sm"> 
						
						<br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidProjectIDSave" name="hidProjectIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divProjectGrid">
					<%
					Bill projectObj = new Bill();
						out.print(projectObj.readProject());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
