package utils.apache.log;

import java.util.Comparator;
import java.util.Map;

public class LogComparator {
	public static Comparator<Log> DateIpComparator=new Comparator<Log>(){
		public int compare(Log l0, Log l1) {
			if(l0.getDate().after(l1.getDate())){
				return 1;
			}else if(l0.getDate().before(l1.getDate())){
				return -1;
			}else{
				int len=l0.getIp().compareToIgnoreCase(l1.getIp());
				if(len >0){
					return 1;
				}else if(len <0){
					return -1;
				}
				return 0;
			}
		}
		
	};
	
	public static Comparator<Log> IpDateComparator=new Comparator<Log>(){
		public int compare(Log l0, Log l1) {
			int len=l0.getIp().compareToIgnoreCase(l1.getIp());
			if(len >0){
				return 1;
			}else if(len <0){
				return -1;
			}else{
				if(l0.getDate().after(l1.getDate())){
					return 1;
				}else if(l0.getDate().before(l1.getDate())){
					return -1;
				}else{
					return 0;
				}
			}
		}
		
	};
	public static Comparator<Log> UrlIpDateComparator=new Comparator<Log>(){
		public int compare(Log l0, Log l1) {
			int len=l0.getUrl().compareToIgnoreCase(l1.getUrl());
			if(len >0){
				return 1;
			}else if(len <0){
				return -1;
			}else{
				len=l0.getIp().compareToIgnoreCase(l1.getIp());
				if(len >0){
					return 1;
				}else if(len <0){
					return -1;
				}else{
					if(l0.getDate().after(l1.getDate())){
						return 1;
					}else if(l0.getDate().before(l1.getDate())){
						return -1;
					}else{
						return 0;
					}
				}
			}
		}
		
	};
	
	public static Comparator<Log> UrlComparator=new Comparator<Log>(){

		public int compare(Log o1, Log o2) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	};

	public static Comparator<Map.Entry<String,Integer>> orderComparator=new Comparator<Map.Entry<String,Integer>>(){
		public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {  
			if(o1.getValue()==null){
				if(o2.getValue()==null)return 0;
				return -1;
			}
			if(o2.getValue()==null)return 1;
			return o2.getValue().compareTo(o1.getValue());
		}
	};
}
