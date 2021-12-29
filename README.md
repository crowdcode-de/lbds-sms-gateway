# SMS Integration for LBDS

## General Prerequisites

* you must be familiar with Spring and Bean injection
* your components must be located within on sibling package to the existing implementation
* you should be familiar with Spring Configuration

## General Procedure

To have an integration of another SMS Provider into LBDS Backend, two lanes have to be considered.
The outbound lane is the implementation of sending an SMS. The inbound or incoming lane is the
implementation of how to receive a SMS.

## Implementation

### The implementation of sending and receiving a SMS from a certain provider

To send a SMS, the implementor has to provide an implementation plus a configuration.

The current implementation relies on an API driven approach. This means, we assume that the backend
will make use of a HTTP endpoint to send all data. See LinkMobilityGatewayService for an example 
implementation

#### Outbound Implementation

First, implement the outbound sending component:

```
@Service
@Slf4j
public class LinkMobilityGatewayService implements SmsGatewayInterface {

```

#### Inbound Implementation

Receiving a SMS is done via callback. The provider will call our service endpoint to handover
the SMS data making use of their own data structures. Their data structure will be placed into
the implementation package. See LinkMobilitySms for an example. Since this is an endpoint activated
from outside and being enabled by configuration you will have all freedom you need to unmarshal the
providers message; there is no specifiv interface from LBDS side necessary to be implemented.

When receiving the SMS, the primary function of the controller is to transform it into a LbdsSms object
(see model/LbdsSms.java for details).

```

@Slf4j
@RestController
@RequiredArgsConstructor
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
            log.debug("SUCCESSFULLY PROCESSED | "+linkedMobilitySms.getBody() + " " + linkedMobilitySms.getFrom() + " " + linkedMobilitySms.getTo());

        } catch (Exception e) {
            log.error("FAILED TO PROCESS | "+linkedMobilitySms.getBody() + " " + linkedMobilitySms.getFrom() + " " + linkedMobilitySms.getTo());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
    
```
Your component has to map the message to a LbdsSms object and hand it over to the message acceptor.
This is a component injected from LBDS Backend. From there, LBDS will take care of the message processing.

#### Configuration

A LBDS backend only can run one provider at the same time since there is no routing information (which message would
go to which provider) present in the system. It will be configured using Spring configuration

```
@Configuration
@ComponentScan
@ConditionalOnProperty(prefix="sormas.sms", name = "provider", havingValue = "link-mobility")
public class LinkMobilityConfig {


}
```

To enable your implementation, after the new version of the sms gateway library has been added to the
service, the only thing you need to do is enabling it inside the configuration

```
sormas.sms.provider=link-mobility
```

#### Example Configuration

To have my-custom-provider enabled, your config would look like this

```
@Configuration
@ComponentScan
@ConditionalOnProperty(prefix="sormas.sms", name = "provider", havingValue = "my-custom-provider")
public class MyCustomProviderConfig {


}
```
and the lbds backend config would have this

```
sormas.sms.provider=my-custom-provider
```

What happens here is:

* The ConditionalOnProperty will match your my-custom-provider
* This will enable a ComponentScan on your package and Gateway plus ReceiverController will be found
* The components will be instantiated by LBDS and injected into the containers
* Also, the LbdsMessageAcceptor Implementation will be injected into your receiver intertwining both
