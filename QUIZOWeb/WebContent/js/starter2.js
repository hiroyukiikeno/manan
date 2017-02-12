/**
 * Global variables and functions for course.jsp
 */
var locat = "/manan";
var homep = "/";

/**
* send selected course to server and move to the quiz screen 
*/
function doSelect(parms){
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	var accok = false;
	var promise = $.ajax(
	  {
		  url:'/CourseList',
		  method: 'POST',
		  data: {course: parms.course, accesscode: parms.accesscode},
		  success: function(response){
			  if(response.status == "OK"){
				accok = true;
				$('#accmessage').addClass('noshow');
				console.log("submission successful");
			  } else {
				var responsemessage = (response.message ? response.message : "");
				if(responsemessage == "COURSE_ACCESS_INVALID"){
				  $('#accmessage').removeClass('noshow');
				} else {
				  console.log("submission not OK " + responsemessage);
				  $('.message').text("could not access the course. restarting the application..");
				  window.setTimeout(returnHome,3000);
				}
			  }
		  },
		  error: function(e){
			  console.log("submission failed");
		  }
	  }
	);
	promise.done(function(){
		if(accok){
			console.log("moving to : " + locat);
			window.setTimeout(seni,1000);
		} else {
			$('#accmessage').removeClass('noshow');
			$('#spinbackground').removeClass('grgrbg');
			$('#spin').removeClass('grgr');
		}
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
		console.log("moving to : " + homep);
		$('.message').text("could not access the course. restarting the application");
		window.setTimeout(returnHome,3000);
	});
}

function seni(){
	window.location.href = locat;
}

function returnHome(){
	window.location.href = homep;
}

