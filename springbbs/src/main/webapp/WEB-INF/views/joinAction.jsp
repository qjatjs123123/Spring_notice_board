<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<script type="text/javascript">
		var message = "${result}";

		if(message == -1 || message == 0){
			alert("회원가입에 실패했습니다.");
			window.history.back();
		}
		else{
			alert("회원가입이 완료되었습니다.");
			location.href = '/springbbs/';
		}
	</script>

</body>
</html>