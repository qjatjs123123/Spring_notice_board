	function confirm_alert(bbsID)
	{
		var url = "deleteAction.jsp?bbsID="+bbsID;
		if (confirm("정말로 삭제하시겠습니까?")) {
			location.href=url;
	    } 
		
	}
	function confirm_alert_reply(replyID,bbsID)
	{
		var url = "deleteReplyAction.jsp?replyID="+replyID+"&bbsID="+bbsID;
		if (confirm("정말로 삭제하시겠습니까?")) {
			location.href=url;
	    } 
		
	}

