package org.hzi.sormas.lbds.smsgateway;

import org.hzi.sormas.lbds.smsgateway.linkmobility.LinkMobilityGatewayService;
import org.hzi.sormas.lbds.smsgateway.model.LbdsSms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LinkMobilityGatewayServiceTest {

    LinkMobilityGatewayService sgs = new LinkMobilityGatewayService();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendSMS() throws URISyntaxException, IOException {
        LbdsSms lbdsSms = sgs.sendSMS("hzi01", "xxx", "ingo", "0000", "HELLO_TEST_MESSAGE");
        assertNotNull(lbdsSms);
        //assertEquals("https://ham.http.api.linkmobility.de:7011/sendsms?user=foo&password=bar&from=1234&to=5678&body=hello+world",u.toString());
    }

//    @Test
//    void receiveSMSResponse() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setServerName("www.example.com");
//        request.setRequestURI("/foo");
//        request.setQueryString("user=foo&password=bar&from=1234&to=5678&body=hello+world");
//        List<LbdsSms> lbdsSms = sgs.receiveSMSResponse(request);
//        assertNotNull(lbdsSms);
//    }
//
//    @Test
//    void getStatusCode() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        response.setStatus(200);
//
//        int statusCode = sgs.getStatusCode(response);
//        assertEquals(200, statusCode);
//
//    }


}
