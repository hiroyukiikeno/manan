/**
 * Global variables and functions for index.jsp
 */
var locat = "/CourseList";
var emailptn = /^[a-zA-Z0-9._-]{1,128}@[a-zA-Z0-9._-]/;

/**
 * Login to server :  post user-id from input 
 */
function doLogin(){
	console.log("logging in ...");
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	var promise = $.ajax(
		{
			url:'/Starter',
			method: 'POST',
			data: {userid:$('input[name="uname"]').val(), username:$('input[name="nname"]').val()},
			success: function(response){
				console.log("login successful");
			},
			error: function(e){
				console.log("login failed");
			}
		}	
	);
	promise.done(function(){
		console.log("moving to: " + locat);
		seni();
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
	});
}

function seni(){
	window.location.href = locat;
}
