package me.sheimi.pig.eval;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;

public abstract class BytesInputEvalFunc<T> extends EvalFunc<T> {
	
	@Override
	public T exec(Tuple input) {
		if (input == null || input.size() == 0)
			return null;
		
    byte[] bytesin;
    try {
      bytesin = ((DataByteArray) input.get(0)).get();
      return exec_func(bytesin);
    } catch (ExecException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
		return null;
	}

  public abstract T exec_func(byte[] input); 
}
