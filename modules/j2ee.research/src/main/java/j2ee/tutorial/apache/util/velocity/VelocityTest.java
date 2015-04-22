package j2ee.tutorial.apache.util.velocity;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import utils.io.PathUtil;

public class VelocityTest {
	public static void test1() throws Exception{
		// Initialize Velocity
		Velocity.init();
		// Create a context and put our subscription object into the context
		VelocityContext context=new VelocityContext();
		context.put("subscription",context);
		// Create a Reader to read our velocity template.
		Reader reader=new FileReader(new File(PathUtil.getFullPathRelateClass("renew.vm",VelocityTest.class) ));
		// Evaluate our template and write the result to a StringWriter
		StringWriter writer=new StringWriter();
		Velocity.evaluate(context,writer,"test",reader);
		System.out.println(writer.toString());
	}
	public static void test() throws Exception{
		// Initialize Velocity
		Velocity.init();
		List<Airport> airports = new ArrayList<Airport>( );
		airports.add( new Airport(1, "ATL", "Hartsfield Atlanta", 76876128L, "US" ) );
		airports.add( new Airport(2, "ORD", "Chicago O'Hare", 66501496L, "US" ) );
		airports.add( new Airport(3, "LHR", "London Heathrow", 63338649L, "UK" ) );
		airports.add( new Airport(4, "HND", "Tokyo-Haneda", 61079478L, "JP" ) );
		airports.add( new Airport(5, "LAX", "Los Angeles", 56198447L, "US" ) );
		airports.add( new Airport(6, "DFW", "Dallas/Fort Worth", 52826304L, "US" ) );
		// Create a context and put a List into the context, and a country code

		// Create a context and put our subscription object into the context
		VelocityContext context=new VelocityContext();
		context.put( "airports", airports );
		context.put( "countryCode", "US" );

		// Create a Reader to read our velocity template.
		Reader reader=new FileReader(new File(PathUtil.getFullPathRelateClass("renew.vm",VelocityTest.class) ));
		// Evaluate our template and write the result to a StringWriter
		StringWriter writer=new StringWriter();
		Velocity.evaluate(context,writer,"test",reader);
		System.out.println(writer.toString());
	}
	public static void main(String[] args) throws Exception {
		test();
	}


	static class Airport{
		private Integer id;
		private String code;
		private String name;
		private Long passengers;
		private String countryCode;
		public Airport(Integer id, String code, String name, Long passengers, String countryCode) {
			super();
			this.id=id;
			this.code=code;
			this.name=name;
			this.passengers=passengers;
			this.countryCode=countryCode;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id=id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code=code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name=name;
		}
		public Long getPassengers() {
			return passengers;
		}
		public void setPassengers(Long passengers) {
			this.passengers=passengers;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode=countryCode;
		}
		
	}
}
