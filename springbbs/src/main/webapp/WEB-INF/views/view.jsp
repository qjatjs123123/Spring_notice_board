<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>

<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/view.css?ver=1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>로그인</title>
</head>

<body>
    <ul class="nav_ul">  	
    	<li><a href="/springbbs/" onclick="nav_a(0)">메인</a></li>
    	<li><a href="/springbbs/bbs" onclick="nav_a(1)">게시판</a></li>
	    <li><a href="/springbbs/findId" onclick="nav_a(2)">아이디 찾기</a></li>
	    <li><a href="/springbbs/findPw" onclick="nav_a(3)">비밀번호 찾기</a></li>	    
    </ul>
    <br>
    
      <div class="wrapper">
    	<form method="post" action="writeAction.jsp">
    	<table class="table_wrapper" >
    		<thead>
				<tr>
    				<td colspan="4">  				
						게시판 글 보기
						<button class="btn"  type="button" onclick="location.href='/springbbs/bbs' " style="float:left; margin-right:70px;">목록</button>
						<c:set var="id" value="${id}" />
						<c:set var="result" value="${result}" />
						
						<c:if test = "${result.userID eq id}">
         					<button class="btn"  type="button" onclick="location.href='/springbbs/update?bbsID=${result.bbsID}' " style="float:right;">수정</button>
						<button type="button" onclick="confirm_alert(${result.bbsID})"  class="btn" style="float:right;">삭제</button>
     					</c:if>
					</td>
    			</tr>
    		</thead>
    		<tbody>
				<tr>
					<td style="width:20%">
						글 제목
					</td>
					<td style="width:80%">
						${result.bbsTitle}
					</td>
				</tr>
				
				<tr>
					<td style="width:20%">
						작성자
					</td>
					<td style="width:80%">
						${result.userID}
					</td>
				</tr>
				
				<tr>
					<td style="width:20%">
						작성일자
					</td>
					<td style="width:80%">
						<fmt:parseDate var="a" value="${result.bbsDate}" pattern="yyyy-MM-dd" />
						<fmt:formatDate value="${a}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				
				<tr>
					<td style="width:20%">
						내용
					</td>
					<td style="width:80%">
						${result.bbsContent}					
					</td>
				</tr>		
    		</tbody>
    	</table>
    </form>
    <br><br>
    </div>  
    
<div style="text-align: center; margin-top:30px">
    	<form action="/springbbs/reply/replyAction" method="post">
		        <input name = "replyContent" id="text1" class="center" type="text" size="40" maxlength = "45" placeholder="댓글을 입력하세요" style="width: 700px; height:30px">
		        <input type=hidden name="userID" value="${id}">
		        <input type=hidden name="bbsID" value="${result.bbsID}">
				<input id = "input" type="submit"  value="입력하기" style=" height:30px">
        </form>
	</div>
    
    <br><br>
    
    <div class="wrapper">
    	<table class="table_wrapper" id="reply">
    		<thead>
    			<tr >  				
    				<th>아이디</th>
					<th>댓글</th>
    			</tr>
    		</thead>
    		<tbody>
				<c:set var="id" value="${id}" />
    			<c:forEach var="row" items="${result1}">
    			<tr>
    				<td ><c:out value="${row.userID}" /></td>
					<td><c:out value="${row.replyContent}" />					
						<c:if test = "${row.userID eq id}">
         					<button  type="button" onclick="confirm_alert_reply(${row.replyID}, ${row.bbsID})"  class="btn" style="float:right;">삭제</button>
     					</c:if>
					</td>  			
    			</tr>
    			</c:forEach>
			
    		</tbody>
    	</table>
    </div>
    

    
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.min.js" ></script>
<script src="resources/js/view.js?ver=1"></script> 
<script>
var index;
var arr_All = new Array();
var arr_ID = new Array();
var arr_content = new Array();
var arr_replyID = new Array();
var arr_userID = new Array();
$(document).ready(function() { index = ${index} });
$(window).scroll(function() {
	   var bbsID = ${bbsID};	
	   var userID = "${id}";
	   var reply_table = document.getElementById('reply');
	   
	   if(Math.abs(parseInt($(window).scrollTop()+$(window).height()) - parseInt($(document).height()))<=2 ) {
		   if (index <= 0 || isNaN(index)==true)
			   return;
		   $.ajax({
				type : "POST",
				url : "/springbbs/reply/replyListAction",
				data:{
					bbsID:bbsID,
					index:index
				},
				success:function(res){
					arr_All = res.split("|");
					arr_ID = arr_All[1].split(",");
					arr_content = arr_All[0].split(",");
					arr_replyID = arr_All[2].split(",");
					arr_userID = arr_All[3].split(",");
					
					for(i = 0 ; i<arr_ID.length-1;i++){
						newRow = reply_table.insertRow();
						newCell1 = newRow.insertCell(0);
						newCell2 = newRow.insertCell(1);
						newCell1.innerHTML = arr_ID[i];
						
						if(userID == arr_userID[i]){
							html2 = arr_content[i]+'<button onclick="confirm_alert_reply('+arr_replyID[i]+','+bbsID+')" type="button"  class="btn" style="float:right;">삭제</button>';
						}
						else{
							html2 = arr_content[i];
						}
						newCell2.innerHTML =html2;
					}
					index = parseInt(arr_All[4]);
				
				}
				
			});
		   
	   }
	   
	});
function confirm_alert(bbsID)
{
	var url = "/springbbs/bbs/deleteAction?bbsID="+bbsID;
	if (confirm("정말로 삭제하시겠습니까?")) {
		location.href=url;
    } 
	
}
function confirm_alert_reply(replyID,bbsID)
{
	var url = "/springbbs/reply/deleteReplyAction?replyID="+replyID+"&bbsID="+bbsID;
	if (confirm("정말로 삭제하시겠습니까?")) {
		location.href=url;
    } 
	
}
</script>

</html>