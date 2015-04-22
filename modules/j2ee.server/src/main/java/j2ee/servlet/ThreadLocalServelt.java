package j2ee.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ClassUtil;
import utils.RequestUtil;

public class ThreadLocalServelt extends HttpServlet{
	private static final long	serialVersionUID	=1200344624186476730L;
	private String name=getThreadName();
	private transient ThreadLocal<String> threadLocal=new ThreadLocal<String>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		threadLocal.set(getThreadName());
		ClassUtil.sleep(3000);
		System.out.println("SimpleServelt.doGet:name="+name+"	threadLocalName="+threadLocal.get()+"	getThreadName:"+getThreadName());
		RequestUtil.responseData(resp,"SimpleServelt.doGet..","html");
		//super.doGet(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SimpleServelt.doPost..");
		RequestUtil.responseData(resp,"SimpleServelt.doPost..","html");
		//super.doPost(req,resp);
	}
	
	String getThreadName(){
		return Thread.currentThread().getName();
	}
}
