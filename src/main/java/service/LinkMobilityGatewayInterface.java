package service;

import model.LbdsSms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface LinkMobilityGatewayInterface {

    LbdsSms sendSMS(String user, String password, String from, String to, String body) throws URISyntaxException, IOException;

    LbdsSms sendSMS(LbdsSms smsToBeSend) throws URISyntaxException, IOException;

    List<LbdsSms> receiveSMSResponse(HttpServletRequest request);

    LbdsSms getStatusCode(HttpServletResponse response);
}
