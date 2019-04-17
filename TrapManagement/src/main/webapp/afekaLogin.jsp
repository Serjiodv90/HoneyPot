<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>

<head>
    <!--<meta charset="windows-1255">-->
    <meta charset="utf-8" />
    <title>Afeka Login</title>
    <link rel="stylesheet" href="stylesheet.css">
</head>

<body>

    <div class="login-page">

        <table id="main_table">
            <tr>
                <td id="main_table_info_cell">
                    <form action="login" method="post">
                        <table id="credentials_table">
                            <tr>
                                <td colspan=2 id="credentials_table_header"><img src="Images/afeka_logo.png"></td>
                            </tr>
                            <tr>
                                <td colspan=2 id="credentials_table_postheader"></td>
                            </tr>
                            <tr>
                                <td class="apm-credentials_table_label_cell"><label for='input_1' id='label_input_1'>��
                                        �����</label></td>
                                <td class="apm-credentials_table_field_cell"><input type='text' name='userName'
                                        class='credentials_input_text' value='' id='input_1' autocomplete='off'/></td>
                            </tr>
                            <tr>
                                <td class="apm-credentials_table_label_cell"><label for='input_2'
                                        id='label_input_2'>�����</label></td>
                                <td class="apm-credentials_table_field_cell"><input type='password' name='password'
                                        class='credentials_input_password' value='' id='input_2' autocomplete='off'/></td>
                            </tr>
                            <tr>
                                <td class="apm-credentials_table_label_cell"><label for='input_3'
                                        id='label_input_3'>����</label></td>
                                <td class="apm-credentials_table_field_cell">
                                    <div class='radio-div'><input type='radio' value="ACADEMIC" name='Domain'
                                            id='input_3_0' /><label for='input_3_0' id='label_input_3_0'
                                            class='radio-label' style='display: inline'>��������</label></div>
                                    <div class='radio-div'><input type='radio' value="MGMT" name='Domain'
                                            id='input_3_1' /><label for='input_3_1' id='label_input_3_1'
                                            class='radio-label' style='display: inline'>���</label></div>
                                    <div class='radio-div'><input type='radio' value="ACADEMIC_Bogrim" name='Domain'
                                            id='input_3_2' /><label for='input_3_2' id='label_input_3_2'
                                            class='radio-label' style='display: inline'>������</label></div>
                                </td>
                            </tr>
                            <tr id="submit_row">
                                <td colspan="2" align="center"><input type=submit
                                        class="submit_button submit_button:hover" value="�����"></td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
    </div>

</body>

</html>