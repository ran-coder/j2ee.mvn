package j2ee.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.RequestUtil;

public class SmsServelt extends HttpServlet{
	private static final long	serialVersionUID	=1200344624186476730L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("SimpleServelt.doGet..");
		RequestUtil.responseData(resp,"RETURNMESSAGE_0","html");
		//super.doGet(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("SimpleServelt.doPost..");
		RequestUtil.responseData(resp,"RETURNMESSAGE_0","html");
		//super.doPost(req,resp);
	}
}
