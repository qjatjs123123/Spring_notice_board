var Is_ID = false;
var Is_Pw = false;
var Is_rePw = false;
var Is_name = false;
var Is_email = false;
var Is_num = false;

function id_check()
{	
	var id = "id=" + $("#text1").val();
	if($("#text1").val()=="")
		return;
	$.ajax({
		type : "POST",
		url : "/springbbs/user/id_check",
		data:id,
		success:function(res){
			var id_label = $("#id");		
			id_label.html(res);

			if(id_label.text().trim() == "사용 가능한 아이디 입니다."){
				id_label.css("color","limegreen");
				Is_ID = true;
			}
			else{
				
				id_label.css("color","red");
				Is_ID = false;
			}
		}
	});
}

function pw_check()
{
    var pw = $("#text2").val();
    var num = pw.search(/[0-9]/g);
    var eng = pw.search(/[a-z]/ig);
    var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    var p = $("#password");


    if (num < 0 || eng < 0 || spe < 0 || pw.length < 8 || pw.length > 16) {
		p.css("color","red");
        p.html("8~16자 영문, 숫자, 특수문자를 사용하세요.");
        Is_Pw = false;
		
    } else {
    	p.css("color","limegreen");
        p.html("사용 가능");
        Is_Pw = true;
    }
	
}

function pw_recheck()
{
	var pw = $("#text2").val();
	var re_pw = $("#text3").val();
	var pwcheck = $("#passwordcheck");
	
	if(Is_Pw == true){
		if(pw == re_pw){
			pwcheck.html("비밀번호 일치");
			pwcheck.css("color","limegreen");
			Is_rePw = true;
		}
		else{
			pwcheck.html("비밀번호가 일치하지 않습니다.");
			pwcheck.css("color","red");
			Is_rePw = false;
		}
	}
	else{
		pwcheck.html("비밀번호를 확인하세요");
		pwcheck.css("color","red");
		Is_rePw = false;
	}
}

function name_check()
{
	var name = $("#text4").val();
	var name_txt = $("#name");
	
	if(name == ""){
		name_txt.html("필수 정보입니다.")
		name_txt.css("color","red");
		Is_name = false;
	}
	else{
		name_txt.html("")
		Is_name = true;
	}
}

function email_check()
{
	var email = $("#email").val();
	var email_check = $("#emailp");
	
	if(email == ""){
		email_check.css("color","red");
		email_check.html("이메일을 입력해주세요.");
		Is_email = false;
	}
	
	else if(email.indexOf("@") == -1){
		email_check.css("color","red");
		email_check.html("'@'포함하여 이메일 주소를 입력해주세요.");
		Is_email = false;
	}
	else if(email.indexOf("@") >= 0){			
		email_check.html("");
		Is_email = true;
	}
		
}

function All_check()
{		
	if (!Is_ID) {
        alert("아이디를 다시 입력해주세요");
        return false;
    } else if(!Is_num){
		alert("주민번호를 확인해주세요");
		return false;
	}else if (!Is_Pw) {
        alert("비밀번호를 입력해주세요");
        return false;
    } else if (!Is_rePw) {
        alert("비밀번호 재확인을 확인해주세요");
        return false;
    } else if (!Is_name) {
        alert("이름을 입력해주세요");
        return false;
    } else if(!Is_email){
		alert("이메일을 확인해주세요");
		return false;
	} 
    else{
    	return true;
    }
}

function num_check()
{
	var first_rule = /^[0-9]{6,6}$/
	var second_rule = /^[0-9]{7,7}$/
	var firstnum = $("#firstnum").val();
	var secondnum = $("#secondnum").val();
	var num = $("#num");
	if(first_rule.test(firstnum) && second_rule.test(secondnum)) { 	
		num.html("[주민등록번호] 형식에 맞음"); 
		num.css("color","limegreen");
		Is_num = true; //
	}
	else{
		num.html("[주민등록번호] 형식에 맞지 않음");
		num.css("color","red");
		Is_num = false; 
	}


	}