package utils.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonServlet {

	public static void printJson(HttpServletRequest request, HttpServletResponse response, String data) {
		response.setHeader("ragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires",0);
		OutputStream os=null;
		try{
			os=response.getOutputStream();
			os.write(data.getBytes());
			os.flush();
		}catch(IOException e){
			//e.printStackTrace();
		}finally{
			if(os!=null){
				try{
					os.close();
				}catch(IOException e){
					//e.printStackTrace();
				}
			}
		}
	}
}