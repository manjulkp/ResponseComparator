package response;


import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

public class responseValidationOperation {

	public String getResponseHeader(ResponseOptions<Response> res, String headerKey) {
		String headerValue = "";
		Headers allHeaders = res.getHeaders();

		// Iterate over all the Headers
		for (Header header : allHeaders) {

			if (headerKey.equals(header.getName())) {
				headerValue = header.getValue();
				break;
			}
		}
		return headerValue;

	}
	
	public int getStatusCode(ResponseOptions<Response> res)
	{
		return res.getStatusCode();
	}
	
	public String getStatusLine(ResponseOptions<Response> res)
	{
		return res.getStatusLine();
	}
	
	

}