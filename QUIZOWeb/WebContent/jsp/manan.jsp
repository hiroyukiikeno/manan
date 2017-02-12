<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="/img/favicon.ico">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/grgr.css">
<title>manan :: study aid ::</title>
</head>
<body>
<div class="pane-a">
  <div class="pane-c">
    <div class="obj-c"><img src="/img/cloudshape.png"/></div>
    <div class="obj-c2"><img src="/img/cloudshape.png"/></div>
    <p id="course-name" class="text1"></p>
    <p id="stage-name" class="text1"></p>
    <div class="userinfo">${username}<span id="listlink" class="listlink">&nbsp;<i class="material-icons icon-font icon-font-listmenu">list</i></span></div>
    <div class="info-line-2">
      <p class="text3">stage score : <span id="score"></span></p>
    </div>
    <div class="signin noshow">
      <form>
        <label for="uname">your e-mail : </label><input type="email" name="uname" id="uname" autofocus placeholder="xxxx@yy.com" autocomplete="email" value="${userid}"><br/>
        <label for="course">course : </label><select name="course"><option>${course_id}</option></select>
        <a id="signinbtn" href="#">Start your study</a>
      </form>
    </div>
    <div class="startbtn noshow">Start the stage</div>
    <div class="quiz1">
      問題 <span id="questionnumber"></span>
      <p id="question"></p>
      <ol class="options">
      	<li class="answeroption answer1" data-answer="1">【１】 <span id="ans1"></span></li>
      	<li class="answeroption answer2" data-answer="2">【２】 <span id="ans2"></span></li>
      	<li class="answeroption answer3" data-answer="3">【３】 <span id="ans3"></span></li>
      </ol>
    </div>
    <div class="answerdescription">
      <span id="correct" class="noshow">正解！</span>
      <span id="wrong" class="noshow">不正解</span>
      <p id="description"></p>
      <a href="#" id="gonext" class="gonext noshow">次へ</a>
    </div>
    <div class="stagecomplete">
      <p id="passed" class="noshow">おつかれさまでした！<br/>ステージをクリアしました。</p>
      <p id="notpassed" class="noshow">おつかれさまでした。<br/>でもこのステージはまだクリアできていません。<br/>クリアできるまで繰り返しがんばりましょう！</p>
      <a href="#" id="gotonextstage" class="gonext noshow"><i class="material-icons icon-font">mood</i>&nbsp;次のステージへ&nbsp;<i class="material-icons icon-font">call_made</i></a>
      <a href="#" id="returntostart" class="gonext noshow"><i class="material-icons icon-font">replay</i>&nbsp;ステージの最初に戻る</a>
      <p id="stagenotready" class="noshow">The next stage is not ready. <br/>come again soon!</p>
      <p id="coursecomplete" class="noshow">これでコース完了です！<br/>
        <i class="material-icons icon-font icon-font-listmenu">pets</i><br/>
        やったねー<br/><br/><br/>
        <a href="#" id="listlink2" class="gonext listlink"><span id="listlink">&nbsp;<i class="material-icons icon-font">list</i>コース一覧へ</span></a>
      </p>
    </div>
    <div id="questionnaire" class="noshow">
      <div id="qnre-head"></div>
      <form class="classroom-course-questionnaire">
        <p id="qnre-q1">・研修時間はどうでしたか？<br/>&nbsp;&nbsp; 長すぎ　←
          <span id="qnre-a1" class="qnre-balance" data-qnum="q1">
            <i id="qnrea1-1" data-answer="1" class="material-icons icon-font balpos balpo-off">sentiment_dissatisfied</i>
            <i id="qnrea1-2" data-answer="2" class="material-icons icon-font balpos balpo-off">sentiment_neutral</i>
            <i id="qnrea1-3" data-answer="3" class="material-icons icon-font balpos balpo-off">sentiment_neutral</i>
            <i id="qnrea1-4" data-answer="4" class="material-icons icon-font balpos balpo-off">mood</i>
            <i id="qnrea1-5" data-answer="5" class="material-icons icon-font balpos balpo-off">sentiment_neutral</i>
            <i id="qnrea1-6" data-answer="6" class="material-icons icon-font balpos balpo-off">sentiment_neutral</i>
            <i id="qnrea1-7" data-answer="7" class="material-icons icon-font balpos balpo-off">sentiment_dissatisfied</i> → 短かすぎ
          </span>
        </p>
        <p id="qnre-q2">・理解度はどうでしたか？<br/>&nbsp;&nbsp; ５段階評価で：
          <span id="qnre-a2" class="qnre-rate" data-qnum="q2">
            <span id="qnrea2-1" data-answer="1" class="rate-pos rate-off">★</span>
            <span id="qnrea2-2" data-answer="2" class="rate-pos rate-off">★</span>
            <span id="qnrea2-3" data-answer="3" class="rate-pos rate-off">★</span>
            <span id="qnrea2-4" data-answer="4" class="rate-pos rate-off">★</span>
            <span id="qnrea2-5" data-answer="5" class="rate-pos rate-off">★</span>
          </span>
        </p>
        <p id="qnre-q3">・今後の仕事でのお役立ち度はどう思いましたか？<br/>&nbsp;&nbsp; ５段階評価で：
          <span id="qnre-a3" class="qnre-rate" data-qnum="q3">
            <span id="qnrea3-1" data-answer="1" class="rate-pos rate-off">★</span>
            <span id="qnrea3-2" data-answer="2" class="rate-pos rate-off">★</span>
            <span id="qnrea3-3" data-answer="3" class="rate-pos rate-off">★</span>
            <span id="qnrea3-4" data-answer="4" class="rate-pos rate-off">★</span>
            <span id="qnrea3-5" data-answer="5" class="rate-pos rate-off">★</span>
          </span>
        </p>
        <p>ご意見、ご要望、ご感想等のコメントをお願いします。<br/>
          <textarea id="qnre-q4" cols="31" maxlength="2400"></textarea>
        </p>
        <br/>
        <a class="submitbtn">アンケートの回答を提出</a>  
      </form>
    </div>
  </div>
  <p class="message"></p>
</div>
<div id="spinbackground"><div id="spin"></div></div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js" integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/quiz_srvr.js"></script>
<script type="text/javascript" src="/js/manan.js"></script>
</body>
</html>