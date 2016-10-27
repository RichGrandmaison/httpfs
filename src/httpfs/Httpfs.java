package httpfs;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;


public class Httpfs {

	//Default values attributed directly.
	static int port = 8080;
	static boolean debugMessages = false;
	static String pathToDir = new File("data").getAbsolutePath();
	static ServerSocket serverSocket;

	public static void main(String[] args) throws Exception {

		CommandLineParser clp = new CommandLineParser(args);
		clp.parse();
		ServerSocket serverSocket = new ServerSocket(port);
		
		if(debugMessages){
			System.out.println("HTTPFS now listening on port " + port + " ...");
			System.out.println("HTTPFS directory set to " + pathToDir);
		}
		
		//wait for connections
		while(true){			
			Socket clientSocket = serverSocket.accept();
			ConnectionHandler c = new ConnectionHandler(clientSocket);
			c.start();
		}
	}
}
