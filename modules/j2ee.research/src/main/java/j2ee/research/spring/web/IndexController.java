package j2ee.research.spring.web;


import j2ee.research.spring.service.IndexService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	private static Logger log=LoggerFactory.getLogger(IndexController.class);
	@Autowired
	@Value("#{constants['app.name']}")
	private String name;
	@Resource(name="indexService")
	private IndexService indexService;


	@RequestMapping("/index.do")
	@ResponseBody
	public String index(){
		log.info("indexService:indexService.getName()="+indexService.getName()+"	name="+name);
		return name;
	}
}
