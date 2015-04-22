package utils.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPOutputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import utils.DateUtil;

public class GzipFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}

	private static int			delayTime	=-1;

	private static Set<String>	pageSet		=null;

	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) 
		throws IOException,	ServletException {
		HttpServletRequest requestHttp=(HttpServletRequest)request;
		String __USERNAME=(String)requestHttp.getSession().getAttribute("SESSION_LOGIN_USERNAME");
		String ctx=requestHttp.getContextPath();
		synchronized(this){
			if(pageSet == null){
				//Set<String> o=((NotTracePageSet)Factory.getBean("NotTracePageSet")).getSet();
				Set<String> o=new HashSet<String>();
				o.add("/touch.jsp");
				o.add("/include/menu.inc.jsp");
				o.add("/include/foruminfo.inc.jsp");
				o.add("/image.jsp");
				pageSet=new HashSet<String>();
				for(String s:o){
					pageSet.add(ctx + s);
				}
			}
		}
		if(delayTime < 0){
			//设置延时时间
			delayTime=100;
		}
		long begin=System.currentTimeMillis();
		String url=requestHttp.getRequestURL().toString();
		String qs=requestHttp.getQueryString();
		String size="";
		HttpServletResponse resp=(HttpServletResponse)response;
		String transferEncoding=getGZIPEncoding((HttpServletRequest) request);
		if(transferEncoding == null){
			try{
				if(__USERNAME != null){
					synchronized(__USERNAME){
						chain.doFilter(request,response);
						//Thread.sleep(delayTime); // 限制每个用户的速度
						//wait(delayTime);
					}
				}else{
					chain.doFilter(request,response);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			ReduceWrapper wrapper=new ReduceWrapper(resp);
			try{
				chain.doFilter(request,wrapper);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			byte[] gzipData=wrapper.getResponseData();
			if(gzipData.length > 128){
				gzipData=gzip(wrapper.getResponseData());
				size=wrapper.getResponseData().length + "=>" + gzipData.length;
				resp.addHeader("Content-Encoding","gzip");
				resp.setContentLength(gzipData.length);
			}
			ServletOutputStream output=response.getOutputStream();
			output.write(gzipData);
			output.flush();
		}
		String uri=requestHttp.getRequestURI();
		if(!pageSet.contains(uri)){
			System.out.println("<DATE>" + DateUtil.now() + "</DATE><COST>"
					+ (System.currentTimeMillis() - begin - (__USERNAME != null ? delayTime : 0)) + "</COST><USER>"
					+ (__USERNAME == null ? "" : __USERNAME) + "</USER><METHOD>" + requestHttp.getMethod()
					+ "</METHOD><PAGE>" + url + (qs == null ? "" : ("?" + qs)) + "</PAGE><IP>"
					+ request.getRemoteAddr() + "</IP><SIZE>" + size + "</SIZE>");
		}
	}

	private String getGZIPEncoding(HttpServletRequest request) {
		String acceptEncoding=request.getHeader("Accept-Encoding");
		if(acceptEncoding == null){ return null; }
		acceptEncoding=acceptEncoding.toLowerCase();
		if(acceptEncoding.indexOf("x-gzip") >= 0){ return "x-gzip"; }
		if(acceptEncoding.indexOf("gzip") >= 0){ return "gzip"; }
		return null;
	}

	private byte[] gzip(byte[] data) {
		ByteArrayOutputStream byteOutput=new ByteArrayOutputStream(10240);
		GZIPOutputStream output=null;
		try{
			output=new GZIPOutputStream(byteOutput);
			output.write(data);
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			if(output != null){
				try{
					output.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return byteOutput.toByteArray();
	}
}

class ReduceWrapper extends HttpServletResponseWrapper {
	public static final int			OT_NONE		=0, OT_WRITER=1, OT_STREAM=2;

	private int						outputType	=OT_NONE;

	private ServletOutputStream		output		=null;

	private PrintWriter				writer		=null;

	private ByteArrayOutputStream	buffer		=null;

	public ReduceWrapper(HttpServletResponse response) throws IOException {
		super(response);
		buffer=new ByteArrayOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if(outputType == OT_STREAM) throw new IllegalStateException();
		else if(outputType == OT_WRITER) return writer;
		else{
			outputType=OT_WRITER;
			writer=new PrintWriter(new OutputStreamWriter(buffer,getCharacterEncoding()));
			return writer;
		}
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if(outputType == OT_WRITER) throw new IllegalStateException();
		else if(outputType == OT_STREAM) return output;
		else{
			outputType=OT_STREAM;
			output=new WrappedOutputStream(buffer);
			return output;
		}
	}

	@Override
	public void flushBuffer() throws IOException {
		if(outputType == OT_WRITER) writer.flush();
		if(outputType == OT_STREAM) output.flush();
	}

	@Override
	public void reset() {
		outputType=OT_NONE;
		buffer.reset();
	}

	public byte[] getResponseData() throws IOException {
		flushBuffer();
		return buffer.toByteArray();
	}

	static class WrappedOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream	buffer;

		public WrappedOutputStream(ByteArrayOutputStream buffer) {
			this.buffer=buffer;
		}

		@Override
		public void write(int b) throws IOException {
			buffer.write(b);
		}

		public byte[] toByteArray() {
			return buffer.toByteArray();
		}
	}
}
