package j2ee.research.spring.web;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.js.JacksonUtil;

@Controller
public class JsonController {
	private static Logger log=LoggerFactory.getLogger(JsonController.class);
	@Autowired
	@Value("#{constants['app.name']}")
	private String name;


	@RequestMapping("/json.do")
	@ResponseBody
	public String json(){
		log.info("entering 'json' method...");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("appname",name);
		map.put("name","中文测试");
		return JacksonUtil.toJson(map);
	}
}
