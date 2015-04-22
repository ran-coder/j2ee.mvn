package j2ee.tutorial.apache.util.jxpath;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private Person			coach;
	private List<Person>	players=new ArrayList<Person>();

	public Person getCoach() {
		return coach;
	}

	public void setCoach(Person coach) {
		this.coach=coach;
	}

	public List<Person> getPlayers() {
		return players;
	}

	public void setPlayers(List<Person> players) {
		this.players=players;
	}

}
