	function pw_check()
	{
		 var pw = $("#pw").val();	
		 var re_pw =  $("#re_pw").val();
		 var num = pw.search(/[0-9]/g);
		 var eng = pw.search(/[a-z]/ig);
		 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		 if (num < 0 || eng < 0 || spe < 0 || pw.length < 8 || pw.length > 16) {
				alert("8~16자 영문, 숫자, 특수문자를 사용하세요.");
				return false;
				
		    } 
		 if(pw != re_pw){
			 alert("비밀번호가 일치하지 않습니다.");
			 return false;
		 }
		 return true;
	}