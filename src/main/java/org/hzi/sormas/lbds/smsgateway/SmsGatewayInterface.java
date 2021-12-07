package org.hzi.sormas.lbds.smsgateway;

import org.hzi.sormas.lbds.smsgateway.model.LbdsSms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface SmsGatewayInterface {

    LbdsSms sendSMS(String user, String password, String from, String to, String body) throws URISyntaxException, IOException;

    List<LbdsSms> receiveSMSResponse(HttpServletRequest request);

    int getStatusCode(HttpServletResponse response);

}
