package service;

import model.LbdsSms;
import model.LinkedMobilitySms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface LinkMobilityGatewayInterface {

    LbdsSms sendSMS(String user, String password, String from, String to, String body) throws URISyntaxException, IOException;

    LbdsSms sendSMS(LbdsSms smsToBeSend) throws URISyntaxException, IOException;

    List<LbdsSms> receiveSMSResponse(HttpServletRequest request);

    int getStatusCode(HttpServletResponse response);

    LbdsSms mapLinkMobiltySmsToLbdsSms(LinkedMobilitySms linkedMobilitySms);
}
