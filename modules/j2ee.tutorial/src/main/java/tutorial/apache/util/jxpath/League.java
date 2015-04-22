package tutorial.apache.util.jxpath;

import java.util.ArrayList;
import java.util.List;

public class League {
	private List<Team>	Teams=new ArrayList<Team>();

	public List<Team> getTeams() {
		return Teams;
	}

	public void setTeams(List<Team> teams) {
		Teams=teams;
	}

}
