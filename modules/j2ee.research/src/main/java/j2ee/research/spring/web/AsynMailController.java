package j2ee.research.spring.web;

import j2ee.research.spring.service.MailManager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.DateUtil;


@Controller
@RequestMapping("/mail")
public class AsynMailController {
	private static Logger log=LoggerFactory.getLogger(AsynMailController.class);
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Resource
	private MailManager mailManager;
	@RequestMapping("/asynMail.do")
	@ResponseBody
	public String asynMail(){
		log.info("asynMail start..");
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try{
					mailManager.sendMessage("yw2325@qq.com","subject-"+DateUtil.now(),"<!doctype html><html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='application-name' content='网易电子邮箱 - 极速4.0' /><link rel='shortcut icon' href='http://mimg.127.net/p/images/favicon3.ico' type='image/x-icon'/><title>网易电子邮箱 - 极速4.0</title><style type='text/css'>.Patch118-safe-tit{ border-bottom:#DADADA 1px solid; padding:15px 0 25px 86px; position:relative; zoom:1}.Patch118-safe-tit .ico{ position:absolute; left:20px; top:10px}.Patch118-safe-ct{ padding:20px 25px; line-height:22px}</style></head><body style='margin:0;padding:0;overflow:hidden' scroll='no'><a href='javascript:void(0)' onclick='' class='gWel-lt-unread-a'>关联邮箱&nbsp;<strong id='stWelcomeRelate' class='txt-impt'></strong>&nbsp;封</a></body></html>");
				}catch(Exception e){
					log.error(e.getMessage(),e);
				}
			}
		});
		return "true";
	}
	@RequestMapping("/asynMailAndGet.do")
	@ResponseBody
	public String asynMailAndGet(){
		log.info("asynMailAndGet start..");
		Future<String> future=threadPoolTaskExecutor.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				try{
					mailManager.sendMessage("yw2325@163.com","subject-"+DateUtil.now(),"<!doctype html><html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='application-name' content='网易电子邮箱 - 极速4.0' /><link rel='shortcut icon' href='http://mimg.127.net/p/images/favicon3.ico' type='image/x-icon'/><title>网易电子邮箱 - 极速4.0</title><style type='text/css'>.Patch118-safe-tit{ border-bottom:#DADADA 1px solid; padding:15px 0 25px 86px; position:relative; zoom:1}.Patch118-safe-tit .ico{ position:absolute; left:20px; top:10px}.Patch118-safe-ct{ padding:20px 25px; line-height:22px}</style></head><body style='margin:0;padding:0;overflow:hidden' scroll='no'><a href='javascript:void(0)' onclick='' class='gWel-lt-unread-a'>关联邮箱&nbsp;<strong id='stWelcomeRelate' class='txt-impt'></strong>&nbsp;封</a></body></html>");
				}catch(Exception e){
					//log.error(e.getMessage(),e);
					return e.getMessage();
				}
				return "send successfull!";
			}
		});
		try{
			return future.get();//一旦调用了get()方法，如果线程还未产生返回值，则将阻塞get()方法，直到得到返回值。
		}catch(InterruptedException e){
			e.printStackTrace();
		}catch(ExecutionException e){
			e.printStackTrace();
		}
		return "false";
	}
}
