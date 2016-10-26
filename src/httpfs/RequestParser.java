package httpfs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RequestParser {

	ArrayList<String> fullRequest;
	String method;
	String path;
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
		if(checkRequestCorrectness()){
			statusCode = 200;
		}
		if(method.equals("POST")){
			extractPostHeadersAndBody();
			if(!checkIfContentLengthIsValid()){
				System.err.println("Invalid content-length");
				statusCode = 411;
			} else {
				File requestedFile = new File(Httpfs.pathToDir + path);
				if(requestedFile.getPath().equals(Httpfs.pathToDir)){
					statusCode = 403;
				}else if(!requestedFile.exists()){
					requestedFile.getParentFile().mkdirs();
				} 

				if(statusCode != 403){
					FileWriter fw = new FileWriter(requestedFile);
					fw.write(postBody.toString());
					fw.flush();
					fw.close();
				}
			}
		}
	}

	//obtain the Request Method, the path to the file, and the file name
	void extractMethodAndPathAndFile() {
		String[] firstLine = fullRequest.get(0).split(" ", 3);
		method = firstLine[0].toUpperCase();
		path = firstLine[1];
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
		//return (contentLength == postBody.length()) ? true : false;
		return true;
	}

	boolean checkRequestCorrectness() {
		if(!(method.equalsIgnoreCase("post") || method.equalsIgnoreCase("get"))){
			return false;
		} if(method.equalsIgnoreCase("get")){ //check if requestedFile exists
			File requestedFile = new File(Httpfs.pathToDir + path);
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
		System.out.println("Port " + port);
		System.out.println("Content-Length: " + contentLength);
		System.out.println("Content-Type: " + contentType);
		System.out.println("Post body: " + postBody.toString());
	}
}
