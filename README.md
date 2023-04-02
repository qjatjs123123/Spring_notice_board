# 스프링 프레임워크를 활용한 게시판

## 패키지 구조
![package](https://user-images.githubusercontent.com/74814641/229345077-3f2512a9-3937-465d-9cda-c9b7ab769956.JPG)

## 이메일 인증 동작 과정
1. Front단에서 id, 주민등록번호 앞자리, 뒷자리, 이메일을 emailSendAction 컨트롤러로 넘긴다.

![캡처](https://user-images.githubusercontent.com/74814641/229347454-591089ea-d795-41c9-9662-3af3eee9b01d.JPG)

2. 전달받은 id, 주민등록번호, 이메일로 result를 알아야 한다. 즉 해당 정보를 만족하는 계정이 없으면 null일 것이고 만족하는 계정이 있다면 else구문이 실행될 것이다.

~~~
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
			  String host = "http://localhost:8002/springbbs/";
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
~~~
3. 이메일을 얻는 코드인데 전달받은 ID, Email를 가지고 해당 SQL문을 실행시킨 후 나온 결과값 중 비밀번호를 체크한다. 비밀번호는 DB에 해쉬값으로 저장되어 있기 때문에 Bcrpyt 라이브러리 함수를 사용하여 비밀번호 체크를 해야한다. 비밀번호 체크가 되면 해당 이메일을 리턴한다.

~~~
	@Override
	public String getUserEmail(final String id, final String firstnum,final String secondnum,final String email) {
		String SQL = "SELECT userEmail,userNum from user where userID = ? and userEmail = ?";		
		final String userNum = firstnum+secondnum;
		List<user> user = null;	
		user = template.query(SQL, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, id);		
				pstmt.setString(2, email);	
			}			
		},  new RowMapper<user>() {
			@Override
			public user mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(BCrypt.checkpw(userNum, rs.getString(2))) {					
  				user mem = new user();
					mem.setEmail(rs.getString(1));			
					return mem;
				}
				return null;
			}			
		});		
		if(user.isEmpty()) return null;
		else return user.get(0).getEmail();	
	
	}
~~~

4. 모든 조건을 만족하면 다음과 같은 content로 메일을 전송하게 된다. 여기서 리턴받은 이메일값을 SHA256으로 암호화한 후 해당 값을 보내준다.

~~~
String content = "다음 링크에 접속하여 이메일 확인을 진행하세요." +
		            "<a href='" + host + "emailCheckAction?code=" + new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
                
~~~
![캡처](https://user-images.githubusercontent.com/74814641/229348989-0d4e6a73-28b0-45c3-a1ee-58f9d78dbdcf.JPG)

5. 사용자가 링크를 클릭하면 emailCheckAction 컨트롤러를 호출한다.
~~~
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
~~~

6. Get방식으로 이메일해시값을 받는다. 해당 ID(반드시 세션이 유지되어 있어야 함)를 세션으로 받아오고 해당 ID에 이메일해쉬코드값과 GET으로 받은 이메일 해시값과 비교한다. 일치하면 비밀번호를 변경할 수 있고 그렇지 않으면 유효하지 않은 코드라는 경고창을 출력한다.
~~~
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
~~~

## BCRPYT를 사용한 중요데이터 암호화
![캡처](https://user-images.githubusercontent.com/74814641/229349397-40d408d5-a8bb-4db6-a162-c37abdb18204.JPG)

## 페이징 처리 - 게시판
페이징 처리란 데이터 베이스에 저장된 데이터가 소량일 경우는 상관이 없지만, 대량일 경우 한꺼번에 불러와서 처리하는 것은 비효율적이다. 그 이유는 사용자가 직접 보는 화면에는 소량의 데이터만 필요하기 때문이다. 

![carbon](https://user-images.githubusercontent.com/74814641/160281871-b7ebbc90-6668-428e-8577-57f77bac259a.png)
위 코드는 DB에서 게시판 데이터를 불러와 ArrayList에 저장한 뒤 리턴하는 코드의 일부이다. 1페이지일 때 실행되는 코드로 쿼리문은 bbs(게시판)테이블에서 데이터를 불러오되 bbsID가 getNext()(이 함수는 데이터가 저장될 때 그 다음 bbsID를 가져오는 함수)보다 작고 bbsAvailable(1이면 존재, 0이면 삭제)가 1이고 bbsID를 내림차순으로 정렬하고 10개만 가져오는 쿼리이다. 

![image](https://user-images.githubusercontent.com/74814641/160282559-cd442204-c56c-4452-bc72-02dc8444d74a.png)
위에 이미지를 보게되면 1페이지 임으로 해쉬맵 변수 head_list에 key값으로 1, value로는 23이 저장된다. tail_list에도 마찬가지로 key값으로 1, value로는 10이 저장된다.

![image](https://user-images.githubusercontent.com/74814641/160282901-5b9c5fda-7c17-43ac-b894-7b43d498b95d.png)
위에 이미지를 보게되면 2페이지 임으로 해쉬맵 변수 head_list에 key값으로 2, value로는 9가 저장된다. tail_list에도 마찬가지로 key값으로 2, value로는 1이 저장된다.
 
![image](https://user-images.githubusercontent.com/74814641/160282791-09933af6-c284-40bb-a267-7bb00dedaaa1.png)
위에 이미지는 해쉬맵에 저장된 head_list, tail_list를 가지고 데이터를 가져오는 코드이다. 이렇게 하여 페이지를 자유롭게 이동하여도 PageNumber를 key값으로 하여 해쉬맵에서 bbsID를 가져와서 데이터를 가져온다. 이렇게 하여 게시판 페이징 처리를 구현하였다.


## 페이징 처리 - 댓글
댓글에 모든 데이터를 불러와서 처리하기 보다는 현재 화면에 필요한 정보의 데이터만 가져오도록 구현하였다. 그 방법은 스크롤바가 화면 맨 아래에 닿았을 때 AJAX를 활용하여 10개의 데이터만 가져오도록 구현하였다. 

![image](https://user-images.githubusercontent.com/74814641/160283538-ca22fc79-773b-4be5-bf12-34b0741ef450.png)
JQuery를 사용하였고 스크롤바가 화면 맨 아래에 있는지 확인하는 조건문이 있다. 그 조건을 만족한다면 AJAX를 활용하여 새로고침하지 않아도 댓글을 볼 수 있도록 하였다. replyListAction.jsp에서 전달받은 문자열을 가공처리하고 화면에 나타나는 함수이다.

![ezgif com-gif-maker](https://user-images.githubusercontent.com/74814641/160284125-e9f5497e-5f9d-45d9-b530-44547012d7bf.gif)                        
댓글 페이징 처리이다.

## 시연영상
https://youtu.be/AlJBiZWza0w
