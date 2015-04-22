package us.codecraft.webmagic.samples.main;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.samples.QzoneBlogProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-10 14:29
 * To change this template use File | Settings | File Templates.
 */
public class MainUsage{
	public static void main(String[] args){
		QzoneBlogProcessor qzoneBlogProcessor=new QzoneBlogProcessor();
		Spider.create(qzoneBlogProcessor)
		      .scheduler(new FileCacheQueueScheduler("/data/temp/webmagic/cache/"))
		      .addPipeline(new FilePipeline())
		      .thread(10).run();
	}
}
