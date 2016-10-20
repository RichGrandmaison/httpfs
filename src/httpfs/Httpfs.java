package httpfs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
		
		ArrayList<String> testPost = new ArrayList<String>();
		testPost.add("POST /path/script.cgi HTTP/1.0\r\n");
		testPost.add("From: frog@jmarshall.com");
		testPost.add("User-Agent: HTTPTool/1.0\r\n");
		testPost.add("Content-Type: application/x-www-form-urlencoded\r\n");
		testPost.add("Content-Length: 32");
		testPost.add("\r\n");
		testPost.add("home=Cosby&favorite+flavor=flies\r\n");
		
		RequestParser rp = new RequestParser(testPost);
		rp.parse();
		rp.DisplayParsedRequest();
		
		/*
		//wait for connections
		while(true){
			Socket clientSocket = serverSocket.accept();
			System.out.println("New connection accepted ...");
			
			BufferedReader request = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream())); 
			PrintWriter response = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(clientSocket.getOutputStream())),true);
			
			String requestLine;
			ArrayList<String> fullRequest = new ArrayList<String>();
			
			while((requestLine = request.readLine()) != null){
				System.err.println(requestLine);
				fullRequest.add(requestLine);
			}
			
			RequestParser rp = new RequestParser(fullRequest);
			
			
			//close all
			request.close();
			response.close();
			clientSocket.close();
		}*/
	}
}
