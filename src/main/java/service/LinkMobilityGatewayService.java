package service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.net.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.util.List;

public class LinkMobilityGatewayService implements LinkMobilityGatewayInterface {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public URI sendSMS(String user, String password, String from, String to, String body) throws URISyntaxException, IOException {
        URI uri = new URIBuilder()
                .setScheme("https")
                //.setHost("ham.http.api.linkmobility.de:7011/sendsms")
                .setHost("www.google.com")
                .setParameter("user", user)
                .setParameter("password", password)
                .setParameter("from", from)
                .setParameter("to",to)
                .setParameter("body",body)
                .build();
        HttpGet httpget = new HttpGet(uri);
        System.out.println(httpget.getUri());

        URL obj = new URL(uri.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            //System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

        return httpget.getUri();
    }

    @Override
    public List<NameValuePair> receiveSMSResponse(HttpServletRequest request) {

        List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));

        for (NameValuePair param : params) {
            System.out.println(param.getName() + " : " + param.getValue());
        }

        return params;

    }

    @Override
    public int getStatusCode(HttpServletResponse response) {
        if(response!=null)
        return response.getStatus();
        else
            return 0;
    }

}

