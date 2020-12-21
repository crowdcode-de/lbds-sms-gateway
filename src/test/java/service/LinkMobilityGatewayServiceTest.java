package service;

import model.LbdsSms;
import org.apache.hc.core5.http.NameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LinkMobilityGatewayServiceTest {

    LinkMobilityGatewayService sgs= new LinkMobilityGatewayService();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendSMS() throws URISyntaxException, IOException {
        LbdsSms lbdsSms = sgs.sendSMS("foo", "bar", "1234", "5678", "hello world");
        assertNotNull(lbdsSms);
        //assertEquals("https://ham.http.api.linkmobility.de:7011/sendsms?user=foo&password=bar&from=1234&to=5678&body=hello+world",u.toString());
    }

    @Test
    void receiveSMSResponse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.example.com");
        request.setRequestURI("/foo");
        request.setQueryString("user=foo&password=bar&from=1234&to=5678&body=hello+world");
        List<LbdsSms> lbdsSms = sgs.receiveSMSResponse(request);
        assertNotNull(lbdsSms);
    }

    @Test
    void getStatusCode() {
        MockHttpServletResponse response=  new MockHttpServletResponse();
        response.setStatus(200);

        LbdsSms statusCode = sgs.getStatusCode(response);
        assertEquals(200,statusCode.getReturnValue());

    }


}