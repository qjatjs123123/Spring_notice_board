package com.pjt.springbbs.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator
{	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("qjatjs123123@gmail.com","ghdhbs159!@");
	}

}
