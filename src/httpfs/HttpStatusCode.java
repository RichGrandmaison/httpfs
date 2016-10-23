package httpfs;

import java.util.HashMap;

public class HttpStatusCode {

	public static final HashMap<Integer, String> codes;
    static
    {
    	codes= new HashMap<Integer, String>();
    	codes.put(100, "Continue");
    	codes.put(101, "Switching Protocols");
    	codes.put(102, "Processing");
    	codes.put(200, "OK");
    	codes.put(201, "Created");
    	codes.put(202, "Accepted");
    	codes.put(203, "Non-Authoritative Information");
    	codes.put(204, "No Content");
    	codes.put(205, "Reset Content");
    	codes.put(206, "Partial Content");
    	codes.put(207, "Multi-Status");
    	codes.put(210, "Content Different");
    	codes.put(226, "IM Used");
    	codes.put(300, "Multiple Choices");
    	codes.put(301, "Moved Permanently");
    	codes.put(302, "Moved Temporarily");
    	codes.put(303, "See Other");
    	codes.put(304, "Not Modified");
    	codes.put(305, "Use Proxy");
    	codes.put(306,"(aucun)");
    	codes.put(307, "Temporary Redirect");
    	codes.put(308, "Permanent Redirect");
    	codes.put(310, "Too many Redirects");
    	codes.put(400, "Bad Request");
    	codes.put(401, "Unauthorized");
    	codes.put(402, "Payment Required");
    	codes.put(403, "Forbidden");
    	codes.put(404, "Not Found");
    	codes.put(405, "Method Not Allowed");
    	codes.put(406, "Not Acceptable");
    	codes.put(407, "Proxy Authentication Required");
    	codes.put(408, "Request Time-out");
    	codes.put(409, "Conflict");
    	codes.put(410, "Gone");
    	codes.put(411, "Length Required");
    	codes.put(412, "Precondition Failed");
    	codes.put(413, "Request Entity Too Large");
    	codes.put(414, "Request-URI Too Long");
    	codes.put(415, "Unsupported Media Type");
    	codes.put(416, "Requested range unsatisfiable");
    	codes.put(417, "Expectation failed");
    	codes.put(418, "I’m a teapot");
    	codes.put(421, "Bad mapping / Misdirected Request");
    	codes.put(422, "Unprocessable entity");
    	codes.put(423, "Locked");
    	codes.put(424, "Method failure");
    	codes.put(425, "Unordered Collection");
    	codes.put(426, "Upgrade Required");
    	codes.put(428, "Precondition Required");
    	codes.put(429, "Too Many Requests");
    	codes.put(431, "Request Header Fields Too Large");
    	codes.put(449, "Retry With");
    	codes.put(450, "Blocked by Windows Parental Controls");
    	codes.put(451, "Unavailable For Legal Reasons");
    	codes.put(456, "Unrecoverable Error");
    	codes.put(499, "Client has closed connection");
    	codes.put(500, "Internal Server Error");
    }	
}
