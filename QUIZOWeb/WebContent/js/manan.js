/**
 * Actions for DOM events in manan.jsp
 */

(function(){
	/* seasonal special */
	$('.obj-c2').click(function(ev){
		$('.obj-c2').toggleClass('noshow');
		$('.message').text("");
	});
	
	var qadata = {};
	var qacount = 0;
	var qnrecount = 0;
	
	var displayQuiz = function(){
		
		if(qadata.quizType == "ALT3"){
			/* ３択式クイズ用 */
			$('#questionnumber').text(qadata["id"]);
			$('#question').text(qadata["question"]);
			$('#ans1').text(qadata.answeroptions[0]);
			$('#ans2').text(qadata.answeroptions[1]);
			$('#ans3').text(qadata.answeroptions[2]);
			$('.quiz1').animate({top:'16%'},100,function(){
				$('.quiz1').css('display','inline-block');
			});
		} else if(qadata.quizType == "QNRE"){
			/* 研修アンケート用 */
			if(userdata.questionnaire == 0 && qnrecount == 0){
				/* 初回 */
				$('#qnre-head').text(qadata["question"]);
				$('#questionnaire').removeClass('noshow');
			} else {
				/* 2回目以降は表示せず、次へ */
				qacount++;
				qadata = getQAdata(qacount);
				if(qadata == null){
					$('.stagecomplete').fadeIn();
					console.log("score: " + score);
					endtime = Date.now();
					onStageCompletion(score, starttime, endtime);
				} else {
					displayQuiz();
					
				}	
			}
		}
		qacount++;
	}
	
	/* 認証されていれば自動ログイン */
	if($('#uname').val() != "" && $('#uname').val() != "null" && $('select[name="course"]').val() != ""){
		console.log("automatic login to course : " + $('select[name="course"]').val());
		$('.signin').addClass("noshow");
		loginToQuiz();
		if(qadataall.length > 0){
			$('.startbtn').removeClass('noshow');
		}
	} else {
		$('.signin').removeClass('noshow');
	}
	
	/* stage score 初期表示 */
	var score = 0;
	$('#score').text(("000000" + score).slice(-6));
	var ansnumber = "";
	
	/* 開始ボタン */
	$('.startbtn').click(function(ev){
		ev.preventDefault();
		score = 0;
		$('#score').text(("000000" + score).slice(-6));
		qadata = getQAdata(qacount);
		$('.startbtn').addClass('noshow');
		displayQuiz();
		starttime = Date.now();
	});
	
	/* ３択の答え選択 */
	$('.answeroption').click(function(ev){
		ev.preventDefault();
		ansnumber = $(ev.currentTarget).data().answer;
		stageresults[qadata.id] = ansnumber;
		if(qadata.correctanswer == ansnumber){
			console.log("correct");
			$('#correct').removeClass('noshow');
			score++;
		} else {
			console.log("wrong");
			$('#wrong').removeClass('noshow');
		}
		$('#description').text(qadata.description);
		$('.answerdescription').animate({top:'14%'},100,function(){
			$('.answerdescription').css('display','inline-block');
			$('#gonext').fadeIn(3000);
			$('.quiz1').fadeOut();
			$('#score').text(("000000" + score).slice(-6));
		});
	});
	
	/* 「次へ」を押したら : 表示初期化＆次の質問 */
	$('#gonext').click(function(evt){
		evt.preventDefault();
		if(qadata.correctanswer == ansnumber){
			$('#correct').addClass('noshow');
		} else {
			$('#wrong').addClass('noshow');
		}
		$('.answerdescription').css('display','none');
		$('#gonext').css('display','none');
		qadata = getQAdata(qacount);
		if(qadata == null){
			$('.stagecomplete').fadeIn();
			console.log("score:" + score);
			endtime = Date.now();
			onStageCompletion(score,starttime,endtime);
		} else {
			displayQuiz();
		}
	});
	
	/* 次のステージに進む */
	$('#gotonextstage').click(function(ev){
		ev.preventDefault();
		$('.stagecomplete').fadeOut();
		$('#stage-name').text(userdata.stage);
		qacount = 0;
		retrieveQA();
		$('.startbtn').removeClass('noshow');
	});
	
	/* ステージのはじめに戻る */
	$('#returntostart').click(function(ev){
		ev.preventDefault();
		$('.stagecomplete').fadeOut();
		qacount = 0;
		$('.startbtn').removeClass('noshow');
	});
	
	/* 研修アンケート */
	var qnreAns = {q1:0,q2:0,q3:0,q4:""};
	var submittable = 0;
	$('.qnre-balance').click(function(ev){
		var balPos = $(ev.target);
		var balValue = balPos.data('answer');
		var balParent = balPos.parent();
		var balChildren = balParent.children();
		qnreAns[balParent.data('qnum')] = balValue;
		for (i=0;i<balChildren.length;i++){
			var $bal = balChildren.eq(i);
			var $balVal = $bal.data('answer');
			if($balVal == balValue){
				$bal.addClass('balpo-on');
			} else {
				$bal.removeClass('balpo-on');
			}
		}
		submittable++;
		if(submittable > 2){
			$('.submitbtn').addClass('btn-enabled');
		}
	});
	
	$('.qnre-rate').click(function(ev){
		var ratePos = $(ev.target);
		var rateValue = ratePos.data('answer');
		var rateParent = ratePos.parent();
		var rateChildren = rateParent.children();
		qnreAns[rateParent.data('qnum')] = rateValue;
		for(i=0;i<rateChildren.length;i++){
			var $rate = rateChildren.eq(i);
			var $rateVal = $rate.data('answer');
			if($rateVal <= rateValue){
				$rate.addClass('rate-on');
			} else {
				$rate.removeClass('rate-on');
			}
		}
		submittable++;
		if(submittable > 2){
			$('.submitbtn').addClass('btn-enabled');
		}
	});
	
	$('.submitbtn').click(function(ev){
		ev.preventDefault();
		if(submittable > 2){
			qnreAns.q4 = $('#qnre-q4').val();
			console.log("a1: "+ qnreAns.q1 + " a2: " + qnreAns.q2 + " a3: " + qnreAns.q3 + " a4: " + qnreAns.q4);
			onSubmitQuestionnaire(qnreAns, $('#qnre-head').text());
			qnrecount++;
			$('#questionnaire').addClass('noshow');
			qadata = getQAdata(qacount);
			if(qadata == null){
				$('.stagecomplete').fadeIn();
				console.log("score: " + score);
				endtime = Date.now();
				onStageCompletion(score,starttime,endtime);
			} else {
				displayQuiz();
			}
		}
	});
	
	$('.listlink').click(function(ev){
		window.location.href = "/courselist";
	});
	
}());