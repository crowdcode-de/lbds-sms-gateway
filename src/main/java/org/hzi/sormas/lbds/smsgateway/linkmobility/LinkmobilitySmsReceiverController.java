package org.hzi.sormas.lbds.smsgateway.linkmobility;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hzi.sormas.lbds.smsgateway.model.LbdsSms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hzi.sormas.lbds.smsgateway.LbdsMessageAcceptor;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
@Profile({"link-mobility"})
public class LinkmobilitySmsReceiverController {

    private final LbdsMessageAcceptor lbdsMessageAcceptor;

    private final LinkMobilityGatewayService linkMobilityGatewayService;

    @Value("${logging.payload}")
    private final boolean loggingpayload = false;

    @GetMapping("/smsreceive")
    public ResponseEntity receiveSms(LinkedMobilitySms linkedMobilitySms) throws IOException {

        try {
            //List<LbdsSms> response = linkMobilityGatewayService.receiveSMSResponse(servletRequest);
            log.debug(linkedMobilitySms.getBody() + " " + linkedMobilitySms.getFrom() + " " + linkedMobilitySms.getTo());
            LbdsSms lbdsSms = linkMobilityGatewayService.mapLinkMobiltySmsToLbdsSms(linkedMobilitySms);

            lbdsMessageAcceptor.acceptMessage(lbdsSms);
            //         FIXME implement logging for notification (DONE) and think about what kind of task have to be done.
        } catch (Exception e) {
            // TODO FAIL
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/smsreceive")
    public ResponseEntity receivePostSms(LinkedMobilitySms linkedMobilitySms) throws IOException {

        //List<LbdsSms> response = linkMobilityGatewayService.receiveSMSResponse(servletRequest);
        log.debug(linkedMobilitySms.getBody() + " " + linkedMobilitySms.getFrom() + " " + linkedMobilitySms.getTo());
        if (linkedMobilitySms.isSMS()) {
            LbdsSms lbdsSms = linkMobilityGatewayService.mapLinkMobiltySmsToLbdsSms(linkedMobilitySms);
            lbdsMessageAcceptor.acceptMessage(lbdsSms);
        } else {
            //FIXME implement logging for notification and think about what kind of task have to be done.
        }

        return ResponseEntity.ok().build();
    }

}
