package utils.js;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


/**  
 * @author yuanwei  
 * @version ctreateTime:2012-6-29 上午11:08:46
 *   
 */
public class JacksonUtil {
	public static String toJson(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.writeValueAsString(obj);
		}catch(JsonGenerationException e){
			e.printStackTrace();
		}catch(JsonMappingException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
