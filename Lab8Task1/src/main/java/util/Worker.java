package util;

import javax.xml.soap.*;

public class Worker {
    public static SOAPMessage createMessage(String headerVal, String msgValue) throws SOAPException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMsg = factory.createMessage();
        SOAPPart part = soapMsg.getSOAPPart();

        SOAPEnvelope envelope = part.getEnvelope();
        SOAPHeader header = envelope.getHeader();
        SOAPBody body = envelope.getBody();

        header.setValue(headerVal);

        SOAPBodyElement element =
                body.addBodyElement(
                        envelope.createName(
                                "JAVA",
                                "prefix",
                                "val"
                        ));
        element.setValue(msgValue);

        return soapMsg;
    }
}
