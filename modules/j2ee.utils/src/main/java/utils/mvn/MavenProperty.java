package utils.mvn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-7 下午3:06:05
 *   
 */
public class MavenProperty {
	private Map<String,String> properties;
	private List<String> dependencies;
	public MavenProperty addDependency(Dependency dependency){
		if(dependencies==null)dependencies=new ArrayList<String>();
		if(dependency!=null && dependency.getGroupId()!=null&& dependency.getArtifactId()!=null){
			//http://repo1.maven.org/maven2/org/apache/cxf/cxf-rt-ws-policy/2.4.3/cxf-rt-ws-policy-2.4.3-sources.jar
			/*
			<dependency>
				<groupId>org.apache.cxf</groupId>
				<artifactId>cxf-rt-frontend-jaxws</artifactId>
				<version>${cxf.version}</version>
			</dependency>
			*/
			if(dependency.getGroupId().indexOf("j2ee")!=-1 || dependency.getGroupId().indexOf("thirdparty")!=-1)return this;
			int len=0;
			StringBuilder url=new StringBuilder();
			url.append(dependency.getGroupId().replaceAll("\\.","/")).append('/')
			.append(dependency.getArtifactId()).append('/')
			.append(dependency.getVersion()).append('/')
			.append(dependency.getArtifactId()).append('-')
			.append(dependency.getVersion())
			//.append('-').append("type").append(".jar")
			;
			len=url.length();
			dependencies.add(url.append(".jar").toString());
			//url.setLength(len);dependencies.add(url.append("-sources").append(".jar").toString());
			url.setLength(len);dependencies.add(url.append("-sources.jar").toString());
			url.setLength(len);dependencies.add(url.append("-javadoc.jar").toString());
			url.setLength(len);dependencies.add(url.append(".pom").toString());
		}
		return this;
	}

	public MavenProperty putProperty(String key,String value){
		if(properties==null)properties=new HashMap<String,String>();
		properties.put(key,value);
		return this;
	}
	public String getProperty(String key){
		if(properties==null)return null;
		if(key.indexOf('$')==-1)return key;
		return properties.get(key);
	}
	public List<String> getDependencies(){
		return this.dependencies;
	}
	public Map<String,String> getProperties(){
		return this.properties;
	}
}
