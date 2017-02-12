<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true" 
    import="java.util.Set"
    import="java.security.Principal"
    import="javax.security.auth.Subject"
    import="com.ibm.websphere.security.auth.WSSubject"
    %><%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" 
    %><%
    	response.setHeader("Pragma", "no-cache");
    	response.setHeader("Cache-control","no-store");
    	Subject s = WSSubject.getCallerSubject();
    	String username="unknown";
    	if(s != null){
    		Set<Principal> principals = s.getPrincipals();
    		if(principals != null & principals.size() > 0){
    			username = principals.iterator().next().getName();
    			if(username.lastIndexOf("/") > 0){
    				String usernamemod = username.substring(username.lastIndexOf("/")).replaceAll("[/]","");
    				username = usernamemod;
    			}
    		}
    	}
     %><html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="shortcut icon" href="/img/favicon.ico">
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/grgr.css">
<title>manan :: study aid ::</title>
</head>
<body>
  <div class="pane-a">
    <div class="pane-c">
      <p id="application-title" class="text1">m a n a n</p>
      <p id="application-subtitle" class="text1"></p>
      <div class="signin noshow"><form>
        <label for="uname">your e-mail</label><input type="email" id="uname" name="uname" autofocus placeholder="yourid@yourdomain" value="<%=username%>" ><br/>
        <label for="nickname">name (optional)</label><input type="text" id="nname" name="nname" >
        <a id="signinbtn" href="#">Start Application</a>
        </form>
      </div>
    </div>
  </div>
  <div id="spinbackground"><div id="spin"></div></div>
  <script src="https://code.jquery.com/jquery-3.1.1.min.js" integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script>
  <script type="text/javascript" src="/js/starter1.js"></script>
  <script type="text/javascript">
  (function(){
    var unameval = $('#uname').val();
    if(unameval != "" & unameval != "null" && unameval != "unknown" && emailptn.test(unameval)){
      $('input[name="uname"]').prop("disabled",true);
      window.setTimeout(doLogin,20000);
    } else {
      $('.signin').removeClass("noshow");
    }
    $('#signinbtn').click(function(ev){
      ev.preventDefault();
      doLogin();
    });
  }());
  
  
  </script>
</body>
</html>