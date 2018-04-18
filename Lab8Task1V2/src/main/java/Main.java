import util.Worker;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SOAPMessage soapMessage = Worker.createMessage(
                    "TEST",
                    "IP: 10.1.144.10",
                    "TARGET: lel");
            soapMessage.writeTo(System.out);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
