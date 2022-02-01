package org.hzi.sormas.lbds.smsgateway.linkmobility;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
/**
 * @author M. Noerder-Tuitje (CROWDCODE)
 */
@Configuration
@ComponentScan
@ConditionalOnProperty(prefix="sormas.sms", name = "provider", havingValue = "link-mobility")
public class LinkMobilityConfig {


}
