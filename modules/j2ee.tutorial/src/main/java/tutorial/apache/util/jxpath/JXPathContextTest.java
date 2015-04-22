package tutorial.apache.util.jxpath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;

public class JXPathContextTest {
	public static void findObject() {
		League league=new League();
		Team team=new Team();
		league.getTeams().add(team);

		team.setCoach(new Person("Coach Bob"));
		team.getPlayers().add(new Person("Player Charlie"));
		team.getPlayers().add(new Person("Player Ted"));
		team.getPlayers().add(new Person("Player Bart"));
		Team team2=new Team();
		league.getTeams().add(team2);

		team2.setCoach(new Person("Coach Susan"));
		team2.getPlayers().add(new Person("Player Jim"));
		// Query for the coach of a specific player.
		JXPathContext context=JXPathContext.newContext(league);
		System.out.println("** Retrieve the first name of Ted's coach");
		// String xpath="teams/players[name='Player Ted']/../coach/firstName";
		System.out.println(context.getValue("teams/players[name='Player Ted']"));
		// Query for the players of a specific coach
		context=JXPathContext.newContext(league);
		System.out.println("** Retrieve the players on Coach Susan's team");
		System.out.println(context.getValue("teams/coach[name='Coach Susan']/../players"));
	}

	@SuppressWarnings("unchecked")
	public static void findFromCollection() {
		List<Person> peoples=new ArrayList<Person>();
		peoples.add(new Person("Ahmad","Russell",28));
		peoples.add(new Person("Tom","Russell",35));
		peoples.add(new Person("Ahmad","Abuzayedeh",33));
		// Select all people older than 30
		System.out.println("** People older than 30");
		JXPathContext context=JXPathContext.newContext(peoples);
		Iterator<Person> iterator=context.iterate(".[@age > 30]");
		printPeople(iterator);
		// Select all people with a first name of 'Ahmad'
		context=JXPathContext.newContext(peoples);
		System.out.println("** People with first name 'Ahmad'");
		iterator=context.iterate(".[@name = 'Ahmad']");
		printPeople(iterator);
		// Select the second person from the List
		context=JXPathContext.newContext(peoples);
		System.out.println("** Third Person in List");
		Person p=(Person)context.getValue(".[2]");
		System.out.println("Person: " + p.getName() + " " + p.getLastName() + ", age: " + p.getAge());
		// A method to print out the result of each iteration.

	}

	private static void printPeople(Iterator<Person> iterator) {
		while(iterator.hasNext()){
			Person p=iterator.next();
			System.out.println("Person: " + p.getName() + " " + p.getLastName() + ", age: " + p.getAge());
		}
	}

	public static void main(String[] args) {
		findObject();
		findFromCollection();

	}
}
