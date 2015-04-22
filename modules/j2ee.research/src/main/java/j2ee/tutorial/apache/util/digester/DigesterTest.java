package j2ee.tutorial.apache.util.digester;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;
import org.apache.commons.digester.BeanPropertySetterRule;
import org.apache.commons.digester.ObjectCreateRule;
import org.apache.commons.digester.Rules;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.digester.SetPropertiesRule;

public class DigesterTest {
	public static void test1() throws IOException, SAXException {
		List<String> plays=new ArrayList<String>();
		// Create an instance of the Digester from the XML rule set
		URL rules=DigesterTest.class.getResource("./play-rules.xml");
		Digester digester=DigesterLoader.createDigester(rules);
		// Push a reference to the plays List on to the Stack
		digester.push(plays);
		// Parse the XML document
		InputStream input=DigesterTest.class.getResourceAsStream("./plays.xml");
		Object root=digester.parse(input);
		System.out.println(root.toString());
		// The XML document contained one play "Hamlet"
	}

	public static void test() {
		Digester digester=new Digester();
		Rules rules=digester.getRules();
		// Add Rules to parse a play element
		rules.add("plays/play",new ObjectCreateRule("xml.digester.Play"));
		rules.add("plays/play",new SetNextRule("add","java.lang.Object"));
		rules.add("plays/play",new SetPropertiesRule());
		rules.add("plays/play/name",new BeanPropertySetterRule("name"));
		rules.add("plays/play/summary",new BeanPropertySetterRule("summary"));
		rules.add("plays/play/author",new BeanPropertySetterRule("author"));
		// Add Rules to parse a character element
		rules.add("plays/play/characters/character",new ObjectCreateRule("xml.digester.Character"));
		rules.add("plays/play/characters/character",new SetNextRule("addCharacter","xml.digester.Character"));
		rules.add("plays/play/characters/character",new SetPropertiesRule());
		rules.add("plays/play/characters/character/name",new BeanPropertySetterRule("name"));
		rules.add("plays/play/characters/character/description",new BeanPropertySetterRule("description"));

		System.out.println(digester);
	}

	public static void main(String[] args) throws Exception {
		test();
	}
}
