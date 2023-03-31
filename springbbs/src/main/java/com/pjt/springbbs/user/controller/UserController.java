package com.pjt.springbbs.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pjt.springbbs.user.user;
import com.pjt.springbbs.user.service.UserService;
import com.pjt.springbbs.util.Gmail;
import com.pjt.springbbs.util.SHA256;


@Controller
public class UserController {
	
	@Autowired
	UserService service;
	
	@RequestMapping(value="/user/joinAction", method=RequestMethod.POST)
	public String joinAction(Model model,user user) {
		
		int result = service.userRegister(user);
		model.addAttribute("result", result);
		return "joinAction";
	}
	
	@RequestMapping(value="/user/id_check", method=RequestMethod.POST)
	public void id_check(HttpServletRequest request, HttpServletResponse response, String id) throws IOException {
		boolean result = service.id_check(id);	
		PrintWriter out = response.getWriter();

		if(result == true)
			out.println("사용 가능한 아이디 입니다.");		
		else
			out.println("중복된 아이디 입니다.");			
	}
	
	@RequestMapping(value="/user/id_find", method=RequestMethod.POST)
	public void id_find(HttpServletRequest request, HttpServletResponse response, String name,String firstnum,String secondnum) throws IOException {
		String result = service.id_find(name,firstnum,secondnum);	
		PrintWriter out = response.getWriter();

		if(result == null)
			out.println("");		
		else
			out.println(result);			
	}
	
	@RequestMapping(value="/user/emailSendAction", method=RequestMethod.POST)
	public void emailSendAction(HttpServletRequest request, HttpServletResponse response, HttpSession session,String id,String firstnum,String secondnum,String email) throws IOException {
		
		
		String result = service.emailSend(id, firstnum, secondnum, email);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");

		if(result == null)
		{	
			out.println("<script charset=\"utf-8\">");
		    out.println("alert('해당하는 정보가 없습니다.')");
		    out.println("location.href = '/springbbs/findPw'");
		    out.println("</script>");
		}
		else
		{
			  String host = "http://localhost:8048/springbbs/";
		      String from = "qjatjs123123@gmail.com";
		      String to = result;
		      String subject = "비밀번호 변경을 위한 이메일 인증 메일입니다.";
		      String content = "다음 링크에 접속하여 이메일 확인을 진행하세요." +
		            "<a href='" + host + "emailCheckAction?code=" + new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
		      
		      Properties p = new Properties();
		      p.put("mail.smtp.user", from);
		      p.put("mail.smtp.host", "smtp.googlemail.com");
		      p.put("mail.smtp.port", "456");
		      p.put("mail.smtp.starttls.enable", "true");
		      p.put("mail.smtp.auth", "true");
		      p.put("mail.smtp.debug", "true");
		      p.put("mail.smtp.socketFactory.port", "465");
		      p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		      p.put("mail.smtp.socketFactory.fallback", "false");
		      p.put("mail.smtp.ssl.protocols", "TLSv1.2"); 
		      
		      try{
		         Authenticator auth = new Gmail();
		         Session ses = Session.getInstance(p, auth);
		         ses.setDebug(true);
		         MimeMessage msg = new MimeMessage(ses);
		         msg.setSubject(subject);
		         Address fromAddr = new InternetAddress(from);
		         msg.setFrom(fromAddr);
		         Address toAddr = new InternetAddress(to);
		         msg.addRecipient(Message.RecipientType.TO, toAddr);
		         msg.setContent(content, "text/html;charset=UTF8");
		         Transport.send(msg);
		         session.setAttribute("findPw",id);  
		         session.setMaxInactiveInterval(1*60);
		      }catch(Exception e){
		         e.printStackTrace();
		         PrintWriter script = response.getWriter();
		         
		         script.println("<script>");
		         script.println("alert('오류가 발생했습니다.')");
		         script.println("location.href = '/springbbs/findPw'");
		         script.println("</script>");
		      }
		      	 PrintWriter script = response.getWriter();
		      	 script.println("<script>");		         
		         script.println("location.href = '/springbbs/emailSendAction'");
		         script.println("</script>");	
		}
				
	}
	
	@RequestMapping(value="/user/FindPasswdAction", method=RequestMethod.POST)
	public void FindPasswdAction(HttpServletRequest request,HttpSession session, HttpServletResponse response, String passwd) throws IOException {
		String id = (String)session.getAttribute("findPw");
		int result = service.ChangePassword(id, passwd);	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		if(result == -1) {
			out.println("<script>");
			out.println("alert('오류가 발생했습니다.');");
			out.println("location.href = '/springbbs/'");
			out.println("</script>");		
		}
		else {
			out.println("<script>");
			out.println("alert('비밀번호 변경되었습니다.');");
			out.println("location.href = '/springbbs/'");
			out.println("</script>");
		}
					
	}
	
	@RequestMapping(value="/user/loginAction", method=RequestMethod.POST)
	public void loginAction(HttpServletRequest request,HttpSession session, HttpServletResponse response,user user,String passwd,String id) throws IOException {
		
		int result = service.userLogin(id, passwd);
		PrintWriter script = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		if(result == 1) {
			session.setAttribute("userID", user.getId());//name, value값
			script.println("<script>");
			script.println("location.href = '/springbbs/bbs'");
			script.println("</script>");
		}
		else {
			script.println("<script>");
			script.println("alert('아이디 또는 비밀번호가 틀립니다.')");
			script.println("history.back()");
			script.println("</script>");
		}		
	}
	
	
		
}

