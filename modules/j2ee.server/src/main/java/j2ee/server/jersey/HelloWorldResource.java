package j2ee.server.jersey;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

//The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorldResource {

	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "text/plain"
	@Produces("text/plain")
	public String getClichedMessage() {
		// Return some cliched textual content
		return "Hello World";
	}

	public static void main(String[] args) throws IOException {

		final String baseUri="http://localhost:9998/";
		final Map<String, String> initParams=new HashMap<String, String>();

		initParams.put("com.sun.jersey.config.property.packages","com.sun.jersey.samples.helloworld.resources");

		System.out.println("Starting grizzly...");
		SelectorThread threadSelector=GrizzlyWebContainerFactory.create(baseUri,initParams);
		System.out.println(String.format("Jersey app started with WADL available at %sapplication.wadl\n"
				+ "Try out %shelloworld\nHit enter to stop it...",baseUri,baseUri));
		System.in.read();
		threadSelector.stopEndpoint();
		System.exit(0);
	}
}