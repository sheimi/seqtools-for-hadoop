package me.sheimi.pig.eval;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;

public class BytesNativeEvalFunc extends EvalFunc<DataByteArray> {

	static {
		System.out.println(System.getProperty("java.library.path"));
		String lib = "cvjni";
		// String lib = "/tmp/sheimi-java-lib/libcvjni.jnilib";
		System.out.println("loading native lib" + lib);
		// File f = new File(lib);
		try {
			// System.load(f.getAbsolutePath());
			System.loadLibrary(lib);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DataByteArray exec(Tuple input) {

		// check if input is valid
		if (input == null || input.size() == 0)
			return null;

		byte[] bytesin;
		String handlePath;
		String funcName;

		try {
			// get the input byte array
			bytesin = ((DataByteArray) input.get(0)).get();
			handlePath = (String)input.get(1);
			funcName = (String)input.get(2);
			// invoke native lib
			System.out.println("invoking start");
			byte[] byteout = invokeNative(handlePath, funcName, bytesin);
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

	public static native byte[] invokeNative(String dlpath, String funcname,
			byte[] imageSource);

}
