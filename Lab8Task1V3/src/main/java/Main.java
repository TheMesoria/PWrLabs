import util.MessageWrapper;
import util.Worker;

import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import java.sql.Wrapper;

public class Main {
    public static void main(String[] args) {
        try
        {
            MessageWrapper mw = new MessageWrapper(
                    "TEST",
                    "NO-NAME",
                    "NO-SOURCE",
                    "Asasdhjkkjasdh123",
                    "WORKS"
            );
            System.out.println(mw);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
