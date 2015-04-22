package j2ee.tutorial.google.guava.collect.map;

import com.google.common.collect.Constraint;

public class MPGConstraint implements Constraint<Automobile> {
	private Integer	minimumMPG;

	public MPGConstraint(int minimumMPG) {
		this.minimumMPG=minimumMPG;
	}

	public Automobile checkElement(Automobile element) {
		if(element.getMilesPerGallon() != null && element.getMilesPerGallon() < minimumMPG){ throw new IllegalArgumentException(
				"Automobile does not meet minimum fuel standards"); }
		return element;
	}
}
