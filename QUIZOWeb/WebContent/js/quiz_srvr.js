/**
 * Global variables and functions for manan.jsp
 */
var qadataall = [];
var stageresults = {};
var starttime = 0;
var endtime = 0;
var userdata = {};

/* start the course */
var loginToQuiz = function(){
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	let parms = {userid:$('input[name="uname"]').val(), course:$('select[name="course"]').val()};
	var promise = $.ajax(
	  {
		url: '/LoginServlet',
		data: parms,
		success: function(response){
			console.log(response);
			userdata = response;
			if(userdata.courseName && userdata.stage){
				$('#course-name').text(userdata.courseName);
				$('#stage-name').text(userdata.stage);
				console.log("Login Success");
				retrieveQA();
			} else {
				$('.message').text("Login Failed");
			}
		},
		error: function(e){
			$('.message').text("Login Failed");
		}
	  }
	);
	promise.done(function(){
		$('#spinbackground').removeClass('grgrbg');
		$('#spin').removeClass('grgr');
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
	});
}

/* retrieve set of QAs */
var retrieveQA = function(){
	$('.startbtn').addClass('noshow');
	var promise = $.ajax(
	 {
		 url: '/QuizStarter',
		 data: userdata,
	     success: function(response){
	    	 qadataall = response;
	    	 if(qadataall.length == 0){
	    		 $('#passed').addClass('noshow');
	    		 $('#notpassed').addClass('noshow');
	    		 $('#gotonextstage').addClass('noshow');
	    		 $('#returntostart').addClass('noshow');
	    		 $('.stagecomplete').fadeIn();
	    		 $('#stagenotready').removeClass('noshow');
	    	 } else {
	    		 console.log("Quiz loaded");
	    	 }
	     },
	     error: function(e){
	    	 $('.message').text("Quiz loading failed");
	     }
	 }
	);
	promise.done(function(){
		if(qadataall.length > 0){
			$('.startbtn').removeClass('noshow');
		}
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
	});
};

/* qadataall から１件データ取得して返す。ない場合はnullを返す */
var getQAdata = function(qacount){
	console.log("質問件数：" + qadataall.length);
	console.log("実施済み件数：" + qacount);
	if(qacount >= qadataall.length){
		return null;
	}
	var ret = {};
	var dat = qadataall[qacount];
	ret.id = dat.quizId;
	ret.quizType = dat.quizType;
	ret.question = dat.question;
	ret.answeroptions = [];
	ret.answeroptions.push(dat.option1);
	ret.answeroptions.push(dat.option2);
	ret.answeroptions.push(dat.option3);
	ret.correctanswer = dat.rightAnswer;
	ret.description = dat.description;
	return ret;
};

/* log stage completion and retrieve next stage */
var onStageCompletion = function(score){
	var parm = {score: score, useranswers: JSON.stringify(stageresults), start: starttime, end: endtime};
	var nextStageName = "";
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	var promise = $.ajax(
	  {
		  url: '/StageCompletion',
		  type: 'POST',
		  data: parm,
		  success: function(response){
			  if(response.nextstage){
				  nextStageName = response.nextstage;
				  if(response.coursecompleted){
					  $('#passed').removeClass('noshow');
					  $('#notpassed').addClass('noshow');
					  $('#gotonextstage').addClass('noshow');
					  $('#returntostart').addClass('noshow');
					  $('#coursecomplete').removeClass('noshow');
				  } else {
					  if(nextStageName == userdata.stage){
						  $('#notpassed').removeClass('noshow');
						  $('#passed').addClass('noshow');
						  $('#returntostart').removeClass('noshow');
						  $('#gotonextstage').addClass('noshow');
					  } else {
						  if(response.nextstagestatus == 'ACTV'){
							  userdata.stage = nextStageName;
							  $('#passed').removeClass('noshow');
							  $('#notpassed').addClass('noshow');
							  $('#gotonextstage').removeClass('noshow');
							  $('#returntostart').addClass('noshow');
						  } else {
							  $('#passed').removeClass('noshow');
							  $('#notpassed').addClass('noshow');
							  $('#gotonextstage').addClass('noshow');
							  $('#returntostart').addClass('noshow');
							  $('#stagenotready').removeClass('noshow');
						  }
					  }
						 
					  
				  } 
			  } else {
				  $('.message').text('score upload failed');
			  }
			  console.log(response);
			  console.log("score uploaded");
		  },
		  error: function(e){
			  $('.message').text("score upload failed");
		  }
	  }		
	);
	promise.done(function(){
		$('#spinbackground').removeClass('grgrbg');
		$('#spin').removeClass('grgr');
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
	});
}

/* アンケートの提出 */
var onSubmitQuestionnaire = function(qnreAns, questionText){
	var answertime = Date.now();
	var submitData = { 
			userid: $('input[name="uname"]').val(),
			course: userdata.courseId,
			question: questionText,
			duration: qnreAns.q1,
			understanding: qnreAns.q2,
			usefulness: qnreAns.q3,
			comment: qnreAns.q4,
			submitTime: answertime
	};
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	var promise = $.ajax(
	  {
		  url: '/QuestionnaireSubmit',
		  type: 'POST',
		  data: submitData,
		  success: function(response){
			  console.log("submit successful");
		  },
		  error: function(e){
			  $('.message').text("Submit Failed");
		  }
	  }		
	);
	promise.done(function(){
		$('#spinbackground').removeClass('grgrbg');
		$('#spin').removeClass('grgr');
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
	});
}
