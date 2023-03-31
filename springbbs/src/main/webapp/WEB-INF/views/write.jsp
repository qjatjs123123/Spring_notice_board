<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/write.css?ver=1">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/main.js?ver=1"></script> 



<title>로그인</title>
</head>
<style>

</style>
<body>
    <ul class="nav_ul">  	
    	<li><a href="/springbbs/" onclick="nav_a(0)">메인</a></li>
    	<li><a href="/springbbs/bbs" onclick="nav_a(1)">게시판</a></li>
	    <li><a href="/springbbs/findId" onclick="nav_a(2)">아이디 찾기</a></li>
	    <li><a href="/springbbs/findPw" onclick="nav_a(3)">비밀번호 찾기</a></li>	    
    </ul>
    <br>
    
    <div class="wrapper">
    <form method="post" action="/springbbs/bbs/writeAction">
    	<table class="table_wrapper">
    		<thead>
    			<tr>
    				<td colspan="4">  				
						게시판 글쓰기 양식
					</td>
    			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<td colspan="4" >
    					<input style="width:100%; height:30px;" type="text" class="form-control" placeholder="글 제목" name="bbsTitle" maxlength="50">
    				</td>	
    			</tr>
    			<tr>	
					<td colspan="4">
						<textarea  class="form-control" placeholder="글 내용" name="bbsContent" maxlength="2048" style="height : 350px; width:100%; resize: none;"></textarea>
						<button class="btn" type="submit" style="float:right;">글쓰기</button>
						<button class="btn" type="button" style="float:left;" onclick="location.href='/springbbs/bbs' ">뒤로</button>
					</td>
					
				</tr>		
    		</tbody>
    	</table>
    </form>
    </div>
    
	
    
    
</body>
</html>