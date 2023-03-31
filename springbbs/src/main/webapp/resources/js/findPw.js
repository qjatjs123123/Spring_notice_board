function pw_find()
{
	
	var name = $("#text1").val();
	var email = $("#text2").val();
	var first_rule = /^[0-9]{6,6}$/
	var second_rule = /^[0-9]{7,7}$/
	var firstnum = $("#firstnum").val();
	var secondnum = $("#secondnum").val();
	
	
	if (name == "") {
        alert("아이디를 입력해주세요");
        return false;
    } else if(!(first_rule.test(firstnum) && second_rule.test(secondnum))){
		alert("주민번호를 확인해주세요");
		return false;
	}else if(email == ""){
		alert("이메일을 확인해주세요");
		return false;
	} 
    else{
    	return true;
    }
}