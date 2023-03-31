	function nav_a(num)
	{
		active = $('.nav_ul').find("li");
		for(i = 0; i<active.length; i++){
			if(i == num)
				$(active.get(i)).css("background-color","#4CAF50");
			else
				$(active.get(i)).css("background-color","#333");
			
		}	

	}