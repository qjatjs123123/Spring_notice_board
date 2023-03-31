<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/findPw.css?ver=1">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/findPw.js?ver=1"></script> 
<title>비밀번호 찾기</title>
</head>


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
            	비밀번호 찾기
         </span>
         <span style="font-size:13px;">
            	비밀번호를 찾고자 하는 정보를 입력해주세요 
         </span>
   </section>
      
   <section class="id_find">
         <article>
            <form action="/springbbs/user/emailSendAction" method = "post" onsubmit="return pw_find();">
				<br>
	            <label class="center1">아이디<br>
	            
		        <input name = "id" id="text1" class="center" type="text" size="50"></label>
		       <br><br>
		        
		        <label class="center1">주민등록번호<br>
		        <input name = "firstnum" id="firstnum" class="center_num" type="text" size="25"><label class="bar"> -  </label>
		        <input name = "secondnum" id="secondnum" class="center_num" type="password" size="25"></label>
		        
		        
				<br><br>
				<label class="center1">이메일<br>         
		        <input name = "email" id="text2" class="center" type="text" size="50"></label><br><br>
		        <input type="submit" value="인증하기"  style="width:85px; height:30px; margin-top:20px;">
            </form>
            <br>
            
         </article>
   </section>
   
   <br><br><br><br>
   <section class="go_main">
      	<a href="/springbbs/">메인 화면</a>
   </section>
    
    
</body>
</html>