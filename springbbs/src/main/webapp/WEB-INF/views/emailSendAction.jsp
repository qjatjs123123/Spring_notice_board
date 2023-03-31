<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/emailSendAction.css?ver=2">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/main.js?ver=2"></script> 

<title>로그인</title>
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
    
    <div class="wrapper">
    	
    	<div class="container">
                이메일 주소 인증 메일이 전송되었습니다. <br>비밀번호 변경시 입력했던 이메일에 들어가셔서 인증해주세요.

       </div>
    </div>
    
    
</body>
</html>
