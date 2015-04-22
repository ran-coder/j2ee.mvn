package utils.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.EncodingUtil;

public class HttpUtil {
	/**
	 * 向指定URL发送POST方法的请求
	 * @param requestUrl 请求的URL
	 * @param requestParamsMap 请求的参数
	 * @return response 结果
	 * http://xujingbao.javaeye.com/blog/362709
	 */
	public static String sendPost(String requestUrl, Map<String, Object> requestParamsMap) {
		PrintWriter printWriter=null;
		BufferedReader bufferedReader=null;
		StringBuffer rs=new StringBuffer();
		StringBuffer params=new StringBuffer();
		HttpURLConnection conn=null;

		if(requestParamsMap!=null){
			// 组织请求参数
			Iterator<Entry<String, Object>> it=requestParamsMap.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Object> element=it.next();
				params.append(element.getKey());
				params.append("=");
				params.append(element.getValue());
				params.append("&");
			}
			if(params.length() > 0){
				params.deleteCharAt(params.length() - 1);
			}
		}

		try{
			URL realUrl=new URL(requestUrl);
			// 打开和URL之间的连接
			conn=(HttpURLConnection)realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept","*/*");
			conn.setRequestProperty("connection","Keep-Alive");
			conn.setRequestProperty("Content-Length",String.valueOf(params.length()));
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			printWriter=new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			printWriter.write(params.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 根据ResponseCode判断连接是否成功
			//int responseCode = conn.getResponseCode();
			if(conn.getResponseCode() != 200){
				//LOGGER.error(" Error===" + responseCode);
			}else{
				//LOGGER.info("Post Success!");
			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			bufferedReader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line=bufferedReader.readLine()) != null){
				rs.append("\n").append(line);
			}
		}catch(IOException e){
			//LOGGER.error("send post request error!" + e);
		}finally{
			conn.disconnect();
			try{
				if(printWriter != null){
					printWriter.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return rs.toString();

	}

	/**
	 * 使用HttpClient向指定URL send Post请求
	 * @param uri 请求的URL
	 * @param map 请求的参数
	 * @return response 结果
	 */
	public static String sendPostByHttpClient(String uri, Map<String, String> map) {
		HttpClient client=new HttpClient();
		//client.getHostConfiguration().setProxyHost(new ProxyHost("172.17.20.80", 8080));
		//client.getState().setProxyCredentials(AuthScope.ANY,new UsernamePasswordCredentials("name", "pass"));
		PostMethod postMethod=new PostMethod(uri);
		try{
			if(map!=null){
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				for(Map.Entry<String, String> enrty:map.entrySet()){
					params.add(new NameValuePair(enrty.getKey(),enrty.getValue()));
				}
				postMethod.setRequestBody(params.toArray(new NameValuePair[params.size()]));
			}
			// use post
			// execute this method
			client.executeMethod(postMethod);
			//int responseCode = postMethod.getStatusCode();
			if(postMethod.getStatusCode() == HttpStatus.SC_OK){
				//LOGGER.info("Post Success!");
				return postMethod.getResponseBodyAsString();
			}else{
				System.out.println(" Error===" + postMethod.getStatusCode());
			}
		}catch(HttpException he){
			System.out.println("HTTP Problem: " + he.getMessage());
		}catch(IOException ioe){
			System.out.println("IO Exeception: " + ioe.getMessage());
		}finally{
			postMethod.releaseConnection();
		}

		return null;
	}
	public static String sendByGetHttpClient(String uri, Map<String, String> map) {
		HttpClient client=new HttpClient();

		GetMethod someRequest=new GetMethod(uri);
		someRequest.addRequestHeader("Content-type","text/html; charset=UTF-8");
		if(map!=null){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			for(Map.Entry<String, String> enrty:map.entrySet()){
				params.add(new NameValuePair(enrty.getKey(),enrty.getValue()));
			}
			someRequest.setQueryString(EncodingUtil.formUrlEncode(params.toArray(new NameValuePair[params.size()]),"UTF-8"));
		}
		try{
			client.executeMethod(someRequest);
			return someRequest.getResponseBodyAsString();
		}catch(Exception e){
		}finally{
			someRequest.releaseConnection();
		}
		return null;
	}

	public static class CrmHTTPConnUtil {
		public static String sendGetRequest(String url, String context, int port, Map<String, String> map) throws Exception {
			HttpClient client=new HttpClient();

			client.getHostConfiguration().setHost(url,port,"http");
			GetMethod someRequest=new GetMethod(context);
			someRequest.addRequestHeader("Content-type","text/html; charset=GBK");
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			for(Map.Entry<String, String> enrty:map.entrySet()){
				params.add(new NameValuePair(enrty.getKey(),enrty.getValue()));
			}
			someRequest.setQueryString(EncodingUtil.formUrlEncode(params.toArray(new NameValuePair[params.size()]),"GBK"));
			client.executeMethod(someRequest);
			String result=(someRequest.getResponseBodyAsString());
			// System.out.println(result);
			someRequest.releaseConnection();
			return result;
		}

		public static String sendPostRequest(String url, String context, int port, Map<String, String> map, String urlEncode) throws Exception {
			HttpClient client=new HttpClient();
			client.getHostConfiguration().setHost(url,port,"http");
			PostMethod copypo=new PostMethod(context);
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			for(Map.Entry<String, String> enrty:map.entrySet()){
				params.add(new NameValuePair(enrty.getKey(),urlEncode == null || urlEncode.equals("") ? enrty.getValue() : java.net.URLEncoder.encode(enrty.getValue(),urlEncode)));
			}
			copypo.setRequestBody(params.toArray(new NameValuePair[params.size()]));
			client.executeMethod(copypo);
			String editpopage=(copypo.getResponseBodyAsString());
			System.out.println(editpopage);
			copypo.releaseConnection();
			return editpopage;
		}
	}

	public static void main(String[] args) throws Exception {
		//String qs = "mobile=13536466244&email=s1@fltrp.com&customerName=邢小燕&babyName=冯悦&babybirthday=02/17/2008&babygender=0&prov=广东&city=汕尾&postalCode=516477&customerSource=mamashuo";
		//String qs0 = "mobile=13536466244&email=s1@fltrp.com&customerName=%E9%82%A2%E5%B0%8F%E7%87%95";
		String qs2=java.net.URLEncoder.encode("邢小燕","UTF-8");
		System.out.println(qs2);

		Map<String, String> map=new HashMap<String, String>();
		map.put("customerName","邢小燕");
		System.out.println(CrmHTTPConnUtil.sendPostRequest("192.168.39.248","/cplcustomers.html?mobile=13536466244&email=s1@fltrp.com",81,map,"UTF-8"));
		System.out.println(sendPostByHttpClient("http://192.168.39.248:81/crm/cplcustomers.html?mobile=13536466244&email=s1@fltrp.com",map));

	}
}
