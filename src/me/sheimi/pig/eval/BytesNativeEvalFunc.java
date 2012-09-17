package me.sheimi.pig.eval;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

public class BytesNativeEvalFunc extends EvalFunc<DataByteArray> {

  static {
    String lib = "cvjni";
    System.out.println("loading native lib" + lib);
    try {
      System.loadLibrary(lib);
    } catch (Exception e) {
      e.printStackTrace();
    }   
  }

	@Override
	public DataByteArray exec(Tuple input) {

    //check if input is valid
		if (input == null || input.size() == 0)
			return null;
		
    byte[] bytesin;
    try {
      // get the input byte array
      bytesin = ((DataByteArray) input.get(0)).get();
      // invoke native lib
      System.out.println("invoking start");
      byte[] byteout = invokeNative(bytesin);
      System.out.println("invoking end");
      // set output
      DataByteArray dba = new DataByteArray(byteout);
      return dba;
    } catch (ExecException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
		return null;
	}

  public static native byte[] invokeNative(byte[] imageSource);

}
