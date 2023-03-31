package com.pjt.springbbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pjt.springbbs.bbs.service.bbsService;
import com.pjt.springbbs.reply.reply;
import com.pjt.springbbs.reply.service.replyService;
import com.pjt.springbbs.user.service.UserService;
import com.pjt.springbbs.util.SHA256;
import com.pjt.springbbs.bbs.bbs;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	bbsService service;
	
	@Autowired
	replyService replyservice;
	
	@Autowired
	UserService Userservice;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "main";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Locale locale, Model model) {	
		return "join";
	}
	
	@RequestMapping(value = "/findId", method = RequestMethod.GET)
	public String findId(Locale locale, Model model) {	
		return "findId";
	}
	
	@RequestMapping(value = "/findPw", method = RequestMethod.GET)
	public String findPw(Locale locale, Model model) {	
		return "findPw";
	}
	
	@RequestMapping(value = "/emailSendAction", method = RequestMethod.GET)
	public String emailSendAction(Locale locale, Model model) {	
		return "emailSendAction";
	}
	
	@RequestMapping(value = "/emailCheckAction", method = RequestMethod.GET)
	public String emailCheckAction(Locale locale, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String code = null;
		
		if(request.getParameter("code") != null)
			code = request.getParameter("code");
		String userID = (String)session.getAttribute("findPw");
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('세션이 종료되었습니다.');");
			script.println("location.href = '/springbbs/'");
			script.println("</script>");
		}
		response.setContentType("text/html; charset=UTF-8");
		String userEmail = Userservice.getUserEmail(userID);
		boolean isRight = (new SHA256().getSHA256(userEmail).equals(code)) ? true : false;
		
		if(isRight == true) {
			   PrintWriter script = response.getWriter();
			   return "emailCheckAction";
			   

		   } else {
			   PrintWriter script = response.getWriter();
			   script.println("<script>");
			   script.println("alert('유효하지 않은 코드입니다.');");
			   script.println("location.href = '/springbbs/'");
			   script.println("</script>");
		   }
		return null;
	}
	
	@RequestMapping(value = "/bbs", method = RequestMethod.GET)
	public String bbs(Locale locale, Model model, HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException {
		
		PrintWriter script = response.getWriter();
		String id = (String)session.getAttribute("userID");
		response.setContentType("text/html; charset=UTF-8");
		int MaxPage = service.getMaxPage();
		int page = 0;
		String pageNumber = request.getParameter("pageNumber");
		if(pageNumber == null) {
			page = 1;
		}
		else {
			page = Integer.parseInt(pageNumber);
		}
		if(id == null) {
			script.println("<script>");
			script.println("alert('로그인을 해주세요.')");
			script.println("location.href = '/springbbs/'");
			script.println("</script>");
			return null;
			
		}
		else {
			List<bbs> result = service.getList(page);
			model.addAttribute("result", result);
			model.addAttribute("id",id);
			model.addAttribute("pageNumber",page);
			model.addAttribute("MaxPage",MaxPage);
			return "bbs";
		}
		
	}
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Locale locale, Model model, HttpSession session) {
		return "write";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Locale locale, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String bbsID = request.getParameter("bbsID");		
		String id = (String)session.getAttribute("userID");
		PrintWriter script = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		if(id == null) {
			script.println("<script>");
			script.println("alert('로그인을 해주세요.')");
			script.println("location.href = '/springbbs/'");
			script.println("</script>");
			return null;
			
		}
		else {
			bbs result = service.getBbsService(bbsID);
			int index = replyservice.getLastService(bbsID);
			List<reply> result1 = replyservice.getListService(bbsID, "-1");
			
			model.addAttribute("result", result);
			model.addAttribute("result1", result1);
			model.addAttribute("id",id);
			model.addAttribute("index",index);
			model.addAttribute("bbsID",bbsID);
			return "view";
		}
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, HttpSession session,HttpServletRequest request) {
		String bbsID = request.getParameter("bbsID");
		model.addAttribute("bbsID",bbsID);
		return "update";
	}
	
}
