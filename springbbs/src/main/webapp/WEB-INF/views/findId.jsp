<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/findId.css?after">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/findId.js?ver=12"></script> 

<title>아이디 찾기</title>
</head>
<script>

</script>
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
            	아이디 찾기
         </span>
         <span style="font-size:13px;">
            	아이디를 찾고자 하는 이름를 입력해주세요 
         </span>
   </section>
      
   <section class="id_find">
         <article>
            <form>
				<br>
	            <label class="center1">이름<br>
	            
		        <input name = "id" id="text1" class="center" type="text" size="50"></label>
		       <br><br>
		        
		        <label class="center1">주민등록번호<br>
		        <input name = "firstnum" id="firstnum" class="center_num" type="text" size="25"><label class="bar"> -  </label>
		        <input name = "secondnum" id="secondnum" class="center_num" type="password" size="25"></label>
		        <br><br>
		        <span id="result1" style="display:inline-block;"></span>
	            <span id="result" style="display:inline-block; color:red"></span>
	            <span id="result2" style="display:inline-block;"></span>
            </form>
            
            <input type="button" value="아이디 찾기" onclick="id_find()" style="width:85px; height:30px; margin-top:10px;">
         </article>
   </section>
   
   <br>
   <section class="go_main">
      	<a href="/springbbs/">메인 화면</a>
   </section>
    
    
</body>
</html>