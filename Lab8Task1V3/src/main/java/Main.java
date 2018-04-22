import servlet.Servlet;
import servlet.ServletFactory;

public class Main {

    public static void main(String[] args) {
        try {
            Servlet servlet = ServletFactory.createServlet(args);
            servlet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
