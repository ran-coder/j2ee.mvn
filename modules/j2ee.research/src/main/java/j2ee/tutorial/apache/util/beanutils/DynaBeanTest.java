package j2ee.tutorial.apache.util.beanutils;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

public class DynaBeanTest {
	public static void main(String[] args) throws Exception {
		DynaProperty[] beanProperties=new DynaProperty[]{
				new DynaProperty("name",String.class),
				new DynaProperty("party",String.class),
				new DynaProperty("votes",Long.class)
		};
		BasicDynaClass politicianClass=new BasicDynaClass("politician",BasicDynaBean.class,beanProperties);
		DynaBean politician=politicianClass.newInstance();
		// Set the properties via DynaBean
		politician.set("name","Tony Blair");
		politician.set("party","Party.LABOUR");
		politician.set("votes",new Long(50000000));
		// Set the properties with PropertyUtils
		PropertyUtils.setProperty(politician,"name","John Major");
		PropertyUtils.setProperty(politician,"party","Party.TORY");
		PropertyUtils.setProperty(politician,"votes",new Long(50000000));

	}
}
