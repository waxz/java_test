
public class HelloJNI {
    static {
        System.loadLibrary("HelloJNI");
    }

    public native void sayHello();

    public static void main(String[] args) {
        new HelloJNI().sayHello();
    }
}