package util;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

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
    public static SOAPMessage createMessage(String headerVal, String... msgValues) throws SOAPException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMsg = factory.createMessage();
        SOAPPart part = soapMsg.getSOAPPart();

        SOAPEnvelope envelope = part.getEnvelope();
        SOAPHeader header = envelope.getHeader();
        SOAPBody body = envelope.getBody();

        header.setValue(headerVal);

        for(String msgValue : msgValues)
        {
            SOAPBodyElement element =
                    body.addBodyElement(
                            envelope.createName(
                                    "JAVA",
                                    "prefix",
                                    "val:"+msgValue
                            ));
            element.setValue(msgValue);
        }

        return soapMsg;
    }
    public static SOAPMessage getSoapMessageFromString(String xml) throws IOException, SOAPException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
        return message;
    }
    public static void sendMessage(SOAPMessage msg, ObjectOutputStream out) throws IOException, SOAPException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        msg.writeTo(baos);
        String strMsg = new String(baos.toByteArray());
        out.writeObject(strMsg);
        out.flush();
    }
}
