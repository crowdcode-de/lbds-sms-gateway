package org.hzi.sormas.lbds.smsgateway;

import org.hzi.sormas.lbds.smsgateway.model.LbdsSms;

/**
 * @author M. Noerder-Tuitje (CROWDCODE)
 */
public interface LbdsMessageAcceptor {

    void acceptMessage(LbdsSms lbdsSms);
}
