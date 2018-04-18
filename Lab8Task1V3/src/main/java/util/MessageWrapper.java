package util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MessageWrapper {
    private String type;
    private String target;
    private String source;
    private String id;
    private String msg;
    private ArrayList<String> stringArrayList=new ArrayList<>();

    public MessageWrapper(@NotNull SOAPMessage msg) throws Exception
    {
        Iterator iterator = msg.getSOAPHeader().getChildElements();

        type        = ((SOAPHeaderElement) iterator.next()).getValue();
        target      = ((SOAPHeaderElement) iterator.next()).getValue();
        source      = ((SOAPHeaderElement) iterator.next()).getValue();
        id          = ((SOAPHeaderElement) iterator.next()).getValue();

        while(iterator.hasNext())
        {
            SOAPHeaderElement headerElement = (SOAPHeaderElement) iterator.next();
            stringArrayList.add(headerElement.getValue());
        }

        //MSG is required to be received
        this.msg = msg.getSOAPBody().getChildElements().next().getValue();
    }

    public MessageWrapper(
            @NotNull String type,
            @NotNull String target,
            @NotNull String source,
            @Nullable String msg,
            @Nullable String id
            )
    {
        this.type = type;
        this.target = target;
        this.source = source;
        this.id = id;
        this.msg = msg==null?msg:"";
    }

    public SOAPMessage getMessage() throws Exception
    {
        return Worker.createMessage(msg,type,target,source,id);
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "type='" + type + '\'' +
                ", target='" + target + '\'' +
                ", source='" + source + '\'' +
                ", id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", stringArrayList=" + stringArrayList +
                '}';
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void generateId(){this.id=new Random().doubles()}
}
