package me.sheimi.pig.eval.demo;

import me.sheimi.pig.eval.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.hadoop.io.Text;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

public class ToBMP2 extends BytesInputEvalFunc<DataByteArray> {
	
	@Override
	public DataByteArray exec_func(byte[] input) {
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(input); 
      BufferedImage img = ImageIO.read(bais);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(img, "bmp", baos);
      byte[] byteout = baos.toByteArray();
      DataByteArray dba = new DataByteArray(byteout);
      return dba;
    } catch (ExecException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
		return null;
	}

}
