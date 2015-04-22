package utils.apache.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Test {
	static String path="D:/Apache/apache2215-crm/201005";
	public static void readLog(){
		List<Log> list=new ArrayList<Log>();
		//D:/Apache/apache2215-crm/logs/access_log_20100913
		FileReader fileReader=null;
		BufferedReader buffer =null;
		try{
			fileReader=new FileReader(new File(path));
			buffer = new BufferedReader(fileReader);
			String currentLine;
				while((currentLine = buffer.readLine())!=null){
					//System.out.println(currentLine);
					if(currentLine!=null && currentLine.indexOf(".html")!=-1 && currentLine.indexOf("saletracelogs.html")==-1){
						list.add(Log.format(currentLine));
						//set.add(Log.format(currentLine));
					}
					//rs.add(currentLine);
				}
		}catch(Exception e){
			System.out.println(e.toString());
		}finally{
			if(buffer!=null) try{
				buffer.close();
			}catch(IOException e1){
				e1.printStackTrace();
			}
			if(fileReader!=null) try{
				fileReader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//Collections.sort(list,LogComparator.UrlIpDateComparator);

		Map<String,Integer> urls=new HashMap<String,Integer>();
		Map<String,Integer> ips=new HashMap<String,Integer>();

		for(Log log:list){
			if(false || log.getStutas()==200){
				Integer count=urls.get(log.getUrl());
				if(count!=null){
					urls.put(log.getUrl(),count+1);
				}else{
					urls.put(log.getUrl(),1);
				}
				count=ips.get(log.getIp());
				if(count!=null){
					ips.put(log.getIp(),count+1);
				}else{
					ips.put(log.getIp(),1);
				}
			}
		}

		List<Map.Entry<String,Integer>> urlSort = new ArrayList<Map.Entry<String,Integer>>(urls.entrySet());
		List<Map.Entry<String,Integer>> ipSort = new ArrayList<Map.Entry<String,Integer>>(ips.entrySet());
		Collections.sort(urlSort,LogComparator.orderComparator);
		Collections.sort(ipSort, LogComparator.orderComparator);

		System.out.println(org.apache.commons.lang.StringUtils.center(" url ",35,"*"));
		for(Map.Entry<String,Integer> entry : urlSort.subList(0,10)){
			System.out.println(entry.getKey()+"	"+entry.getValue());
		}
		System.out.println(org.apache.commons.lang.StringUtils.center(" ip ",35,"*"));
		for(Map.Entry<String,Integer> entry : ipSort.subList(0,ipSort.size()>10?10:ipSort.size())){
			System.out.println(entry.getKey()+"	"+entry.getValue());
		}
	}
	
	public static void main(String[] args) {
		readLog();
		//System.out.println(DateUtil.format(DateUtil.getDate("02/Jul/2010:21:40:41 +0800","dd/MMM/yyyy:HH:mm:ss Z")	,"yyyy-MM-dd HH:mm:ss"));
		//System.out.println(DateUtil.format(new Date(),"dd/MM/yyyy:HH:mm:ss Z"));
	}
}
