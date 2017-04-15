/**
 * Global variables and functions for index.jsp
 */

var emailptn = /^[a-zA-Z0-9._-]{1,128}@[a-zA-Z0-9._-]/;

/**
 * Login to server :  post user-id from input 
 */
function doLogin(){
	console.log("logging in ...");
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	let ok = false;
	var promise = $.ajax(
		{
			url:'/Starter',
			method: 'POST',
			data: {userid:$('input[name="uname"]').val(), username:$('input[name="nname"]').val()},
			success: function(response){
				ok = true;
				console.log("login successful");
			},
			error: function(e){
				console.log("login failed");
			}
		}	
	);
	promise.done(function(){
		if(ok){
			seni();
		}
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
	});
}

function seni(){
	let locat = "/CourseList";
	let q = window.location.search;
	if(q){
		locat = "/login" +  q;
	}
	console.log("moving to: " + locat);
	window.location.href = locat;
}
