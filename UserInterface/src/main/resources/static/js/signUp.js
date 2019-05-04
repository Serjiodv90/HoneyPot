$(document).ready(function () {
    $("#nextBtn").click(function () {
//function registration() {
    	alert("in function");
        var org = $("#org").val();
        var email = $("#email").val();
        var password = $("#password").val();
        var cpassword = $("#rPass").val();

        var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;

        if (org == '' || email == '' || password == '' || cpassword == '') {
            alert("Please fill all fields!!!!!!");
        }
        else if ((password.length) < 8) {
            alert("Password should atleast 8 character in length!!!!!!");
        }
        else if (!(password).match(cpassword)) {
            alert("Your passwords don't match. Try again?");
        }
        else if(!(emailReg.test(email))) {
            alert("Wrong email address");
        }
    });
});
