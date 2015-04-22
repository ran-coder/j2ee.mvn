package utils.mvn;

import com.google.common.io.Files;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import utils.net.Httpclient3Util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author yuanwei
 * @version ctreateTime:2012-5-7 下午3:37:42
 */
public class MavenUtil {
	public static final String REPOSITORY_DIR="D:/Server/lib/repository/mvn/";
	public final static String REPO_URI="http://repo1.maven.org/maven2/";
	public static void downDependency(String groupId, String artifactId, String version){
		downDependency(groupId,artifactId,version,null,true);
	}
	public static void downDependency(String groupId, String artifactId, String version,boolean ignoreExists){
		downDependency(groupId,artifactId,version,null,ignoreExists);
	}
	public static void downDependency(String groupId, String artifactId, String version, String type,boolean ignoreExists){
		downDependency(groupId,artifactId,version,type,null,null,ignoreExists);
	}
	public static void downDependency(String groupId, String artifactId, String version, String type,String repositoryPath,String repoUri,boolean ignoreExists){
		//down(new Dependency("org.apache.openjpa","openjpa-all","2.1.0",null));
		if(repositoryPath==null)repositoryPath=REPOSITORY_DIR;
		if(repoUri==null)repoUri=REPO_URI;
		down(new Dependency(groupId,artifactId,version,type),repositoryPath,repoUri,ignoreExists);
	}
	public static void down(String pomPath) {
		down(pomPath,REPOSITORY_DIR,REPO_URI);
	}
	public static void down(String pomPath,boolean ignoreExists) {
		down(pomPath,REPOSITORY_DIR,REPO_URI,ignoreExists);
	}
	public static void down(String pomPath,String repositoryPath,String repoUri){
		down(pomPath,repositoryPath,repoUri,true);
	}
	public static void down(String pomPath,String repositoryPath,String repoUri,boolean ignoreExists) {
		MavenProperty mavenProperty=readConfig(pomPath);
		//System.out.println(mavenProperty.getDependencies());
		for(String dependency:mavenProperty.getDependencies()){
			saveDependency(dependency,repoUri,repositoryPath,ignoreExists);
		}
	}
	public static void down(Dependency dependency) {
		down(dependency,REPOSITORY_DIR,REPO_URI,true);
	}
	public static void down(Dependency dependency,String repositoryPath,String repoUri,boolean ignoreExists) {
		MavenProperty mavenProperty=new MavenProperty().addDependency(dependency);
		//System.out.println(mavenProperty.getDependencies());
		for(String dp:mavenProperty.getDependencies()){
			saveDependency(dp,repoUri,repositoryPath,ignoreExists);
		}
	}

	static MavenProperty readConfig(String pomPath){
		SAXReader saxReader=new SAXReader();
		Document document=null;
		try{
			document=saxReader.read(new File(pomPath));
		}catch(DocumentException e){
			e.printStackTrace();
		}
		if(document==null)return null;
		MavenProperty mavenProperty=new MavenProperty();
		@SuppressWarnings("unchecked")
		List<Element> propertieElements=document.getRootElement().element("properties").elements();
		for(Element e:propertieElements){
			//System.out.println(e.getName()+":"+e.getData());
			mavenProperty.putProperty("${"+e.getName()+'}',e.getData().toString());
		}
		//System.out.println(StringUtil.mapToString(mavenProperty.getProperties()));
		@SuppressWarnings("unchecked")
		Iterator<Element> dependencies =document.getRootElement().element("dependencies").elementIterator("dependency");

		Dependency dependency=null;
		while(dependencies.hasNext()){
			Element ele = dependencies.next();
			//System.out.println(dependency.asXML());
			dependency=new Dependency();
			dependency.setGroupId(ele.elementTextTrim("groupId"));
			dependency.setArtifactId(ele.elementTextTrim("artifactId"));
			dependency.setVersion( mavenProperty.getProperty(ele.elementTextTrim("version")) );
			//System.out.println(ele.elementTextTrim("version")+":"+mavenProperty.getProperty(ele.elementTextTrim("version")));
			dependency.setType(ele.elementTextTrim("type"));
			//System.out.println(dependency);
			mavenProperty.addDependency(dependency);
			dependency=null;
		}
		return mavenProperty;
	}
	static void saveDependency(String dependency,String repo_uri,String repositoryPath){
		saveDependency(dependency,repo_uri,repositoryPath,true);
	}
	static void saveDependency(String dependency,String repo_uri,String repositoryPath,boolean ignoreExists){
		String dependencyURI=repo_uri+dependency;
		File save=new File(repositoryPath+dependency);

		if(!ignoreExists){
			System.out.println("exists:"+repositoryPath+dependency);
			return;
		}
		System.out.println("downloading:"+dependencyURI);
		//InputStream inputStream=Httpclient3Util.getHttpClient(dependencyURI,null);
		System.out.println("downloading:"+dependencyURI);
		byte[] bytes=Httpclient3Util.sendByGet(dependencyURI,null);



		try{
			Files.createParentDirs(save);
		}catch(IOException e){
			e.printStackTrace();
		}
		if(save.exists() && !save.delete())return;
		if(bytes==null){
			System.out.println("downloaded fail:"+dependencyURI);
			return;
		}else{
			System.out.println("downloaded:"+dependencyURI+"	"+bytes.length);
			try{
				//Files.write(ByteStreams.toByteArray(inputStream),save);
				Files.write(bytes,save);
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				//Closeables.closeQuietly(inputStream);
			}
		}
	}

	public static void main(String[] args) {
		//down("D:/Server/IDE/eclipseWork/j2ee.mvn/modules/j2ee.util/pom.xml",REPOSITORY_DIR,REPO_URI);
		//down(new Dependency("org.apache.openjpa","openjpa-all","2.1.0",null));
		downDependency("org.apache.derby","derby","10.8.1.2");
	}
}
