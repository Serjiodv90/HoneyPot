<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Insert title here</title>
</head>
<body>
<table id="main_table" bgcolor="white">
<tbody><tr>
     <td id="main_table_info_cell">
		
    <form id="auth_form" name="e1" method="post" onsubmit="javascript: return masterSubmit(this);" autocomplete="off">
    <table id="credentials_table">
    <tbody><tr>
        <td colspan="2" id="credentials_table_header"><img src="data:image/png;base64,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" ;<="" td="">
    </td></tr>
    <tr>
        <td colspan="2" id="credentials_table_postheader"></td>
    </tr>
  
    <tr>
        <td class="apm-credentials_table_label_cell"><label for="input_1" id="label_input_1">׳©׳� ׳�׳©׳×׳�׳©</label></td>
        <td class="apm-credentials_table_field_cell"><input type="text" name="username" class="credentials_input_text" value="" id="input_1" autocomplete="off" autocapitalize="off"></td>
    </tr>
    <tr>
        <td class="apm-credentials_table_label_cell"><label for="input_2" id="label_input_2">׳¡׳™׳¡׳�׳”</label></td>
        <td class="apm-credentials_table_field_cell"><input type="password" name="password" class="credentials_input_password" value="" id="input_2" autocomplete="off" autocapitalize="off"></td>
    </tr>
    <tr>
        <td class="apm-credentials_table_label_cell"><label for="input_3" id="label_input_3">׳©׳™׳•׳�</label></td>
        <td class="apm-credentials_table_field_cell"><div class="radio-div"><input type="radio" value="ACADEMIC" name="Domain" id="input_3_0"><label for="input_3_0" id="label_input_3_0" class="radio-label" style="display: inline">׳¡׳˜׳•׳“׳ ׳˜׳™׳�</label></div><div class="radio-div"><input type="radio" value="MGMT" name="Domain" id="input_3_1"><label for="input_3_1" id="label_input_3_1" class="radio-label" style="display: inline">׳¡׳’׳�</label></div><div class="radio-div"><input type="radio" value="ACADEMIC_Bogrim" name="Domain" id="input_3_2"><label for="input_3_2" id="label_input_3_2" class="radio-label" style="display: inline">׳‘׳•׳’׳¨׳™׳�</label></div></td>
    </tr>
    <tr id="submit_row">
        <td colspan="2" align="center"><input type="submit" class="submit_button submit_button:hover" value="׳›׳ ׳™׳¡׳”"></td>
    </tr>
     <tr>
        <td colspan="2" id="credentials_table_header"><font size="2"> ׳©׳›׳—׳× ׳¡׳™׳¡׳�׳”? <a href="https://forgotpassword.afeka.ac.il/FreePass/"> ׳�׳—׳¥ ׳›׳�׳� </a>
    </font></td></tr>
    </tbody></table>
    <input type="hidden" name="vhost" value="standard">
    </form>
	

    
    </td>
	
   
</tr>
</tbody></table>
</body>
</html>