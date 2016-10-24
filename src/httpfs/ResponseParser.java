package httpfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseParser {

	private RequestParser rp;
	private int contentLength;
	private String httpHeader;
	private String contentType = "text/plain";
	private String content;
	private String dateNow;
	private String modifiedDate;
	private String server = "Rich&Simon =D v1";
	private String finalResponse;
	
	public ResponseParser(RequestParser r)
	{
		rp = r;
		httpHeader = GetHTTPMessage(r.statusCode);

		dateNow = new Date().toString();
		modifiedDate = GetModifiedDate(r.path);		
		
		String response = "";
		response += httpHeader + "\n";
		response += "Date: " + dateNow;
		response += "\nServer: " + server;
		
		if(r.method.equals("GET"))
		{	
			content = GetContent(r.path);
			contentLength = content.length();
			response += "\nMIME-version: 1.0";
			response += "\nLast-Modified: " + modifiedDate;
			response += "\nContent-Type: " + contentType;
			response += "\nContent-Length " + contentLength;
			response += "\n\n" + content;	
		}

		finalResponse = response;
	}
	
	public String GetResponse()
	{
		String toReturn ="";
		
		return toReturn;
	}
	
	private String GetHTTPMessage(int statusCode)
	{
		String toReturn = "HTTP/1.0 "+statusCode +" "+ HttpStatusCode.codes.get(statusCode);
		return toReturn;
	}
	
	private String GetContent(String fileName)
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
	
	private String GetModifiedDate(String fileName)
	{	    
	    File file = new File(fileName);
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return sdf.format(file.lastModified()).toString();
	}
}
