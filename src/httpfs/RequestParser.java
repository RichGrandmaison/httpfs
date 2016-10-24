package httpfs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RequestParser {

	ArrayList<String> fullRequest;
	String method;
	String path;
	String file;
	String host;
	int port;
	int contentLength;
	String contentType;
	String postBody;
	int statusCode; //ok
	
	public RequestParser(ArrayList<String> fr){
		this.fullRequest = fr;
		postBody = new String();
	}
	
	public void parse() throws IOException{
		extractHostAndPort();
		extractMethodAndPathAndFile();
		if(method.equals("POST")){
			extractPostHeadersAndBody();
			if(!checkIfContentLengthIsValid()){
				System.err.println("Invalid content-length");
				statusCode = 411;
			}
		}
		if(checkRequestCorrectness()){
			statusCode = 200;
		}
	}
	
	//Create a file at the correct location. If the directories do not exist, create them
	void servePOST() throws IOException{
		File file = new File(Httpfs.pathToDir + path);
		file.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(file);
		fw.write(postBody.toString());
		fw.close();
	}
	
	//obtain the Request Method, the path to the file, and the file name
	void extractMethodAndPathAndFile() {
		String[] firstLine = fullRequest.get(0).split(" ", 3);
		method = firstLine[0].toUpperCase();
		path = firstLine[1];
		String[] extractFile = path.split("/");
		file = extractFile[extractFile.length - 1];
	}
	
	//obtain the host name, and if it port number is specified, capture it.
	void extractHostAndPort() {
		String[] fullHost = fullRequest.get(1).split(" ", 2);
		String[] splitHost = fullHost[1].split(":", 2);
		host = splitHost[0];
		if(splitHost.length > 1)
			port = Integer.parseInt(splitHost[1]);
		else port = Httpfs.port;
	}
	
	//obtain the headers and the body associated with a POST request
	void extractPostHeadersAndBody(){
		
		for(String requestLine : fullRequest){
			if(requestLine.startsWith("Content-Length")){
				String[] temp = requestLine.split(": ");
				contentLength = Integer.parseInt(temp[1]);
			} else if(requestLine.startsWith("Content-Type")){
				String[] temp = requestLine.split(": ");
				contentType = temp[1];
			} 
		}
		postBody = fullRequest.get(fullRequest.size() - 1);
	}
	
	boolean checkIfContentLengthIsValid(){
		return (contentLength == postBody.length()) ? true : false;
	}
	
	boolean checkRequestCorrectness() {
		if(!(method.equalsIgnoreCase("post") || method.equalsIgnoreCase("get"))){
			return false;
		} if(method.equalsIgnoreCase("get")){ //check if requestedFile exists
			File requestedFile = new File(Httpfs.pathToDir + path);
			System.out.println(requestedFile.getAbsolutePath());
			if(!requestedFile.exists()){
				statusCode = 404;
				return false;
			}
		} else if(method.equalsIgnoreCase("post")){
			File requestedFile = new File(Httpfs.pathToDir + path);
			if(requestedFile.isDirectory()){ //check if file is not a directory
				statusCode = 404;
				return false;
			}
				
		} 
		return true;
	}
	
	//Only for testing purposes
	public void DisplayParsedRequest(){
		System.out.println("Method: " + method);
		System.out.println("Path: " + path);
		System.out.println("Host: " + host);
		System.out.println("File " + file);
		System.out.println("Port " + port);
		System.out.println("Content-Length: " + contentLength);
		System.out.println("Content-Type: " + contentType);
		System.out.println("Post body: " + postBody.toString());
	}
}
