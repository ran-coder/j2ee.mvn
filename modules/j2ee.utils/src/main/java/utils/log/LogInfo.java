package utils.log;

import java.io.File;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class LogInfo {
	private Logger	log	= null;

	public LogInfo(String patten) {
		log = Logger.getLogger(patten);
		if(log==null)return;
		File f = new File(this.getClass().getResource("/").getPath());
		//System.out.println(f.getPath());
		PropertyConfigurator.configure(f.getPath() + "/log4j.properties");
	}
	public LogInfo(Object obj) {
		System.out.println(obj.getClass().getName());
		log = Logger.getLogger(obj.getClass().getName());
		if(log==null)return;
		File f = new File(this.getClass().getResource("/").getPath());
		//System.out.println(f.getPath());
		PropertyConfigurator.configure(f.getPath() + "/log4j.properties");
	}

	public LogInfo() {
		log = Logger.getLogger("com.base.util.LogUtil");
		if(log==null)return;
		File f = new File(this.getClass().getResource("/").getPath());
		//System.out.println(f.getPath());
		PropertyConfigurator.configure(f.getPath() + "/log4j.properties");
	}
	
	public String getString(Object[] obj) {
		if(obj!=null && obj.length>0) {
			StringBuffer rs=new StringBuffer("");
			for (int i = 0; i < obj.length; i++) {
				Object object = obj[i];
				if(object!=null)
					rs.append(object.toString());
			}
			return (rs.toString());
		}
		return "";
	}

}
