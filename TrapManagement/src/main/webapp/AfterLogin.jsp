<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Insert title here</title>
</head>
<body>
		<img id="image1" class="img-responsive" src="Images/topAfeka.jpg" width="100%">
 		
 		<form name="AddForm" method="Get" action="Home">
  	  		<input type="submit" style="float:right" name="press" value="Add/Delete Teacher"><br>
		</form>
		<form method="Get" action="Home">
  	  		<input type="submit" style="float:right" name="press" value="Students"><br>
		</form>
		<form method="Get" action="Home">
  	  		<input type="submit" style="float:right" name="press" value="Finance Department"><br>
		</form>
		
		<form method="Get" action="Home">
			<input type="hidden" name="press" value="ADD">
			<a href="/Home">Add/Delete Teacher</a>
		</form>

</body>
</html>