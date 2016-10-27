package httpfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/*This class is used to manage multiple connections on the server. Every time our socket accepts a connection, it will create a new object of this 
 * class. This will create a thread per connection therefore enabling our server to treat multiple connections.
 * */
public class ConnectionHandler extends Thread{
	
	Socket clientSocket;
	PrintWriter response;
	BufferedReader request;
	final String contentLengthString = "Content-Length: ";
	ArrayList<String> fullRequest;
	
	public ConnectionHandler(Socket s) throws IOException
	{
		clientSocket = s;
		request = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		response = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(clientSocket.getOutputStream())),true);
		fullRequest = new ArrayList<String>();
		
		if(Httpfs.debugMessages){
			System.out.println("New connection accepted socket address: "+clientSocket.getLocalSocketAddress());
		}
	}
	
	@Override
	public void run() 
	{
		try{				
			String requestLine;
			int contentLength = -1;
	
			//Reading the client's request
			while(true){
				requestLine = request.readLine();
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
				fullRequest.add(new String(postContent));
			}
			
			
			
			//Class called to format and parse the request
			RequestParser rp = new RequestParser(fullRequest);
			rp.parse();
			
			//Class called to format the response
			ResponseParser responseParser = new ResponseParser(rp);
			response.print(responseParser.finalResponse);
			
			if(Httpfs.debugMessages){
				System.out.println("Full request: ");
				for(String r : fullRequest){
					System.out.println(r);
				}
				System.out.println("Full response:");
				System.out.println(responseParser.finalResponse);
			}	
			
			response.flush();
	
			//close all
			request.close();
			response.close();
			clientSocket.close();
		}
		catch(Exception e)
		{//if we are here, something went wrong therefore we are returning error 500			
			response.print(ResponseParser.errorMessage(500));
		}
		finally{
			if(Httpfs.debugMessages)
			{
				System.out.println("Connection closed...\n");
			}
		}
	}
}
