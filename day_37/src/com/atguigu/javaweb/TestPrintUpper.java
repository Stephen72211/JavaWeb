package com.atguigu.javaweb;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TestPrintUpper extends SimpleTagSupport {

	private String time;
	
	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public void doTag() throws JspException, IOException {
JspFragment bodyContent = getJspBody();
		
		StringWriter sw = new StringWriter();
		
		bodyContent.invoke(sw);
		
		String content = sw.toString().toUpperCase();
		
		int count = 1;

		try {
			count = Integer.parseInt(time);
		} catch (Exception e) {}
		
		
		for(int i = 0;i < count; i++){
			
			getJspContext().getOut().print(i + 1 + "." + content + "<br>");
			
		}
		
	}
	
}
