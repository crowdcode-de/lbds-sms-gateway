package model;

import java.net.URI;


public class LbdsSms {

    private URI uri;

    private int returnValue;

    private String user;

    private String password;

    private String from;

    private String to;

    private String body;

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


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }


    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }
}
