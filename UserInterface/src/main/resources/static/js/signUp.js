$(document).ready(function () {
    $("#nextBtn").click(function () {
    	$("#nextBtn").attr("disable", "true");
    	
    	
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
        else if (!(emailReg.test(email))) {
            alert("Wrong email address");
        }
        else {
   
        	$("#userRegistrationFirst").css("display", "none");
        	$("#userRegistrationSecond").css("display", "block");
         }

    });
});




$(document).ready(function () {
    var counter = 0;
    var minNames = 1;

    $("#addrow").on("click", function () {

        var reqlength = $('.required').length;
        
        var value = $('.required').filter(function () {
            return this.value != '';
        });

        console.log("value.length = " + value.length);
        console.log("value = " + value);
        console.log("reqlength: " +  reqlength);
        console.log("all required fields: " + $('.required')[0].value);
        console.log("value.length !== reqlength: " + (value.length !== reqlength));

        if (value.length >= 0 && (value.length !== reqlength)) {
            alert('Please fill out all required fields.');

        } else {
            var rowCount = $('#myTable >tbody >tr').length + 1;
            var newRow = $("<tr>");
            var cols = "";

            cols += '<td>' + rowCount + '</td>';
//            cols += '<td><input type="text" class="form-control required" name="fname' + counter + '"/></td>';
//            cols += '<td><input type="text" class="form-control required" name="lname' + counter + '"/></td>';

//            cols += '<td class="col-sm-2" th:text="${stat.index}+1"></td>';
            cols += '<td class="col-sm-4"> <input name="credentialsForTraps[' + (rowCount-1) + '].firstName" id="credentialsForTraps' + (rowCount-1) + '.firstName" type="text"  class="form-control required" /> </td>';
            cols += '<td class="col-sm-3"> <input name="credentialsForTraps[' + (rowCount-1) + '].lastName"  id="credentialsForTraps' + (rowCount-1) + '.lastName" type="text" class="form-control required" /> </td>';
            
            cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger "  value="Delete"></td></tr>';
            newRow.append(cols);
            $("table.order-list").append(newRow);
            counter++;
            if (rowCount > 1)
                $("#nextBtnAfterFillingNames").prop('disabled', false);
        }
    });



    $("table.order-list").on("click", ".ibtnDel", function (event) {
        $(this).closest("tr").remove();
        counter -= 1
        var rowCount = $('#myTable >tbody >tr').length;
        if (rowCount <= 1)
            $("#nextBtnAfterFillingNames").prop('disabled', true);
    });


});



// function calculateRow(row) {
//     var price = +row.find('input[name^="price"]').val();

// }

// function calculateGrandTotal() {
//     var grandTotal = 0;
//     $("table.order-list").find('input[name^="price"]').each(function () {
//         grandTotal += +$(this).val();
//     });
//     $("#grandtotal").text(grandTotal.toFixed(2));
// }