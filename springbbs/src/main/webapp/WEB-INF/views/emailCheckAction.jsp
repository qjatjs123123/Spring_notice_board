<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
</head>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/emailCheckAction.js?ver=1"></script> 
<link rel="stylesheet" href="resources/css/emailCheckAction.css?after">


<body>
    <ul class="nav_ul">  	
    	<li><a href="/springbbs/" onclick="nav_a(0)">메인</a></li>
    	<li><a href="/springbbs/bbs" onclick="nav_a(1)">게시판</a></li>
	    <li><a href="/springbbs/findId" onclick="nav_a(2)">아이디 찾기</a></li>
	    <li><a href="/springbbs/findPw" onclick="nav_a(3)">비밀번호 찾기</a></li>	    
    </ul>
      <section id="infor_text">
         <br>
         <span style="font-weight:bolder;">
            	비밀번호 변경
            	<hr>
         </span>
         <span style="font-size:13px;">
            	변경 하고자 하는 비밀번호를 입력해주세요. 
         </span>
      </section>
      
      <section class="id_find">
         <article>
            <form action="/springbbs/user/FindPasswdAction" method = "post" onsubmit="return pw_check();">
               <input class="name" id="pw" name="passwd" type="password" placeholder="변경할 비밀번호를 입력해주세요." ><br>
               <input class="name" id="re_pw" name="re_passwd" type="password" placeholder="변경할 비밀번호를 다시 입력해주세요"><br><br>
               <input type="submit" value="비밀번호 변경" style="width:100px; height:30px;">
            </form>
            <p id="result"></p>
         </article>
      </section>
      <section class="go_main">
      	<a href="main.jsp">메인 화면</a>
      </section>

</body>
</html>