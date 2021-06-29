package chat.delta.java;

public class hello {
  public native void sayHello(int length) ;
  public static void main (String args[]) {
    String str = "I am a good boy" ;
    hello h = new hello () ;
    h.sayHello (str.length() ) ;
  }
  static {
    System.loadLibrary ( "hello" ) ;
  }
}
