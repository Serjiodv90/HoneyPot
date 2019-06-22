/**
 * 
 */

function validateReg() {
	var password = $("#password").val();
	var cpassword = $("#rPass").val();

	if (password == '' || cpassword == '') {
		$(".generalError").append("<small>Please fill all fields</small>");
		$(".generalError").show();
		return false;
	}
	else if (password != '' || cpassword != '') {
		$(".generalError").hide();
	}

	if ((password.length) < 8) {
		$(".PwdError").append("<small>Password must contain atleast 8 character in length</small>");
		$(".PwdError").show();
		return false;
	}
	else if ((password.length) >= 8) {
		$(".PwdError").hide();
	}
	if (!(password).match(cpassword)) {
		$(".rptPwdError").append("<small>Passwords don't match. Try again</small>");
		$(".rptPwdError").show();
		return false;
	}
	else if ((password).match(cpassword)) {
		$(".rptPwdError").hide();
	}
	
	$(".errorField").hide();
	return true;
}