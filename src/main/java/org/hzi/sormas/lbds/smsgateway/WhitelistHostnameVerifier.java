package org.hzi.sormas.lbds.smsgateway;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.util.HashSet;
import java.util.Set;

public enum WhitelistHostnameVerifier implements HostnameVerifier {
    // these hosts get whitelisted
    INSTANCE("localhost", "ham.http.api.org.hzi.sormas.lbds.smsgateway.linkmobility.service.linkmobility.de");

    private Set whitelist = new HashSet<>();
    private HostnameVerifier defaultHostnameVerifier =
            HttpsURLConnection.getDefaultHostnameVerifier();

    WhitelistHostnameVerifier(String... hostnames) {
        for (String hostname : hostnames) {
            whitelist.add(hostname);
        }
    }

    @Override
    public boolean verify(String host, SSLSession session) {
        if (whitelist.contains(host)) {
            return true;
        }
        // important: use default verifier for all other hosts
        return defaultHostnameVerifier.verify(host, session);
    }
}
