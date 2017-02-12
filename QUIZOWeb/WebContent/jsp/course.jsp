<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.manandakana.dto.UserCourseData"
    import="java.util.Vector"%><%
    String userid = (String)session.getAttribute("userid");
    String username = (String)session.getAttribute("username");
    if(username != null && !username.equals("")) userid = username;
    @SuppressWarnings("unchecked")
    Vector<UserCourseData> courseList = (Vector<UserCourseData>)request.getAttribute("userCourseList");
     %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
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
      <div class="userinfo"><%= userid %><span id="listlink">&nbsp;<i class="material-icons icon-font">bubble_chart</i></span></div>
      <p id="application-title" class="text1">m a n a n</p>
      <p id="application-subtitile" class="text1"></p>
      <div class="catalog">
        <p>&nbsp;コースを選択してください</p>
        <ul class="catalog-list">
          <% if(courseList.size() < 1){
           %><span class="p">利用可能なコースがありません。</span><%
             } else {
               for (int i=0; i < courseList.size(); i++){
                 UserCourseData ucd = courseList.get(i);
                 String courseId = ucd.getCourseId();
                 String courseName = ucd.getCourseName();
                 String description = ucd.getDescription();
                 String courseStatus = (ucd.getStatus().equals("ACTV") ? "" : "このコースは準備中です。" );
                 String userStatus = (ucd.isStarted() ? "あなたはこのコースを実行中です。" : (ucd.isAchieved() ? "あなたはこのコースを完了しています。" : ""));
                 String userStatusIcon = (ucd.isStarted() ? "directions_run" : (ucd.isAchieved() ? "beenhere" : ""));
                 String peopleStatus = (ucd.getNumberOfAchievedUsers() > 0 ? ucd.getNumberOfAchievedUsers() + "人のユーザーがすでにこのコースを完了しています。" : "");
                 String accessCodeReq = (ucd.isAchieved() ? "" : (ucd.isStarted() ? "" : (ucd.getAccessCode().equals("") ? "" : "このコースを開始するには確認コードを入力してください。")));
            %><li class="catalog-item"><span class="item-title"><%= courseId %></span><span class="item-name"><%= courseName %></span>
                <div class="catalog-item-detail noshow">&nbsp;<span><%= description %></span><br/><br/>
                  <span id="coursestatus" class="catalog-item-detail-info noshow">&nbsp;<i class="material-icons icon-font">brightness_3</i><span id="coursestatusvalue"><%= courseStatus %></span></span>
                  <span id="userstatus" class="catalog-item-detail-info noshow"><br/>&nbsp;<i class="material-icons icon-font"><%= userStatusIcon %></i><span id="userstatusvalue"><%= userStatus %></span></span>
                  <span id="peoplestatus" class="catalog-item-detail-info noshow"><br/>&nbsp;<i class="material-icons icon-font">group</i><span id="peoplestatusvalue"><%= peopleStatus %></span></span>
                  <span id="accesscodereq" class="catalog-item-detail-info noshow"><br/>&nbsp;<i class="material-icons icon-font">lock</i><span id="accesscodereqvalue"><%= accessCodeReq %></span><input type="text" id="acccode"><span id="accmessage" class="noshow">正しい確認コードを入力してください。</span></span>
                  <a class="catalog-item-action">このコースを開始！</a><br/>
                </div>
              </li><%
              } } %>
        </ul>
      </div>
      <div class="coin"></div>
      <div id="totalscore" class="info-line-3 noshow">total score &#36;${score}</div>
    </div>
    <p class="message"></p>
  </div>
  <div id="spinbackground"><div id="spin"></div></div>
  <script src="https://code.jquery.com/jquery-3.1.1.min.js" integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script>
  <script type="text/javascript" src="/js/starter2.js"></script>
  <script type="text/javascript" src="/js/course.js"></script>
</body>
</html>