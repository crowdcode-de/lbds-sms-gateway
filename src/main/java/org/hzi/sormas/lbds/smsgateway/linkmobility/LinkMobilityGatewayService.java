package org.hzi.sormas.lbds.smsgateway.linkmobility;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.net.URIBuilder;
import org.hzi.sormas.lbds.smsgateway.SmsGatewayInterface;
import org.hzi.sormas.lbds.smsgateway.WhitelistHostnameVerifier;
import org.hzi.sormas.lbds.smsgateway.model.LbdsSms;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@Slf4j
public class LinkMobilityGatewayService implements SmsGatewayInterface {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public LbdsSms sendSMS(String user, String password, String from, String to, String body) throws URISyntaxException, IOException {
        HttpsURLConnection.setDefaultHostnameVerifier(WhitelistHostnameVerifier.INSTANCE);

        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("ham.http.api.org.hzi.sormas.lbds.smsgateway.linkmobility.service.linkmobility.de")
                .setPort(7011)
                .setPath("/sendsms")
                .setParameter("user", user)
                .setParameter("password", password)
                .setParameter("from", from)
                .setParameter("to",to)
                .setParameter("body",body)
                .build();
        HttpGet httpget = new HttpGet(uri);
        log.debug(String.valueOf(httpget.getUri()));

        URL obj = new URL(uri.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        log.debug("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            try(BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        } else {
            log.debug("GET request not worked");
        }

        return new LbdsSms(httpget.getUri());
    }

    public LbdsSms mapLinkMobiltySmsToLbdsSms(LinkedMobilitySms linkedMobilitySms) {
        return new LbdsSms()
                .setBody(linkedMobilitySms.getBody())
                .setFrom(linkedMobilitySms.getFrom())
                .setId(linkedMobilitySms.getId())
                .setTo(linkedMobilitySms.getTo());
    }
}

