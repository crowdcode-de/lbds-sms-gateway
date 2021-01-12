package model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.URI;

@Data
public class LinkedMobilitySms {

    private String udh;

    private String dcs;

    private String from_ton;

    private String to_ton;

    private String not;

    private String original_id;

    private String scts;

    private String org_operator_nwc;

    private String org_provider_id;

    private String op_rsn;

    private String op_dst;

    private String sms_block_seq;

    private String sms_block_max;

    private String operator_nwc;

    private URI uri;

    private int returnValue;

    private String user;

    private String password;

    private String from;

    private String to;

    private String body;

    private String id;

    public LinkedMobilitySms() {
    }

    public LinkedMobilitySms(int returnValue) {
        this.returnValue = returnValue;
    }

    public LinkedMobilitySms(URI uri) {
        this.uri = uri;
    }

    public LinkedMobilitySms(URI uri, int returnValue) {
        this.uri = uri;
        this.returnValue = returnValue;
    }

    public boolean isSMS() {
        return this.getNot()!=null && !this.getNot().equals(1);
    }
}
