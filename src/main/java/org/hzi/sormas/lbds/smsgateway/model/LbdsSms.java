package org.hzi.sormas.lbds.smsgateway.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.net.URI;

@Data
@Accessors(chain = true)
public class LbdsSms {

    private URI uri;

    private int returnValue;

    private String user;

    private String password;

    private String from;

    private String to;

    private String body;

    private String id;

    public LbdsSms() {
    }

    public LbdsSms(int returnValue) {
        this.returnValue = returnValue;
    }

    public LbdsSms(URI uri) {
        this.uri = uri;
    }

    public LbdsSms(URI uri, int returnValue) {
        this.uri = uri;
        this.returnValue = returnValue;
    }



}
