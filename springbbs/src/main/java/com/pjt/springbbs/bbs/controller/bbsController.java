package com.pjt.springbbs.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pjt.springbbs.bbs.bbs;
import com.pjt.springbbs.bbs.service.bbsService;
import com.pjt.springbbs.reply.service.replyService;

@Controller
@RequestMapping("/bbs")
public class bbsController {
	
	@Autowired
	bbsService service;
	
	@RequestMapping(value="/deleteAction", method=RequestMethod.GET)
	public void deleteAction(Model model,String bbsID, String bbsTitle, String bbsContent, HttpSession session, HttpServletResponse response) throws IOException {
		
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
			int result = service.deleteService(bbsID);
			if(result == 1) {
				script.println("<script>");
				script.println("location.href = '/springbbs/bbs'");
				script.println("</script>");
			}
			else {
				script.println("<script>");
				script.println("alert('글 삭제에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
		}
	}
	
	@RequestMapping(value="/updateAction", method=RequestMethod.POST)
	public void updateAction(Model model,String bbsID, String bbsTitle, String bbsContent, HttpSession session, HttpServletResponse response) throws IOException {
		
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
			if(bbsTitle == "" || bbsContent == "")
			{
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else {
				int result = service.updateService(bbsID, bbsTitle, bbsContent);
				if(result == 1) {
					script.println("<script>");
					script.println("alert('수정되었습니다.')");
					script.println("location.href = '/springbbs/bbs'");
					script.println("</script>");
				}
				else {
					script.println("<script>");
					script.println("alert('글 수정에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
			}
		}
	}
	
	

	
	@RequestMapping(value="/writeAction", method=RequestMethod.POST)
	public void writeAction(Model model,String bbsTitle, String bbsContent, HttpSession session, HttpServletResponse response) throws IOException {
		
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
			if(bbsTitle == "" || bbsContent == "")
			{
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else {
				int result = service.bbsWrite(bbsTitle, userID, bbsContent);
				if(result == 1) {
					script.println("<script>");
					script.println("alert('글쓰기에 성공했습니다.')");
					script.println("location.href = '/springbbs/bbs'");
					script.println("</script>");
				}
				else {
					script.println("<script>");
					script.println("alert('글쓰기에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
			}
		}
	}
	
}
