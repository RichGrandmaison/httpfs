package httpfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReponseParser {

	private RequestParser rp;
	private int contentLength;
	private String httpHeader;
	private String contentType = "text/html";
	private String content;
	private String dateNow;
	private String modifiedDate;
	private String server = "Rich&Simon =D v1";
	
	public ReponseParser(RequestParser r)
	{
		rp = r;
		
		httpHeader = GetHTTPMessage();
		content = GetContent(r.file);
		contentLength = content.length();
		dateNow = new Date().toString();
		modifiedDate = GetModifiedDate(r.file);		
	}
	
	public String GetResponse()
	{
		String toReturn ="";
		
		return toReturn;
	}
	
	private String GetHTTPMessage()
	{
		int statusCode  = 200;
	
		String toReturn = "HTTP/1.1 "+statusCode +" "+ HttpStatusCode.codes.get(statusCode);
		
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
