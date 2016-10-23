package httpfs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Httpfs {
	
	//Default values attributed directly.
	static int port = 8080;
	static boolean debugMessages = false;
	static String pathToDir = new File("").getAbsolutePath();
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
			rp.parse();
			rp.DisplayParsedRequest();
			
			
			//close all
			/*
			request.close();
			response.close();
			clientSocket.close();*/
		}
	}
}
