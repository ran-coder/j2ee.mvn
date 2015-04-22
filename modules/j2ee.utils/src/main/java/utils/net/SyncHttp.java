package utils.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import utils.StringUtil;

public class SyncHttp {
	/**
	 * 这是进行第一次授权验证的第一步，这一步真正的连接网络访问服务器验证RequestToken的URL 
	 * 通过GET方式发送请求 请求后将会返回串oauth_token=字符串&oauth_token_secret=字符串&oauth_callback_confirmed=trued
	 * @param url URL地址
	 * @param params 参数
	 * @return
	 * @throws Exception
	 */
	
	private static final int		TIME_OUT	=1000 * 6;	// 超时
	private static final String	METHOD_POST	="POST";
	private static final String	METHOD_GET	="GET";
	private static final int		HTTP_OK		=200;
	private static final String	CHARTSET	="UTF-8";	// 字符编码
	private static final int		BUFFER		=1024 * 8;	// 缓冲区
	
	public String httpGet(String urlStr, String paramsStr) throws Exception {
		// 构造请求的URL
		StringBuilder urlBuilder=new StringBuilder();
		urlBuilder.append(urlStr);
		if(!StringUtil.isEmpty(paramsStr)){
			urlBuilder.append("?");
			urlBuilder.append(paramsStr);
		}
		// Log.i(Constants.TAG, urlBuilder.toString());
		URL url=null;
		HttpURLConnection conn=null;
		InputStream inStream=null;
		String response=null;
		try{
			url=new URL(urlBuilder.toString());
			System.out.println("");
			// 根据URL打开远程连接
			conn=(HttpURLConnection)url.openConnection();
			// 设置参数
			conn.setDoInput(true);
			conn.setConnectTimeout(TIME_OUT);
			conn.setRequestMethod(METHOD_GET);
			conn.setRequestProperty("accept","*/*");
			// 建立连接
			conn.connect();
			// 接受返回码
			int responseCode=conn.getResponseCode();
			if(responseCode == HTTP_OK){
				// 获取输入流
				inStream=conn.getInputStream();
				// 从输入流中获取信息
				response=getResponse(inStream);
			}else{
				// 请求失败
				response="返回码：" + responseCode;
			}
		}catch(Exception e){
			throw e;
		}finally{
			// 关闭连接
			conn.disconnect();
		}
		return response;
	}
	
	/**
	 * 通过POST方式发送请求
	 * @param url URL地址
	 * @param params 参数
	 * @return
	 * @throws Exception
	 */
	
	public String httpPost(String urlStr, String paramsStr) throws Exception {
		// 把参数转换成字符数组
		byte[] data=paramsStr.getBytes();
		URL url=null;
		HttpURLConnection conn=null;
		InputStream inStream=null;
		String response=null;
		try{
			url=new URL(urlStr);
			// 根据URL打开远程连接
			conn=(HttpURLConnection)url.openConnection();
			// 设置参数
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(METHOD_POST);
			conn.setRequestProperty("Connection","Keep-Alive");
			conn.setRequestProperty("Charset",CHARTSET);
			conn.setRequestProperty("Content-Length",String.valueOf(data.length));
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// 建立连接
			conn.connect();
			DataOutputStream outputStream=new DataOutputStream(conn.getOutputStream());
			// 把字节数组写到流中
			outputStream.write(data);
			// 发送数据
			outputStream.flush();
			outputStream.close();
			// 获取返回码
			int responseCode=conn.getResponseCode();
			if(responseCode == HTTP_OK){
				// 获取输入流
				inStream=conn.getInputStream();
				// 从输入流中获取信息
				response=getResponse(inStream);
			}else{
				// 请求失败
				response=responseCode + "";
			}
		}catch(Exception e){
			throw e;
		}finally{
			// 关闭连接
			conn.disconnect();
		}
		return response;
	}
	
	/**
	 * 获取输入流中信息
	 * @param inStream 输入流
	 * @return
	 * @throws IOException
	 */
	private String getResponse(InputStream inStream) throws IOException {
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		int len=-1;
		byte[] buffer=new byte[BUFFER];// 缓冲区
		while((len=inStream.read(buffer)) != -1){
			outputStream.write(buffer,0,len);
		}
		byte[] data=outputStream.toByteArray();
		return new String(data);
	}
	
	/**
	 * 模拟可发送文件的POST方式请求
	 * @param httpUrl 请求URL地址
	 * @param queryString 请求参数
	 * @param files 传送文件集合
	 * @return
	 * @throws Exception
	 */
	public String postWithFile(String httpUrl, String queryString, List<File> files) throws Exception {
		// 数据分隔线
		final String BOUNDARY="---------------------------7da2137580612";
		// 回车
		final String RETURN="\r\n";
		// 前缀
		final String PREFIX="--";
		
		HttpURLConnection conn=null;
		InputStream inStream=null;
		String response=null;
		try{
			URL url=new URL(httpUrl);
			// 根据URL打开远程连接
			conn=(HttpURLConnection)url.openConnection();
			// 设置参数
			conn.setRequestMethod(METHOD_POST);
			conn.setRequestProperty("Connection","Keep-Alive");
			conn.setRequestProperty("Charset",CHARTSET);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + BOUNDARY);
			
			DataOutputStream out=new DataOutputStream(conn.getOutputStream());
			// 传送文本类型参数
			if(queryString != null && !queryString.equals("")){
				// 分割文本类型参数字符串
				String[] params=queryString.split("&");
				for(String str:params){
					if(str != null && !str.equals("")){
						if(str.indexOf("=") > -1){
							String[] param=str.split("=");
							// 获取参数值
							String value=(param.length == 2 ? param[1] : "");
							// 添加数据分隔线
							out.writeBytes(PREFIX + BOUNDARY + RETURN);
							// 添加参数名
							out.writeBytes("Content-Disposition: form-data; name=\"" + param[0] + "\"" + RETURN);
							// 添加\r\n
							out.writeBytes(RETURN);
							// 添加参数的值
							out.write(value.getBytes(CHARTSET));
							// 添加\r\n
							out.writeBytes(RETURN);
						}
					}
				}
			}
			
			// 遍历文件列表
			for(File file:files){
				// 获取文件名
				String fileName=file.getName();
				// 添加数据分隔线
				out.writeBytes(PREFIX + BOUNDARY + RETURN);
				// 添加文件信息
				out.writeBytes("Content-Disposition: form-data; name=\"" + file.getName() + "\"; filename=\"" + fileName + "\"" + RETURN);
				// 添加\r\n
				out.writeBytes(RETURN);
				
				// 创建文件输入流
				FileInputStream fis=new FileInputStream(file);
				// 设置缓冲区
				byte[] buffer=new byte[BUFFER];
				int count=0;
				while((count=fis.read(buffer)) != -1){
					out.write(buffer,0,count);
				}
				fis.close();
				out.writeBytes(RETURN);
			}
			// 添加数据分隔线
			out.writeBytes(PREFIX + BOUNDARY + PREFIX + RETURN);
			out.flush();
			out.close();
			// 获取返回码
			int responseCode=conn.getResponseCode();
			System.out.println("返回码=" + responseCode);
			if(responseCode == HTTP_OK){
				// 获取输入流
				
				inStream=conn.getInputStream();
				// 从输入流中获取信息
				response=getResponse(inStream);
			}else{
				// 请求失败
				response="返回码：" + responseCode;
			}
			
		}catch(IOException e){
			throw e;
		}finally{
			// 关闭连接
			conn.disconnect();
		}
		return response;
	}
}
