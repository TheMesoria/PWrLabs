public class Main {
    static
    {
        System.setProperty("java.library.path","/home/black/Work/lab/Lab11Task2/lib/");
        System.loadLibrary("Func");
    }

    public native void hello();

    public static void main(String[] args) {
        Main main = new Main();
        main.hello();
    }
}
