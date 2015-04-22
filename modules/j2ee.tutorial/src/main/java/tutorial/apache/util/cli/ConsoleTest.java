package tutorial.apache.util.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;

public class ConsoleTest {

	public static void main(String[] args) {
		Options options = new Options();
		// add t option, option is the command parameter, false indicates that 
		// this parameter is not required.
		options.addOption("t", false, "display current time");
		options.addOption("c", true, "country code");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd;
		try {
			cmd = parser.parse( options, args);
			if(cmd.hasOption("t")) {
			    // print the date and time
				System.out.println("t:"+cmd.getOptionValue("t"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		StringUtils.isEmpty("");
		
	}

}
