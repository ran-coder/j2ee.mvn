package utils.net;

import com.google.common.io.ByteStreams;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-4 上午10:37:59
 *   
 */
public class Httpclient3Util {
	private static org.slf4j.Logger log=org.slf4j.LoggerFactory.getLogger(Httpclient3Util.class);
	public static byte[] sendByGet(String uri, Map<String, String> map) {
		return sendByGet(uri,map,"text/html; charset=UTF-8");
	}
	public static byte[] sendByGet(String uri, Map<String, String> map,String contentType) {
		HttpClient client=new HttpClient();

		GetMethod getMethod=new GetMethod(uri);
		if(contentType!=null)getMethod.addRequestHeader("Content-type",contentType);
		if(map!=null){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			for(Map.Entry<String, String> enrty:map.entrySet()){
				params.add(new NameValuePair(enrty.getKey(),enrty.getValue()));
			}
			getMethod.setQueryString(EncodingUtil.formUrlEncode(params.toArray(new NameValuePair[params.size()]),"UTF-8"));
		}
		try{
			client.executeMethod(getMethod);
			//someRequest.getResponseBodyAsStream();
			if(getMethod.getStatusCode()==200){
				return ByteStreams.toByteArray(getMethod.getResponseBodyAsStream());
				//return someRequest.getResponseBody();
			}
		}catch(Exception e){
			getMethod.abort();
			log.error("",e);
		}finally{
			getMethod.releaseConnection();
			log.info("get:"+uri);
		}
		return null;
	}
}
