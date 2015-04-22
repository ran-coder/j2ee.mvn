package j2ee.research.spring.example.collection;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CollectionBean {

	// set集合
	private Set<String>	sets;

	public Set<String> getSets() {
		return sets;
	}

	public void setSets(Set<String> sets) {
		this.sets=sets;
	}

	// list集合
	private List<User>	users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users=users;
	}

	// map集合
	private Map<Integer, User>	map;

	public Map<Integer, User> getMap() {
		return map;
	}

	public void setMap(Map<Integer, User> map) {
		this.map=map;
	}

	// properties集合
	public Properties	props;

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props=props;
	}

}