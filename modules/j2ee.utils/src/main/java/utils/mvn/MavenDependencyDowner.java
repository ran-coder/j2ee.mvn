package utils.mvn;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.common.io.Files;
import utils.StringUtil;
import utils.net.Httpclient3Util;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2014-04-25 11:22
 * To change this template use File | Settings | File Templates.
 */
public class MavenDependencyDowner{
	public static final String REPOSITORY_DIR="D:/Server/lib/repository/mvn/";
	public final static String REPO_URI="http://repo1.maven.org/maven2/";
	private final static boolean IGNORE_EXIST_FILE=true;
	private boolean ignoreExists=IGNORE_EXIST_FILE;
	private Set<String> dependencyUrls;
	private String repositoryPath=REPOSITORY_DIR;
	private String repoUri=REPO_URI;
	public MavenDependencyDowner(){}
	public MavenDependencyDowner(boolean ignoreExists){
		//this.ignoreExists=ignoreExists;
		this(null,null,ignoreExists);
	}
	public MavenDependencyDowner(String repositoryPath,String repoUri,boolean ignoreExists){
		if(!StringUtil.isEmpty(repositoryPath))this.repositoryPath=repositoryPath;
		if(!StringUtil.isEmpty(repoUri))this.repoUri=repoUri;
		this.ignoreExists=ignoreExists;
	}
	public MavenDependencyDowner addDependency(String groupId, String artifactId, String version){
		return addDependency(groupId,artifactId,version,null);
	}
	public MavenDependencyDowner addDependency(String groupId, String artifactId, String version, String type){
		MavenProperty mavenProperty=new MavenProperty().addDependency(new Dependency(groupId,artifactId,version,type));
		//System.out.println(mavenProperty.getDependencies());
		for(String url:mavenProperty.getDependencies()){
			addDependencyUrl(url);
		}

		return this;
	}
	public MavenDependencyDowner addDependencyUrl(String dependencyUrl){
		if(dependencyUrls==null||dependencyUrls.isEmpty())dependencyUrls=new HashSet<String>();
		dependencyUrls.add(dependencyUrl);
		return this;
	}
	public MavenDependencyDowner addPom(String pomPath){
		MavenProperty mavenProperty=MavenUtil.readConfig(pomPath);
		//System.out.println(mavenProperty.getDependencies());
		for(String url:mavenProperty.getDependencies()){
			addDependencyUrl(url);
		}
		return this;
	}
	void saveDependencyUrl(String dependencyUrl){
		String dependencyURI=repoUri+dependencyUrl;
		File save=new File(repositoryPath+dependencyUrl);

		if(!ignoreExists){
			System.out.println("exists:"+repositoryPath+dependencyUrl);
			return;
		}
		//System.out.println("downloading:"+dependencyURI);
		//InputStream inputStream=Httpclient3Util.getHttpClient(dependencyURI,null);
		System.out.println("downloading:"+dependencyURI);
		byte[] bytes=Httpclient3Util.sendByGet(dependencyURI, null);



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
			System.out.println("downloaded :"+dependencyURI+"	"+bytes.length);
			System.out.println("save to:"+repositoryPath+dependencyUrl);
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
	public void down(){
		if(dependencyUrls==null||dependencyUrls.isEmpty())return;
		for(String url:dependencyUrls){
			saveDependencyUrl(url);
		}
	}

	public static void main(String[] args){
		//new MavenDependencyDowner().addDependencyUrl("org/apache/shiro/shiro-web/1.2.3/shiro-web-1.2.3-sources.jar").down();
		new MavenDependencyDowner(false).addDependencyUrl("org/apache/shiro/shiro-web/1.2.3/shiro-web-1.2.3-sources.jar").down();
		new MavenDependencyDowner(false).addPom("D:\\Server\\IDE\\idea\\IDEA12\\IdeaProjects\\shiroApps\\shiroConfig\\pom.xml").down();
	}
}
