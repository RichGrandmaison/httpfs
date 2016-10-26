package httpfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResponseParser {

	private RequestParser rp;
	private int contentLength;
	private String httpHeader;
	private String contentType = "text/plain";
	private String content;
	private String dateNow;
	private String modifiedDate;
	private static String server = "Rich&Simon =D v1";
	public String finalResponse;

	public static String ErrorMessage(int statusCode)
	{
		String response = "";
		response += getHTTPMessage(statusCode) + "\r\n";
		response += "Date: " + new Date().toString();
		response += "\r\nServer: " + server;
		return response;
	}
	
	public ResponseParser(RequestParser r)
	{
		rp = r;
		httpHeader = getHTTPMessage(r.statusCode);

		dateNow = new Date().toString();
		modifiedDate = getModifiedDate(Httpfs.pathToDir + r.path);		

		String response = "";
		response += httpHeader + "\r\n";
		response += "Date: " + dateNow;
		response += "\r\nServer: " + server;

		if(r.method.equals("GET"))
		{	
			if(r.path.length() > 1){
				content = getContent(Httpfs.pathToDir + r.path);
				contentLength = content.length();
				response += "\r\nMIME-version: 1.0";
				response += "\r\nLast-Modified: " + modifiedDate;
				response += "\r\nContent-Type: " + contentType;
				response += "\r\nContent-Length " + contentLength;
				response += "\r\n\r\n" + content;	
			} else {
				File dir = new File(Httpfs.pathToDir);
				ArrayList<String> files = new ArrayList<String>();
				int filesLength = 0;
				
				if(dir.exists() && dir.isDirectory()){
					File[] filesInDir = dir.listFiles();
					for(File f : filesInDir){
						//if(f.isFile()){
							files.add(f.getName() + "\r\n");
							filesLength += f.getName().length();
						//}
					}
				}
				response += "\r\nMIME-version: 1.0";
				response += "\r\nLast-Modified: " + modifiedDate;
				response += "\r\nContent-Type: " + contentType;
				response += "\r\nContent-Length " + filesLength;
				response += "\r\n\r\n";
				for(String file : files){
					response += file;
				}
			}
		}

		finalResponse = response;
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
