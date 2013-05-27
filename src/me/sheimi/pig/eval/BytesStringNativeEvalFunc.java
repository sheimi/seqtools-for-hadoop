package me.sheimi.pig.eval;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;

public class BytesStringNativeEvalFunc extends EvalFunc<String> {

	static {
		System.out.println(System.getProperty("java.library.path"));
		String lib = "cvjni_byte_in";
		System.out.println("loading native lib" + lib);
		try {
			System.loadLibrary(lib);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String exec(Tuple input) {

		// check if input is valid
		if (input == null || input.size() == 0)
			return null;

		byte[] bytesin;
		String handlerPath;
		String funcName;

		try {
			// get the input byte array
			bytesin = ((DataByteArray) input.get(0)).get();
			handlerPath = (String) input.get(1);
			funcName = (String) input.get(2);
			// invoke native lib
			System.out.println("invoking start");
			String out = invokeNative(handlerPath, funcName, bytesin);
			System.out.println("invoking end");
			return out;
		} catch (ExecException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static native String invokeNative(String dlpath, String funcname,
			byte[] imageSource);

}
