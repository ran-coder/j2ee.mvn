package j2ee.research.fw.activemq.com.fltrp.activemq.consumer;

import java.util.Date;
import java.util.Map;

public class MyMessage  {

    private Map<String,Object> model;
    private String type;
    private Date sendTime;
    private Date completeTime;

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String,Object> getModel() {
		return model;
	}
	public void setModel(Map<String,Object> model) {
		this.model = model;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}


}
