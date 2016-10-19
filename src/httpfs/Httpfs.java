package httpfs;
import java.io.IOException;
import java.net.ServerSocket;

public class Httpfs {
	
	static int port = 8080;
	static boolean debugMessages = false;
	static String pathToDir = null;
	static ServerSocket serverSocket;
	
	public static void main(String[] args) throws Exception {
		
		CommandLineParser clp = new CommandLineParser(args);
		clp.parse();
		ServerSocket serverSocket = new ServerSocket(port);
		
		System.out.println("HTTPFS now listening on port " + port + " ...");
		if(debugMessages){
			System.out.println("Debug messages will be printed ...");
		}
	}
}
