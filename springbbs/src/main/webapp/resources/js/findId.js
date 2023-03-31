	function id_find()
	{
		var name = $("#text1").val();
		var firstnum = $("#firstnum").val().toString();
		var secondnum = $("#secondnum").val().toString();
		
		if(name =="" || firstnum == "" || secondnum == ""){
			alert("정보를 입력해주세요");
			return;}
		
		$.ajax({
			type : "POST",
			url : "/springbbs/user/id_find",
			data:{
				name:name,firstnum:firstnum,secondnum:secondnum
			},
			success:function(res){
				var id_label = $("#result");	
				var id_label1 = $("#result1");
				var id_label2 = $("#result2");
				id_label1.html("");
				id_label.html("");
				id_label2.html("");
				

				if (res.trim() != ""){
					id_label1.html("찾으시는 이름의 아이디는 ");
					id_label.html(res);
					id_label2.html("입니다.");
				}
				else
					id_label1.html("아이디가 없습니다.");

			}
		});
			
		
	}
