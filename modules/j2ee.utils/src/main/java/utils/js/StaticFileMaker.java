package utils.js;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import utils.DateUtil;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.OutputSupplier;

public class StaticFileMaker {
	private Set<String> csss;
	private Set<String> scripts;
	private String baseDir;
	private final static String ENCODING="UTF-8";
	public StaticFileMaker setBaseDir(String baseDir){
		this.baseDir=baseDir;
		return this;
	}
	public StaticFileMaker addCss(String css){
		if(csss==null)csss=new LinkedHashSet<String>();
		if(css!=null&&css.trim().length()>0)csss.add(css);
		return this;
	}
	public StaticFileMaker addScript(String script){
		if(scripts==null)scripts=new LinkedHashSet<String>();
		if(script!=null&&script.trim().length()>0)scripts.add(script);
		return this;
	}
	public void makeAll(Set<String> set,String allFileName){
		if(set!=null&&!set.isEmpty()){
			File targetFile=new File(allFileName);
			File parent=targetFile.getParentFile();
			if(!parent.exists()&&!parent.mkdirs()){
				throw new IllegalArgumentException("targetFile parent can not make!");
			}
			if(targetFile.exists()){
				//targetFile.deleteOnExit();
				try{
					ByteStreams.write(("/*************** "+(DateUtil.toString(new Date(),"yyyy-MM-dd HH:mm:ss"))+" ***************/\n").getBytes(ENCODING),
							Files.newOutputStreamSupplier(targetFile,false));
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			try{
				if(!targetFile.exists()&&!targetFile.createNewFile()){
					throw new IllegalArgumentException("targetFile is not exists!");
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			OutputSupplier<FileOutputStream> outputSupplier=Files.newOutputStreamSupplier(targetFile,true);
			for(String a:set){
				//data=FileUtils.readFileToByteArray(new File);
				//FileUtils.writeByteArrayToFile(allCssFile,data);
				readFile2All(baseDir,a,outputSupplier);
			}
		}
	}
	public void makeAllCss(String allCss){
		makeAll(csss,allCss);
	}
	public void makeAllScript(String allScript){
		makeAll(scripts,allScript);
	}
	
	public static void readFile2All(String baseDir,String name,OutputSupplier<FileOutputStream> outputSupplier){
		byte[] data=null;
		try{
			data=FileUtils.readFileToByteArray(new File(baseDir+name));
		}catch(IOException e){
			System.out.println(e.toString());
		}
		if(data!=null){
			try{
				//FileUtils.writeByteArrayToFile(allCssFile,("/*************** "+(name)+" ***************/").getBytes(ENCODING));
				ByteStreams.write(("/*************** "+(name)+" ***************/\n").getBytes(ENCODING),outputSupplier);
				ByteStreams.write(data,outputSupplier);
				//FileUtils.writeByteArrayToFile(allCssFile,data);
			}catch(IOException e){
				System.out.println(e.toString());
			}
		}else{
			System.out.println(name+" data is null!");
		}
	}
	public static void main(String[] args) {
		StaticFileMaker maker=new StaticFileMaker()
			.setBaseDir("D:/Server/src/crm/web/")
			.addScript("scripts/jquery-1.4.4.min.js")
			.addScript("scripts/plugin/jquery.bgiframe.js")
			.addScript("scripts/plugin/validation/jquery.validate.js")
			.addScript("scripts/plugin/validation/validationrule.js")
			.addScript("scripts/plugin/ui/jquery-ui-1.8.2.custom.js")
			.addScript("scripts/plugin/ui/jquery.ui.dialog.position.js")
			.addScript("scripts/plugin/ui/jquery.layout_1.2.2beta.js")
			.addScript("scripts/plugin/overlay/jquery.tools.min.js")//http://ran/crm/scripts/plugin/overlay/jquery.tools.min.js
			.addScript("scripts/plugin/hoverIntent/jquery.hoverIntent.minified.js")
			.addScript("scripts/plugin/form/jquery.form.js")
			.addScript("scripts/plugin/tooltip/jquery.cluetip.js")
			.addScript("scripts/plugin/xml/jquery.xmldom-1.0.min.js")
			.addScript("scripts/plugin/overlay/jquery.pop3.js")
			.addScript("scripts/plugin/center/jquery.center.js")
			.addScript("scripts/plugin/overlay/jquery.tools.min.js")
			.addScript("scripts/plugin/modal/jquery.simplemodal-1.3.3.min.js")
			.addScript("scripts/plugin/flashintegration/JavaScriptFlashGateway.js")
			.addScript("scripts/crmutil.js")
			.addScript("scripts/header.js")
		;
		maker.addCss("styles/puzzlewithstyle3/tools.css")
			.addCss("styles/puzzlewithstyle3/typo.css")
			.addCss("styles/puzzlewithstyle3/layout-navtop-subright.css")
			.addCss("styles/puzzlewithstyle3/layout.css")
			.addCss("styles/displaytag.css")
			.addCss("styles/puzzlewithstyle3/forms.css")
			.addCss("styles/messages.css")
			.addCss("styles/puzzlewithstyle3/nav-horizontal.css")
			.addCss("styles/tablesorter.css")
			.addCss("styles/table/bluetable.css")
			.addCss("styles/table/niko.css")
			.addCss("styles/table/10min.css")
			.addCss("styles/table/muffin.css")
			.addCss("styles/table/cnc.css")
			.addCss("styles/table/coffee-with-milk.css")
			.addCss("styles/table/datatable.css")
			.addCss("styles/table/bluetable.css")
			.addCss("styles/overlay-minimal.css")
			.addCss("scripts/plugin/tooltip/jquery.cluetip.css")
			.addCss("styles/ui/cupertino/jquery-ui-1.8.custom.css")
			.addCss("styles/modal.css")
			.addCss("scripts/plugin/modal/basic.css")
		;

		maker.makeAllScript("D:/Server/src/crm/web/static/all20120803.js");
		maker.makeAllCss("D:/Server/src/crm/web/static/all20120803.css");

	}

}
