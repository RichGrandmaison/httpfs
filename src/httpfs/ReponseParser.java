package httpfs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReponseParser {

	public String GetContent(String fileName)
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
	
}
