package utils.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

public class FileExample {

	static void copyDir() throws IOException{
		String dir="D:/Server/java/大连早教中心/";
		String web="D:/Server/java/ec/";
		File file=new File(dir);
		File[] dirs=file.listFiles();
		File[] imgs;
		StringBuilder sb=new StringBuilder();
		String name=null,parent=null,fileName=null;
		for(File d:dirs){
			parent=Base64.encodeBase64URLSafeString(d.getName().getBytes());
			FileUtils.forceMkdir(new File(web+parent));
			sb.append(parent).append('\t').append(d.getName()).append('\n');
			imgs=d.listFiles();

			for(File img:imgs){
				fileName=img.getName().replaceAll("[.][^.]+$","");
				name=Base64.encodeBase64URLSafeString(fileName.getBytes());
				sb.append('\t').append(parent).append('/').append(name).append(".jpg").append('\t')
					.append(fileName).append('\n');
				//FileUtils.forceMkdir(new File(web+parent+name));
				FileUtils.copyFile(img,new File(web+parent+"/"+name+".jpg"));
				//ImageUtil.scaleImage(img,new File(web+parent+"/"+name+".jpg"),200,176);
			}
			
			sb.append('\n');
		}
		System.out.println(sb);
	}
	static void toJson() throws IOException{
		String dir="D:/Server/java/大连早教中心/";
		File file=new File(dir);
		File[] dirs=file.listFiles();
		File[] imgs;
		StringBuilder json=new StringBuilder().append('[').append('\n');
		String name=null,parent=null,fileName=null;
		File img=null;
		/**
		 * [{"name":"","dir":"",list:[{n1:"name",d1:'',n2:'',d2:''},{}]}]
		 */
		for(File d:dirs){
			parent=Base64.encodeBase64URLSafeString(d.getName().getBytes());
			////FileUtils.forceMkdir(new File(web+parent));
			imgs=d.listFiles();
			json.append("{'name':'").append(d.getName()).append("','dir':'").append(parent).append("'")
				.append(",'list':[")
				.append('\n');
			for(int i=0,j=imgs.length;i<j;i+=2){
				img=imgs[i];
				fileName=img.getName().replaceAll("[.][^.]+$","");
				name=Base64.encodeBase64URLSafeString(fileName.getBytes());
				//{n1:"name",d1:'',n2:'',d2:''}
				json.append("\t{\n\t\t'n1':'").append(fileName)
				.append("',\n\t\t'd1':'").append("/images/courses/intro/").append(parent).append('/').append(name).append(".jpg")
				.append("',");
				img=imgs[i+1];
				fileName=img.getName().replaceAll("[.][^.]+$","");
				name=Base64.encodeBase64URLSafeString(fileName.getBytes());
				
				json.append("\n\t\t'n2':'").append(fileName)
				.append("',\n\t\t'd2':'").append("/images/courses/intro/").append(parent).append('/').append(name).append(".jpg")
				.append("'\n\t},\n");
			}
			json.setLength(json.length()-2);
			json.append('\n').append(']').append('}').append(',').append('\n');
		}
		json.setLength(json.length()-2);
		json.append('\n').append(']');
		System.out.println(json);
	}
	public static void main(String[] args) throws IOException {
		//copyDir();
		toJson();
	}

}
