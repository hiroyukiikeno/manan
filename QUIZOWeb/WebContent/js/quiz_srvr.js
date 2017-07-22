/**
 * Global variables and functions for manan.jsp
 */
var qadataall = [];
var stageresults = {};
var oxs = {};
var starttime = 0;
var endtime = 0;
var userdata = {};
var qacount = 0;
var removeStageListItems = function(){
	  var stagenames = document.querySelectorAll('.stage-name');
	  stagenames.forEach(function(el){
		  var listElem = el.parentNode;
		  var oldref = listElem.removeChild(el);
	  });
	  var stagedescs = document.querySelectorAll('.stage-description');
	  stagedescs.forEach(function(el){
		  var listElem = el.parentNode;
		  var oldref = listElem.removeChild(el);
	  });
	  var stagebtns = document.querySelectorAll('.stage-rerun-btn');
	  stagebtns.forEach(function(el){
		  var listElem = el.parentNode;
		  var oldref = listElem.removeChild(el);
	  });
	  var stagelistitems = document.querySelectorAll('.stage-item');
	  stagelistitems.forEach(function(el){
		  var listElem = el.parentNode;
		  var oldref = listElem.removeChild(el);
	  });
}



/* start the course */
var loginToQuiz = function(){
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	let parms = {userid:$('input[name="uname"]').val(), course:$('select[name="course"]').val(), acccode: $('#accesscode').val()};
	var promise = $.ajax(
	  {
		url: '/login',
		type: 'POST',
		data: parms,
		success: function(response){
			console.log(response);
			userdata = response;
			if(userdata.courseName && userdata.stage){
				$('#course-name').text(userdata.courseName);
				$('#stage-name').text(userdata.stage);
				console.log("Login Success");
				$('#accmessage').addClass('noshow');
				retrieveQA();
			} else if(userdata.message == "COURSE_ACCESS_INVALID") {
				$('#accmessage').removeClass('noshow');
			} else {
				$('.message').text("Login Failed");
			}
		},
		error: function(jqXHR,textStatus, errorThrown){
			console.log("textStatus: " + textStatus);
			console.log("errorThrown: " + errorThrown);
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
	    		 $('.stagecomplete').removeClass('noshow');
	    		 $('#stagenotready').removeClass('noshow');
	    	 } else {
	    		 console.log("Quiz loaded");
	    	 }
	     },
	     error: function(jqXHR,textStatus, errorThrown){
	    	 console.log("textStatus: " + textStatus);
			 console.log("errorThrown: " + errorThrown);
	    	 $('.message').text("Quiz loading failed");
	     }
	 }
	);
	promise.done(function(){
		if(qadataall.length > 0){
			$('.signin').addClass("noshow");
			$('.stagecomplete').addClass("noshow");
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

/* ステージのクイズ数を返す（アンケートを含めないで数える） */
var getFullScore = function(){
	let count = 0;
	for (var ix in qadataall){
		if(qadataall[ix].quizType != "QNRE"){
			count++;
		}
	}
	return count;
};


/* log stage completion and retrieve next stage */
var onStageCompletion = function(score){
	var parm = {score: score, useranswers: JSON.stringify(stageresults), start: starttime, end: endtime, oxs: JSON.stringify(oxs)};
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
				  $('#passed').addClass('noshow');
				  $('#notpassed').addClass('noshow');
				  $('#gotonextstage').addClass('noshow');
				  $('#returntostart').addClass('noshow');
				  $('#stagenotready').removeClass('noshow');
			  }
			  console.log(response);
			  console.log("score uploaded");
		  },
		  error: function(jqXHR,textStatus, errorThrown){
			  console.log("textStatus: " + textStatus);
			  console.log("errorThrown: " + errorThrown);
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

/* ステージ一覧を表示 */
var loadStagesList = function(cb){
	thelist = [];
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	var promise = $.ajax(
	  {
		  url: '/stagelist',
		  type: 'GET',
		  success: function(response){
			console.log("submit successful");
			thelist = response;
			console.log(thelist);
		  },
		  error: function(jqXHR,textStatus, errorThrown){
			  console.log("textStatus: " + textStatus);
			  console.log("errorThrown: " + errorThrown);
			  $('.message').text("Server Error Occured");
		  }
	  }
	);
	promise.done(function(){
		cb(thelist);
		$('#stagelist-box').removeClass('noshow');
		$('#spinbackground').removeClass('grgrbg');
		$('#spin').removeClass('grgr');
	});
	promise.fail(function(){
		$('#spinbackground').removeClass('grgrbg');
		$('#spin').removeClass('grgr');
	});
	return thelist;
}

var resetStage = function(ev){
	var tgt = $(ev.target);
	var stageName = tgt.parent().children('.stage-name').text();
	console.log("going to stage: " + stageName);
	$('#spinbackground').addClass('grgrbg');
	$('#spin').addClass('grgr');
	var resetStageParm = {userid: userdata.userid, resetToStage: stageName};
	var promise = $.ajax(
	  {
		  url: '/resetstage',
		  type: 'POST',
		  data: resetStageParm,
		  success: function(response){
			  console.log("submit successful");
		  },
		  error: function(jqXHR,textStatus, errorThrown){
			  console.log("textStatus: " + textStatus);
			  console.log("errorThrown: " + errorThrown);
			  $('.message').text("Update Failed");
		  }
	  }
	);
	promise.done(function(){
		$('#spinbackground').removeClass('grgrbg');
		$('#spin').removeClass('grgr');
		userdata.stage = stageName;
		$('#stage-name').text(userdata.stage);
		$('#stagelist-box').addClass('noshow');
		$('#stagenotready').addClass('noshow');
		removeStageListItems();
		qacount = 0;
		stageresults = {};
		oxs = {};
		retrieveQA();
	});
	promise.fail(function(){
		$('#spin').removeClass('grgr');
		$('#spinbackground').removeClass('grgrbg');
	});
}
