package j2ee.server.jetty;

import org.eclipse.jetty.webapp.WebAppContext;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-25 下午3:49:00
 *   
 */
public class JettyUtil {
	public static void debug(WebAppContext webapp){
		if(webapp==null)webapp=WebAppContext.getCurrentWebAppContext();
		System.out.println("ContextPath:"+webapp.getContextPath());
		System.out.println("DefaultsDescriptor:"+webapp.getDefaultsDescriptor());
		System.out.println("Descriptor:"+webapp.getDescriptor());
		System.out.println("ClassPath:"+webapp.getClassPath());
		System.out.println("ResourceBase:"+webapp.getResourceBase());
		System.out.println("ResourceAliases:"+webapp.getResourceAliases());
	}
	public static void jettyRunMojo(){
		
	}
}
