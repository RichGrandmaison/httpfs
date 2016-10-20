package httpfs;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandLineParser {

	final String DEBUGMESSAGES = "-v";
	final String PORT = "-p";
	final String PATHTODIR = "-d";
	ArrayList<String> args;

	public CommandLineParser(String[] args){
		this.args = new ArrayList<String>(Arrays.asList(args));	
	}

	//Parse the arguments from the command line. We count the number of arguments
	//The acceptable parameters are -v, -p and -d
	//At the end of the parsing process, if the number of arguments is greater than 1, we have a problem
	public void parse(){
		int argsLength = args.size();
		if(args.contains(DEBUGMESSAGES)){
			Httpfs.debugMessages = true;
			argsLength--;
		}
		if(args.contains(PATHTODIR)){
			Httpfs.pathToDir = args.get(args.indexOf(PATHTODIR) + 1);
			argsLength -= 2;
		}
		if(args.contains(PORT)) {
			Httpfs.port = Integer.parseInt(args.get(args.indexOf(PORT) + 1));
			argsLength -= 2;
		}
		if(argsLength > 0){
			System.out.println("Invalid Arguments. Please try again!");
			System.exit(0);
		}
	}

}
