package utils.mvn;
/**  
 * @author yuanwei  
 * @version ctreateTime:2012-5-7 下午2:59:26
 *   
 */
public class Dependency {
	private String groupId;
	private String artifactId;
	private String version;
	private String type;

	public Dependency(){}
	public Dependency(String groupId, String artifactId, String version, String type) {
		super();
		this.groupId=groupId;
		this.artifactId=artifactId;
		this.version=version;
		this.type=type;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId=groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId=artifactId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version=version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type=type;
	}
	@Override
	public int hashCode() {
		final int prime=31;
		int result=1;
		result=prime * result + ((artifactId == null) ? 0 : artifactId.hashCode());
		result=prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result=prime * result + ((type == null) ? 0 : type.hashCode());
		result=prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Dependency other=(Dependency)obj;
		if(artifactId == null){
			if(other.artifactId != null) return false;
		}else if(!artifactId.equals(other.artifactId)) return false;
		if(groupId == null){
			if(other.groupId != null) return false;
		}else if(!groupId.equals(other.groupId)) return false;
		if(type == null){
			if(other.type != null) return false;
		}else if(!type.equals(other.type)) return false;
		if(version == null){
			if(other.version != null) return false;
		}else if(!version.equals(other.version)) return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("Dependency [groupId=");
		builder.append(groupId);
		builder.append(", artifactId=");
		builder.append(artifactId);
		builder.append(", version=");
		builder.append(version);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
