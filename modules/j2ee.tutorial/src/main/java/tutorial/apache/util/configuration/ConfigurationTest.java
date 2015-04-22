package tutorial.apache.util.configuration;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public class ConfigurationTest {

	public static void main(String[] args) throws Exception {
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		config.addConfiguration(new PropertiesConfiguration("application.properties"));
		try {
			PropertiesConfiguration config1 = new PropertiesConfiguration("usergui.properties");
			config1.setProperty("colors.background", "#000000");
			config1.save();
			config1.save("usergui.backup.properties");//save a copy
			Integer integer = config.getInteger("window.width",1);
			System.out.println(integer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
