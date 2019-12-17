package utility;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

public class RestAssuredHelper {

	public static RequestSpecification Request;
	public ResponseOptions<Response> res;
	
	/**
	 * 
	 * @param uriPattern
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws IncorrectHttpProtocolException
	 */
	public Response getResponse(String uriPattern)
			throws ClientProtocolException, IOException, IncorrectHttpProtocolException {
		if (!uriPattern.startsWith("http")) {
			throw new IncorrectHttpProtocolException(
					"The UrlPattern should be either http or https,we have found the protocol is missing ");
		}
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(uriPattern);
		builder.setContentType(ContentType.JSON);
		builder.addHeader("Accept", "application/json");
		Request = RestAssured.given().spec(builder.build());
		return Request.get();
	}
	
	/**
	 * 
	 * @param r
	 * @return
	 */
	public String getResponseAsString(Response r) {
		return r.getBody().asString();
	}

}