<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	  	<form:form method="POST" action="submituserprofile" commandName="userProfileSetup" enctype="multipart/form-data">
		<table>
			<tr>
				<td>IMEI Number:</td>
				<td><form:input path="imeiNumber" name="imeiNumber"/></td>				
			</tr>
			<tr>
				<td>First Name:</td>
				<td><form:input path="firstName" name="firstName"/></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><form:input path="lastName" name="lastName"/></td>
			</tr>
			<tr>
				<td>Account Number:</td>
				<td><form:input path="accountNumber" name="accountNumber"/></td>
			</tr>
			<tr>
				<td>Image to upload:</td>
				<td> <input type="file" name="multiPart" ></td>
			</tr>				
			<tr>
				<td><input type="submit" name="submit" value="Submit"></td>
			</tr>
			<tr>
		</table>
	</form:form> 
</body>
</html>