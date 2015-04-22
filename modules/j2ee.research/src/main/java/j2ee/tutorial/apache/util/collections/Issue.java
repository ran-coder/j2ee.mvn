package j2ee.tutorial.apache.util.collections;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Issue {

	private long	id;
	private String	severity;
	private String	owner;

	public Issue() {}

	public Issue(long id, String severity, String owner) {
		this.id = id;
		this.severity = severity;
		this.owner = owner;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id)
				.append("severity", severity).append("owner", owner).toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

}