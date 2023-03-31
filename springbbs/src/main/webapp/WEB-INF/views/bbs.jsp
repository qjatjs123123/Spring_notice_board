<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/bbs.css?ver=1">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/main.js?ver=1"></script> 


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<title>로그인</title>
</head>
<script>
	function change_page(page,IsRight){
		$.ajax({
			type : "POST",
			url : "bbs.jsp",
			data:{
				pageNumber:page,IsRight:IsRight
			},
			success:function(){
				location.href="bbs.jsp";
				}
			
		});
	}
</script>
<body>
    <ul class="nav_ul">  	
    	<li><a href="/springbbs/" onclick="nav_a(0)">메인</a></li>
    	<li><a href="/springbbs/bbs" onclick="nav_a(1)">게시판</a></li>
	    <li><a href="/springbbs/findId" onclick="nav_a(2)">아이디 찾기</a></li>
	    <li><a href="/springbbs/findPw" onclick="nav_a(3)">비밀번호 찾기</a></li>	    
    </ul>
    <br>
    <h2 style="text-align:center;">게시판</h2>
    
    <div class="wrapper">
    	<table class="table_wrapper">
    		<thead>
    			<tr>  				
    				<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
    			</tr>
    		</thead>
    		
    		
    		<tbody>
    			<c:set var="id" value="${id}" />
    			<c:forEach var="row" items="${result}">
    			<tr>
    				<td ><c:out value="${row.bbsID}" /></td>
					<td><a href = "/springbbs/view?bbsID=${row.bbsID}">${row.bbsTitle}</a></td>
					<td>${row.userID}
						<c:if test = "${row.userID eq id}">
         					<span style="color:red">(나)</span>
     					</c:if>
					</td>
					
					<td>
						<fmt:parseDate var="a" value="${row.bbsDate}" pattern="yyyy-MM-dd" />
						<fmt:formatDate value="${a}" pattern="yyyy-MM-dd" />
					</td>
    			
    			</tr>
    			</c:forEach>
    		
    			<tr>
					<td colspan="4">
						
						<c:set var="pageNumber" value="${pageNumber}" />
						<c:choose> 
							<c:when test="${pageNumber != 1}">
								<button class="btn" type="button" onclick="location.href='/springbbs/bbs?pageNumber=${pageNumber-1}'" style="float:left;">이전</button>
							</c:when>
							<c:otherwise>
								<button class="btn" disabled='disabled' type="button" onclick="location.href='/springbbs/bbs?pageNumber=${pageNumber-1}'" style="float:left;">이전</button>
							</c:otherwise> 						
						</c:choose>
					
						${pageNumber}/${MaxPage}
						<c:set var="MaxPage" value="${MaxPage}" />
						<c:choose> 
							<c:when test="${pageNumber < MaxPage}">
								<button class="btn" type="button" onclick="location.href='/springbbs/bbs?pageNumber=${pageNumber+1}'" style="float:left;">다음</button>
							</c:when>
							<c:otherwise>
								<button class="btn" disabled='disabled' type="button" onclick="location.href='/springbbs/bbs?pageNumber=${pageNumber+1}'" style="float:left;">다음</button>
							</c:otherwise> 						
						</c:choose>
						
						<button class="btn" type="button" onclick="location.href='/springbbs/write' "  style="float:right;">글쓰기</button>
					</td>
				</tr>
				
    		</tbody>
    	</table>
    </div>
    
</body>
</html>