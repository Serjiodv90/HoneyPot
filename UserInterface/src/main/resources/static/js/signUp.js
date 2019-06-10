
function validateReg() {

	var org = $("#organization").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var cpassword = $("#rPass").val();

	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	$("small").remove();

	if (org == '' || email == '' || password == '' || cpassword == '') {
		$(".generalError").append("<small>Please fill all fields</small>");
		$(".generalError").show();
		return false;
	}
	else if (org != '' || email != '' || password != '' || cpassword != '') {
		$(".generalError").hide();
	}
	if (!(emailReg.test(email))) {
		$(".emailError").append("<small>Incorrect email address</small>");
		$(".emailError").show();
		return false;
	}
	else if ((emailReg.test(email))) {
		$(".emailError").hide();
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



var minNames = 1;

$(document).ready(function () {
	var counter = 0;
	
	$(".errorField").hide();

	$("#addrow").on("click", function () {

		var reqlength = $('.required').length;

		var value = $('.required').filter(function () {
			return this.value != '';
		});

		$("small").remove();
		
		console.log("value.length = " + value.length);
		console.log("value = " + value);
		console.log("reqlength: " +  reqlength);
		console.log("all required fields: " + $('.required')[0].value);
		console.log("value.length !== reqlength: " + (value.length !== reqlength));

		if (value.length >= 0 && (value.length !== reqlength)) {
			$(".emplError").append("<small>Please fill in the current row available, before adding a new one</small>");
			$(".emplError").show();

		} else {
			var rowCount = $('#myTable >tbody >tr').length + 1;
			var newRow = $("<tr>");
			var cols = "";
			$(".emplError").hide();

			cols += '<td class="col-sm-2 control-label">' + rowCount + '</td>';
			cols += '<td class="col-sm-4"> <input name="fakeUsers[' + (rowCount-1) + '].firstName" id="fakeUsers' + (rowCount-1) + '.firstName" type="text"  class="form-control required" /> </td>';
			cols += '<td class="col-sm-3"> <input name="fakeUsers[' + (rowCount-1) + '].lastName"  id="fakeUsers' + (rowCount-1) + '.lastName" type="text" class="form-control required" /> </td>';

			cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger "  value="Delete"></td></tr>';
			newRow.append(cols);
			$("table.order-list").append(newRow);
			counter++;

		}
	});

	 


	$("table.order-list").on("click", ".ibtnDel", function (event) {
		$(this).closest("tr").remove();
		counter -= 1;
		var rowCount = $('#myTable >tbody >tr').length;
//		if (rowCount <= 1)
//			$("#nextBtnAfterFillingNames").prop('disabled', true);
	});


});

function signUpAction() {

	$("small").remove();
	
	var reqlength = $('.required').length;
	var values = $('.required').filter(function () {
		return this.value != '';
	});
	
	if((reqlength / 2) > minNames) {
		if(values.length === reqlength) {
			$(".emplError").hide();
			return true;
		}
	}
	
	$(".emplError").append("<small>Please fill at least " + minNames + " `employee details</small>");
	$(".emplError").show();
	return false;
}



//function calculateRow(row) {
//var price = +row.find('input[name^="price"]').val();

//}

//function calculateGrandTotal() {
//var grandTotal = 0;
//$("table.order-list").find('input[name^="price"]').each(function () {
//grandTotal += +$(this).val();
//});
//$("#grandtotal").text(grandTotal.toFixed(2));
//}