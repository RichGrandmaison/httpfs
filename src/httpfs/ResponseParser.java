package httpfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResponseParser {

	private int contentLength;
	private String httpHeader;
	private String contentType = "text/plain";
	private StringBuilder content;
	private String dateNow;
	private String modifiedDate;
	private static String server = "Rich&Simon =D v1";
	public String finalResponse;

	public static StringBuilder errorMessage(int statusCode)
	{
		StringBuilder response = new StringBuilder();
		response.append(getHTTPMessage(statusCode) + "\r\n");
		response.append("Date: " + new Date().toString());
		response.append("\r\nServer: " + server);
		return response;
	}
	
	public ResponseParser(RequestParser r)
	{
		httpHeader = getHTTPMessage(r.statusCode);

		dateNow = new Date().toString();
		modifiedDate = getModifiedDate(Httpfs.pathToDir + r.path);		

		StringBuilder response = new StringBuilder();
		response.append(httpHeader + "\r\n");
		response.append("Date: " + dateNow);
		response.append("\r\nServer: " + server);

		if(r.method.equals("GET"))
		{	
			content = new StringBuilder();
			
			if(r.path.length() > 1){
				String temp = getContent(Httpfs.pathToDir + r.path);
				content.append(temp);
				contentLength = content.length();
			} else {
				File dir = new File(Httpfs.pathToDir);
				ArrayList<String> files = new ArrayList<String>();
				contentLength = 0;
				files.add("Available files...\r\n");
				files.add("/\r\n");
				
				if(dir.isDirectory()){
					getFileList(dir, files);
				}
				for(String file : files){
					if(file != null){
						content.append(file);
					}
				}
			}
			
			response.append("\r\nMIME-version: 1.0");
			response.append("\r\nLast-Modified: " + modifiedDate);
			response.append("\r\nContent-Type: " + contentType);
			response.append("\r\nContent-Length " + contentLength);
			response.append("\r\n\r\n");
			response.append(content);
		}

		finalResponse = response.toString();
	}

	private void getFileList(File dir, ArrayList<String> files) {
		
		File[] filesInDir = dir.listFiles();
		
		for(File f : filesInDir){
			if(f.isFile()){
				files.add(f.getName() + "\r\n");
				contentLength += f.getName().length() + 3;
			}
		}
		for(File f : filesInDir){
			if(f.isDirectory()){
				files.add("/" + f.getName() + "\r\n");
				contentLength += f.getName().length() + 1;
				File insidePath = new File(Httpfs.pathToDir + "/" + f.getName());
				getFileList(insidePath, files);
			}
		}
		
	}

	private static String getHTTPMessage(int statusCode)
	{
		String toReturn = "HTTP/1.0 "+statusCode +" "+ HttpStatusCode.codes.get(statusCode);
		return toReturn;
	}

	private String getContent(String fileName)
	{
		BufferedReader br = null;
		String Response = "";

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			while ((sCurrentLine = br.readLine()) != null) {
				Response += sCurrentLine;
				Response += "\n";
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return Response;		
	}

	private String getModifiedDate(String fileName)
	{	    
		File file = new File(fileName);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return sdf.format(file.lastModified()).toString();
	}
}
