package com.pjt.springbbs.reply.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pjt.springbbs.reply.reply;
import com.pjt.springbbs.reply.service.replyService;

@Controller

public class replyController {

	@Autowired
	replyService service;
	
	@RequestMapping(value="/reply/deleteReplyAction", method=RequestMethod.GET)
	public void deleteReplyAction(Locale locale, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String userID = (String)session.getAttribute("userID");
		PrintWriter script = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		if(userID == null)
		{
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = '/springbbs/'");
			script.println("</script>");
		}
		else {
			String replyID = request.getParameter("replyID");
			String bbsID = request.getParameter("bbsID");
			int result = service.deleteService(replyID);	
			if (result == 1) // -1이면 데이터베이스 오류인데 아이디는 primarykey라 중복이 되면 데이터베이스 오류가 발생
			{
				script.println("<script>");
				script.print("location.href = '/springbbs/view?bbsID=");
				script.print(bbsID+"'");
				script.println("</script>");
			}
			else 
			{					
				script.println("<script>");
				script.println("alert('글 삭제에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			
		}
	}
	
	
	
	
	@RequestMapping(value="/reply/replyAction", method=RequestMethod.POST)
	public void replyAction(Locale locale, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response,int bbsID, String replyContent) throws IOException {
		
		String userID = (String)session.getAttribute("userID");
		PrintWriter script = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		if(userID == null)
		{
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = '/springbbs/'");
			script.println("</script>");
		}
		else {		
			if(replyContent == "")
			{
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else {
				int result = service.writeService(bbsID, userID, replyContent);
				if(result == 1) {
					response.sendRedirect("/springbbs/view?bbsID="+bbsID);
				}
				else {
					script.println("<script>");
					script.println("alert('댓글 쓰기에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
			}
		}	
	}
	
	
	@RequestMapping(value="/reply/replyListAction", method=RequestMethod.POST)
	public void replyListAction(Locale locale, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String bbsID = request.getParameter("bbsID");
		String index = request.getParameter("index");
		String data = "";
		List<reply> list = service.getListService(bbsID, index);
		
		for(int i = 0 ;i<list.size();i++)
			data = data + list.get(i).getReplyContent()+ ",";
		data = data+"|";
		for(int i = 0 ;i<list.size();i++)
			data = data + list.get(i).getUserID()+ ",";
		data = data+"|";
		for(int i = 0 ;i<list.size();i++)
			data = data + list.get(i).getReplyID()+ ",";	
		data = data+"|";
		for(int i = 0 ;i<list.size();i++)
			data = data + list.get(i).getUserID()+ ",";	
		if(list.size() != 0)
			data = data+"|"+list.get(list.size()-1).getReplyID();
		
		response.getWriter().print(data);
		
	}
}
