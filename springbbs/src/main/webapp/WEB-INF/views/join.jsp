<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/join.css?after">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/join.js?ver=4"></script> 
<title>회원가입양식</title>
</head>
<script>

</script>
<body>
	<header><h1>회원가입</h1></header>

    <section>
		<form action="/springbbs/user/joinAction" method="post" onsubmit="return All_check()">
	        <label class="center1">아이디<br>
	        <input name = "id" id="text1" class="center" type="text" size="50" onblur="id_check()"></label>
	        <p id="id" class="center2"></p>
	        
	        <label class="center1">주민등록번호<br>
	        <input name = "firstnum" id="firstnum" class="center_num" type="text" size="25"><label class="bar"> -  </label>
	        <input name = "secondnum" id="secondnum" class="center_num" type="password" size="25" onblur="num_check()"></label>
	        <p id="num" class="center2"></p>
	        
	        <label class="center1">비밀번호<br>
	        <input name ="passwd" id="text2" class="center" type="password" size="50" onchange="pw_check()"/></label>
	        <p id="password" class="center2"></p>
	
	        <label class="center1">비밀번호 재확인<br>
	        <input id="text3" class="center" type="password" size="50" onchange="pw_recheck()"/></label>
	        <p id="passwordcheck" class="center2"></p>
	
	        <label class="center1">이름 <br>
	        <input name="name" id="text4" class="center" type="text" size="50" onchange="name_check()"/></label>
	        <p id="name" class="center2"></p>
	
	        <label class="center1">이메일 <br>
	        <input name= "email" class="center" id = "email" type="text" size="50" onchange="email_check()" /></label>
	        <p id="emailp" class="center2"></p>
       
       

        
            <input id = "input" type="submit"  value="가입하기">
        </form>
       
    </section>
</body>
</html>