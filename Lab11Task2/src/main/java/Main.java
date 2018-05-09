public class Main {
    static
    {
        System.setProperty("java.library.path","/home/black/Work/lab/Lab11Task2/lib/");
        System.loadLibrary("library");
    }

    public native void hello();

    public static void main(String[] args) {

    }
}
