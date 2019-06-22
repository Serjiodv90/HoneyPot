<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	
<link rel="stylesheet" type="text/css" href="/resources/static/stylesheet.css">

<script type="text/javascript" src="/resources/static/js/passwordCheck.js"></script>


<title>Insert title here</title>
</head>
<body>

	<img id="image1" class="img-responsive" src="/resources/static/Images/topAfeka.jpg" width="100%">
	
	<div style="float:right;">
	<form action="changePassword" method="Post" onsubmit="return validateReg()" dir="rtl">
		<label for="userName" class="control-label"><b>שם משתמש</b></label>
		
		<input id="userName" type="text" name="userName" required>
		
		<br>
		<label for="password" class="control-label"><b>הזן סיסמה</b></label>
		
		<input id="password" type="password" placeholder="Enter Password" name="password" required>
		<br>
		<label for="rptPassword" class="control-label"><b>הזן סיסמה שנית</b></label>
		<input id="rPass" type="password" placeholder="Repeat Password" name="rptPassword" required>
		
		
		<input type="submit" name="resetPassword" value="אפס סיסמה">
		<c:if test="${not empty error}">
		   Error: ${error}
		</c:if>
	</form>
	</div>

</body>
</html>