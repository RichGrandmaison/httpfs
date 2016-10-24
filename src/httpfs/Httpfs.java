package httpfs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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

			ArrayList<String> fullRequest = new ArrayList<String>();

			String requestLine;
			int contentLength = -1;
			final String contentLengthString = "Content-Length: ";

			while(true){
				requestLine = request.readLine();
				System.err.println(requestLine);
				fullRequest.add(requestLine);
				if(requestLine.startsWith(contentLengthString)){
					contentLength = Integer.parseInt(requestLine.substring(
							contentLengthString.length()));
				}
				if(requestLine.length() == 0){
					break;
				}
			}

			if(fullRequest.get(0).toUpperCase().contains("POST")){
				final char[] postContent = new char[contentLength];
				request.read(postContent);
				System.err.println(postContent);
				fullRequest.add(new String(postContent));
			}

			RequestParser rp = new RequestParser(fullRequest);
			rp.parse();

			ResponseParser responseParser = new ResponseParser(rp);
			response.print(responseParser.finalResponse);
			response.flush();

			//close all

			request.close();
			response.close();
			clientSocket.close();
		}
	}
}
