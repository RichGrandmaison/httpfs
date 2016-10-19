package httpfs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
		
		//wait for connections
		while(true){
			Socket clientSocket = serverSocket.accept();
			
			BufferedReader request = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream())); 
			PrintWriter response = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(clientSocket.getOutputStream())),true);
			
			String requestLine;
			while((requestLine = request.readLine()) != null){
				System.out.println(requestLine);
			}
			
			response.write("k lol");
			
			//close all
			request.close();
			response.close();
			clientSocket.close();
		}
	}
}
