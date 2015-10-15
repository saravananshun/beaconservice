<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>


<head>
<meta charset="UTF-8">
<title>User Profile Setup</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<style type="text/css">
.bs-example {
	margin-left: 80px;
	margin-top: 20px;
}
</style>
</head>

<body>
	<div class="container bs-example">
		<div class="row">
		  <h3>User Profile Setup</h3>
		</div>
		<form:form method="POST" action="submituserprofile"
			commandName="userProfileSetup" enctype="multipart/form-data">
			<div class="row">
				<div class="form-group col-xs-5">
					<label for="imeiNumber">IMEI Number:</label>
					<form:input path="imeiNumber" name="imeiNumber" id="imeiNumber"
						class="form-control" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-5">
					<label for="firstName">First Name:</label>
					<form:input path="firstName" name="firstName" id="firstName"
						class="form-control" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-5">
					<label for="lastName">Last Name</label>
					<form:input path="lastName" name="lastName" id="lastName"
						class="form-control" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-5">
					<label for="accountNumber">Account Number:</label>
					<form:input path="accountNumber" name="accountNumber"
						id="accountNumber" class="form-control" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-5">
					<label for="multiPart">Image to upload:</label> <input type="file"
						name="multiPart" id="multiPart" class="form-control" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-xs-5">
					<input type="submit" name="submit" value="Submit"
						class="btn btn-primary" />
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>