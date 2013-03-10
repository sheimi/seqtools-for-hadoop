import java.io.*;
public class Test {

  static {
    //String lib = "/tmp/sheimi-java-lib/libcvjni.jnilib";
    //File f = new File(lib);
    try {
      //System.load(f.getAbsolutePath());
      System.loadLibrary("cvjni");
    } catch (Exception e) {
      e.printStackTrace();
    }   
  }

  public static native byte[] invokeNative(String dlpath, String funcname,
            byte[] imageSource);

  public static void main(String [] args) {
    invokeNative(null, null, null);
  }

}
