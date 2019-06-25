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


<title>Reset Password</title>
</head>
<body>

<div style="height:50%; position: fixed;">
	<img id="image1" class="img-responsive" src="/resources/static/Images/topAfeka.jpg" width="100%">
</div>
	
	<div class="login-page">
		<table id="main_table">
    		<tr>
            <td id="main_table_info_cell">
				<form action="changePassword" method="Post" onsubmit="return validateReg()" dir="rtl">
	 				<table id="credentials_table">
                    	<tr>
                        	<td colspan=2 id="credentials_table_header"><img src="/resources/static/Images/afeka_logo.png"></td>
                        </tr>
                        <tr>
                            <td colspan=2 id="credentials_table_postheader"></td>
                        </tr>
                        <tr>
                            <td class="apm-credentials_table_label_cell"><label for='userName' id='label_input_1'>שם
                                        משתמש</label></td>
                            <td class="apm-credentials_table_field_cell"><input type='text' name='userName'
                                        class='credentials_input_text' value='' id='userName' autocomplete='off'/></td>
                        </tr>
                        <tr>
                            <td class="apm-credentials_table_label_cell"><label for='password'
                                        id='label_input_2'>הזן סיסמה חדשה</label></td>
                            <td class="apm-credentials_table_field_cell"><input type='password' name='password'
                                        class='credentials_input_password' value='' id='password' autocomplete='off'/></td>
                        </tr>
                        <tr>
                            <td class="apm-credentials_table_label_cell"><label for='rptPassword'
                                        id='label_input_2'>הזן סיסמה חדשה שוב</label></td>
                            <td class="apm-credentials_table_field_cell"><input type='password' name='rptPassword'
                                        class='credentials_input_password' value='' id='rPass' autocomplete='off'/></td>
                        </tr>
                        <tr>
                        <td colspan="2">
                        <div class="errorField rptPwdError alert alert-danger"></div>
                        </td>
                        </tr>
                        <tr id="submit_row">
                            <td colspan="2" align="center"><input type=submit name="resetPassword"
                                        class="submit_button submit_button:hover" value="אפס סיסמה"></td>
                        </tr>
                        
                        <tr>
                        	<td colspan="2">
                        			<div id="myDIV" style="float:left; margin:10px; font-size: 15px; color:red;">
                        		<c:if test="${not empty error}">
						  				${error}
								</c:if>
                        			</div>
                        
                        	</td>
                        </tr>
                    </table>
		<!-- <label for="userName" class="control-label"><b>שם משתמש</b></label>
		
		<input id="userName" type="text" name="userName" required>
		
		<br>
		<label for="password" class="control-label"><b>הזן סיסמה</b></label>
		
		<input id="password" type="password" placeholder="Enter Password" name="password" required>
		<br>
		<label for="rptPassword" class="control-label"><b>הזן סיסמה שנית</b></label>
		<input id="rPass" type="password" placeholder="Repeat Password" name="rptPassword" required> 
		
		
		<input type="submit" name="resetPassword" value="אפס סיסמה">
		<c:if test="${not empty error}">
		  ${error}
		</c:if>-->
				</form>
			</td>
			</tr>
		</table>
	</div>
	
	
<!--  	 <div class="login-page">

        <table id="main_table">
            <tr>
                <td id="main_table_info_cell">
                <!--  ${SPRING_SECURITY_LAST_EXCEPTION.message}-->
                    <!-- <form action="login" method="post">
                        <table id="credentials_table">
                            <tr>
                                <td colspan=2 id="credentials_table_header"><img src="/resources/static/Images/afeka_logo.png"></td>
                            </tr>
                            <tr>
                                <td colspan=2 id="credentials_table_postheader"></td>
                            </tr>
                            <tr>
                                <td class="apm-credentials_table_label_cell"><label for='input_1' id='label_input_1'>שם
                                        משתמש</label></td>
                                <td class="apm-credentials_table_field_cell"><input type='text' name='username'
                                        class='credentials_input_text' value='' id='input_1' autocomplete='off'/></td>
                            </tr>
                            <tr>
                                <td class="apm-credentials_table_label_cell"><label for='input_2'
                                        id='label_input_2'>סיסמה</label></td>
                                <td class="apm-credentials_table_field_cell"><input type='password' name='password'
                                        class='credentials_input_password' value='' id='input_2' autocomplete='off'/></td>
                            </tr>
                            <tr>
                                <td class="apm-credentials_table_label_cell"><label for='input_3'
                                        id='label_input_3'>שיוך</label></td>
                                <td class="apm-credentials_table_field_cell">
                                    <div class='radio-div'><input type='radio' value="ACADEMIC" name='Domain'
                                            id='input_3_0' checked /><label for='input_3_0' id='label_input_3_0'
                                            class='radio-label' style='display: inline'>סטודנטים</label></div>
                                    <div class='radio-div'><input type='radio' value="MGMT" name='Domain'
                                            id='input_3_1' /><label for='input_3_1' id='label_input_3_1'
                                            class='radio-label' style='display: inline'>סגל</label></div>
                                    <div class='radio-div'><input type='radio' value="ACADEMIC_Bogrim" name='Domain'
                                            id='input_3_2' /><label for='input_3_2' id='label_input_3_2'
                                            class='radio-label' style='display: inline'>בוגרים</label></div>
                                </td>
                            </tr>
                            <tr id="submit_row">
                                <td colspan="2" align="center"><input type=submit
                                        class="submit_button submit_button:hover" value="כניסה"></td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
    </div>-->

</body>
</html>