package service;

import org.apache.hc.core5.http.NameValuePair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface LinkMobilityGatewayInterface {

    URI sendSMS(String user, String password, String from, String to, String body) throws URISyntaxException, IOException;

    List<NameValuePair> receiveSMSResponse(HttpServletRequest request);

    int getStatusCode(HttpServletResponse response);
}
