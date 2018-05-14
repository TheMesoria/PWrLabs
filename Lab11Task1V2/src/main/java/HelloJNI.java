public class HelloJNI {
    static {
        System.loadLibrary("hello"); // Load native library at runtime
        // hello.dll (Windows) or libhello.so (Unixes)
    }

    // Declare a native method sayHello() that receives nothing and returns void
    private native void sayHello();
    private native boolean isPrime(int number);
    private native float[] forEachElement(float []arr, float val, String op);

    // Test Driver
    public static void main(String[] args) {
        new HelloJNI().sayHello();
        System.out.println(new HelloJNI().isPrime(30));
        float[] arr = {10,20,30};
        new HelloJNI().forEachElement(arr,10,"add");

        System.out.println(arr.length);
        for(float val : arr)
        {
            System.out.println(val);
        }
    }
}
